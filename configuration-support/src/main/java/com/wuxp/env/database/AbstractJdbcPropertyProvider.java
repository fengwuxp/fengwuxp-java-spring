package com.wuxp.env.database;

import com.wuxp.env.AbstractPropertyProvider;
import com.wuxp.env.util.SpringFactoriesLoaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 抽象的jdbc 配置加载者，使用者必须实现 并且在spring.factories中配置
 * example：
 * <code>
 * com.wuxp.env.database.PropertyProvider=\
 * com.xxx.MyJdbcPropertyProvider
 * </code>
 * or 或者在配置文件中配置
 * <code>
 * wuxp.env.providers=com.xxx.MyJdbcPropertyProvider
 * </code>
 *
 * @author wuxp
 */
@Slf4j
public abstract class AbstractJdbcPropertyProvider extends AbstractPropertyProvider<PropertiesPropertySource, DataSourceResource> {


    protected JdbcOperations jdbcOperations;

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private DataSourceResource dataSourceResource;

    @Override
    public List<PropertiesPropertySource> load(DataSourceResource resource, String[] activeProfiles) {
        this.init(resource);
        Assert.notNull(jdbcOperations, "JdbcOperations must not null");
        Map<String, Properties> map = this.load(activeProfiles);
        List<PropertiesPropertySource> sources = new ArrayList<>(map.size());
        map.forEach((name, properties) -> sources.add(new PropertiesPropertySource(name, properties)));
        return sources;
    }

    @Override
    public DataSourceResource resource() {
        if (this.dataSourceResource == null) {
            DataSource dataSource = this.loadDataSource();
            this.dataSourceResource = new DataSourceResource(dataSource);
        }
        return this.dataSourceResource;
    }

    @Override
    public void refresh() {
        synchronized (this) {
            this.destroyDataSource();
        }
        DataSource dataSource = this.applicationContext.getBean(DataSource.class);
        this.dataSourceResource = new DataSourceResource(dataSource);
        this.initialized.compareAndSet(true, false);
    }

    /**
     * 从数据库加载 配置
     * key：配置源名称
     * value 配置源数据
     *
     * @param activeProfiles 激活的Profile {@link org.springframework.core.env.Environment#getActiveProfiles}
     * @return 多组配置，不能为null
     */
    protected abstract Map<String, Properties> load(String[] activeProfiles);

    /**
     * 销毁数据源
     */
    protected abstract void destroyDataSource();

    protected void init(DataSourceResource resource) {
        if (initialized.get()) {
            return;
        }
        initialized.compareAndSet(false, true);
        this.jdbcOperations = new JdbcTemplate(resource.getDataSource());
    }

    private DataSource loadDataSource() {
        ConfigurableEnvironment environment = (ConfigurableEnvironment) this.environment;
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        ConfigurationPropertiesBindingPostProcessor propertiesBindingPostProcessor = applicationContext.getBean(ConfigurationPropertiesBindingPostProcessor.class);
        try {
            propertiesBindingPostProcessor.postProcessBeforeInitialization(dataSourceProperties, DataSourceProperties.class.getName());
        } catch (BeansException e) {
            e.printStackTrace();
            log.warn("初始化DataSourceProperties失败", e);
            dataSourceProperties = null;
        }

        for (DataSourceProvider dataSourceProvider : loadDataSourceProviders()) {
            return dataSourceProvider.dataSource(environment, dataSourceProperties);
        }
        Assert.notNull(dataSourceProperties, "DataSourceProperties  must not null");
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    private List<DataSourceProvider> loadDataSourceProviders() {
        String dataSourceProvider = this.environmentProviderProperties.getDataSourceProvider();
        if (StringUtils.hasText(dataSourceProvider)) {
            return Stream.of(dataSourceProvider)
                    .map(className -> SpringFactoriesLoaderUtils.instantiateFactory(className, DataSourceProvider.class, getClass().getClassLoader()))
                    .collect(Collectors.toList());
        }
        return SpringFactoriesLoader.loadFactories(DataSourceProvider.class, getClass().getClassLoader());
    }

}

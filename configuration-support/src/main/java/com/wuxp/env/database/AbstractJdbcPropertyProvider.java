package com.wuxp.env.database;

import com.wuxp.env.PropertyProvider;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 抽象的jdbc 配置加载者，使用者必须实现 并且在spring.factories中配置
 * example：
 * <code>
 * com.wuxp.env.database.AbstractJdbcPropertyProvider=\
 * com.xxx.MyJdbcPropertyProvider
 * </code>
 * or 或者在配置文件中配置
 * <code>
 *  wuxp.env.providers.jdbc=com.xxx.MyJdbcPropertyProvider
 * </code>
 *
 * @author wuxp
 */
public abstract class AbstractJdbcPropertyProvider implements PropertyProvider<DataSourceResource> {

    public static final String DATA_BASE_PROPER_SOURCE_NAME = "jdbcPropertySource";

    protected JdbcOperations jdbcOperations;

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    @Override
    public List<PropertySource<?>> load(DataSourceResource resource) {
        this.init(resource);
        Assert.notNull(jdbcOperations, "JdbcOperations must not null");
        Map<String, Properties> map = this.load(jdbcOperations);
        List<PropertySource<?>> sources = new ArrayList<>(map.size());
        map.forEach((name, properties) -> {
            sources.add(new PropertiesPropertySource(name, properties));
        });
        return sources;
    }

    /**
     * 从数据库加载 配置
     * key：配置源名称 {@link AbstractJdbcPropertyProvider#DATA_BASE_PROPER_SOURCE_NAME}
     * value 配置源数据
     *
     * @param jdbcOperations jdbc 操作模板
     * @return 多组配置，不能为null
     */
    protected abstract Map<String, Properties> load(final JdbcOperations jdbcOperations);

    protected void init(DataSourceResource resource) {
        if (initialized.get()) {
            return;
        }
        initialized.set(true);
        this.jdbcOperations = new JdbcTemplate(resource.getDataSource());

    }
}

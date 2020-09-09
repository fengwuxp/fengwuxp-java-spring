package com.wuxp.env.database;

import com.wuxp.env.EnvironmentProviderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.FileEncodingApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.boot.context.config.ConfigFileApplicationListener.DEFAULT_ORDER;

/**
 * 在{@link org.springframework.boot.context.config.ConfigFileApplicationListener#postProcessEnvironment(ConfigurableEnvironment, SpringApplication)}
 * 之后执行，此时所有的上线问一句被加了
 * 基于数据库环境加载的 配置
 *
 * @author wuxp
 */
public class DatabaseEnvironmentApplicationListener implements SmartApplicationListener, Ordered, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(FileEncodingApplicationListener.class);

    private GenericApplicationContext genericApplicationContext = new GenericApplicationContext();

    private DataSource dataSource;

    {
        genericApplicationContext.refresh();
    }

    private final int order = DEFAULT_ORDER + 1;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType)
                || ApplicationPreparedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
        }
        if (event instanceof ApplicationPreparedEvent) {
            onApplicationPreparedEvent((ApplicationPreparedEvent) event);
        }
    }


    private void onApplicationPreparedEvent(ApplicationPreparedEvent event) {

    }


    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        genericApplicationContext.setEnvironment(environment);
        ConfigurationPropertiesBindingPostProcessor.register(genericApplicationContext.getDefaultListableBeanFactory());
        ConfigurationPropertiesBindingPostProcessor propertiesBindingPostProcessor = genericApplicationContext.getBean(ConfigurationPropertiesBindingPostProcessor.class);

        EnvironmentProviderProperties environmentProviderProperties = new EnvironmentProviderProperties();
        propertiesBindingPostProcessor.postProcessBeforeInitialization(environmentProviderProperties, EnvironmentProviderProperties.class.getName());
        DataSource dataSource = this.dataSource;
        if (dataSource == null) {
            dataSource = loadDataSource(environment, environmentProviderProperties, propertiesBindingPostProcessor);
            this.dataSource = dataSource;
        }

        for (AbstractJdbcPropertyProvider propertyProvider : loadJdbcPropertyProviders(environmentProviderProperties)) {
            List<PropertySource<?>> databaseProperSources = propertyProvider.load(new DataSourceResource(dataSource));
            margeEnvironment(environment, databaseProperSources);

        }

    }

    /**
     * 合并数据库和{@link ConfigurableEnvironment}中的配置
     *
     * @param environment           上下文中的配置
     * @param databaseProperSources 数据库中的配置
     */
    private void margeEnvironment(ConfigurableEnvironment environment, List<PropertySource<?>> databaseProperSources) {
        final MutablePropertySources propertySources = environment.getPropertySources();
        for (PropertySource<?> propertySource : databaseProperSources) {
            String name = propertySource.getName();
            if (propertySources.contains(name)) {
                propertySources.replace(name, propertySource);
            } else {
                propertySources.addLast(propertySource);
            }
        }
    }

    private DataSource loadDataSource(ConfigurableEnvironment environment,
                                      EnvironmentProviderProperties environmentProviderProperties,
                                      ConfigurationPropertiesBindingPostProcessor propertiesBindingPostProcessor) {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        try {
            propertiesBindingPostProcessor.postProcessBeforeInitialization(dataSourceProperties, DataSourceProperties.class.getName());
        } catch (BeansException e) {
            e.printStackTrace();
            logger.warn("初始化DataSourceProperties失败", e);
            dataSourceProperties = null;
        }

        for (DataSourceProvider dataSourceProvider : loadDataSourceProviders(environmentProviderProperties)) {
            return dataSourceProvider.build(environment, dataSourceProperties);
        }
        Assert.notNull(dataSourceProperties, "DataSourceProperties  must not null");
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    private List<AbstractJdbcPropertyProvider> loadJdbcPropertyProviders(EnvironmentProviderProperties environmentProviderProperties) {

        String jdbcProvider = environmentProviderProperties.getJdbcProvider();
        if (StringUtils.hasText(jdbcProvider)) {
            return Stream.of(jdbcProvider)
                    .map(className -> instantiateFactory(className, AbstractJdbcPropertyProvider.class, getClass().getClassLoader()))
                    .collect(Collectors.toList());
        }

        return SpringFactoriesLoader.loadFactories(AbstractJdbcPropertyProvider.class, getClass().getClassLoader());
    }

    private List<DataSourceProvider> loadDataSourceProviders(EnvironmentProviderProperties environmentProviderProperties) {
        String dataSourceProvider = environmentProviderProperties.getDataSourceProvider();
        if (StringUtils.hasText(dataSourceProvider)) {
            return Stream.of(dataSourceProvider)
                    .map(className -> instantiateFactory(className, DataSourceProvider.class, getClass().getClassLoader()))
                    .collect(Collectors.toList());
        }
        return SpringFactoriesLoader.loadFactories(DataSourceProvider.class, getClass().getClassLoader());
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @SuppressWarnings("unchecked")
    private static <T> T instantiateFactory(String factoryImplementationName, Class<T> factoryType, ClassLoader classLoader) {
        try {
            Class<?> factoryImplementationClass = ClassUtils.forName(factoryImplementationName, classLoader);
            if (!factoryType.isAssignableFrom(factoryImplementationClass)) {
                throw new IllegalArgumentException(
                        "Class [" + factoryImplementationName + "] is not assignable to factory type [" + factoryType.getName() + "]");
            }
            return (T) ReflectionUtils.accessibleConstructor(factoryImplementationClass).newInstance();
        } catch (Throwable ex) {
            throw new IllegalArgumentException(
                    "Unable to instantiate factory class [" + factoryImplementationName + "] for factory type [" + factoryType.getName() + "]",
                    ex);
        }
    }
}

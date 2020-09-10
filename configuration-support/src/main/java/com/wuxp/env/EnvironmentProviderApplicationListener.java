package com.wuxp.env;

import com.wuxp.env.refresh.EnvironmentChangeEvent;
import com.wuxp.env.util.SpringFactoriesLoaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.boot.context.config.ConfigFileApplicationListener.DEFAULT_ORDER;

/**
 * 扩展配置加载
 * 在{@link org.springframework.boot.context.config.ConfigFileApplicationListener#postProcessEnvironment(ConfigurableEnvironment, SpringApplication)}
 *
 * @author wuxp
 * @see EnvironmentProviderApplicationListener#getOrder()
 * 之后执行，此时所有配置文件已被加载
 * <ul>
 *     <li>{@link com.wuxp.env.database.AbstractJdbcPropertyProvider}从数据库加载</li>
 * </ul>
 */
@Slf4j
public class EnvironmentProviderApplicationListener implements SmartApplicationListener, Ordered {

    private ApplicationContext applicationContext = new GenericApplicationContext();

    private List<PropertyProvider<MapPropertySource, Resource>> propertyProviders;

    private final Object lock = new Object();

    private final Map<PropertyProvider<MapPropertySource, Resource>, List<MapPropertySource>> providerSources = new LinkedHashMap<>(4);

    public EnvironmentProviderApplicationListener() {

        propertyProviders = Collections.emptyList();
    }

    {
        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
        genericApplicationContext.refresh();
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType)
                || ApplicationStartedEvent.class.isAssignableFrom(eventType)
                || EnvironmentChangeEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ApplicationStartedEvent) {
            onApplicationStartedEvent((ApplicationStartedEvent) event);
        }

        synchronized (lock) {
            if (event instanceof ApplicationEnvironmentPreparedEvent) {
                onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
            }
            if (event instanceof EnvironmentChangeEvent) {
                onApplicationEnvironmentChangeEvent((EnvironmentChangeEvent) event);
            }
        }
    }

    /**
     * spring 环境启动完成
     *
     * @param event
     */
    private void onApplicationStartedEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        this.applicationContext = applicationContext;
        for (PropertyProvider provider : this.propertyProviders) {
            provider.setApplicationContext(applicationContext);
            provider.refresh();
        }
    }

    /**
     * 通过{@link PropertyProvider}加载配置
     *
     * @param event 准备环境的事件
     */
    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {

        ConfigurableEnvironment environment = event.getEnvironment();
        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
        genericApplicationContext.setEnvironment(environment);

        ConfigurationPropertiesBindingPostProcessor.register(genericApplicationContext.getDefaultListableBeanFactory());
        ConfigurationPropertiesBindingPostProcessor propertiesBindingPostProcessor = genericApplicationContext.getBean(ConfigurationPropertiesBindingPostProcessor.class);
        EnvironmentProviderProperties environmentProviderProperties = new EnvironmentProviderProperties();
        propertiesBindingPostProcessor.postProcessBeforeInitialization(environmentProviderProperties, EnvironmentProviderProperties.class.getName());
        String[] activeProfiles = environment.getActiveProfiles();
        Map<PropertyProvider<MapPropertySource, Resource>, List<MapPropertySource>> providerSources = this.providerSources;
        for (PropertyProvider<MapPropertySource, Resource> propertyProvider : loadPropertyProviders(environmentProviderProperties.getProviders())) {
            propertyProvider.setApplicationContext(applicationContext);
            propertyProvider.setEnvironment(environment);
            propertyProvider.setEnvironmentProviderProperties(environmentProviderProperties);
            List<MapPropertySource> propertySources = propertyProvider.load(propertyProvider.resource(), activeProfiles);
            margeEnvironment(environment, propertySources);
            providerSources.put(propertyProvider, propertySources);
        }

    }

    private void onApplicationEnvironmentChangeEvent(EnvironmentChangeEvent event) {

    }

    /**
     * 合并数据库和{@link ConfigurableEnvironment}中的配置
     *
     * @param environment           上下文中的配置
     * @param databaseProperSources 数据库中的配置
     */
    private void margeEnvironment(ConfigurableEnvironment environment, List<MapPropertySource> databaseProperSources) {
        final MutablePropertySources propertySources = environment.getPropertySources();
        for (MapPropertySource propertySource : databaseProperSources) {
            String name = propertySource.getName();
            if (propertySources.contains(name)) {
                propertySources.replace(name, propertySource);
            } else {
                propertySources.addLast(propertySource);
            }
        }
    }


    /**
     * 从类路径加载
     *
     * @param providerClassNames {@link PropertyProvider}类名集合
     * @return providers
     */
    private List<PropertyProvider<MapPropertySource, Resource>> loadPropertyProviders(List<String> providerClassNames) {
        if (this.propertyProviders != null) {
            return this.propertyProviders;
        }
        if (providerClassNames == null || providerClassNames.isEmpty()) {
            this.propertyProviders = SpringFactoriesLoader
                    .loadFactories(PropertyProvider.class, getClass().getClassLoader())
                    .stream()
                    .map(propertyProvider -> (PropertyProvider<MapPropertySource, Resource>) propertyProvider)
                    .collect(Collectors.toList());
        } else {
            this.propertyProviders = providerClassNames
                    .stream()
                    .map(className -> SpringFactoriesLoaderUtils.instantiateFactory(className, PropertyProvider.class, getClass().getClassLoader()))
                    .map(propertyProvider -> (PropertyProvider<MapPropertySource, Resource>) propertyProvider)
                    .collect(Collectors.toList());
        }
        return this.propertyProviders;
    }


    @Override
    public int getOrder() {
        return DEFAULT_ORDER + 1;
    }


}

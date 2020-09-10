package com.wuxp.env;

import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.Resource;

/**
 * @author wuxp
 */
@Setter
public abstract class AbstractPropertyProvider<T extends MapPropertySource, R extends Resource> implements PropertyProvider<T, R> {

    protected ApplicationContext applicationContext;

    protected Environment environment;

    protected EnvironmentProviderProperties environmentProviderProperties;
}

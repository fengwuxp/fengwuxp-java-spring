package com.wuxp.env;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * 环境配置提供者的Properties
 *
 * @author wuxp
 */
@ConfigurationProperties(prefix = EnvironmentProviderProperties.PREFIX)
@Data
public class EnvironmentProviderProperties {

    public static final String PREFIX = "wuxp.env";

    /**
     * {@link com.wuxp.env.PropertyProvider}的实现类的全限定名称
     */
    private List<String> providers;

    /**
     * {@link com.wuxp.env.database.DataSourceProvider}的实现类的全限定名称
     */
    private String dataSourceProvider;

}

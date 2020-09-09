package com.wuxp.env;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 环境配置提供者的Properties
 *
 * @author wuxp
 */
@ConfigurationProperties(prefix = EnvironmentProviderProperties.PREFIX)
public class EnvironmentProviderProperties {

    public static final String PREFIX = "wuxp.env";

    /**
     * {@link com.wuxp.env.database.AbstractJdbcPropertyProvider}的实现类的全限定名称
     */
    private String jdbcProvider;

    /**
     * {@link com.wuxp.env.database.DataSourceProvider}的实现类的全限定名称
     */
    private String dataSourceProvider;

    public String getJdbcProvider() {
        return jdbcProvider;
    }

    public void setJdbcProvider(String jdbcProvider) {
        this.jdbcProvider = jdbcProvider;
    }

    public String getDataSourceProvider() {
        return dataSourceProvider;
    }

    public void setDataSourceProvider(String dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }
}

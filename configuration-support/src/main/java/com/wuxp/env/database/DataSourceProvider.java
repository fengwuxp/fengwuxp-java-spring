package com.wuxp.env.database;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;

/**
 * 数据源Builder
 *
 * @author wuxp
 */
public interface DataSourceProvider {


    /**
     * 自定义方式构建数据源
     *
     * @param environment          上下文环境，此刻所有的配置文件已被加载
     * @param dataSourceProperties 数据源配置，可能为空
     * @return 数据源
     */
    DataSource dataSource(Environment environment, @Nullable DataSourceProperties dataSourceProperties);
}

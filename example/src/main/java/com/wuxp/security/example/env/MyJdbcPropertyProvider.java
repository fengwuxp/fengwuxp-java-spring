package com.wuxp.security.example.env;

import com.wuxp.env.database.AbstractJdbcPropertyProvider;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.*;

/**
 * @author wuxp
 */
public class MyJdbcPropertyProvider extends AbstractJdbcPropertyProvider {

    @Override
    protected Map<String, Properties> load(JdbcOperations jdbcOperations) {

        // TODO 使用 JdbcOperations 去数据库中查询配置
        Map<String, Properties> result = new LinkedHashMap<>();
        for (String name : Collections.singletonList(DATA_BASE_PROPER_SOURCE_NAME)) {
            Properties properties = new Properties();
            properties.setProperty("wuxp.db.enabled", "true");
            result.put(name, properties);
        }
        return result;
    }
}

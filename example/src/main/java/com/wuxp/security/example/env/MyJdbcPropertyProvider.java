package com.wuxp.security.example.env;

import com.wuxp.env.database.AbstractJdbcPropertyProvider;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.*;

/**
 * @author wuxp
 */
public class MyJdbcPropertyProvider extends AbstractJdbcPropertyProvider {

    @Override
    protected Map<String, Properties> load(String[] activeProfiles) {
        // TODO 使用 JdbcOperations 去数据库中查询配置
        Map<String, Properties> result = new LinkedHashMap<>();
        for (String name : Collections.singletonList("jdbc-config")) {
            Properties properties = new Properties();
            properties.setProperty("wuxp.db.enabled", "true");
            result.put(name, properties);
        }
        return result;
    }

    @Override
    protected void destroyDataSource() {

    }

    @Override
    public List<PropertiesPropertySource> loadByName(Map<String, Set<String>> names) {
        return null;
    }

    @Override
    public Map<String, Map<String, String>> loadKeys(Set<String> keys) {
        return null;
    }
}

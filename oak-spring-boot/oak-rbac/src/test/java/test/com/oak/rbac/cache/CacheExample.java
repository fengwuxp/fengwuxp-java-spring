package test.com.oak.rbac.cache;

import org.springframework.cache.annotation.Cacheable;

public interface CacheExample {

    String EXAMPLE_CACHE_NAME = "EXAMPLE_CACHE";

//    @Cacheable(value =EXAMPLE_CACHE_NAME, key = "#key", condition = "#key!=null")
    String getValue(String key);

    String getValueByCache(String key);
}

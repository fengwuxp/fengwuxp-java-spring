package test.com.oak.rbac.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static test.com.oak.rbac.cache.CacheExample.EXAMPLE_CACHE_NAME;

@Service
@Slf4j
public class CacheExampleImpl implements CacheExample {

    @Cacheable(value =EXAMPLE_CACHE_NAME, key = "#key", condition = "#key!=null")
    @Override
    public String getValue(String key) {
        String value = RandomStringUtils.randomAlphabetic(32);
        log.info("key = {} , value = {}", key, value);
        return value;
    }

    @Override
    public String getValueByCache(String key) {
        return this.getValue(key);
    }
}

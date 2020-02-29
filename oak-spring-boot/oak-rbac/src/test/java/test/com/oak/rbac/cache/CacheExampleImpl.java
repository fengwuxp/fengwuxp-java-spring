package test.com.oak.rbac.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheExampleImpl implements CacheExample {

    @Cacheable(value =EXAMPLE_CACHE_NAME, key = "#key", condition = "#formCache")
    @Override
    public String getValue(String key,boolean formCache) {
        String value = RandomStringUtils.randomAlphabetic(32);
        log.info("key = {} , value = {}", key, value);
        return value;
    }

    @Override
    public String getValueByCache(String key) {
        return getValue(key,true);
    }
}


/**
 *   class CacheExampleImpl$ClIBProxy extends CacheExampleImpl{
 *
 *       public String getValue(){
 *          // 是否缓存中
 *
 *         String value  super.getValue();
 *         // 加入缓存
 *         return value
 *       }
 *
 *   }
 *
 *
 *
 */

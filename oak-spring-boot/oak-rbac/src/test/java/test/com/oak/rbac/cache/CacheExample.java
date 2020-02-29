package test.com.oak.rbac.cache;

public interface CacheExample {

    String EXAMPLE_CACHE_NAME = "EXAMPLE_CACHE";

//    @Cacheable(value =EXAMPLE_CACHE_NAME, key = "#key", condition = "#key!=null")
    String getValue(String key,boolean formCache);

    String getValueByCache(String key);
}

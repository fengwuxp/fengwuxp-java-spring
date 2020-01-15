package com.oak.api.helper;

import com.oak.api.services.system.SystemService;
import com.oak.api.services.system.info.SettingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

import static com.oak.api.services.system.SystemService.CONFIG_CACHE_NAME;

/**
 * 获取数据库中setting的配置
 */
@Slf4j
@Component
public class SettingValueHelper {

    private SystemService systemService;

    private Environment env;

    private PropertySourcesPlaceholderConfigurer bootConfigurer;

    private FormattingConversionService formattingConversionService;

    @Autowired
    public SettingValueHelper(SystemService systemService,
                              Environment env,
                              PropertySourcesPlaceholderConfigurer bootConfigurer,
                              FormattingConversionService formattingConversionService) {

        this.systemService = systemService;
        this.env = env;
        this.bootConfigurer = bootConfigurer;
        formattingConversionService.addFormatter(new DateFormatter());
        this.formattingConversionService = formattingConversionService;
    }


    public String getValue(String key, String defaultValue) {

        SettingInfo settingInfo = systemService.findSettingByName(key);

        if (settingInfo != null) {
            return settingInfo.getValue();
        }

        if (env != null) {
            String value = env.getProperty(key);
            if (value != null && !value.isEmpty()) {
                return value;
            }
        }

        if (bootConfigurer != null) {
            PropertySource<?> propertySource = bootConfigurer.getAppliedPropertySources().get("environmentProperties");
            if (propertySource != null) {
                Object valueObj = propertySource.getProperty(key);
                if (valueObj != null) {
                    return valueObj.toString();
                }
            }
        }
        return defaultValue;
    }


    /**
     * 从配置表，env 和配置文件中或者
     *
     * @param key
     * @param targetType
     * @param defaultValue
     * @param <T>
     * @return
     */
    @Cacheable(value = CONFIG_CACHE_NAME, key = "#key", condition = "#key!=null", unless = "#result!=null")
    public <T> T getValue(String key, Class<T> targetType, T defaultValue) {
        String value = this.getValue(key, null);
        return this.convertValue(value, targetType, defaultValue);
    }

    /**
     * 从配置表中获取
     *
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
     * @see com.oak.api.entities.system.Setting
     */
    @Cacheable(value = CONFIG_CACHE_NAME, key = "#key", condition = "#key!=null", unless = "#result!=null")
    public <T> T getSettingValue(String key, T defaultValue) {
        SettingInfo settingInfo = systemService.findSettingByName(key);
        if (settingInfo == null) {
            return null;
        }
        Class<?> targetType = null;

        switch (settingInfo.getType()) {
            case DECIMAL:
                targetType = BigDecimal.class;
                break;
            case DATE:
            case DATETIME:
                targetType = Date.class;
                break;
            case INT:
                targetType = Integer.class;
                break;
            case BOOLEAN:
                targetType = Boolean.class;
                break;
            case ARRAY:
                String[] strings = new String[0];
                targetType = strings.getClass();
                break;
            case ARRAY_NUMBER:
                Number[] numbers = new Number[0];
                targetType = numbers.getClass();
                break;
            default:
                return (T) settingInfo.getValue();
        }

        String value = settingInfo.getValue();
        return this.convertValue(value, (Class<T>) targetType, defaultValue);
    }

    private <T> T convertValue(String value, Class<T> targetType, T defaultValue) {
        if (value == null && defaultValue != null) {
            return defaultValue;
        }

        return this.formattingConversionService.convert(value, targetType);
    }
}

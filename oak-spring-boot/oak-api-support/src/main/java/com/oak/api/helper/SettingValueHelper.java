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

import java.math.BigDecimal;
import java.util.Date;

/**
 * 获取数据库中setting的配置
 */
@Slf4j
public final class SettingValueHelper {

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
     * 从配置表中获取
     *
     * @param key
     * @param targetType
     * @param defaultValue
     * @param <T>
     * @return
     * @see com.oak.api.entities.system.Setting
     */
    @Cacheable()
    public <T> T getValue(String key, Class<T> targetType, T defaultValue) {

        String value = this.getValue(key, null);

        if (value == null && defaultValue != null) {
            return defaultValue;
        }

        return this.formattingConversionService.convert(value, targetType);

    }

}

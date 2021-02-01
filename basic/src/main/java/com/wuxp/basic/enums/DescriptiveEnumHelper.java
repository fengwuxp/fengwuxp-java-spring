package com.wuxp.basic.enums;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 可描述枚举的的帮助者，用于获取枚举注解上的值
 *
 * @author wxup
 * @create 2018-06-08 22:39
 **/
public final class DescriptiveEnumHelper {

    /**
     * @key enum value
     * @value enum filed desc
     */
    private static final Map<Enum<?>, String> ENUM_DESC_MAP = new ConcurrentReferenceHashMap<>(128);

    /**
     * @key enum filed desc
     * @value enum value
     */
    private static final Map<String, Enum<?>> DESC_ENUM_MAP = new ConcurrentReferenceHashMap<>(128);


    private DescriptiveEnumHelper() {
    }

    /**
     * 获取枚举的描述信息
     *
     * @param e 枚举对象实例
     * @return 枚举描述
     */
    public static String getEnumDesc(final Enum<?> e) {
        return ENUM_DESC_MAP.computeIfAbsent(e, key -> DescriptiveEnumHelper.putEnum(e));
    }

    /**
     * 通过描述获取枚举的值
     *
     * @param desc      枚举描述
     * @param enumClass 枚举类类型
     * @param <T>       类类型
     * @return 枚举的实例
     */
    public static <T extends Enum<T>> T getValueByDesc(String desc, Class<T> enumClass) {

        T[] enumConstants = enumClass.getEnumConstants();
        String key = MessageFormat.format("{0}_{1}", desc, enumClass.getName());
        T result = (T) DESC_ENUM_MAP.get(key);
        if (result != null) {
            return result;
        }
        for (T en : enumConstants) {
            if (getEnumDesc(en, enumClass).equals(desc)) {
                DESC_ENUM_MAP.put(key, en);
                return en;
            }
        }
        return null;
    }


    /**
     * put 一个枚举到map中
     *
     * @param enumValue 枚举值
     * @return String
     */
    private static String putEnum(Enum<?> enumValue) {
        Class<? extends Enum> enumClass = enumValue.getClass();
        String description = getEnumDesc(enumValue, enumClass);
        ENUM_DESC_MAP.put(enumValue, description);
        return description;
    }

    private static String getEnumDesc(Enum<?> enumValue, Class<? extends Enum> enumClass) {
        Field field;
        try {
            field = enumClass.getField(enumValue.name());
        } catch (NoSuchFieldException exception) {
            throw new RuntimeException(MessageFormat.format("枚举{0}上未存在{1}属性", enumClass.getName(), enumValue.name()), exception);
        }
        Schema schema = field.getAnnotation(Schema.class);
        String description;
        if (schema != null) {
            description = schema.description();
        } else {
            description = "";
        }
        return description;
    }
}

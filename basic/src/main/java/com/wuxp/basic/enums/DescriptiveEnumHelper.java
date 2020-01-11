package com.wuxp.basic.enums;


import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可描述枚举的的帮助者，用于获取枚举注解上的值
 *
 * @author wxup
 * @create 2018-06-08 22:39
 **/
public final class DescriptiveEnumHelper {

    private static final Map<Class<? extends Enum>, String[]/* enum desc*/> ENUM_DESC_MAP = new ConcurrentHashMap<>();




    static {

        //TODO 通过包扫描的方式在spring 初始化的后将所有的枚举put到Map中

    }

    private DescriptiveEnumHelper() {
    }

    /**
     * 获取枚举的描述信息
     *
     * @param e 枚举对象实例
     * @return 枚举描述
     */
    public static String getEnumDesc(Enum<?> e) {
        Class<? extends Enum> enumClass = e.getClass();
        String[] descValues = ENUM_DESC_MAP.get(enumClass);
        if (descValues == null) {
            synchronized (enumClass) {
                descValues = DescriptiveEnumHelper.putEnum(enumClass);
            }
        }
        int ordinal = e.ordinal();
        return descValues[ordinal];
    }

    /**
     * 通过描述获取枚举的值
     *
     * @param desc
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T getValueByDesc(String desc, Class<T> enumClass) {
        String[] descValues = getDescValues(enumClass);
        T[] enumConstants = enumClass.getEnumConstants();
        int length = descValues.length;
        for (int i = 0; i < length; i++) {
            if (descValues[i].equals(desc)) {
                return enumConstants[i];
            }
        }
        return null;
    }

    private static <T extends Enum<T>> String[] getDescValues(Class<T> enumClass) {
        String[] descValues = ENUM_DESC_MAP.get(enumClass);
        if (descValues == null) {
            synchronized (enumClass) {
                descValues = DescriptiveEnumHelper.putEnum(enumClass);
            }
        }
        return descValues;
    }

    /**
     * put 一个枚举到map中
     *
     * @param enumClass 枚举类类型
     * @return String[]
     */
    private static String[] putEnum(Class<? extends Enum> enumClass) {
        Enum[] enumConstants = enumClass.getEnumConstants();
        int length = enumConstants.length;
        final String[] descValues = new String[length];

        for (int i = 0; i < length; i++) {
            Enum en = enumConstants[i];
            String name = en.name();
            Field field;
            try {
                field = enumClass.getField(name);
            } catch (NoSuchFieldException exception) {
                throw new RuntimeException(MessageFormat.format("枚举{0}上未存在{1}属性", enumClass.getName(), name), exception);
            }
            Schema schema = field.getAnnotation(Schema.class);
            assert schema != null;
            descValues[en.ordinal()] = schema.description();
        }
        ENUM_DESC_MAP.put(enumClass, descValues);
        return descValues;

    }
}

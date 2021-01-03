package com.wuxp.basic.enums;


/**
 * 带描述的枚举，实现该接口的枚举都是可描述的
 *
 * @author wxup
 **/
public interface DescriptiveEnum {


    /**
     * 获取枚举值的描述
     *
     * @return String
     */
    default String getDesc() {
        return DescriptiveEnumHelper.getEnumDesc((Enum<?>) this);
    }


    /**
     * 通过枚举的描述获取枚举值
     *
     * @param desc      枚举描述 {@link #getDesc()}
     * @param enumClass 枚举类类型
     * @param <T>       枚举实现类类型
     * @return 枚举的实例
     */
    static <T extends Enum<T>> T valueOf(String desc, Class<T> enumClass) {
        return DescriptiveEnumHelper.getValueByDesc(desc, enumClass);
    }

}

package com.wuxp.basic.enums;


/**
 * 带描述的枚举，实现该接口的枚举都是可描述的
 *
 * @author wxup
 * @create 2018-06-08 21:58
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
     * @param desc
     * @param enumClass
     * @param <T>
     * @return
     */
    static <T extends Enum<T>> T valueOf(String desc, Class<T> enumClass) {
        return DescriptiveEnumHelper.getValueByDesc(desc, enumClass);
    }

}

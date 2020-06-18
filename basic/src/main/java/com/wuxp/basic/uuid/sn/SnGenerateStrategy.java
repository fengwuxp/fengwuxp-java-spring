package com.wuxp.basic.uuid.sn;

/**
 * sn生成策略
 *
 * @author wxup
 */
public interface SnGenerateStrategy {


    /**
     * 获取下一个sn
     *
     * @param type
     * @return
     */
    String nextSn(SnType type);


    /**
     * 用于识别sn的类型
     */
    interface SnType {

        /**
         * 返回sn的类型编码
         *
         * @return
         */
        String getCode();
    }
}

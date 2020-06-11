package com.wuxp.api.model;


import com.wuxp.api.ApiRequest;

/**
 * 接口查询请求
 *
 * @author wxup
 */
public interface ApiQueryReq<OrderField extends QueryOrderField> extends ApiRequest {

    /**
     * 当前查询页码
     *
     * @return
     */
    Integer getQueryPage();

    /**
     * 当前查询大小
     *
     * @return
     */
    Integer getQuerySize();

    /**
     * 当前查询类型
     *
     * @return
     */
    QueryType getQueryType();

    /**
     * 获取排序类型
     *
     * @return
     */
    QueryOrderType[] getOrderTypes();

    /**
     * 获取排序字段
     *
     * @return
     */
    OrderField[] getOrderFields();


    /**
     * 是否需要处理排序
     *
     * @return <code>true</code> 需要处理排序
     */
    default boolean isOrderBy() {
        QueryOrderType[] orderTypes = this.getOrderTypes();
        OrderField[] orderFields = this.getOrderFields();
        if (orderTypes == null || orderFields == null) {
            return false;
        }
        return orderTypes.length == orderFields.length && orderTypes.length > 0;
    }

}

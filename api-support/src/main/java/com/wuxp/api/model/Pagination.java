package com.wuxp.api.model;


import java.beans.Transient;
import java.util.List;

/**
 * 分页对象
 *
 * @author wuxp
 */
public interface Pagination<T> {

    /**
     * @return 总记录数据
     */
    long getTotal();

    /**
     * @return 数据集合列表
     */
    List<T> getRecords();

    /**
     * @return 当前查询页面
     */
    int getQueryPage();

    /**
     * @return 当前查询大小
     */
    int getQuerySize();

    /**
     * @return 当前查询类型
     */
    QueryType getQueryType();

    /**
     * @return 获取第一天数据
     */
    @Transient
    T getFirst();

    /**
     * @return {@link #getRecords()}是否为null 或空集合
     */
    @Transient
    boolean isEmpty();

}

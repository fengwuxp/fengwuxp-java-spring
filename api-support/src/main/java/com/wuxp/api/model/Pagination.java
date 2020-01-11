package com.wuxp.api.model;


import java.beans.Transient;
import java.util.List;

/**
 * 分页对象
 */
public interface Pagination<T> {

    long getTotal();

    List<T> getRecords();

    int getQueryPage();

    int getQuerySize();

    QueryType getQueryType();

    @Transient
    T getFirst();

    boolean isEmpty();

}

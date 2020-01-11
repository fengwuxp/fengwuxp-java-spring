package com.wuxp.resouces;

/**
 * resource find condition
 */
public interface ResourceFindCondition {


    /**
     * find page , starting from 1
     * @return
     */
    int getQueryPage();

    /**
     * find page size
     * if the query is all return -1
     * @return
     */
    int getQuerySize();
}

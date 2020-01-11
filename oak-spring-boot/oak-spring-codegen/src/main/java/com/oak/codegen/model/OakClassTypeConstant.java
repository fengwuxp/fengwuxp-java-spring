package com.oak.codegen.model;

import com.wuxp.codegen.model.enums.ClassType;
import com.wuxp.codegen.model.languages.java.codegen.JavaCodeGenClassMeta;

public final class OakClassTypeConstant {

    public static final JavaCodeGenClassMeta OAK_SERVICE_REQ = new JavaCodeGenClassMeta("ApiBaseReq", "", ClassType.CLASS, false, null, "com.oak.springboot.model.ApiBaseReq", true);

    public static final JavaCodeGenClassMeta OAK_SERVICE_QUERY_REQ = new JavaCodeGenClassMeta("ApiBaseQueryReq", "", ClassType.CLASS, false, null, "com.oak.springboot.model.ApiBaseQueryReq", true);

    public static final JavaCodeGenClassMeta OAK_SERVICE_PAGE = new JavaCodeGenClassMeta("PageInfo", "PageInfo<T>", ClassType.CLASS, false, null, "com.oak.springboot.model.PageInfo", true);

    public static final JavaCodeGenClassMeta OAK_QUERY_TYPE = new JavaCodeGenClassMeta("QueryType", "", ClassType.ENUM, false, null, "com.wuxp.api.model.QueryType", true);

    public static final JavaCodeGenClassMeta OAK_QUERY_SORT_TYPE = new JavaCodeGenClassMeta("QuerySortType", "", ClassType.ENUM, false, null, "com.wuxp.api.model.QuerySortType", true);


}

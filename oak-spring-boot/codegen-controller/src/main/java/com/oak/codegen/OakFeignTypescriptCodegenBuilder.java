package com.oak.codegen;

import com.oak.api.model.ApiBaseQueryReq;
import com.oak.api.model.ApiBaseReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import com.wuxp.basic.enums.DescriptiveEnum;
import com.wuxp.codegen.core.CodeGenerator;
import com.wuxp.codegen.model.TemplateFileVersion;
import com.wuxp.codegen.model.enums.ClassType;
import com.wuxp.codegen.model.languages.typescript.TypescriptClassMeta;
import com.wuxp.codegen.swagger3.builder.Swagger3FeignTypescriptCodegenBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class OakFeignTypescriptCodegenBuilder extends Swagger3FeignTypescriptCodegenBuilder {

    protected final static String OAK_COMMON_MODEL_PATH = "oak-common";

    public final static TypescriptClassMeta OAK_COMMON_API_REQ = new TypescriptClassMeta("ApiReq", null, ClassType.INTERFACE, false, null, OAK_COMMON_MODEL_PATH);

    public final static TypescriptClassMeta OAK_COMMON_API_QUERY_REQ = new TypescriptClassMeta("ApiQueryReq", null, ClassType.INTERFACE, false, null, OAK_COMMON_MODEL_PATH);

//    public final static TypescriptClassMeta OAK_COMMON_API_RESP = new TypescriptClassMeta("ApiResp", null, ClassType.CLASS, false, null, OAK_COMMON_MODEL_PATH);

//    public final static TypescriptClassMeta OAK_COMMON_API_QUERY_RESP = new TypescriptClassMeta("ApiQueryResp", null, ClassType.CLASS, false, null, OAK_COMMON_MODEL_PATH );

//    public final static TypescriptClassMeta OAK_COMMON_ACTION = new TypescriptClassMeta("Action", null, ClassType.CLASS, false, null, OAK_COMMON_MODEL_PATH);

//    public final static TypescriptClassMeta OAK_COMMON_PROMPT_DATA = new TypescriptClassMeta("PromptData", null, ClassType.CLASS, false, null, OAK_COMMON_MODEL_PATH);

    public final static TypescriptClassMeta OAK_COMMON_PAGE_INFO = new TypescriptClassMeta("PageInfo", "PageInfo<T>", ClassType.INTERFACE, false, null, OAK_COMMON_MODEL_PATH);

    public final static TypescriptClassMeta OAK_COMMON_QUERY_TYPE = new TypescriptClassMeta("QueryType", null, ClassType.ENUM, false, null, OAK_COMMON_MODEL_PATH);

    private OakFeignTypescriptCodegenBuilder() {
        super();
    }

    public static OakFeignTypescriptCodegenBuilder builder() {
        return new OakFeignTypescriptCodegenBuilder();
    }


    @Override
    public CodeGenerator buildCodeGenerator() {

        this.baseTypeMapping.put(Date.class, TypescriptClassMeta.DATE);
        this.baseTypeMapping.put(ApiResp.class, TypescriptClassMeta.PROMISE);
        this.customTypeMapping.put(Void.class, TypescriptClassMeta.VOID);
        this.customTypeMapping.put(ApiBaseReq.class, OAK_COMMON_API_REQ);
        this.customTypeMapping.put(ApiBaseQueryReq.class, OAK_COMMON_API_QUERY_REQ);
        this.customTypeMapping.put(Pagination.class, OAK_COMMON_PAGE_INFO);
        this.customTypeMapping.put(QueryType.class, OAK_COMMON_QUERY_TYPE);
        this.ignorePackages.add(DescriptiveEnum.class.getName());
        this.templateFileVersion(TemplateFileVersion.V_2_0_0);

        return super.buildCodeGenerator();
    }
}

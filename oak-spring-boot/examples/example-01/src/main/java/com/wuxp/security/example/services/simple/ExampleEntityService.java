package com.wuxp.security.example.services.simple;

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import com.wuxp.security.example.services.simple.req.*;
import com.wuxp.security.example.services.simple.info.ExampleEntityInfo;



/**
 *  example例子服务
 *  2020-2-16 10:20:18
 */
public interface ExampleEntityService {


    ApiResp<Long> create(CreateExampleEntityReq req);


    ApiResp<Void> edit(EditExampleEntityReq req);


    ApiResp<Void> delete(DeleteExampleEntityReq req);


    ExampleEntityInfo findById(Long id);


    Pagination<ExampleEntityInfo> query(QueryExampleEntityReq req);

}

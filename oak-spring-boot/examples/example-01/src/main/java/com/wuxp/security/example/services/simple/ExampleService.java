package com.wuxp.security.example.services.simple;



import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.security.example.services.simple.info.ExampleInfo;
import com.wuxp.security.example.services.simple.req.CreateExampleReq;
import com.wuxp.security.example.services.simple.req.DeleteExampleReq;
import com.wuxp.security.example.services.simple.req.EditExampleReq;
import com.wuxp.security.example.services.simple.req.QueryExampleReq;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "例子服务")

public interface ExampleService {

    ApiResp<Long> create(CreateExampleReq req);

    ApiResp<Void> edit(EditExampleReq req);

    ApiResp<Void> delete(DeleteExampleReq req);

    Pagination<ExampleInfo> query(QueryExampleReq req);

    ExampleInfo findById(Long id);

}

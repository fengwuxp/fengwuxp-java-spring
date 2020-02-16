package com.wuxp.security.example.controller.simple;

import com.wuxp.api.ApiResp;
import com.wuxp.api.log.ApiLog;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.example.services.simple.ExampleEntityService;
import com.wuxp.security.example.services.simple.info.ExampleEntityInfo;
import com.wuxp.security.example.services.simple.req.CreateExampleEntityReq;
import com.wuxp.security.example.services.simple.req.DeleteExampleEntityReq;
import com.wuxp.security.example.services.simple.req.EditExampleEntityReq;
import com.wuxp.security.example.services.simple.req.QueryExampleEntityReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/example")
@Tag(name = "用例", description = "这是一个用于演示的例子")
@Slf4j
public class ExampleController {

    @Autowired
    private ExampleEntityService exampleService;

    @PostMapping("/create")
    @Operation(summary = "创建example", description = "描述的文字")
    @ApiLog(value = "#JSON.toJSONString(req)")
    public ApiResp<Long> create(CreateExampleEntityReq req) {

        return exampleService.create(req);
    }

    @PutMapping("/edit")
    @Operation(summary = "编辑example", description = "描述的文字")
    public ApiResp<Void> edit(EditExampleEntityReq req) {

        return exampleService.edit(req);
    }

    @GetMapping("/delete")
    @Operation(summary = "删除example", description = "描述的文字")
    public ApiResp<Void> delete(DeleteExampleEntityReq req) {
        return exampleService.delete(req);
    }


    @GetMapping("/query")
    @Operation(summary = "查询example", description = "描述的文字")
    public ApiResp<Pagination<ExampleEntityInfo>> query(QueryExampleEntityReq req) {

        return RestfulApiRespFactory.queryOk(exampleService.query(req));
    }

    @GetMapping("/{id}")
    @Operation(summary = "example详情", description = "描述的文字")
    public ApiResp<ExampleEntityInfo> detail(@PathVariable Long id) {
        return RestfulApiRespFactory.ok(exampleService.findById(id));
    }

}

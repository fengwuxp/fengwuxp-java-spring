package com.wuxp.security.example.controller.version;

import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.api.version.ApiVersion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@Tag(name = "版本", description = "这是一个用于演示的api版本的例子")
@Slf4j
@ApiVersion(1)
public class ApiVersionExampleController {


    @GetMapping("/test")
    @Operation(summary = "版本例子", description = "版本管理的例子")
    public ApiResp<Void> v1() {
        return RestfulApiRespFactory.ok();
    }


}

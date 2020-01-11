package com.wuxp.security.example.controller.simple;

import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example/")
public class ExampleController {


    @RequestMapping("create")
    @Operation()
    public ApiResp<Long> create() {

        return RestfulApiRespFactory.ok(null);
    }

}

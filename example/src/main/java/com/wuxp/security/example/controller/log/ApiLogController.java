package com.wuxp.security.example.controller.log;

import com.wuxp.api.ApiResp;
import com.wuxp.api.context.InjectField;
import com.wuxp.api.log.ApiLog;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.example.request.TestRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/log")
@Slf4j
@Tag(name = "日志", description = "日志演示")
public class ApiLogController {


    @Operation(
            summary = "测试",
            responses = {
                    @ApiResponse(
                            description = "测试",
                            content = @Content(mediaType = "application/json")),
            }
    )
    @GetMapping("/test")
    @ApiLog(value = "#testRequest.ip")
    public ApiResp<String> test(TestRequest testRequest) {

        log.info("--{}-->", testRequest);

        return RestfulApiRespFactory.ok(testRequest.getIp());
    }

    @Operation(
            summary = "测试2",
            responses = {
                    @ApiResponse(
                            description = "测试2",
                            content = @Content(mediaType = "application/json")),
            }
    )
    @GetMapping("/test2")
    @ApiLog(value = "这是一个测试请求，访问者:{name} ip：{ip}")
    public ApiResp<String> test2(@NotNull(message = "姓名不能为空")@InjectField(value = "#name") String name, @InjectField(value = "#ip") String ip) {

        log.info("--{}-->", name);

        //获取所有的RequestMapping
//        Map<String, RequestMappingHandlerMapping> requestMappingHandlerMappingMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(
//                SpringContextHolder.getApplicationContext(),
//                RequestMappingHandlerMapping.class,
//                true,
//                false);
//
//        requestMappingHandlerMappingMap.forEach((s, requestMappingHandlerMapping) -> {
//            Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
//            System.out.println(handlerMethods.size());
//        });


        return RestfulApiRespFactory.ok("");
    }
}

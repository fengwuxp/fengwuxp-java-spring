package com.wuxp.security.example.controller;

import com.wuxp.api.ApiResp;
import com.wuxp.api.context.InjectField;
import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.api.log.ApiLog;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.example.model.StudyUserDetails;
import com.wuxp.security.example.request.TestRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/log")
@Slf4j
public class ApiLogController {


    @Operation(
            summary = "测试",
            responses = {
                    @ApiResponse(
                            description = "测试",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudyUserDetails.class))),
            }
    )
    @GetMapping("/test")
    @ApiLog(value = "#testRequest.ip")
    public ApiResp<StudyUserDetails> test(TestRequest testRequest) {

        log.info("--{}-->", testRequest);

        return RestfulApiRespFactory.ok(new StudyUserDetails(testRequest.getName(), null));
    }

    @Operation(
            summary = "测试2",
            responses = {
                    @ApiResponse(
                            description = "测试2",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudyUserDetails.class))),
            }
    )
    @GetMapping("/test2")
    @ApiLog(value = "#name")
    public ApiResp<StudyUserDetails> test2(@NotNull(message = "姓名不能为空") @InjectField(value = "#ip") String name) {

        log.info("--{}-->", name);
        //获取所有的RequestMapping
        Map<String, RequestMappingHandlerMapping> requestMappingHandlerMappingMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                SpringContextHolder.getApplicationContext(),
                RequestMappingHandlerMapping.class,
                true,
                false);

        requestMappingHandlerMappingMap.forEach((s, requestMappingHandlerMapping) -> {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
            handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
                Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
                System.out.println(patterns);
            });

        });

        return RestfulApiRespFactory.ok(new StudyUserDetails(name, null));
    }
}

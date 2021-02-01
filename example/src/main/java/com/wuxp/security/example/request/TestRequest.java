package com.wuxp.security.example.request;


import com.wuxp.api.context.InjectField;
import com.wuxp.api.signature.ApiSignatureRequest;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

import static com.wuxp.api.context.InjectFieldExpressionConstant.INJECT_IP;

/**
 * @author wxup
 */
@Data
@Accessors(chain = true)
public class TestRequest implements ApiSignatureRequest {

    @InjectField(value = "#name")
    @NotNull(message = "名称不能为空")
    private String name;

    @InjectField(value = INJECT_IP)
    private String ip;
}

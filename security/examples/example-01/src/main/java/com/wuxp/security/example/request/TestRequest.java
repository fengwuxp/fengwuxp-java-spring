package com.wuxp.security.example.request;


import com.wuxp.api.ApiBaseReq;
import com.wuxp.api.context.InjectField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TestRequest extends ApiBaseReq {

    @InjectField(value = "#name")
    @NotNull(message = "名称不能为空")
    private String name;

    @InjectField(value = "#ip")
    private String ip;
}

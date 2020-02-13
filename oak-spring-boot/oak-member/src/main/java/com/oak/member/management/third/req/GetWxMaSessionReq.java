package com.oak.member.management.third.req;

import com.levin.commons.service.domain.Desc;
import com.oak.api.model.ApiBaseReq;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Desc(value = "获取微信用户信息")
@Data
public class GetWxMaSessionReq extends ApiBaseReq {

    @Desc(value = "调用凭证")
    @NotNull
    private String code;

}

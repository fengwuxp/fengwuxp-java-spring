package com.oak.member.management.third.req;

import com.levin.commons.service.domain.Desc;
import com.levin.commons.service.domain.Sign;
import com.oak.api.model.ApiBaseReq;

import javax.validation.constraints.NotNull;

@Desc(value = "获取微信UnionID")
public class GetWxUnionIDReq extends ApiBaseReq {

    @Desc(value = "调用凭证")
    @NotNull
    @Sign
    private String accessToken;

    @Desc(value = "openid")
    @NotNull
    @Sign
    private String openid;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        return "GetWxUnionIDEvt{" +
                "accessToken='" + accessToken + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}

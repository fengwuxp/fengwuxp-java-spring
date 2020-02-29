package com.oak.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-19 15:10
 * @Description
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CheckMobilePhoneAndOpenIdWxMaReq extends ApiBaseReq {

    @Schema(name = "openId", description = "OPENID")
    private String openId;

    @Schema(name = "unionId", description = "unionId")
    private String unionId;

    @Schema(name = "sessionKey", description = "获取手机号sessionKey")
    @NotNull
    private String sessionKey;

    @Schema(name = "encryptedData", description = "获取手机号加密数据")
    @NotNull
    private String encryptedData;

    @Schema(name = "ivStr", description = "获取手机号ivStr")
    @NotNull
    private String ivStr;


}

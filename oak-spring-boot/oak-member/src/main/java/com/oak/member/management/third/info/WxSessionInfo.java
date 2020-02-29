package com.oak.member.management.third.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"sessionKey"})
public class WxSessionInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "session_key")
    private String sessionKey;

    @Schema(description = "openid")
    private String openid;

    @Schema(description = "unionid")
    private String unionid;
}

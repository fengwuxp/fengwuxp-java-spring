package com.oak.member.management.third.info;

import com.levin.commons.service.domain.Desc;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"sessionKey"})
public class WxSessionInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Desc(value = "session_key")
    private String sessionKey;

    @Desc(value = "openid")
    private String openid;

    @Desc(value = "unionid")
    private String unionid;
}

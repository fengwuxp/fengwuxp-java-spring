package com.oak.member.management.third.req;

import com.levin.commons.service.domain.Desc;
import com.oak.api.model.ApiBaseReq;
import lombok.*;

@Desc(value = "微信小程序用户手机号")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetWxMaPhoneNumberReq extends ApiBaseReq {

    @Desc(value = "sessionKey")
    private String sessionKey;

    @Desc(value = "加密数据")
    private String encryptedData;

    @Desc(value = "ivStr")
    private String ivStr;

}

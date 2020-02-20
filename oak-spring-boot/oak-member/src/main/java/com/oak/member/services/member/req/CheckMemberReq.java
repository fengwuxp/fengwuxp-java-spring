package com.oak.member.services.member.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author laiy
 * create at 2020-02-19 15:40
 * @Description
 */

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CheckMemberReq extends ApiBaseReq {

    @Schema(description = "手机号")
    private  String mobilePhone;

    @Schema(description = "用户名")
    private  String userName;

    @Schema(description = "电子邮箱")
    private  String email;
}

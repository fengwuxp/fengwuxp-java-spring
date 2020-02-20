package com.oak.member.management.member.req;

import com.levin.commons.service.domain.Desc;
import com.oak.member.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-12 10:33
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RegisterMemberFromWxReq {

    @Desc("code")
    @NotNull
    private String code;

    @Schema(description =  "区域编码")
    private String areaId;

    @Schema(description =  "区域名称")
    private String areaName;

    @Schema(description =  "地址")
    private String address;

}

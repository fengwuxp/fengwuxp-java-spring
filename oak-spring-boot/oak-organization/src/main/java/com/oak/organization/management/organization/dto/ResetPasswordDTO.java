package com.oak.organization.management.organization.dto;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author: zhuox
 * @create: 2020-02-17
 * @description: 重置密码入参
 **/
@Schema(description = "重置密码入参")
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ResetPasswordDTO extends ApiBaseReq {

    @Schema(name = "id", description = "id")
    @NotNull
    private Long id;

    @Schema(name = "id", description = "密码")
    private String password;
}

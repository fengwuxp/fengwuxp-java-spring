package com.oak.rbac.services.permission.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "权限信息")
public class PermissionInfo implements Serializable {

    private static final long serialVersionUID = -5768684537903557933L;

    @Schema(name = "id")
    private Long id;

    @Schema(description = "权限名称(操作)")
    private String name;

    @Schema(description = "权限值")
    private String value;

    @Schema(description = "资源标识")
    private String resourceId;

}

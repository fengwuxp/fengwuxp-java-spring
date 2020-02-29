package com.oak.organization.management.organization.req;


import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "编辑机构")
public class UpdateOrganizationReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    private Long id;

    @Schema(description = "机构名称")
    @NotNull
    private String name;

    @Schema(description = "联系人")
    @NotNull
    private String contacts;

    @Schema(description = "区域ID")
    @Size(max = 20)
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;

}


package com.wuxp.miniprogram.services.wxopenconfig.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑组织
 *  2020-3-2 14:28:21
 */
@Schema(description = "编辑组织")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditWxOpenConfigReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "组织编号")
    @UpdateColumn
    private Long organizationId;

    @Size(max = 32)
    @Schema(description = "组织代码")
    @UpdateColumn
    private String organizationCode;

    @Schema(description = "小程序AppId")
    @UpdateColumn
    private String miniProgramAppId;

    @Schema(description = "小程序AppSecret")
    @UpdateColumn
    private String miniProgramAppSecret;

    @Schema(description = "小程序Token")
    @UpdateColumn
    private String miniProgramToken;

    @Schema(description = "小程序消息加密解密key")
    @UpdateColumn
    private String miniProgramMsgKey;

    @Schema(description = "小程序额外参数json格式")
    @UpdateColumn
    private String miniProgramExtra;

    @Schema(description = "小程序额外文件名")
    @UpdateColumn
    private String miniProgramFilename;

    @Schema(description = "小程序额外文件内容")
    @UpdateColumn
    private String miniProgramFilecontent;

    @Schema(description = "小程序审核id")
    @UpdateColumn
    private String miniProgramAuditId;

    @Schema(description = "公众号Appid")
    @UpdateColumn
    private String officialAccountAppId;

    @Schema(description = "公众号AppSecret")
    @UpdateColumn
    private String officialAccountAppSecret;

    @Schema(description = "排序代码")
    @UpdateColumn
    private Integer orderCode;

    @Schema(description = "是否允许")
    @UpdateColumn
    private Boolean enable;

    @Schema(description = "是否可编辑")
    @UpdateColumn
    private Boolean editable;

    @Schema(description = "更新时间")
    @UpdateColumn
    private Date lastUpdateTime;

    @Size(max = 1000)
    @Schema(description = "备注")
    @UpdateColumn
    private String remark;

    public EditWxOpenConfigReq() {
    }

    public EditWxOpenConfigReq(Long id) {
        this.id = id;
    }
}

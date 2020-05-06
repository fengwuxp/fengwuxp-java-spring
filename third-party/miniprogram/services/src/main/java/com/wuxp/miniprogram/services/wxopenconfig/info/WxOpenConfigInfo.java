package com.wuxp.miniprogram.services.wxopenconfig.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* 组织
* 2020-3-2 14:28:21
*/
@Schema(description ="组织")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class WxOpenConfigInfo implements Serializable {

        @Schema(description = "ID")
        private Long id;

        @Schema(description = "组织编号")
        private Long organizationId;

        @Schema(description = "组织代码")
        private String organizationCode;

        @Schema(description = "小程序AppId")
        private String miniProgramAppId;

        @Schema(description = "小程序AppSecret")
        private String miniProgramAppSecret;

        @Schema(description = "小程序Token")
        private String miniProgramToken;

        @Schema(description = "小程序消息加密解密key")
        private String miniProgramMsgKey;

        @Schema(description = "小程序额外参数json格式")
        private String miniProgramExtra;

        @Schema(description = "小程序额外文件名")
        private String miniProgramFilename;

        @Schema(description = "小程序额外文件内容")
        private String miniProgramFilecontent;

        @Schema(description = "小程序审核id")
        private String miniProgramAuditId;

        @Schema(description = "公众号Appid")
        private String officialAccountAppId;

        @Schema(description = "公众号AppSecret")
        private String officialAccountAppSecret;

        @Schema(description = "排序代码")
        private Integer orderCode;

        @Schema(description = "是否允许")
        private Boolean enable;

        @Schema(description = "是否可编辑")
        private Boolean editable;

        @Schema(description = "创建时间")
        private Date createTime;

        @Schema(description = "更新时间")
        private Date lastUpdateTime;

        @Schema(description = "备注")
        private String remark;


}

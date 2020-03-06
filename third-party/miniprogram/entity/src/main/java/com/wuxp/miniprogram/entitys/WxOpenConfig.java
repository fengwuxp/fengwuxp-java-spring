package com.wuxp.miniprogram.entitys;

import com.levin.commons.dao.domain.support.AbstractBaseEntityObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @Classname WxOpenConfig
 * @Description 微信小程序以及公众号配置
 * @Date 2020/3/2 11:22
 * @Created by 44487
 */

@Schema(description = "微信小程序以及公众号配置")
@Entity
@Table(name = "t_organization_wxopenconfig", indexes = {
        @Index(columnList = "organization_id")
})
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class WxOpenConfig extends AbstractBaseEntityObject<Long> {

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(description = "组织编号")
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Schema(description = "组织代码")
    @Column(name = "organization_code", length = 32, nullable = false, unique = true)
    private String organizationCode;

    @Schema(description = "小程序AppId")
    @Column(name = "mini_program_app_id")
    private String miniProgramAppId;

    @Schema(description = "小程序AppSecret")
    @Column(name = "mini_program_app_secret")
    private String miniProgramAppSecret;

    @Schema(description = "小程序Token")
    @Column(name = "mini_program_token")
    private String miniProgramToken;

    @Schema(description = "小程序消息加密解密key")
    @Column(name = "mini_program_msg_key")
    private String miniProgramMsgKey;

    @Schema(description = "小程序额外参数json格式")
    @Column(name = "mini_program_extra")
    private String miniProgramExtra;

    @Schema(description = "小程序额外文件名")
    @Column(name = "mini_program_filename")
    private String miniProgramFilename;

    @Schema(description = "小程序额外文件内容")
    @Column(name = "mini_program_filecontent")
    private String miniProgramFilecontent;

    @Schema(description = "小程序审核id")
    @Column(name = "mini_program_audit_id")
    private String miniProgramAuditId;

    @Schema(description = "公众号Appid")
    @Column(name = "official_account_app_id")
    private String officialAccountAppId;

    @Schema(description = "公众号AppSecret")
    @Column(name = "official_account_app_secret")
    private String officialAccountAppSecret;


}

package com.wuxp.miniprogram.services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Classname OnekeyUploadCodeDTO
 * @Description TODO
 * @Date 2020/3/17 19:02
 * @Created by 44487
 */

@Schema(description = "一键提交代码")
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class OnekeyUploadCodeDTO {

    @Schema(
            description = "小程序appid",
            name = "appid"
    )
    @NotBlank
    private String appid;
    @Schema(
            description = "代码提交",
            name = "code_commit"
    )
    private CodeCommitDTO codeCommit;
    @Schema(
            description = "代码审核",
            name = "submit_audit_message"
    )
    private SubmitAuditMessageDTO submitAuditMessage;
    @Schema(
            description = "小程序业务域名，当 action 参数是 get 时不需要此字段",
            name = "webviewdomain"
    )
    private List<String> webviewdomain;
    @Schema(
            description = "request合法域名",
            name = "requestdomain"
    )
    private List<String> requestdomain;
    @Schema(
            description = "socket合法域名",
            name = "wsrequestdomain"
    )
    private List<String> wsrequestdomain;
    @Schema(
            description = "uploadFile合法域名",
            name = "uploaddomain"
    )
    private List<String> uploaddomain;
    @Schema(
            description = "downloadFile合法域名",
            name = "downloaddomain"
    )
    private List<String> downloaddomain;

}

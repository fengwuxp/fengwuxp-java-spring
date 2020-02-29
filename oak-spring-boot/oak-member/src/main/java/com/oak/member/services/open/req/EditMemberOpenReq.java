package com.oak.member.services.open.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑会员绑定信息
 *  2020-2-8 20:22:08
 */
@Schema(description = "编辑会员绑定信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberOpenReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "会员ID")
    @UpdateColumn
    private Long memberId;

    @Schema(description = "平台类型")
    @UpdateColumn
    private OpenType openType;

    @Schema(description = "OPENID")
    @UpdateColumn
    private String openId;

    @Schema(description = "UNIONID")
    @UpdateColumn
    private String unionId;

    @Size(max = 500)
    @Schema(description = "绑定信息")
    @UpdateColumn
    private String bindInfo;

    @Schema(description = "到期日期")
    @UpdateColumn
    private Date expirationDate;

    @Schema(description = "是否关注")
    @UpdateColumn
    private Boolean subscribe;

    @Schema(description =  "绑定的渠道")
    @UpdateColumn
    @Column(name = "bind_channel_code", nullable = false, length = 32)
    private String bindChannelCode;

    public EditMemberOpenReq() {
    }

    public EditMemberOpenReq(Long id) {
        this.id = id;
    }
}

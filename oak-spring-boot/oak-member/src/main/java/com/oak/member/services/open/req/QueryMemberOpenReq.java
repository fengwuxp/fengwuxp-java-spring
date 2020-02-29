package com.oak.member.services.open.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.util.Date;

/**
 *  查询会员绑定信息
 *  2020-2-8 20:22:08
 */
@Schema(description = "查询会员绑定信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberOpenReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "加载会员信息")
    @Fetch(value = "member", condition = "#_val==true")
    private Boolean loadMember;

    @Schema(description = "加载绑定渠道")
    @Fetch(value = "bindChannel", condition = "#_val==true")
    private Boolean loadBindChannel;

    @Schema(description =  "绑定的渠道")
    @Column(name = "bind_channel_code", nullable = false, length = 32)
    private String bindChannelCode;

    @Schema(description = "平台类型")
    private OpenType openType;

    @Schema(description = "OPENID")
    private String openId;

    @Schema(description = "UNIONID")
    private String unionId;

    @Schema(description = "绑定信息")
    private String bindInfo;

    @Schema(description = "最小到期日期")
    @Gte("expirationDate")
    private Date minExpirationDate;

    @Schema(description = "最大到期日期")
    @Lte("expirationDate")
    private Date maxExpirationDate;

    @Schema(description = "是否关注")
    private Boolean subscribe;

    @Schema(description = "最小登记日期")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大登记日期")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "最小变更日期")
    @Gte("updateTime")
    private Date minUpdateTime;

    @Schema(description = "最大变更日期")
    @Lte("updateTime")
    private Date maxUpdateTime;

    public QueryMemberOpenReq() {
    }

    public QueryMemberOpenReq(Long id) {
        this.id = id;
    }
}

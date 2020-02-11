package com.oak.member.services.open.info;

import com.levin.commons.service.domain.Desc;
import com.oak.member.enums.OpenType;
import com.oak.member.services.member.info.MemberInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* 会员绑定信息
* 2020-2-8 20:22:07
*/
@Schema(description ="会员绑定信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"memberInfo",})
public class MemberOpenInfo implements Serializable {

        @Schema(description = "ID")
        private Long id;

        @Schema(description = "会员ID")
        private Long memberId;

          @Desc(value = "",code = "member")
        @Schema(description = "会员信息")
        private MemberInfo memberInfo;

        @Schema(description = "平台类型")
        private OpenType openType;

        @Schema(description = "OPENID")
        private String openId;

        @Schema(description = "UNIONID")
        private String unionId;

        @Schema(description = "绑定信息")
        private String bindInfo;

        @Schema(description = "到期日期")
        private Date expirationDate;

        @Schema(description = "是否关注")
        private Boolean subscribe;

        @Schema(description = "登记日期")
        private Date createTime;

        @Schema(description = "变更日期")
        private Date updateTime;


}

package com.oak.member.services.token.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除会员登录的token信息
 *  2020-2-18 16:22:54
 */
@Schema(description = "删除会员登录的token信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteMemberTokenReq extends ApiBaseReq {

    @Schema(description = "会员id")
    private Long id;

    @Schema(description = "会员id集合")
    @In("id")
    private Long[] ids;

    public DeleteMemberTokenReq() {
    }

    public DeleteMemberTokenReq(Long id) {
        this.id = id;
    }

}

package com.oak.member.services.account.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除会员账户信息
 *  2020-2-6 15:42:46
 */
@Schema(description = "删除会员账户信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteMemberAccountReq extends ApiBaseReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "id集合")
    @In("id")
    private Long[] ids;

    public DeleteMemberAccountReq() {
    }

    public DeleteMemberAccountReq(Long id) {
        this.id = id;
    }

}

package com.oak.member.services.member.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除会员信息
 *  2020-2-6 15:32:43
 */
@Schema(description = "删除会员信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteMemberReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "会员ID集合")
    @In("id")
    private Long[] ids;

    public DeleteMemberReq() {
    }

    public DeleteMemberReq(Long id) {
        this.id = id;
    }

}

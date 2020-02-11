package com.oak.member.services.open.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除会员绑定信息
 *  2020-2-8 20:22:08
 */
@Schema(description = "删除会员绑定信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteMemberOpenReq extends ApiBaseReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "ID集合")
    @In("id")
    private Long[] ids;

    public DeleteMemberOpenReq() {
    }

    public DeleteMemberOpenReq(Long id) {
        this.id = id;
    }

}

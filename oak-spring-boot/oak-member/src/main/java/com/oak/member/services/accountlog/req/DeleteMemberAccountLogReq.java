package com.oak.member.services.accountlog.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;
import com.oak.member.enums.AccountStatus;

/**
 *  删除会员账户信息日志
 *  2020-2-18 14:06:41
 */
@Schema(description = "删除会员账户信息日志")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteMemberAccountLogReq extends ApiBaseReq {

    @Schema(description = "日志ID")
    private Long id;

    @Schema(description = "日志ID集合")
    @In("id")
    private Long[] ids;

    public DeleteMemberAccountLogReq() {
    }

    public DeleteMemberAccountLogReq(Long id) {
        this.id = id;
    }

}

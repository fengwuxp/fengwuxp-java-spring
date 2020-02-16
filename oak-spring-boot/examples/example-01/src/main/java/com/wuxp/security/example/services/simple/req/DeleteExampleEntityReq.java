package com.wuxp.security.example.services.simple.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;
import com.wuxp.security.example.enums.Week;

/**
 *  删除example例子
 *  2020-2-16 10:20:18
 */
@Schema(description = "删除example例子")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteExampleEntityReq extends ApiBaseReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "id集合")
    @In("id")
    private Long[] ids;

    public DeleteExampleEntityReq() {
    }

    public DeleteExampleEntityReq(Long id) {
        this.id = id;
    }

}

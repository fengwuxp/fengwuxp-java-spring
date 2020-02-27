package com.oak.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author laiy
 * create at 2020-02-24 16:44
 * @Description
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FrozenReq extends ApiBaseReq {

    @Schema(description = "用户id")
    private Long uid;

    @Schema(description = "冻结时间 （-1解冻）")
    private Integer days;

}

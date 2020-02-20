package com.oak.member.management.member.req;

import com.levin.commons.service.domain.Desc;
import com.oak.api.model.ApiBaseReq;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-17 16:55
 * @Description
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CheckPasswordReq extends ApiBaseReq {


    @Desc(value = "用户ID")
    @NotNull
    private Long uid;

    @Desc(value = "密码")
    @NotNull
    private String password;

}

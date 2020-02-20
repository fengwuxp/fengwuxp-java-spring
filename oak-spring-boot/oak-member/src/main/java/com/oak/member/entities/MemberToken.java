package com.oak.member.entities;


import com.oak.api.entities.system.ClientChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@Entity
@Schema(description = "会员登录的token信息")
@Table(name = "t_member_token")
@Data
@Accessors(chain = true)
public class MemberToken implements java.io.Serializable {

    private static final long serialVersionUID = -6312123664440141268L;

    @Schema(description = "会员id")
    @Id
    private Long id;

    @Schema(description = "登录令牌")
    @Column(name = "token", nullable = false, length = 512)
    private String token;

    @Schema(description = "刷新令牌")
    @Column(name = "refresh_token", length = 512)
    private String refreshToken;

    @Schema(description = "登录时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "login_time", nullable = false)
    private Date loginTime;

    @Schema(description = "token到期日期")
    @Column(name = "expiration_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Schema(description = "刷新token到期日期")
    @Column(name = "refresh_expiration_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date refreshExpirationDate;

    @Schema(description = "客户端类型")
    @JoinColumn(name = "client_channel")
    @ManyToOne(fetch = FetchType.EAGER)
    private ClientChannel clientChannel;


}

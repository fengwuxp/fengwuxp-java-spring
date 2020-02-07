package com.oak.rbac.security;

import com.wuxp.security.authenticate.PasswordUserDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OakUser extends User implements PasswordUserDetails {

    @Schema(description = "管理员ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号码")
    private String mobilePhone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "用于密码加密的盐")
    private String cryptoSalt;

    @Schema(description = "是否超管理")
    private Boolean root;

    @Schema(description = "登录token")
    private String token;

    @Schema(description = "token失效时间")
    private Date tokenExpired;

    private static final long serialVersionUID = -6068509382111627921L;


    public OakUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public OakUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.cryptoSalt = null;
    }
}

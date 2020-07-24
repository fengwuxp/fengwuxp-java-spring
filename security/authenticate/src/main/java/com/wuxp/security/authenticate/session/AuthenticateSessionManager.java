package com.wuxp.security.authenticate.session;


import com.wuxp.api.ApiResp;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * 在线会话管理者
 * <>
 * 1：用于通过token交换，存储，移除用户信息
 * 2：支持一个账号多个token
 * 3：支持尝试踢出用户
 * 4：支持获取用户被踢出的原因
 * </>
 *
 * @author wuxp
 */
public interface AuthenticateSessionManager<T extends UserDetails> {


    /**
     * 通过token获取用户信息
     * 优先从缓存中获取，如果没有，尝试去数据库中获取
     * 从数据库中获取成功，将对应信息加入缓存中
     *
     * @param token 用户登录的token
     * @return token无效，或已过期 <code>null</code>
     */
    T get(String token);


    /**
     * 把用户加入会话管理器
     * <>
     * 1：生成token
     * 2: 生成refresh token
     * </>
     *
     * @param userDetails 用户登录信息
     * @return <T>
     */
    T join(T userDetails);

    /**
     * 刷新token
     * <>
     * 1：判断refreshToken 是否过期
     * 2：通过refreshToken 交换用户信息
     * 3：生成新的用户token，并加入处理
     * </>
     * 如果refreshToken过期，则不处理
     *
     * @param refreshToken 用户的refreshToken
     * @param clientCode   客户端代码
     * @return <code>null</code> 刷新失败，else 刷新后的用户信息
     */
    T refreshToken(String refreshToken, String clientCode);

    /**
     * 正常移除在线用户，一般是退出登录使用
     *
     * @param token 用户登录的token
     */
    void remove(String token);

    /**
     * 获取当前用户同时登录的会话个数
     *
     * @param userName   用户id
     * @param clientCode 客户端代码 可以为null
     * @return 登录的个数
     */
    int getCurrentSessions(@NotNull String userName, String clientCode);


    /**
     * 获取当前用户同时登录的会话个数
     *
     * @param userName   用户名称
     * @param clientCode 客户端代码 可以为null
     * @return 当前账号的token列表
     */
    Collection<String> getTokens(String userName, String clientCode);

    /**
     * 获取当前用户同时登录的会话个数
     *
     * @param userName   用户名称
     * @param clientCode 客户端代码 可以为null
     * @return 当前账号已登录的用户列表
     */
    Collection<T> getUserDetailsList(String userName, String clientCode);


    /**
     * 尝试踢出用户
     *
     * @param token  用户的登录token
     * @param reason 踢出用户的原因
     */
    void tryKickOut(String token, String reason);

    /**
     * 尝试踢出用户
     *
     * @param userDetails 用户的登录信息
     * @param reason      踢出用户的原因
     */
    void tryKickOut(T userDetails, String reason);

    /**
     * 尝试获取被踢出登录的原因，如果存在，返回被踢出的描述信息，
     * 并移除该描述
     *
     * @param token 用户登录的token
     * @return 踢出用户的原因和业务编码
     */
    ApiResp<Void> tryGetKickOutReason(String token);
}

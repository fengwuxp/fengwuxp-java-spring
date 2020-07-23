package com.wuxp.security.authenticate.session;


import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.authenticate.PasswordUserDetails;
import com.wuxp.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuxp
 */
public abstract class AbstractAuthenticateSessionManager<T extends PasswordUserDetails> implements AuthenticateSessionManager<T>, InitializingBean, BeanFactoryAware {

    /**
     * 踢出用户的业务错误编码
     */
    protected final static int KICK_OUT_USER_ERROR_CODE = 200001;

    protected BeanFactory beanFactory;

    protected JwtTokenProvider jwtTokenProvider;

    protected CacheManager cacheManager;

    protected UserDetailsService userDetailsService;

    protected Class<T> userClassType;


    public AbstractAuthenticateSessionManager(Class<T> userClassType) {
        this.userClassType = userClassType;
    }

    @Override
    public T get(String token) {
        Cache cache = this.cacheManager.getCache(this.getUserCacheName());
        T user = cache.get(token, userClassType);
        if (user != null) {
            return user;
        }
        user = this.findUserByToken(token);
        cache.put(token, user);
        return user;
    }

    @Override
    public T join(String token, T userDetails) {

        // 把用户登录信息加入缓存
        Cache userCache = this.cacheManager.getCache(this.getUserCacheName());
        userCache.put(token, userDetails);

        // 增加一条缓存条目
        Cache cache = this.getTokenCache(userDetails.getClientCode());
        List<String> tokens = cache.get(userDetails.getUsername(), List.class);
        if (tokens == null) {
            tokens = new ArrayList<>(1);
        }
        tokens.add(token);
        cache.put(userDetails.getUsername(), tokens);
        return userDetails;
    }

    @Override
    public void remove(String token) {
        // 更新数据库的token记录
        String username = this.getUsername(token);
        if (username == null) {
            return;
        }

        T user = this.get(token);
        if (user == null) {
            return;
        }
        this.removeDbToken(user);
        Cache userCache = this.cacheManager.getCache(this.getUserCacheName());
        // 移除掉缓存中的用户
        userCache.evict(token);
        // 移除缓存中的token
        Cache cache = this.getTokenCache(user.getClientCode());
        List<String> tokens = cache.get(username, List.class);
        if (tokens == null) {
            return;
        }
        tokens.remove(token);
        cache.put(username, tokens);

    }

    @Override
    public int getCurrentSessions(@NotNull String userName, String clientCode) {
        Collection<String> tokens = this.getTokens(userName, clientCode);
        return tokens.size();
    }

    @Override
    public Collection<String> getTokens(String userName, String clientCode) {
        Cache cache = this.getTokenCache(clientCode);
        List<String> tokens = cache.get(userName, List.class);
        if (tokens == null) {
            // 查询数据库
            // TODO 同步处理
            tokens = this.getTokensByDb(userName, clientCode);
            cache.put(userName, tokens);
        }
        return tokens;
    }

    @Override
    public Collection<T> getUserDetailsList(String userName, String clientCode) {
        Collection<String> tokens = this.getTokens(userName, clientCode);
        return tokens.stream().map(this::get)
                .collect(Collectors.toList());
    }

    @Override
    public void tryKickOut(String token, String reason) {
        this.remove(token);
        // 写入用户被踢出的原因
        Cache cache = this.cacheManager.getCache(this.getKickOutReasonCacheName());
        cache.put(token, RestfulApiRespFactory.error(reason, this.getKickOutUserErrorCode(), null));
    }


    @Override
    public ApiResp<Void> tryGetKickOutReason(String token) {
        Cache cache = this.cacheManager.getCache(this.getKickOutReasonCacheName());
        return cache.get(token, ApiResp.class);
    }

    /**
     * 获取token的缓存
     *
     * @param clientCode 客户端code
     * @return 缓存对象
     */
    protected Cache getTokenCache(String clientCode) {
        return cacheManager.getCache(MessageFormat.format("{0}{1}", this.getTokenCacheName(), clientCode == null ? "" : "_" + clientCode));
    }


    /**
     * 通过用户名获取用户登录信息
     *
     * @param token token
     * @return 用户登录信息。包括token信息
     */
    protected abstract T findUserByToken(String token);

    /**
     * 移除掉数据库中的token
     *
     * @param user 需要移除登录的用户
     */
    protected abstract void removeDbToken(T user);

    /**
     * 获取数据库中的token
     *
     * @param userName   用户名
     * @param clientCode 客户端code
     * @return token 列表
     */
    protected abstract List<String> getTokensByDb(String userName, String clientCode);


    /**
     * 获取用户缓存名称
     *
     * @return 用户缓存名称
     */
    protected abstract String getUserCacheName();

    /**
     * 获取保存token的缓存名称
     *
     * @return 保存token的缓存名称
     */
    protected abstract String getTokenCacheName();

    /**
     * 获取踢出用户原因的缓存名称
     *
     * @return 踢出用户原因的缓存名称
     */
    protected abstract String getKickOutReasonCacheName();


    /**
     * 解析token 获取用户名
     *
     * @param token
     * @return
     */
    protected String getUsername(String token) {
        Jws<Claims> jws = null;
        try {
            jws = jwtTokenProvider.parse(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return jws.getBody().getAudience();
    }


    /**
     * 获取账号被踢出的业务响应错误码
     *
     * @return
     */
    protected int getKickOutUserErrorCode() {
        return KICK_OUT_USER_ERROR_CODE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanFactory beanFactory = this.beanFactory;
        if (this.jwtTokenProvider == null) {
            this.jwtTokenProvider = beanFactory.getBean(JwtTokenProvider.class);
        }

        if (this.cacheManager == null) {
            this.cacheManager = beanFactory.getBean(CacheManager.class);
        }

        if (this.userDetailsService == null) {
            this.userDetailsService = beanFactory.getBean(UserDetailsService.class);
        }
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}

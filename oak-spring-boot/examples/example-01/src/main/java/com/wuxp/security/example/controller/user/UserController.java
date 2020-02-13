package com.wuxp.security.example.controller.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/users")
@Slf4j
@Tag(name = "用户", description = "用户相关")
public class UserController {





    @GetMapping("/my")
//    @ResponseBody
    public UserDetails getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {

        log.info("当前用户信息{}", userDetails);

        /**
         SecurityContextHolder.getContext()获取安全上下文对象，就是那个保存在 ThreadLocal 里面的安全上下文对象
         总是不为null(如果不存在，则创建一个authentication属性为null的empty安全上下文对象)
         获取当前认证了的 principal(当事人),或者 request token (令牌)
         如果没有认证，会是 null,该例子是认证之后的情况
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //有登陆用户就返回登录用户，没有就返回null
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                Object principal = authentication.getPrincipal();
                return (UserDetails) principal;
            }
        }

        return userDetails;
    }

    @GetMapping("/user1")
    public Principal user1(Principal principal) {
        return principal;
    }

    @GetMapping("/user2")
    public Authentication user2(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/user3")
    public Principal user3(HttpServletRequest request) {
        return request.getUserPrincipal();
    }

    @GetMapping("/user4")
    public Authentication user4() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}

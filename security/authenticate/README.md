

#### 认证流程

- 密码登录
```
   常规的登录方式，使用用户名(邮箱、手机号码)、密码进行登录。
   1：默认不开启人机验证验证
   2：如果登录环境检查需要进行人机验证，则页面需要展示验证码
   3：登录成功后返回token和refresh_token
   4：access_token将要过期但是使用refresh_token进行刷新token

```
- 手机验证码
```
```
- 扫码登录
- 谷歌验证
- Oauth2
- 第三方平台授权

##### 登录环境检查

- 常用登录IP检查
- 常在地区检查
- 常用设备检查（App端检查）
- 同一个会话是否多次登录失败



#### 参考资料
- [SpringSecurity原理剖析与权限系统设计](https://www.cnblogs.com/fanzhidongyzby/archive/2019/09/29/11610334.html)
- [Springboot + Spring Security 实现前后端分离登录认证及权限控制](https://blog.csdn.net/I_am_Hutengfei/article/details/100561564)
- [Spring Security 自定义PermissionEvaluator 进行数据权限校验和访问限制](https://www.jianshu.com/p/acad97bcaea6)
- [spring security 核心 -- AbstractSecurityInterceptor](https://www.jianshu.com/p/5f03d17212c6)
- [Spring security(五)-完美权限管理系统（授权过程分析）](https://juejin.im/post/5d0a0398e51d45772a49ad4e)
- [Ccww 博客](https://juejin.im/user/5cd27385e51d453f146bb8e7/posts)
- [Spring Security（19）——对Acl的支持](https://blog.csdn.net/elim168/article/details/73368953)
- [Spring Security（16）——基于表达式的权限控制](https://www.cnblogs.com/fenglan/p/5913463.html)

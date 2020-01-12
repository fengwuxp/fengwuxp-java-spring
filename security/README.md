
#### spring security 集成

- [Spring Security 实战干货](https://felord.cn/categories/spring-security/)

#####  验证
```
  常见的验证方式：
  图片验证码
  手机验证码
  邮箱验证
  滑动验证

```
- 认证
```
  密码登录
  手机验证码
  第三方登录
  Oauth2
```
#### 授权
- 资源抽象
```
 1：所有的实体（表）都是一种资源，通过资源提供者可以加载到对应的资源
 2：权限是一种特殊的资源
```
- url权限
```
  1：url是一种资源
  2: 在应用启动时扫描所有的控制器，将url权限保存到数据库
```
```
  权限（Permission） = 资源（Resource） + 操作（Privilege）
  角色（Role） = 权限的集合（a set of low-level permissions）
  用户（User） = 角色的集合（high-level roles
```
- 资源抽象
```
 所有的都是资源，资源按照功能模块、特征类型来区分，资源有用唯一标识
 通过资源提供者来维护和加载资源

```


#### 参数验证
- [SpringBoot使用Validation校验参数](https://blog.csdn.net/justry_deng/article/details/86571671)

#### springdoc
- [springdoc-openapi](https://github.com/springdoc/springdoc-openapi)
- [springboot集成springdoc-openapi-ui](https://www.jianshu.com/p/b6f31966c5e1)


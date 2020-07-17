

#### api支持
- restful 统一处理
- 接口签名验证
- 参数注入支持

#### 接口签名处理
- [开放API接口签名验证](https://blog.csdn.net/long_chuanren/article/details/82851031)
- 表单去重支持

#### restful 接口错误处理
- 统一返回2xx响应，业务异常使用异常码区分
```
 
   {
      errorCode:0,
      errorMessage:"",
      data: "xxxx"
   }

```
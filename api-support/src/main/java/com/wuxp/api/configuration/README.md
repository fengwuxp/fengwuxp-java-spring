

#### AspectJExpressionPointcut
```java
  /**
     * 在多个表达式之间使用  || , or 表示  或 ，使用  && , and 表示  与 ， ！ 表示 非
     */
    private static final String DEFAULT_POINT_CUT = StringUtils.join(
            "@annotation(org.springframework.web.bind.annotation.GetMapping) ",
            "or @annotation(org.springframework.web.bind.annotation.PostMapping) ",
            "or @annotation(org.springframework.web.bind.annotation.PutMapping) ",
            "or @annotation(org.springframework.web.bind.annotation.DeleteMapping) ",
            "or @annotation(org.springframework.web.bind.annotation.RequestMapping)"
    );

    @Bean
    @ConditionalOnBean(ApiInterceptor.class)
    public DefaultPointcutAdvisor logAopPointCutAdvice() {
        //声明一个AspectJ切点
        AspectJExpressionPointcut pointcut = new ApiSupportAspectjExpressionPointcut();
        //设置切点表达式
        pointcut.setExpression(DEFAULT_POINT_CUT);

        // 配置增强类advisor, 切面=切点+增强
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        //设置切点
        advisor.setPointcut(pointcut);
        //设置增强（Advice）
        advisor.setAdvice(apiInterceptor());
        //设置增强拦截器执行顺序
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

```
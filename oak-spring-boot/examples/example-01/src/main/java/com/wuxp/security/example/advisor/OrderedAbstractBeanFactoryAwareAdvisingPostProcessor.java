package com.wuxp.security.example.advisor;

import com.wuxp.api.log.ApiLog;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;

public class OrderedAbstractBeanFactoryAwareAdvisingPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor
        implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        Pointcut pointcut = new AnnotationMatchingPointcut(ApiLog.class, true);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new OrderedMethodInterceptor());
        advisor.setOrder(1);
        this.advisor = advisor;
    }
}

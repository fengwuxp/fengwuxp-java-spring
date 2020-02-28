package com.wuxp.security.captcha;

import com.wuxp.security.captcha.configuration.WuxpCaptchaProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;

/**
 * 抽象的验证码Bean
 */
@Slf4j
@Setter
public abstract class AbstractCaptchaBean implements BeanFactoryAware, InitializingBean {

    protected BeanFactory beanFactory;

    protected CaptchaStore captchaStore;

    protected MessageSource messageSource;

    protected WuxpCaptchaProperties wuxpCaptchaProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanFactory beanFactory = this.beanFactory;
        if (this.captchaStore == null) {
            this.captchaStore = beanFactory.getBean(CaptchaStore.class);
        }
        if (this.messageSource == null) {
            this.messageSource = beanFactory.getBean(MessageSource.class);
        }
        if (this.wuxpCaptchaProperties == null) {
            this.wuxpCaptchaProperties = beanFactory.getBean(WuxpCaptchaProperties.class);
        }

    }
}

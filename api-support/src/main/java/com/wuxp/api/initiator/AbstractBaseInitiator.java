package com.wuxp.api.initiator;

import com.wuxp.api.configuration.WuxpApiSupportProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.List;

/**
 * 抽象的 initiator 用于初始化某一个表对象或者是持久化对象 {@link AbstractBaseInitiator#init}
 * 支持同步或者一次 {@link AbstractBaseInitiator#isAsync}
 *
 * @param <T>
 * @author wxup
 */
@Slf4j
@Setter
public abstract class AbstractBaseInitiator<T> implements ApplicationListener<ApplicationStartedEvent>, InitializingBean, BeanFactoryAware {

    protected List<T> initData;

    protected BeanFactory beanFactory;

    /**
     * 默认使用异步执行
     */
    protected boolean isAsync = true;

    protected ThreadPoolTaskScheduler threadPoolTaskScheduler;

    protected volatile boolean isInit = false;

    protected WuxpApiSupportProperties wuxpApiSupportProperties;

    /**
     * 用于初始化某一个表对象或者是持久化对象
     */
    public void init() {
        List<T> data = this.initData;
        if (data == null || data.isEmpty()) {
            return;
        }
        long count = data.stream().map(this::initSingleItem).filter(success -> success).count();
        log.info("{}：初始化成功{}条记录", this.getClass().getSimpleName(), count);
    }

    /**
     * 初始化单条记录
     *
     * @param data 初始化数据
     * @return 是否初始化成功
     */
    protected abstract boolean initSingleItem(T data);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        if (!Boolean.TRUE.equals(wuxpApiSupportProperties.getEnabledInitiator())) {
            log.info("initiator not enabled");
            return;
        }
        if (this.isInit) {
            return;
        }

        if (this.isAsync && this.threadPoolTaskScheduler != null) {
            this.threadPoolTaskScheduler.execute(this::init);
        } else {
            init();
        }
    }


    @Override
    public void afterPropertiesSet() {
        BeanFactory factory = this.beanFactory;
        if (this.threadPoolTaskScheduler == null) {
            try {
                this.threadPoolTaskScheduler = factory.getBean(ThreadPoolTaskScheduler.class);
            } catch (BeansException e) {
                e.printStackTrace();
                this.isAsync = false;
            }
        }
        if (this.wuxpApiSupportProperties == null) {
            this.wuxpApiSupportProperties = factory.getBean(WuxpApiSupportProperties.class);
        }

    }

    public void setInitData(List<T> initData) {
        this.initData = initData;
    }

    /**
     * 提供给子类在 init 方法中使用
     * @return 获取初始化数据
     */
    protected List<T> getInitData() {
        return initData;
    }

}

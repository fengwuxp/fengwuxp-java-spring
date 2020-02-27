package com.wuxp.api.initiator;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象的 initiator
 *
 * @param <T>
 */
@Slf4j
@Setter
public abstract class AbstractBaseInitiator<T> implements ApplicationListener<ApplicationStartedEvent>, InitializingBean, BeanFactoryAware {

    protected List<T> initData;

    protected BeanFactory beanFactory;

    // 默认使用异步执行
    protected boolean isAsync = true;

    protected ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private static final Map<Class<? extends AbstractBaseInitiator>, Boolean> INITIATOR_MAP = new ConcurrentHashMap<>();

    public abstract void init();

    @Override
    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {


        Class<? extends AbstractBaseInitiator> aClass = this.getClass();

        Boolean isInit = INITIATOR_MAP.computeIfAbsent(aClass, k -> false);
        if (isInit) {
            return;
        }
        INITIATOR_MAP.put(aClass, true);

        if (this.isAsync && this.threadPoolTaskScheduler != null) {
            this.threadPoolTaskScheduler.execute(this::init);
        } else {
            init();
        }


    }

    public List<T> getInitData() {
        return initData;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.threadPoolTaskScheduler == null) {
            try {
                this.threadPoolTaskScheduler = this.beanFactory.getBean(ThreadPoolTaskScheduler.class);
            } catch (BeansException e) {
                e.printStackTrace();
                this.isAsync = false;
            }
        }
    }

    public void setInitData(List<T> initData) {
        this.initData = initData;
    }
}

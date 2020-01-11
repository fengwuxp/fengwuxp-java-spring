package com.wuxp.api.initiator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象的 initiator
 * @param <T>
 */
@Slf4j
public abstract class AbstractBaseInitiator<T> implements ApplicationListener<ApplicationStartedEvent> {

    protected List<T> initData;


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


        init();


    }

    public List<T> getInitData() {
        return initData;
    }

    public void setInitData(List<T> initData) {
        this.initData = initData;
    }
}

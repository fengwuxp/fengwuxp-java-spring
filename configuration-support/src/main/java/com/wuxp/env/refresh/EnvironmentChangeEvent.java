package com.wuxp.env.refresh;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * @author wuxp
 */
@Getter
public class EnvironmentChangeEvent extends ApplicationEvent {

    private final Set<String> keys;

    private final EnvironmentRefreshScope refreshScope;

    public EnvironmentChangeEvent(Object source, Set<String> keys, EnvironmentRefreshScope refreshScope) {
        super(source);
        this.keys = keys;
        this.refreshScope = refreshScope;
    }

}

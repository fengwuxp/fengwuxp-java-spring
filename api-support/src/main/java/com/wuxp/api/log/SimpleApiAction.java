package com.wuxp.api.log;

import java.lang.reflect.Method;

/**
 * @author wxup
 */
public enum SimpleApiAction {

    /**
     * 创建
     */
    CREATE,

    /**
     * 更新
     */
    UPDATE,

    /**
     * 删除
     */
    DELETED;

    public static SimpleApiAction valueOfByMethod(Method method) {
        String name = method.getName();
        if (name.startsWith("crate")) {
            return SimpleApiAction.CREATE;
        }


        if (name.startsWith("update")) {
            return SimpleApiAction.UPDATE;
        }

        if (name.startsWith("delete")) {
            return SimpleApiAction.DELETED;
        }
        return null;
    }
}

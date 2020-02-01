package com.wuxp.api.log;

import java.lang.reflect.Method;

public enum SimpleApiAction {

    CREATE,

//    QUERY,

    EDIT,

    DELETED;

    public static SimpleApiAction valueOfByMethod(Method method) {
        String name = method.getName();
        if (name.startsWith("crate")) {
            return SimpleApiAction.CREATE;
        }


        if (name.startsWith("edit")) {
            return SimpleApiAction.DELETED;
        }

        if (name.startsWith("delete")) {
            return SimpleApiAction.DELETED;
        }
        return null;
    }
}

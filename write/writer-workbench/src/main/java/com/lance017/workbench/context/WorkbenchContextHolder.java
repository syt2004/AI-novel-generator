package com.lance017.workbench.context;

import cn.hutool.core.util.ObjectUtil;

public class WorkbenchContextHolder {
    public static final ThreadLocal<WorkbenchContext> contextHolder = new ThreadLocal<>();

    public static void setContext(WorkbenchContext context) {
        contextHolder.set(context);
    }

    public static WorkbenchContext getContext() {
        WorkbenchContext workbenchContext = contextHolder.get();
        if (ObjectUtil.isEmpty(workbenchContext)) {
            workbenchContext = new WorkbenchContext();
            setContext(workbenchContext);
        }
        return workbenchContext;
    }

}

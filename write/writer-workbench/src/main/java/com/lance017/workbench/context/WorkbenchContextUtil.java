package com.lance017.workbench.context;

import com.lance017.system.domain.WorkUser;

public class WorkbenchContextUtil {

    public static WorkUser getWorkUser() {
        return WorkbenchContextHolder.getContext().getWorkbenchUser();
    }

}

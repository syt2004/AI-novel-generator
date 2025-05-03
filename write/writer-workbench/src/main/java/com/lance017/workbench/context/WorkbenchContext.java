package com.lance017.workbench.context;

import com.lance017.system.domain.WorkUser;

public class WorkbenchContext {

    private WorkUser workUser = new WorkUser();

    public void setWorkbenchUser(WorkUser workUser) {
        this.workUser = workUser;
    }

    public WorkUser getWorkbenchUser() {
        return this.workUser;
    }


}

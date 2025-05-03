package com.lance017.workbench.service;

import com.lance017.workbench.domain.request.*;

public interface ApiRoleService {

    /**
     * 新增角色
     * @param request
     */
    Object add(RoleAddRequest request);


//    Object infoRole(BookInfoRequest request);

    /**
     * 更新角色信息
     * @param request
     */
    void update(RoleUpdateRequest request);


    /**
     * 删除角色
     * @param request
     */
    void delete(RoleDeleteRequest request);


    /**
     * 获取作品列表
     * @return
     */
    Object list(RoleListRequest request);

    /**
     * 获取角色列表
     * @param request
     * @return
     */
    Object listByAi(RoleListRequest request);


    int aiAdd(RoleAIAddRequest request);

}

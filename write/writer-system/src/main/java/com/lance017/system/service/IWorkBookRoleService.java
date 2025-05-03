package com.lance017.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lance017.system.domain.WorkBookRole;

/**
 * 小说角色Service接口
 *
 * @author writer
 * @date 2024-10-05
 */
public interface IWorkBookRoleService extends IService<WorkBookRole> {
    /**
     * 查询小说角色
     *
     * @param id 小说角色主键
     * @return 小说角色
     */
    WorkBookRole selectWorkBookRoleById(Long id);

    /**
     * 查询小说角色列表
     *
     * @param workBookRole 小说角色
     * @return 小说角色集合
     */
    List<WorkBookRole> selectWorkBookRoleList(WorkBookRole workBookRole);

    /**
     * 新增小说角色
     *
     * @param workBookRole 小说角色
     * @return 结果
     */
    int insertWorkBookRole(WorkBookRole workBookRole);

    /**
     * 修改小说角色
     *
     * @param workBookRole 小说角色
     * @return 结果
     */
    int updateWorkBookRole(WorkBookRole workBookRole);

    /**
     * 批量删除小说角色
     *
     * @param ids 需要删除的小说角色主键集合
     * @return 结果
     */
    int deleteWorkBookRoleByIds(Long[] ids);

    /**
     * 删除小说角色信息
     *
     * @param id 小说角色主键
     * @return 结果
     */
    int deleteWorkBookRoleById(Long id);
}

package com.lance017.system.service;

import java.util.List;
import com.lance017.system.domain.WorkUser;

/**
 * workUserService接口
 *
 * @author writer
 * @date 2024-09-28
 */
public interface IWorkUserService {
    /**
     * 查询workUser
     *
     * @param id workUser主键
     * @return workUser
     */
    WorkUser selectWorkUserById(Long id);

    /**
     * 查询workUser列表
     *
     * @param workUser workUser
     * @return workUser集合
     */
    List<WorkUser> selectWorkUserList(WorkUser workUser);

    /**
     * 新增workUser
     *
     * @param workUser workUser
     * @return 结果
     */
    int insertWorkUser(WorkUser workUser);

    /**
     * 修改workUser
     *
     * @param workUser workUser
     * @return 结果
     */
    int updateWorkUser(WorkUser workUser);

    /**
     * 批量删除workUser
     *
     * @param ids 需要删除的workUser主键集合
     * @return 结果
     */
    int deleteWorkUserByIds(Long[] ids);

    /**
     * 删除workUser信息
     *
     * @param id workUser主键
     * @return 结果
     */
    int deleteWorkUserById(Long id);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    WorkUser selectWorkUserByUsername(String username);

    /**
     * 根据用户名查询用户信息
     * @param nickname
     * @return
     */
    WorkUser selectWorkUserByNickName(String nickname);

}

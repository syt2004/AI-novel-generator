package com.lance017.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lance017.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lance017.system.mapper.WorkUserMapper;
import com.lance017.system.domain.WorkUser;
import com.lance017.system.service.IWorkUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * workUserService业务层处理
 *
 * @author writer
 * @date 2024-09-28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkUserServiceImpl implements IWorkUserService {

    private final WorkUserMapper workUserMapper;

    /**
     * 查询workUser
     *
     * @param id workUser主键
     * @return workUser
     */
    @Override
    public WorkUser selectWorkUserById(Long id) {
        return workUserMapper.selectById(id);
    }

    /**
     * 查询workUser列表
     *
     * @param workUser workUser
     * @return workUser
     */
    @Override
    public List<WorkUser> selectWorkUserList(WorkUser workUser) {
        return workUserMapper.selectWorkUserList(workUser);
    }

    /**
     * 新增workUser
     *
     * @param workUser workUser
     * @return 结果
     */
    @Override
    public int insertWorkUser(WorkUser workUser) {
        workUser.setCreateTime(DateUtils.getNowDate());
        return workUserMapper.insert(workUser);
    }

    /**
     * 修改workUser
     *
     * @param workUser workUser
     * @return 结果
     */
    @Override
    public int updateWorkUser(WorkUser workUser) {
        workUser.setUpdateTime(DateUtils.getNowDate());
        return workUserMapper.updateById(workUser);
    }

    /**
     * 批量删除workUser
     *
     * @param ids 需要删除的workUser主键
     * @return 结果
     */
    @Override
    public int deleteWorkUserByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workUserMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除workUser信息
     *
     * @param id workUser主键
     * @return 结果
     */
    @Override
    public int deleteWorkUserById(Long id) {
        return workUserMapper.deleteById(id);
    }


    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public WorkUser selectWorkUserByUsername(String username) {
        return workUserMapper.selectOne(
                new LambdaQueryWrapper<WorkUser>()
                        .eq(WorkUser::getUsername, username)
        );
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param nickname
     * @return
     */
    @Override
    public WorkUser selectWorkUserByNickName(String nickname) {
        return workUserMapper.selectOne(
                new LambdaQueryWrapper<WorkUser>()
                        .eq(WorkUser::getNickname, nickname)
        );
    }
}

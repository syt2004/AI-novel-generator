package com.lance017.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lance017.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lance017.system.mapper.WorkBookRoleMapper;
import com.lance017.system.domain.WorkBookRole;
import com.lance017.system.service.IWorkBookRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * 小说角色Service业务层处理
 *
 * @author writer
 * @date 2024-10-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkBookRoleServiceImpl extends ServiceImpl<WorkBookRoleMapper, WorkBookRole> implements IWorkBookRoleService {

    private final WorkBookRoleMapper workBookRoleMapper;

    /**
     * 查询小说角色
     *
     * @param id 小说角色主键
     * @return 小说角色
     */
    @Override
    public WorkBookRole selectWorkBookRoleById(Long id) {
        return workBookRoleMapper.selectById(id);
    }

    /**
     * 查询小说角色列表
     *
     * @param workBookRole 小说角色
     * @return 小说角色
     */
    @Override
    public List<WorkBookRole> selectWorkBookRoleList(WorkBookRole workBookRole) {
        return workBookRoleMapper.selectWorkBookRoleList(workBookRole);
    }

    /**
     * 新增小说角色
     *
     * @param workBookRole 小说角色
     * @return 结果
     */
    @Override
    public int insertWorkBookRole(WorkBookRole workBookRole) {
        workBookRole.setCreateTime(DateUtils.getNowDate());
        return workBookRoleMapper.insert(workBookRole);
    }

    /**
     * 修改小说角色
     *
     * @param workBookRole 小说角色
     * @return 结果
     */
    @Override
    public int updateWorkBookRole(WorkBookRole workBookRole) {
        workBookRole.setUpdateTime(DateUtils.getNowDate());
        return workBookRoleMapper.updateById(workBookRole);
    }

    /**
     * 批量删除小说角色
     *
     * @param ids 需要删除的小说角色主键
     * @return 结果
     */
    @Override
    public int deleteWorkBookRoleByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workBookRoleMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除小说角色信息
     *
     * @param id 小说角色主键
     * @return 结果
     */
    @Override
    public int deleteWorkBookRoleById(Long id) {
        return workBookRoleMapper.deleteById(id);
    }
}

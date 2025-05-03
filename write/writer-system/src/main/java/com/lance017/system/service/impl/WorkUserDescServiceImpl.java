package com.lance017.system.service.impl;

import java.util.List;
import com.lance017.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.lance017.system.mapper.WorkUserDescMapper;
import com.lance017.system.domain.WorkUserDesc;
import com.lance017.system.service.IWorkUserDescService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 用户关联idService业务层处理
 *
 * @author writer
 * @date 2024-10-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkUserDescServiceImpl extends ServiceImpl<WorkUserDescMapper, WorkUserDesc> implements IWorkUserDescService {

    private final WorkUserDescMapper workUserDescMapper;

    /**
     * 查询用户关联id
     *
     * @param id 用户关联id主键
     * @return 用户关联id
     */
    @Override
    public WorkUserDesc selectWorkUserDescById(Long id) {
        return workUserDescMapper.selectById(id);
    }

    /**
     * 查询用户关联id列表
     *
     * @param workUserDesc 用户关联id
     * @return 用户关联id
     */
    @Override
    public List<WorkUserDesc> selectWorkUserDescList(WorkUserDesc workUserDesc) {
        return workUserDescMapper.selectWorkUserDescList(workUserDesc);
    }

    /**
     * 新增用户关联id
     *
     * @param workUserDesc 用户关联id
     * @return 结果
     */
    @Override
    public int insertWorkUserDesc(WorkUserDesc workUserDesc) {
        workUserDesc.setCreateTime(DateUtils.getNowDate());
        return workUserDescMapper.insert(workUserDesc);
    }

    /**
     * 修改用户关联id
     *
     * @param workUserDesc 用户关联id
     * @return 结果
     */
    @Override
    public int updateWorkUserDesc(WorkUserDesc workUserDesc) {
        workUserDesc.setUpdateTime(DateUtils.getNowDate());
        return workUserDescMapper.updateById(workUserDesc);
    }

    /**
     * 批量删除用户关联id
     *
     * @param ids 需要删除的用户关联id主键
     * @return 结果
     */
    @Override
    public int deleteWorkUserDescByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workUserDescMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除用户关联id信息
     *
     * @param id 用户关联id主键
     * @return 结果
     */
    @Override
    public int deleteWorkUserDescById(Long id) {
        return workUserDescMapper.deleteById(id);
    }
}

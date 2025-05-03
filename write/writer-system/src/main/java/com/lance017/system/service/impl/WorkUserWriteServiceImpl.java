package com.lance017.system.service.impl;

import java.util.List;
import com.lance017.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.lance017.system.mapper.WorkUserWriteMapper;
import com.lance017.system.domain.WorkUserWrite;
import com.lance017.system.service.IWorkUserWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 用户生成Service业务层处理
 *
 * @author writer
 * @date 2024-10-09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkUserWriteServiceImpl extends ServiceImpl<WorkUserWriteMapper, WorkUserWrite> implements IWorkUserWriteService {

    private final WorkUserWriteMapper workUserWriteMapper;

    /**
     * 查询用户生成
     *
     * @param id 用户生成主键
     * @return 用户生成
     */
    @Override
    public WorkUserWrite selectWorkUserWriteById(Long id) {
        return workUserWriteMapper.selectById(id);
    }

    /**
     * 查询用户生成列表
     *
     * @param workUserWrite 用户生成
     * @return 用户生成
     */
    @Override
    public List<WorkUserWrite> selectWorkUserWriteList(WorkUserWrite workUserWrite) {
        return workUserWriteMapper.selectWorkUserWriteList(workUserWrite);
    }

    /**
     * 新增用户生成
     *
     * @param workUserWrite 用户生成
     * @return 结果
     */
    @Override
    public int insertWorkUserWrite(WorkUserWrite workUserWrite) {
        workUserWrite.setCreateTime(DateUtils.getNowDate());
        return workUserWriteMapper.insert(workUserWrite);
    }

    /**
     * 修改用户生成
     *
     * @param workUserWrite 用户生成
     * @return 结果
     */
    @Override
    public int updateWorkUserWrite(WorkUserWrite workUserWrite) {
        workUserWrite.setUpdateTime(DateUtils.getNowDate());
        return workUserWriteMapper.updateById(workUserWrite);
    }

    /**
     * 批量删除用户生成
     *
     * @param ids 需要删除的用户生成主键
     * @return 结果
     */
    @Override
    public int deleteWorkUserWriteByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workUserWriteMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除用户生成信息
     *
     * @param id 用户生成主键
     * @return 结果
     */
    @Override
    public int deleteWorkUserWriteById(Long id) {
        return workUserWriteMapper.deleteById(id);
    }
}

package com.lance017.system.service.impl;

import java.util.List;
import com.lance017.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.lance017.system.mapper.WorkDescMapper;
import com.lance017.system.domain.WorkDesc;
import com.lance017.system.service.IWorkDescService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 提示词Service业务层处理
 *
 * @author writer
 * @date 2024-10-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkDescServiceImpl extends ServiceImpl<WorkDescMapper, WorkDesc> implements IWorkDescService {

    private final WorkDescMapper workDescMapper;

    /**
     * 查询提示词
     *
     * @param id 提示词主键
     * @return 提示词
     */
    @Override
    public WorkDesc selectWorkDescById(Long id) {
        return workDescMapper.selectById(id);
    }

    /**
     * 查询提示词列表
     *
     * @param workDesc 提示词
     * @return 提示词
     */
    @Override
    public List<WorkDesc> selectWorkDescList(WorkDesc workDesc) {
        return workDescMapper.selectWorkDescList(workDesc);
    }

    /**
     * 新增提示词
     *
     * @param workDesc 提示词
     * @return 结果
     */
    @Override
    public int insertWorkDesc(WorkDesc workDesc) {
        workDesc.setCreateTime(DateUtils.getNowDate());
        return workDescMapper.insert(workDesc);
    }

    /**
     * 修改提示词
     *
     * @param workDesc 提示词
     * @return 结果
     */
    @Override
    public int updateWorkDesc(WorkDesc workDesc) {
        workDesc.setUpdateTime(DateUtils.getNowDate());
        return workDescMapper.updateById(workDesc);
    }

    /**
     * 批量删除提示词
     *
     * @param ids 需要删除的提示词主键
     * @return 结果
     */
    @Override
    public int deleteWorkDescByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workDescMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除提示词信息
     *
     * @param id 提示词主键
     * @return 结果
     */
    @Override
    public int deleteWorkDescById(Long id) {
        return workDescMapper.deleteById(id);
    }
}

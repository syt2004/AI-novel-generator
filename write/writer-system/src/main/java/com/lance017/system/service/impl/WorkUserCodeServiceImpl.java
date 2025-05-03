package com.lance017.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lance017.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lance017.system.mapper.WorkUserCodeMapper;
import com.lance017.system.domain.WorkUserCode;
import com.lance017.system.service.IWorkUserCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * 用户兑换码Service业务层处理
 *
 * @author writer
 * @date 2024-10-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkUserCodeServiceImpl extends ServiceImpl<WorkUserCodeMapper, WorkUserCode> implements IWorkUserCodeService {

    private final WorkUserCodeMapper workUserCodeMapper;

    /**
     * 查询用户兑换码
     *
     * @param id 用户兑换码主键
     * @return 用户兑换码
     */
    @Override
    public WorkUserCode selectWorkUserCodeById(Long id) {
        return workUserCodeMapper.selectById(id);
    }

    /**
     * 查询用户兑换码列表
     *
     * @param workUserCode 用户兑换码
     * @return 用户兑换码
     */
    @Override
    public List<WorkUserCode> selectWorkUserCodeList(WorkUserCode workUserCode) {
        return workUserCodeMapper.selectWorkUserCodeList(workUserCode);
    }

    /**
     * 新增用户兑换码
     *
     * @param workUserCode 用户兑换码
     * @return 结果
     */
    @Override
    public int insertWorkUserCode(WorkUserCode workUserCode) {
        workUserCode.setCreateTime(DateUtils.getNowDate());
        return workUserCodeMapper.insert(workUserCode);
    }

    /**
     * 修改用户兑换码
     *
     * @param workUserCode 用户兑换码
     * @return 结果
     */
    @Override
    public int updateWorkUserCode(WorkUserCode workUserCode) {
        workUserCode.setUpdateTime(DateUtils.getNowDate());
        return workUserCodeMapper.updateById(workUserCode);
    }

    /**
     * 批量删除用户兑换码
     *
     * @param ids 需要删除的用户兑换码主键
     * @return 结果
     */
    @Override
    public int deleteWorkUserCodeByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workUserCodeMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除用户兑换码信息
     *
     * @param id 用户兑换码主键
     * @return 结果
     */
    @Override
    public int deleteWorkUserCodeById(Long id) {
        return workUserCodeMapper.deleteById(id);
    }
}

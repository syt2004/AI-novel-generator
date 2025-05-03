package com.lance017.system.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lance017.common.utils.DateUtils;
import com.lance017.common.utils.SecurityUtils;
import com.lance017.system.domain.WorkCode;
import com.lance017.system.domain.excel.WorkCodeExcel;
import com.lance017.system.enums.WorkCodeStatus;
import com.lance017.system.mapper.WorkCodeMapper;
import com.lance017.system.service.IWorkCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 兑换码Service业务层处理
 *
 * @author writer
 * @date 2024-10-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkCodeServiceImpl extends ServiceImpl<WorkCodeMapper, WorkCode> implements IWorkCodeService  {

    private final WorkCodeMapper workCodeMapper;

    /**
     * 查询兑换码
     *
     * @param id 兑换码主键
     * @return 兑换码
     */
    @Override
    public WorkCode selectWorkCodeById(Long id) {
        return workCodeMapper.selectById(id);
    }

    /**
     * 根据兑换码查询兑换码
     *
     * @param code
     * @return
     */
    @Override
    public WorkCode selectWorkCodeByCode(String code) {
        return workCodeMapper.selectOne(
                new LambdaQueryWrapper<WorkCode>().eq(WorkCode::getCode, code)
        );
    }

    /**
     * 查询兑换码列表
     *
     * @param workCode 兑换码
     * @return 兑换码
     */
    @Override
    public List<WorkCode> selectWorkCodeList(WorkCode workCode) {
        return workCodeMapper.selectWorkCodeList(workCode);
    }

    /**
     * 新增兑换码
     *
     * @param workCode 兑换码
     * @return 结果
     */
    @Override
    public int insertWorkCode(WorkCode workCode) {
        return workCodeMapper.insert(workCode);
    }

    /**
     * 修改兑换码
     *
     * @param workCode 兑换码
     * @return 结果
     */
    @Override
    public int updateWorkCode(WorkCode workCode) {
        workCode.setUpdateTime(DateUtils.getNowDate());
        return workCodeMapper.updateById(workCode);
    }

    /**
     * 批量删除兑换码
     *
     * @param ids 需要删除的兑换码主键
     * @return 结果
     */
    @Override
    public int deleteWorkCodeByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workCodeMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除兑换码信息
     *
     * @param id 兑换码主键
     * @return 结果
     */
    @Override
    public int deleteWorkCodeById(Long id) {
        return workCodeMapper.deleteById(id);
    }

    /**
     * 批量创建兑换码
     *
     * @param workCode
     * @return
     */
    @Override
    @Transactional
    public List<WorkCodeExcel> createWorkCode(WorkCode workCode) {
        List<WorkCode> workCodes = new ArrayList<>();
        for (int i = 0; i < workCode.getNum(); i++) {
            WorkCode code = new WorkCode();
            code.setCode(RandomUtil.randomString(32));
            code.setCreateTime(DateUtils.getNowDate());
            code.setCreateBy(SecurityUtils.getUsername());
            code.setWords(workCode.getWords());
            code.setType(workCode.getType());
            code.setStatus(WorkCodeStatus.NO_USED.getCode());
            workCodes.add(code);
        }
        boolean b = saveBatch(workCodes);
        List<WorkCodeExcel> workCodeExcels = new ArrayList<>();
        for (WorkCode workCode1 : workCodes) {
            WorkCodeExcel workCodeExcel = new WorkCodeExcel();
            workCodeExcel.setId(workCode1.getId());
            workCodeExcel.setCode(workCode1.getCode());
            workCodeExcel.setWords(workCode1.getWords());
            workCodeExcel.setType(workCode1.getType());
            workCodeExcel.setStatus(workCode1.getStatus());
            workCodeExcel.setCreateBy(SecurityUtils.getUsername());
            workCodeExcel.setCreateTime(workCode1.getCreateTime());
            workCodeExcels.add(workCodeExcel);
        }
        return workCodeExcels;
    }
}

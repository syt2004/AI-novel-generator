package com.lance017.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lance017.system.domain.WorkCode;
import com.lance017.system.domain.excel.WorkCodeExcel;

/**
 * 兑换码Service接口
 *
 * @author writer
 * @date 2024-10-05
 */
public interface IWorkCodeService extends IService<WorkCode> {
    /**
     * 查询兑换码
     *
     * @param id 兑换码主键
     * @return 兑换码
     */
    WorkCode selectWorkCodeById(Long id);


    /**
     * 根据兑换码查询兑换码
     * @param code
     * @return
     */
    WorkCode selectWorkCodeByCode(String code);

    /**
     * 查询兑换码列表
     *
     * @param workCode 兑换码
     * @return 兑换码集合
     */
    List<WorkCode> selectWorkCodeList(WorkCode workCode);

    /**
     * 新增兑换码
     *
     * @param workCode 兑换码
     * @return 结果
     */
    int insertWorkCode(WorkCode workCode);

    /**
     * 修改兑换码
     *
     * @param workCode 兑换码
     * @return 结果
     */
    int updateWorkCode(WorkCode workCode);

    /**
     * 批量删除兑换码
     *
     * @param ids 需要删除的兑换码主键集合
     * @return 结果
     */
    int deleteWorkCodeByIds(Long[] ids);

    /**
     * 删除兑换码信息
     *
     * @param id 兑换码主键
     * @return 结果
     */
    int deleteWorkCodeById(Long id);

    /**
     * 批量创建兑换码
     * @param workCode
     * @return
     */
    List<WorkCodeExcel> createWorkCode(WorkCode workCode);
}

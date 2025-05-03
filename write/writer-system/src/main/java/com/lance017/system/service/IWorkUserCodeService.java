package com.lance017.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lance017.system.domain.WorkUserCode;

/**
 * 用户兑换码Service接口
 *
 * @author writer
 * @date 2024-10-05
 */
public interface IWorkUserCodeService extends IService<WorkUserCode> {
    /**
     * 查询用户兑换码
     *
     * @param id 用户兑换码主键
     * @return 用户兑换码
     */
    WorkUserCode selectWorkUserCodeById(Long id);

    /**
     * 查询用户兑换码列表
     *
     * @param workUserCode 用户兑换码
     * @return 用户兑换码集合
     */
    List<WorkUserCode> selectWorkUserCodeList(WorkUserCode workUserCode);

    /**
     * 新增用户兑换码
     *
     * @param workUserCode 用户兑换码
     * @return 结果
     */
    int insertWorkUserCode(WorkUserCode workUserCode);

    /**
     * 修改用户兑换码
     *
     * @param workUserCode 用户兑换码
     * @return 结果
     */
    int updateWorkUserCode(WorkUserCode workUserCode);

    /**
     * 批量删除用户兑换码
     *
     * @param ids 需要删除的用户兑换码主键集合
     * @return 结果
     */
    int deleteWorkUserCodeByIds(Long[] ids);

    /**
     * 删除用户兑换码信息
     *
     * @param id 用户兑换码主键
     * @return 结果
     */
    int deleteWorkUserCodeById(Long id);
}

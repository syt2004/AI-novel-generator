package com.lance017.system.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lance017.system.domain.WorkDesc;

/**
 * 提示词Service接口
 *
 * @author writer
 * @date 2024-10-07
 */
public interface IWorkDescService  extends IService<WorkDesc> {
    /**
     * 查询提示词
     *
     * @param id 提示词主键
     * @return 提示词
     */
    WorkDesc selectWorkDescById(Long id);

    /**
     * 查询提示词列表
     *
     * @param workDesc 提示词
     * @return 提示词集合
     */
    List<WorkDesc> selectWorkDescList(WorkDesc workDesc);

    /**
     * 新增提示词
     *
     * @param workDesc 提示词
     * @return 结果
     */
    int insertWorkDesc(WorkDesc workDesc);

    /**
     * 修改提示词
     *
     * @param workDesc 提示词
     * @return 结果
     */
    int updateWorkDesc(WorkDesc workDesc);

    /**
     * 批量删除提示词
     *
     * @param ids 需要删除的提示词主键集合
     * @return 结果
     */
    int deleteWorkDescByIds(Long[] ids);

    /**
     * 删除提示词信息
     *
     * @param id 提示词主键
     * @return 结果
     */
    int deleteWorkDescById(Long id);
}

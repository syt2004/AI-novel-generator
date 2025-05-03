package com.lance017.system.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lance017.system.domain.WorkUserWrite;

/**
 * 用户生成Service接口
 *
 * @author writer
 * @date 2024-10-09
 */
public interface IWorkUserWriteService  extends IService<WorkUserWrite> {
    /**
     * 查询用户生成
     *
     * @param id 用户生成主键
     * @return 用户生成
     */
    WorkUserWrite selectWorkUserWriteById(Long id);

    /**
     * 查询用户生成列表
     *
     * @param workUserWrite 用户生成
     * @return 用户生成集合
     */
    List<WorkUserWrite> selectWorkUserWriteList(WorkUserWrite workUserWrite);

    /**
     * 新增用户生成
     *
     * @param workUserWrite 用户生成
     * @return 结果
     */
    int insertWorkUserWrite(WorkUserWrite workUserWrite);

    /**
     * 修改用户生成
     *
     * @param workUserWrite 用户生成
     * @return 结果
     */
    int updateWorkUserWrite(WorkUserWrite workUserWrite);

    /**
     * 批量删除用户生成
     *
     * @param ids 需要删除的用户生成主键集合
     * @return 结果
     */
    int deleteWorkUserWriteByIds(Long[] ids);

    /**
     * 删除用户生成信息
     *
     * @param id 用户生成主键
     * @return 结果
     */
    int deleteWorkUserWriteById(Long id);
}

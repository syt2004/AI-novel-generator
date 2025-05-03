package com.lance017.system.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lance017.system.domain.WorkUserDesc;

/**
 * 用户关联idService接口
 *
 * @author writer
 * @date 2024-10-07
 */
public interface IWorkUserDescService  extends IService<WorkUserDesc> {
    /**
     * 查询用户关联id
     *
     * @param id 用户关联id主键
     * @return 用户关联id
     */
    WorkUserDesc selectWorkUserDescById(Long id);

    /**
     * 查询用户关联id列表
     *
     * @param workUserDesc 用户关联id
     * @return 用户关联id集合
     */
    List<WorkUserDesc> selectWorkUserDescList(WorkUserDesc workUserDesc);

    /**
     * 新增用户关联id
     *
     * @param workUserDesc 用户关联id
     * @return 结果
     */
    int insertWorkUserDesc(WorkUserDesc workUserDesc);

    /**
     * 修改用户关联id
     *
     * @param workUserDesc 用户关联id
     * @return 结果
     */
    int updateWorkUserDesc(WorkUserDesc workUserDesc);

    /**
     * 批量删除用户关联id
     *
     * @param ids 需要删除的用户关联id主键集合
     * @return 结果
     */
    int deleteWorkUserDescByIds(Long[] ids);

    /**
     * 删除用户关联id信息
     *
     * @param id 用户关联id主键
     * @return 结果
     */
    int deleteWorkUserDescById(Long id);
}

package com.lance017.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lance017.system.domain.WorkUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * workUserMapper接口
 *
 * @author writer
 * @date 2024-09-28
 */
@Mapper
public interface WorkUserMapper extends BaseMapper<WorkUser> {
    /**
     * 查询workUser
     *
     * @param id workUser主键
     * @return workUser
     */
    WorkUser selectWorkUserById(Long id);

    /**
     * 查询workUser列表
     *
     * @param workUser workUser
     * @return workUser集合
     */
    List<WorkUser> selectWorkUserList(WorkUser workUser);

    /**
     * 新增workUser
     *
     * @param workUser workUser
     * @return 结果
     */
    int insertWorkUser(WorkUser workUser);

    /**
     * 修改workUser
     *
     * @param workUser workUser
     * @return 结果
     */
    int updateWorkUser(WorkUser workUser);

    /**
     * 删除workUser
     *
     * @param id workUser主键
     * @return 结果
     */
    int deleteWorkUserById(Long id);

    /**
     * 批量删除workUser
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWorkUserByIds(Long[] ids);
}

package com.lance017.system.mapper;

import java.util.List;
import com.lance017.system.domain.WorkBookRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 小说角色Mapper接口
 *
 * @author writer
 * @date 2024-10-05
 */
@Mapper
public interface WorkBookRoleMapper extends BaseMapper<WorkBookRole> {

    /**
     * 查询小说角色列表
     *
     * @param workBookRole 小说角色
     * @return 小说角色集合
     */
    List<WorkBookRole> selectWorkBookRoleList(WorkBookRole workBookRole);


}

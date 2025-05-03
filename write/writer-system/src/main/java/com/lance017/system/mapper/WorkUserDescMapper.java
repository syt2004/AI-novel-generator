package com.lance017.system.mapper;

import java.util.List;
import com.lance017.system.domain.WorkUserDesc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户关联idMapper接口
 *
 * @author writer
 * @date 2024-10-07
 */
@Mapper
public interface WorkUserDescMapper extends BaseMapper<WorkUserDesc> {

    /**
     * 查询用户关联id列表
     *
     * @param workUserDesc 用户关联id
     * @return 用户关联id集合
     */
    List<WorkUserDesc> selectWorkUserDescList(WorkUserDesc workUserDesc);


}

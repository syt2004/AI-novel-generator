package com.lance017.system.mapper;

import java.util.List;
import com.lance017.system.domain.WorkUserWrite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户生成Mapper接口
 *
 * @author writer
 * @date 2024-10-09
 */
@Mapper
public interface WorkUserWriteMapper extends BaseMapper<WorkUserWrite> {

    /**
     * 查询用户生成列表
     *
     * @param workUserWrite 用户生成
     * @return 用户生成集合
     */
    List<WorkUserWrite> selectWorkUserWriteList(WorkUserWrite workUserWrite);


}

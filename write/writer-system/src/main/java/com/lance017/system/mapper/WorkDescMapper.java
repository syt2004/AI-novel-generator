package com.lance017.system.mapper;

import java.util.List;
import com.lance017.system.domain.WorkDesc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 提示词Mapper接口
 *
 * @author writer
 * @date 2024-10-07
 */
@Mapper
public interface WorkDescMapper extends BaseMapper<WorkDesc> {

    /**
     * 查询提示词列表
     *
     * @param workDesc 提示词
     * @return 提示词集合
     */
    List<WorkDesc> selectWorkDescList(WorkDesc workDesc);


}

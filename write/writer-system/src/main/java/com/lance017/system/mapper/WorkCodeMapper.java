package com.lance017.system.mapper;

import java.util.List;
import com.lance017.system.domain.WorkCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 兑换码Mapper接口
 *
 * @author writer
 * @date 2024-10-05
 */
@Mapper
public interface WorkCodeMapper extends BaseMapper<WorkCode> {

    /**
     * 查询兑换码列表
     *
     * @param workCode 兑换码
     * @return 兑换码集合
     */
    List<WorkCode> selectWorkCodeList(WorkCode workCode);


}

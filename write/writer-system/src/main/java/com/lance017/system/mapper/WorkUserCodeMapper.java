package com.lance017.system.mapper;

import java.util.List;
import com.lance017.system.domain.WorkUserCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户兑换码Mapper接口
 *
 * @author writer
 * @date 2024-10-05
 */
@Mapper
public interface WorkUserCodeMapper extends BaseMapper<WorkUserCode> {

    /**
     * 查询用户兑换码列表
     *
     * @param workUserCode 用户兑换码
     * @return 用户兑换码集合
     */
    List<WorkUserCode> selectWorkUserCodeList(WorkUserCode workUserCode);


}

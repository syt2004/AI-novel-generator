package com.lance017.system.mapper;

import java.util.List;
import com.lance017.system.domain.WorkBookPart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 小说章节Mapper接口
 *
 * @author writer
 * @date 2024-10-05
 */
@Mapper
public interface WorkBookPartMapper extends BaseMapper<WorkBookPart> {

    /**
     * 查询小说章节列表
     *
     * @param workBookPart 小说章节
     * @return 小说章节集合
     */
    List<WorkBookPart> selectWorkBookPartList(WorkBookPart workBookPart);


}

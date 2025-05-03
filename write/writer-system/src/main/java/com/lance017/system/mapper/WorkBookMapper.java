package com.lance017.system.mapper;

import java.util.List;
import com.lance017.system.domain.WorkBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作品Mapper接口
 *
 * @author writer
 * @date 2024-10-05
 */
@Mapper
public interface WorkBookMapper extends BaseMapper<WorkBook> {

    /**
     * 查询作品列表
     *
     * @param workBook 作品
     * @return 作品集合
     */
    List<WorkBook> selectWorkBookList(WorkBook workBook);


}

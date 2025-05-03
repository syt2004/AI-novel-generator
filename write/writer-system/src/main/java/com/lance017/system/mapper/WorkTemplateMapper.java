package com.lance017.system.mapper;

import java.util.List;
import com.lance017.system.domain.WorkTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 小说模板Mapper接口
 *
 * @author writer
 * @date 2024-10-07
 */
@Mapper
public interface WorkTemplateMapper extends BaseMapper<WorkTemplate> {

    /**
     * 查询小说模板列表
     *
     * @param workTemplate 小说模板
     * @return 小说模板集合
     */
    List<WorkTemplate> selectWorkTemplateList(WorkTemplate workTemplate);


}

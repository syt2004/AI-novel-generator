package com.lance017.system.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lance017.system.domain.WorkTemplate;

/**
 * 小说模板Service接口
 *
 * @author writer
 * @date 2024-10-07
 */
public interface IWorkTemplateService  extends IService<WorkTemplate> {
    /**
     * 查询小说模板
     *
     * @param id 小说模板主键
     * @return 小说模板
     */
    WorkTemplate selectWorkTemplateById(Long id);

    /**
     * 查询小说模板列表
     *
     * @param workTemplate 小说模板
     * @return 小说模板集合
     */
    List<WorkTemplate> selectWorkTemplateList(WorkTemplate workTemplate);

    /**
     * 新增小说模板
     *
     * @param workTemplate 小说模板
     * @return 结果
     */
    int insertWorkTemplate(WorkTemplate workTemplate);

    /**
     * 修改小说模板
     *
     * @param workTemplate 小说模板
     * @return 结果
     */
    int updateWorkTemplate(WorkTemplate workTemplate);

    /**
     * 批量删除小说模板
     *
     * @param ids 需要删除的小说模板主键集合
     * @return 结果
     */
    int deleteWorkTemplateByIds(Long[] ids);

    /**
     * 删除小说模板信息
     *
     * @param id 小说模板主键
     * @return 结果
     */
    int deleteWorkTemplateById(Long id);
}

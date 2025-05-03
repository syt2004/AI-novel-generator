package com.lance017.system.service.impl;

import java.util.List;
import com.lance017.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.lance017.system.mapper.WorkTemplateMapper;
import com.lance017.system.domain.WorkTemplate;
import com.lance017.system.service.IWorkTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 小说模板Service业务层处理
 *
 * @author writer
 * @date 2024-10-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkTemplateServiceImpl extends ServiceImpl<WorkTemplateMapper, WorkTemplate> implements IWorkTemplateService {

    private final WorkTemplateMapper workTemplateMapper;

    /**
     * 查询小说模板
     *
     * @param id 小说模板主键
     * @return 小说模板
     */
    @Override
    public WorkTemplate selectWorkTemplateById(Long id) {
        return workTemplateMapper.selectById(id);
    }

    /**
     * 查询小说模板列表
     *
     * @param workTemplate 小说模板
     * @return 小说模板
     */
    @Override
    public List<WorkTemplate> selectWorkTemplateList(WorkTemplate workTemplate) {
        return workTemplateMapper.selectWorkTemplateList(workTemplate);
    }

    /**
     * 新增小说模板
     *
     * @param workTemplate 小说模板
     * @return 结果
     */
    @Override
    public int insertWorkTemplate(WorkTemplate workTemplate) {
        workTemplate.setCreateTime(DateUtils.getNowDate());
        return workTemplateMapper.insert(workTemplate);
    }

    /**
     * 修改小说模板
     *
     * @param workTemplate 小说模板
     * @return 结果
     */
    @Override
    public int updateWorkTemplate(WorkTemplate workTemplate) {
        workTemplate.setUpdateTime(DateUtils.getNowDate());
        return workTemplateMapper.updateById(workTemplate);
    }

    /**
     * 批量删除小说模板
     *
     * @param ids 需要删除的小说模板主键
     * @return 结果
     */
    @Override
    public int deleteWorkTemplateByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workTemplateMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除小说模板信息
     *
     * @param id 小说模板主键
     * @return 结果
     */
    @Override
    public int deleteWorkTemplateById(Long id) {
        return workTemplateMapper.deleteById(id);
    }
}

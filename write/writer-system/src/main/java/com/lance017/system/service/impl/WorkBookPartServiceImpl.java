package com.lance017.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lance017.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lance017.system.mapper.WorkBookPartMapper;
import com.lance017.system.domain.WorkBookPart;
import com.lance017.system.service.IWorkBookPartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * 小说章节Service业务层处理
 *
 * @author writer
 * @date 2024-10-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkBookPartServiceImpl extends ServiceImpl<WorkBookPartMapper, WorkBookPart> implements IWorkBookPartService {

    private final WorkBookPartMapper workBookPartMapper;

    /**
     * 查询小说章节
     *
     * @param id 小说章节主键
     * @return 小说章节
     */
    @Override
    public WorkBookPart selectWorkBookPartById(Long id) {
        return workBookPartMapper.selectById(id);
    }

    /**
     * 查询小说章节列表
     *
     * @param workBookPart 小说章节
     * @return 小说章节
     */
    @Override
    public List<WorkBookPart> selectWorkBookPartList(WorkBookPart workBookPart) {
        return workBookPartMapper.selectWorkBookPartList(workBookPart);
    }

    /**
     * 新增小说章节
     *
     * @param workBookPart 小说章节
     * @return 结果
     */
    @Override
    public int insertWorkBookPart(WorkBookPart workBookPart) {
        workBookPart.setCreateTime(DateUtils.getNowDate());
        return workBookPartMapper.insert(workBookPart);
    }

    /**
     * 修改小说章节
     *
     * @param workBookPart 小说章节
     * @return 结果
     */
    @Override
    public int updateWorkBookPart(WorkBookPart workBookPart) {
        workBookPart.setUpdateTime(DateUtils.getNowDate());
        return workBookPartMapper.updateById(workBookPart);
    }

    /**
     * 批量删除小说章节
     *
     * @param ids 需要删除的小说章节主键
     * @return 结果
     */
    @Override
    public int deleteWorkBookPartByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workBookPartMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除小说章节信息
     *
     * @param id 小说章节主键
     * @return 结果
     */
    @Override
    public int deleteWorkBookPartById(Long id) {
        return workBookPartMapper.deleteById(id);
    }
}

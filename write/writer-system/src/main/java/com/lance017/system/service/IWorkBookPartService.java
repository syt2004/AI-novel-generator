package com.lance017.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lance017.system.domain.WorkBookPart;

/**
 * 小说章节Service接口
 *
 * @author writer
 * @date 2024-10-05
 */
public interface IWorkBookPartService extends IService<WorkBookPart> {
    /**
     * 查询小说章节
     *
     * @param id 小说章节主键
     * @return 小说章节
     */
    WorkBookPart selectWorkBookPartById(Long id);

    /**
     * 查询小说章节列表
     *
     * @param workBookPart 小说章节
     * @return 小说章节集合
     */
    List<WorkBookPart> selectWorkBookPartList(WorkBookPart workBookPart);

    /**
     * 新增小说章节
     *
     * @param workBookPart 小说章节
     * @return 结果
     */
    int insertWorkBookPart(WorkBookPart workBookPart);

    /**
     * 修改小说章节
     *
     * @param workBookPart 小说章节
     * @return 结果
     */
    int updateWorkBookPart(WorkBookPart workBookPart);

    /**
     * 批量删除小说章节
     *
     * @param ids 需要删除的小说章节主键集合
     * @return 结果
     */
    int deleteWorkBookPartByIds(Long[] ids);

    /**
     * 删除小说章节信息
     *
     * @param id 小说章节主键
     * @return 结果
     */
    int deleteWorkBookPartById(Long id);
}

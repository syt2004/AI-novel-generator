package com.lance017.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lance017.system.domain.WorkBook;

/**
 * 作品Service接口
 *
 * @author writer
 * @date 2024-10-05
 */
public interface IWorkBookService extends IService<WorkBook> {
    /**
     * 查询作品
     *
     * @param id 作品主键
     * @return 作品
     */
    WorkBook selectWorkBookById(Long id);



    /**
     * 查询作品列表
     *
     * @param workBook 作品
     * @return 作品集合
     */
    List<WorkBook> selectWorkBookList(WorkBook workBook);

    /**
     * 新增作品
     *
     * @param workBook 作品
     * @return 结果
     */
    int insertWorkBook(WorkBook workBook);

    /**
     * 修改作品
     *
     * @param workBook 作品
     * @return 结果
     */
    int updateWorkBook(WorkBook workBook);

    /**
     * 批量删除作品
     *
     * @param ids 需要删除的作品主键集合
     * @return 结果
     */
    int deleteWorkBookByIds(Long[] ids);

    /**
     * 删除作品信息
     *
     * @param id 作品主键
     * @return 结果
     */
    int deleteWorkBookById(Long id);


//    List<WorkBook> selectWorkBookList(WorkBook workBook);
}

package com.lance017.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lance017.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lance017.system.mapper.WorkBookMapper;
import com.lance017.system.domain.WorkBook;
import com.lance017.system.service.IWorkBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * 作品Service业务层处理
 *
 * @author writer
 * @date 2024-10-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkBookServiceImpl extends ServiceImpl<WorkBookMapper, WorkBook> implements IWorkBookService {

    private final WorkBookMapper workBookMapper;

    /**
     * 查询作品
     *
     * @param id 作品主键
     * @return 作品
     */
    @Override
    public WorkBook selectWorkBookById(Long id) {
        return workBookMapper.selectById(id);
    }



    /**
     * 查询作品列表
     *
     * @param workBook 作品
     * @return 作品
     */
    @Override
    public List<WorkBook> selectWorkBookList(WorkBook workBook) {
        return workBookMapper.selectWorkBookList(workBook);
    }

    /**
     * 新增作品
     *
     * @param workBook 作品
     * @return 结果
     */
    @Override
    public int insertWorkBook(WorkBook workBook) {
        workBook.setCreateTime(DateUtils.getNowDate());
        return workBookMapper.insert(workBook);
    }

    /**
     * 修改作品
     *
     * @param workBook 作品
     * @return 结果
     */
    @Override
    public int updateWorkBook(WorkBook workBook) {
        workBook.setUpdateTime(DateUtils.getNowDate());
        return workBookMapper.updateById(workBook);
    }

    /**
     * 批量删除作品
     *
     * @param ids 需要删除的作品主键
     * @return 结果
     */
    @Override
    public int deleteWorkBookByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i = i + workBookMapper.deleteById(id);
        }
        return i;
    }

    /**
     * 删除作品信息
     *
     * @param id 作品主键
     * @return 结果
     */
    @Override
    public int deleteWorkBookById(Long id) {
        return workBookMapper.deleteById(id);
    }
}

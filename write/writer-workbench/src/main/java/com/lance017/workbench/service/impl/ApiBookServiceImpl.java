package com.lance017.workbench.service.impl;


import cn.hutool.core.date.DateUtil;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lance017.system.domain.WorkBook;
import com.lance017.system.domain.WorkBookPart;
import com.lance017.system.domain.WorkUser;
import com.lance017.system.enums.WorkBookStatus;
import com.lance017.system.service.IWorkBookPartService;
import com.lance017.system.service.IWorkBookService;
import com.lance017.workbench.context.WorkbenchContextUtil;
import com.lance017.workbench.domain.request.*;
import com.lance017.workbench.service.ApiBookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ApiBookServiceImpl implements ApiBookService {

    private final IWorkBookService workBookService;

    private final IWorkBookPartService workBookPartService;

    /**
     * 创建作品
     *
     * @param addBook
     */
    @Override
    public void addBook(BookAddRequest addBook) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkBook workBook = workBookService.getOne(
                new LambdaQueryWrapper<WorkBook>()
                        .eq(WorkBook::getTitle, addBook.getTitle())
                        .eq(WorkBook::getUserId, workUser.getId())
        );


        if (ObjectUtil.isNotEmpty(workBook)) {
            throw new RuntimeException("已存在同名作品");
        }
        workBook = new WorkBook();
        workBook.setTitle(addBook.getTitle());
        workBook.setSynopsis(addBook.getContent());
        workBook.setStatus(WorkBookStatus.NORMAL.getCode());
        workBook.setUserId(workUser.getId());
        workBook.setCreateBy(workUser.getUsername());
        workBook.setCreateTime(DateUtil.date());
        workBookService.save(workBook);
    }


    /**
     * 获取作品信息
     *
     * @param request
     * @return
     */
    @Override
    public Object infoBook(BookInfoRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkBook workBook = workBookService.getOne(
                new LambdaQueryWrapper<WorkBook>()
                        .eq(WorkBook::getId, request.getId())
                        .eq(WorkBook::getUserId, workUser.getId())
                        .eq(WorkBook::getStatus, WorkBookStatus.NORMAL.getCode())

        );
        if (ObjectUtil.isEmpty(workBook)) {
            throw new RuntimeException("未找到您名下该作品");
        }
        return workBook;
    }


    /**
     * 更新作品信息
     *
     * @param request
     */
    @Override
    public void update(BookUpdateRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkBook workBook = workBookService.getOne(
                new LambdaQueryWrapper<WorkBook>()
                        .eq(WorkBook::getId, request.getId())
                        .eq(WorkBook::getUserId, workUser.getId())
                        .eq(WorkBook::getStatus, WorkBookStatus.NORMAL.getCode())
        );
        if (ObjectUtil.isEmpty(workBook)) {
            throw new RuntimeException("未找到您名下该作品");
        }
        workBook.setTitle(request.getTitle());
        workBook.setSynopsis(request.getContent());
        workBook.setUpdateBy(workUser.getUsername());
        workBook.setUpdateTime(DateUtil.date());
        workBookService.saveOrUpdate(workBook);
    }


    /**
     * 删除作品
     *
     * @param request
     */
    @Override
    public void delete(BookDeleteRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkBook workBook = workBookService.getOne(
                new LambdaQueryWrapper<WorkBook>()
                        .eq(WorkBook::getId, request.getId())
                        .eq(WorkBook::getUserId, workUser.getId())
                        .eq(WorkBook::getStatus, WorkBookStatus.NORMAL.getCode())
        );
        if (ObjectUtil.isEmpty(workBook)) {
            throw new RuntimeException("未找到您名下该作品");
        }
        workBookService.removeById(request.getId());
    }

    /**
     * 获取作品列表
     *
     * @return
     */
    @Override
    public Object list() {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkBook workBook = new WorkBook();
        workBook.setUserId(workUser.getId());
        workBook.setStatus(WorkBookStatus.NORMAL.getCode());
        return workBookService.selectWorkBookList(workBook);
    }


    /**
     * 导出作品
     *
     * @param request
     * @return
     */
    @Override
    public Object export(BookExportRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkBook workBook = workBookService.getOne(
                new LambdaQueryWrapper<WorkBook>()
                        .eq(WorkBook::getId, request.getId())
                        .eq(WorkBook::getUserId, workUser.getId())
                        .eq(WorkBook::getStatus, WorkBookStatus.NORMAL.getCode())

        );
        if (ObjectUtil.isEmpty(workBook)) {
            throw new RuntimeException("未找到您名下该作品");
        }

        List<WorkBookPart> workBookParts = workBookPartService.list(
                new LambdaQueryWrapper<WorkBookPart>()
                        .eq(WorkBookPart::getBookId, request.getId())
                        .eq(WorkBookPart::getUserId, workUser.getId())
                        .eq(WorkBookPart::getStatus, WorkBookStatus.NORMAL.getCode())
                        .orderByAsc(WorkBookPart::getSort)
        );

        StringBuilder stringBuilder = new StringBuilder();
        for (WorkBookPart workBookPart : workBookParts) {
            stringBuilder.append(workBookPart.getPartTitle()).append("\n");
            stringBuilder.append(workBookPart.getContent()).append("\n\n");
        }
        return stringBuilder;
    }
}

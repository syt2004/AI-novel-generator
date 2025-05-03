package com.lance017.workbench.service.impl;

import java.util.Date;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lance017.system.domain.WorkBook;
import com.lance017.system.domain.WorkBookPart;
import com.lance017.system.domain.WorkUser;
import com.lance017.system.enums.WorkBookStatus;
import com.lance017.system.service.IWorkBookPartService;
import com.lance017.system.service.IWorkBookService;
import com.lance017.workbench.context.WorkbenchContextUtil;
import com.lance017.workbench.domain.request.*;
import com.lance017.workbench.service.ApiPartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class ApiPartServiceImpl implements ApiPartService {

    private final IWorkBookService workBookService;

    private final IWorkBookPartService workBookPartService;

    /**
     * 判断作品是否存在
     *
     * @param bookId
     */
    private void judgeBook(Long bookId, WorkUser workUser) {

        WorkBook workBook = workBookService.getOne(
                new LambdaQueryWrapper<WorkBook>()
                        .eq(WorkBook::getId, bookId)
                        .eq(WorkBook::getUserId, workUser.getId())
                        .eq(WorkBook::getStatus, WorkBookStatus.NORMAL.getCode())

        );
        if (ObjectUtil.isEmpty(workBook)) {
            throw new RuntimeException("作品信息错误");
        }
    }

    /**
     * 添加作品章节
     *
     * @param request
     */
    @Override
    public Object add(PartAddRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        judgeBook(request.getBookId(), workUser);
        WorkBookPart bookPart = new WorkBookPart();
        bookPart.setBookId(request.getBookId());
        bookPart.setPartTitle(request.getPartTitle());
        bookPart.setUserId(workUser.getId());
        if (StrUtil.isBlank(request.getContent())) {
            bookPart.setContent("");
        } else {
            bookPart.setContent(request.getContent());
        }
        bookPart.setWords(bookPart.getContent().length());
        bookPart.setSort(0);
        bookPart.setStatus(WorkBookStatus.NORMAL.getCode());
        bookPart.setCreateBy(workUser.getUsername());
        bookPart.setCreateTime(DateUtil.date());
        workBookPartService.save(bookPart);
        LambdaUpdateWrapper<WorkBookPart> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.setSql("sort = sort + 1")
                .eq(WorkBookPart::getBookId, request.getBookId())
                .eq(WorkBookPart::getUserId, workUser.getId())
                .ne(WorkBookPart::getId, bookPart.getId())
                .eq(WorkBookPart::getStatus, WorkBookStatus.NORMAL.getCode());
        workBookPartService.update(updateWrapper);
        return MapUtil.of("id", bookPart.getId());

    }

    /**
     * 获取作品章节信息
     *
     * @param request
     * @return
     */
    @Override
    public Object info(PartInfoRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        judgeBook(request.getBookId(), workUser);
        WorkBookPart bookPart = workBookPartService.getOne(
                new LambdaQueryWrapper<WorkBookPart>()
                        .eq(WorkBookPart::getBookId, request.getBookId())
                        .eq(WorkBookPart::getId, request.getId())
                        .eq(WorkBookPart::getUserId, workUser.getId())
                        .eq(WorkBookPart::getStatus, WorkBookStatus.NORMAL.getCode())
        );
        if (ObjectUtil.isEmpty(bookPart)) {
            throw new RuntimeException("作品章节信息错误");
        }
        return bookPart;
    }

    /**
     * 排序
     *
     * @param request
     */
    @Override
    public void sort(PartSortRequest request) {
        for (int i = 0; i < request.getIds().size(); i++) {
            Long id = request.getIds().get(i);
            LambdaUpdateWrapper<WorkBookPart> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(WorkBookPart::getSort, i)
                    .eq(WorkBookPart::getId, id);
            workBookPartService.update(updateWrapper);
        }
    }

    /**
     * 更新作品章节信息
     *
     * @param request
     */
    @Override
    public Object update(PartUpdateRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        judgeBook(request.getBookId(), workUser);
        WorkBookPart bookPart = workBookPartService.getOne(
                new LambdaQueryWrapper<WorkBookPart>()
                        .eq(WorkBookPart::getBookId, request.getBookId())
                        .eq(WorkBookPart::getId, request.getId())
                        .eq(WorkBookPart::getUserId, workUser.getId())
                        .eq(WorkBookPart::getStatus, WorkBookStatus.NORMAL.getCode())
        );
        if (ObjectUtil.isEmpty(bookPart)) {
            throw new RuntimeException("作品章节信息错误");
        }
        bookPart.setBookId(request.getBookId());
        bookPart.setPartTitle(request.getPartTitle());
        if (StrUtil.isBlank(request.getContent())) {
            bookPart.setContent("");
        } else {
            bookPart.setContent(request.getContent());
        }
        bookPart.setWords(bookPart.getContent().length());
        bookPart.setUpdateBy(workUser.getUsername());
        bookPart.setUpdateTime(DateUtil.date());
        workBookPartService.saveOrUpdate(bookPart);
        return bookPart;
    }

    /**
     * 删除作品章节
     *
     * @param request
     */
    @Override
    public void delete(PartDeleteRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        judgeBook(request.getBookId(), workUser);
        WorkBookPart bookPart = workBookPartService.getOne(
                new LambdaQueryWrapper<WorkBookPart>()
                        .eq(WorkBookPart::getBookId, request.getBookId())
                        .eq(WorkBookPart::getId, request.getId())
                        .eq(WorkBookPart::getUserId, workUser.getId())
                        .eq(WorkBookPart::getStatus, WorkBookStatus.NORMAL.getCode())
        );
        if (ObjectUtil.isEmpty(bookPart)) {
            throw new RuntimeException("作品章节信息错误");
        }
        bookPart.setStatus(WorkBookStatus.DELETED.getCode());
        bookPart.setUpdateBy(workUser.getUsername());
        bookPart.setUpdateTime(DateUtil.date());
        workBookPartService.saveOrUpdate(bookPart);
    }

    /**
     * 获取作品章节列表
     *
     * @param request
     * @return
     */
    @Override
    public Object list(PartListRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        judgeBook(request.getBookId(), workUser);
        return workBookPartService.list(
                new LambdaQueryWrapper<WorkBookPart>()
                        .eq(WorkBookPart::getBookId, request.getBookId())
                        .eq(WorkBookPart::getUserId, workUser.getId())
                        .eq(WorkBookPart::getStatus, WorkBookStatus.NORMAL.getCode())
                        .orderByAsc(WorkBookPart::getSort)
                        .orderByDesc(WorkBookPart::getId)
        );
    }
}

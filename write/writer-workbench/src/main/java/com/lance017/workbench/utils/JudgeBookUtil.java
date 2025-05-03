package com.lance017.workbench.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lance017.system.domain.WorkBook;
import com.lance017.system.enums.WorkBookStatus;
import com.lance017.system.service.IWorkBookService;

public class JudgeBookUtil {

    public static WorkBook judgeBook(Long bookId, Long userId) {
        IWorkBookService bookService = SpringUtil.getBean(IWorkBookService.class);
        WorkBook workBook = bookService.getOne(
                new LambdaQueryWrapper<WorkBook>()
                        .eq(WorkBook::getId, bookId)
                        .eq(WorkBook::getUserId, userId)
                        .eq(WorkBook::getStatus, WorkBookStatus.NORMAL.getCode())

        );
        if (ObjectUtil.isEmpty(workBook)) {
            throw new RuntimeException("作品信息错误");
        }
        return workBook;
    }

}

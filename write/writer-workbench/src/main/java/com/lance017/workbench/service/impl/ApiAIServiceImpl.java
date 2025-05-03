package com.lance017.workbench.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lance017.common.exception.ServiceException;
import com.lance017.system.domain.*;
import com.lance017.system.enums.WorkAllType;
import com.lance017.system.enums.WorkBookStatus;
import com.lance017.system.enums.WorkDescType;
import com.lance017.system.enums.WorkWriteType;
import com.lance017.system.service.*;
import com.lance017.workbench.context.WorkbenchContextUtil;
import com.lance017.workbench.domain.request.AIBreakRequest;
import com.lance017.workbench.domain.request.AIPolishRequest;
import com.lance017.workbench.domain.request.AIWriteRequest;
import com.lance017.workbench.service.AIService;
import com.lance017.workbench.service.ApiAIService;
import com.lance017.workbench.service.ApiTemplateService;
import com.lance017.workbench.utils.JudgeBookUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;


@Slf4j
@Service
@AllArgsConstructor
public class ApiAIServiceImpl implements ApiAIService {

    private final ISysConfigService configService;

    private final IWorkBookPartService workBookPartService;

    private final IWorkDescService workDescService;

    private final AIService aiService;

    private final IWorkBookRoleService workBookRoleService;

    private final ApiTemplateService templateService;

    private final IWorkUserCodeService workUserCodeService;

    @Override
    public SseEmitter aiwrite(AIWriteRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        JudgeBookUtil.judgeBook(request.getBookId(), workUser.getId());
        judgeCode(workUser);

        LambdaQueryWrapper<WorkBookRole> queryWrapper = new LambdaQueryWrapper<WorkBookRole>()
                .eq(WorkBookRole::getUserId, workUser.getId())
                .eq(WorkBookRole::getBookId, request.getBookId())
                .eq(WorkBookRole::getStatus, WorkBookStatus.NORMAL.getCode());

        // 角色
        List<WorkBookRole> bookRoles = new ArrayList<>();
        if (StrUtil.isNotBlank(request.getRoles())) {
            long[] array = Arrays.stream(request.getRoles().trim().split(",")).mapToLong(Long::parseLong).toArray();
            queryWrapper = queryWrapper.in(WorkBookRole::getId, Arrays.asList(array));
            bookRoles = workBookRoleService.list(queryWrapper);
        }


        // 写作风格
        if (request.getStyleType().equals(WorkAllType.ID.getCode())) {
            WorkDesc workDesc = workDescService.getOne(
                    new LambdaQueryWrapper<WorkDesc>()
                            .eq(WorkDesc::getId, request.getStyleId())
                            .eq(WorkDesc::getType, WorkDescType.STYLE.getCode())
                            .eq(WorkDesc::getStatus, WorkBookStatus.NORMAL.getCode())
            );
            if (ObjectUtil.isEmpty(workDesc)) {
                throw new ServiceException("提示词错误");
            }
            request.setStyle(workDesc.getDes());
        }

        // 写作要求
        if (request.getRequiresType().equals(WorkAllType.ID.getCode())) {
            WorkDesc workDesc = workDescService.getOne(
                    new LambdaQueryWrapper<WorkDesc>()
                            .eq(WorkDesc::getId, request.getRequiresId())
                            .eq(WorkDesc::getType, WorkDescType.REQUIRES.getCode())
                            .eq(WorkDesc::getStatus, WorkBookStatus.NORMAL.getCode())
            );
            if (ObjectUtil.isEmpty(workDesc)) {
                throw new ServiceException("提示词错误");
            }
            request.setRequires(workDesc.getDes());
        }

//        StringBuilder title = new StringBuilder();

        SseEmitter sseEmitter = new SseEmitter();


        WorkUserWrite workUserWrite = new WorkUserWrite();
        workUserWrite.setUserId(workUser.getId());
        workUserWrite.setBookId(request.getBookId());
        workUserWrite.setWords(0L);
        workUserWrite.setDes(request.getRequires());
        workUserWrite.setContent("");
        workUserWrite.setTitle("");
        workUserWrite.setType(WorkWriteType.AI_WRITE.getCode());
        workUserWrite.setPlot(request.getPlot());
        workUserWrite.setStyle(request.getStyle());


//        new Thread(() -> {
//            TemplateUpdateRequest data = new TemplateUpdateRequest();
//            BeanUtil.copyProperties(request, data);
//            templateService.update(data);
//        }).start();

//        BeanUtil.copyProperties(request, data);


//        aiService.aibreak(sseEmitter, request.getRequires(), workBookParts);

        aiService.aiwrite(
                request.getBg(),
                request.getRelation(),
                request.getPlot(),
                request.getStyle(),
                request.getRequires(),
                bookRoles,
                workUserWrite,
                sseEmitter
        );

        return sseEmitter;
    }


    @Override
    public SseEmitter aibreak(AIBreakRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        JudgeBookUtil.judgeBook(request.getBookId(), workUser.getId());
        judgeCode(workUser);

        List<WorkBookPart> workBookParts = workBookPartService.list(
                new LambdaQueryWrapper<WorkBookPart>()
                        .in(WorkBookPart::getId, request.getIds())
                        .eq(WorkBookPart::getBookId, request.getBookId())
                        .eq(WorkBookPart::getUserId, workUser.getId())
                        .eq(WorkBookPart::getStatus, WorkBookStatus.NORMAL.getCode())
        );



        if (request.getRequiresType().equals(WorkAllType.ID.getCode())) {
            WorkDesc workDesc = workDescService.getOne(
                    new LambdaQueryWrapper<WorkDesc>()
                            .eq(WorkDesc::getId, request.getRequiresId())
//                            .eq(WorkDesc::getUserId, workUser.getId())
                            .eq(WorkDesc::getType, WorkDescType.BREAK_REQUIRES.getCode())
                            .eq(WorkDesc::getStatus, WorkBookStatus.NORMAL.getCode())
            );
            if (ObjectUtil.isEmpty(workDesc)) {
                throw new ServiceException("提示词错误");
            }
            request.setRequires(workDesc.getDes());
        }


        StringBuilder title = new StringBuilder();

        if (request.getUuType().equals(0)) {
            // 合并
            if (workBookParts.size() > 1) {
                WorkBookPart workBookPart = new WorkBookPart();
                workBookPart.setPartTitle("合并后的章节");
                title.append("多章");
                for (WorkBookPart bookPart : workBookParts) {
                    title.append(":").append(bookPart.getPartTitle());
                    workBookPart.setContent(workBookPart.getContent() + bookPart.getContent());
                }
                workBookParts = Collections.singletonList(workBookPart);
            }
        } else {
            if (workBookParts.size() > 1) {
                title.append("多章");
                for (WorkBookPart bookPart : workBookParts) {
                    title.append(":").append(bookPart.getPartTitle());
                }
            }
        }









//        List<String> userPrompts = new ArrayList<>();
//        userPrompts.add(request.getRequires());
//        for (WorkBookPart workBookPart : workBookParts) {
//            String prompt = String.format("这是章节标题:[%s],这是章节内容:[%s]", workBookPart.getPartTitle(), workBookPart.getContent());
//            userPrompts.add(prompt);
//        }


        SseEmitter sseEmitter = new SseEmitter();


        WorkUserWrite workUserWrite = new WorkUserWrite();
        workUserWrite.setUserId(workUser.getId());
        workUserWrite.setBookId(request.getBookId());
        workUserWrite.setWords(0L);
        workUserWrite.setDes(request.getRequires());
        workUserWrite.setContent("");
        workUserWrite.setTitle(title.toString());
        workUserWrite.setType(WorkWriteType.AI_BREAK.getCode());


//        aiService.aibreak(sseEmitter, request.getRequires(), workBookParts);

        aiService.aibreak(request.getRequires(), workUserWrite, sseEmitter, new ArrayList<>(workBookParts));



//        List<WorkBookPart> finalWorkBookParts = workBookParts;
//        new Thread(() -> {
//            try {
//                for (int j = 0; j < 10; j++) {
//                    sseEmitter.send(MapUtil.of("message", finalWorkBookParts.get(0).getPartTitle() + "\n\n"));
//                    Thread.sleep(1000);
//                }
//                sseEmitter.complete();
//            } catch (Exception e) {
//                sseEmitter.completeWithError(e);
//            }
//        }).start();


        return sseEmitter;
    }

    @Override
    public SseEmitter polish(AIPolishRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        JudgeBookUtil.judgeBook(request.getBookId(), workUser.getId());
        judgeCode(workUser);


        LambdaQueryWrapper<WorkBookRole> queryWrapper = new LambdaQueryWrapper<WorkBookRole>()
                .eq(WorkBookRole::getUserId, workUser.getId())
                .eq(WorkBookRole::getBookId, request.getBookId())
                .eq(WorkBookRole::getStatus, WorkBookStatus.NORMAL.getCode());

        List<WorkBookRole> bookRoles = new ArrayList<>();
        // 角色
        if (StrUtil.isNotBlank(request.getRoles())) {
            long[] array = Arrays.stream(request.getRoles().trim().split(",")).mapToLong(Long::parseLong).toArray();
            queryWrapper = queryWrapper.in(WorkBookRole::getId, Arrays.asList(array));
            bookRoles = workBookRoleService.list(queryWrapper);
        }




        if (request.getRequiresType().equals(WorkAllType.ID.getCode())) {
            WorkDesc workDesc = workDescService.getOne(
                    new LambdaQueryWrapper<WorkDesc>()
                            .eq(WorkDesc::getId, request.getRequiresId())
//                            .eq(WorkDesc::getUserId, workUser.getId())
                            .eq(WorkDesc::getType, WorkDescType.POLISH_REQUIRES.getCode())
                            .eq(WorkDesc::getStatus, WorkBookStatus.NORMAL.getCode())
            );
            if (ObjectUtil.isEmpty(workDesc)) {
                throw new ServiceException("提示词错误");
            }
            request.setRequires(workDesc.getDes());
        }


        SseEmitter sseEmitter = new SseEmitter();

        WorkUserWrite workUserWrite = new WorkUserWrite();
        workUserWrite.setUserId(workUser.getId());
        workUserWrite.setBookId(request.getBookId());
        workUserWrite.setWords(0L);
        workUserWrite.setDes(request.getRequires());
        workUserWrite.setContent("");
        workUserWrite.setTitle("");
        workUserWrite.setType(WorkWriteType.AI_POLISH.getCode());


//        new Thread(() -> {
//            TemplateUpdateRequest data = new TemplateUpdateRequest();
//            BeanUtil.copyProperties(request, data);
//            templateService.update(data);
//        }).start();

//        BeanUtil.copyProperties(request, data);


//        aiService.aibreak(sseEmitter, request.getRequires(), workBookParts);

        aiService.aipolish(
                request.getContent(),
                request.getRelation(),
                request.getRequires(),
                bookRoles,
                workUserWrite,
                sseEmitter
        );
        return sseEmitter;
    }


    private void judgeCode(WorkUser workUser) {
        List<WorkUserCode> list = workUserCodeService.list(
                new LambdaQueryWrapper<WorkUserCode>()
                        .eq(WorkUserCode::getUserId, workUser.getId())
                        .ge(WorkUserCode::getDeadline, new Date())
        );
        long sum = list.stream().mapToLong(WorkUserCode::getRemainingWords).sum();
        if (sum < 500) {
            throw new ServiceException("可使用字数小于500，请充值后使用");
        }
    }

}

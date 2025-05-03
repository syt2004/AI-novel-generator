package com.lance017.workbench.service.impl;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.lance017.system.domain.WorkBookPart;
import com.lance017.system.domain.WorkBookRole;
import com.lance017.system.domain.WorkUserWrite;
import com.lance017.system.service.IWorkUserWriteService;
import com.lance017.workbench.listener.BreakEventSourceListener;
import com.lance017.workbench.listener.PolishEventSourceListener;
import com.lance017.workbench.listener.WriteEventSourceListener;
import com.lance017.workbench.service.AIService;
import com.lance017.workbench.utils.ClaudeHttpUtils;
import com.lance017.workbench.utils.OpenAIHttpUtils;
import com.lance017.workbench.utils.OpenAIStreamHttpUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AIServiceImpl implements AIService {

    private final IWorkUserWriteService workUserWriteService;


    @Async
    @Override
    @SneakyThrows
    public void aiwrite(String bg, String relation, String plot, String style, String requires, List<WorkBookRole> workBookRoles, WorkUserWrite workUserWrite, SseEmitter sseEmitter) {
        sseEmitter.send(MapUtil.of("message", ""));
        List<String> userPrompts = new ArrayList<>();
        // 背景
        userPrompts.add(String.format("背景:%s", bg));
        // 角色
        if (ObjectUtil.isNotEmpty(workBookRoles)) {
            StringBuilder ssss = new StringBuilder("角色:\n");
            for (WorkBookRole workBookRole : workBookRoles) {
                ssss.append("姓名:")
                        .append(workBookRole.getName())
                        .append("  性格:")
                        .append(workBookRole.getCharacte())
                        .append("  信息:")
                        .append(workBookRole.getPlot());
            }
            userPrompts.add(ssss.toString());
        }

        //角色关系
        userPrompts.add(String.format("角色关系:%s", relation));
        //剧情
        userPrompts.add(String.format("剧情:%s", plot));
        //写作风格
        userPrompts.add(String.format("写作风格:%s", style));
        // 写作要求
        userPrompts.add(String.format("写作要求:%s", requires));
        userPrompts.add("直接返回生成的内容，不要添加额外的文字");
//        ClaudeHttpUtils.request("你是一名资深网文大神作家,按照以下要求,写一个章节", userPrompts, sseEmitter);

        String systemPrompt = "你是一名资深网文大神作家,按照以下要求,撰写小说的一个章节";

        for (String userPrompt : userPrompts) {
            workUserWrite.setWords(workUserWrite.getWords() + (long) userPrompt.length());
        }
        workUserWrite.setWords(workUserWrite.getWords() + (long) systemPrompt.length());

        WriteEventSourceListener eventSourceListener = new WriteEventSourceListener(workUserWrite, sseEmitter);
        OpenAIStreamHttpUtils.request(systemPrompt, userPrompts, eventSourceListener);

    }

    @Async
    @Override
    @SneakyThrows
    public void aibreak(String requires, WorkUserWrite workUserWrite, SseEmitter sseEmitter, List<WorkBookPart> finalWorkBookParts) {

        WorkBookPart bookPart = finalWorkBookParts.get(0);
        String partTitle = bookPart.getPartTitle();
        sseEmitter.send(MapUtil.of("message", partTitle + "\n"));
        finalWorkBookParts.remove(0);
        List<String> userPrompts = new ArrayList<>();

        userPrompts.add(requires);
        String prompt = String.format("这是章节标题:[%s],这是章节内容:[%s]", bookPart.getPartTitle(), bookPart.getContent());
        userPrompts.add(prompt);
        userPrompts.add("直接返回生成的内容，不要添加额外的文字");

//        ClaudeHttpUtils.request("你是一名资深网文大神作家,按照以下要求,写一个章节", userPrompts, sseEmitter);

//        OpenAIHttpUtils.tokens("你是一名资深网文大神作家", userPrompts);
        String systemPrompt = "你是一名资深网文大神作家";
        for (String userPrompt : userPrompts) {
            workUserWrite.setWords(workUserWrite.getWords() + (long) userPrompt.length());
        }
        workUserWrite.setWords(workUserWrite.getWords() + (long) systemPrompt.length());

        BreakEventSourceListener eventSourceListener = new BreakEventSourceListener(workUserWrite, sseEmitter, finalWorkBookParts);
        OpenAIStreamHttpUtils.request(systemPrompt, userPrompts, eventSourceListener);

    }


    @Async
    @Override
    @SneakyThrows
    public void aipolish(String content, String relation, String requires, List<WorkBookRole> workBookRoles, WorkUserWrite workUserWrite, SseEmitter sseEmitter) {
        sseEmitter.send(MapUtil.of("message", ""));
        List<String> userPrompts = new ArrayList<>();
        // 背景
        userPrompts.add(String.format("文本:[%s]", content));
        // 角色
        if (ObjectUtil.isNotEmpty(workBookRoles)) {
            StringBuilder ssss = new StringBuilder("角色:\n");
            for (WorkBookRole workBookRole : workBookRoles) {
                ssss.append("姓名:")
                        .append(workBookRole.getName())
                        .append("  性格:")
                        .append(workBookRole.getCharacte())
                        .append("  信息:")
                        .append(workBookRole.getPlot());
            }
            userPrompts.add(ssss.toString());
        }

        //角色关系
        userPrompts.add(String.format("角色关系:%s", relation));
        // 扩写
        userPrompts.add(String.format("扩写要求:%s", requires));

        userPrompts.add("直接返回生成的内容，不要添加额外的文字");


//        ClaudeHttpUtils.request("你是一名资深网文大神作家,按照以下要求,写一个章节", userPrompts, sseEmitter);



        String systemPrompt = "你是一名资深网文大神作家,按照以下要求,把文本扩写润色一下";
        for (String userPrompt : userPrompts) {
            workUserWrite.setWords(workUserWrite.getWords() + (long) userPrompt.length());
        }
        workUserWrite.setWords(workUserWrite.getWords() + (long) systemPrompt.length());

        PolishEventSourceListener eventSourceListener = new PolishEventSourceListener(workUserWrite, sseEmitter);
        OpenAIStreamHttpUtils.request(systemPrompt, userPrompts, eventSourceListener);
    }


    /**
     * AI添加角色
     *
     * @param info
     * @return
     */
    @Override
    public String aiAddRoleRole(String info) {
        return OpenAIHttpUtils.request("根据以下信息分析出每个人物的角色名称，性别，角色性格，角色信息，返回格式如下\n" +
                "角色名称:;性别:;角色性格:;角色信息:;   每个人物之间用&&分隔", Collections.singletonList(info));
//        return null;
    }
}

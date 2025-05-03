package com.lance017.workbench.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.lance017.system.domain.WorkUserWrite;
import com.lance017.system.service.IWorkUserWriteService;
import com.lance017.workbench.domain.response.OpenAiData;
import com.lance017.workbench.service.ApiLoginService;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Getter
@Setter
public class PolishEventSourceListener extends EventSourceListener {

    private SseEmitter sseEmitter;

    private WorkUserWrite workUserWrite;


    public PolishEventSourceListener(WorkUserWrite workUserWrite, SseEmitter sseEmitters) {
        this.workUserWrite = workUserWrite;
        this.sseEmitter = sseEmitters;
    }

    private PolishEventSourceListener() {

    }

    private void hadError() {
        try {
            sseEmitter.send(MapUtil.of("message", "\n"));
            sseEmitter.send(MapUtil.of("message", "当前使用人数较多，请稍后重试"));
            sseEmitter.send(MapUtil.of("message", "DONE"));
            workUserWrite.setStatus(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sseEmitter.complete();
    }

    private void had() {
        sseEmitter.complete();
        this.workUserWrite.setCreateTime(DateUtil.date());
        IWorkUserWriteService writeService = SpringUtil.getBean(IWorkUserWriteService.class);
        workUserWrite.setWords(workUserWrite.getWords() + (long) workUserWrite.getContent().length());
        workUserWrite.setWords(workUserWrite.getWords());
        writeService.save(this.workUserWrite);
        ApiLoginService apiLoginService = SpringUtil.getBean(ApiLoginService.class);
        apiLoginService.subCode(workUserWrite.getUserId(), workUserWrite.getWords());
    }


    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("OpenAI建立sse连接...");
    }

    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("OpenAI返回数据：{}", data);
        if (data.equals("[DONE]")) {
            log.info("OpenAI返回数据结束了");
            try {
                sseEmitter.send(MapUtil.of("message", "\n"));
                sseEmitter.send(MapUtil.of("message", "DONE"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return;
        }

        if (StrUtil.isNotEmpty(data)) {
            try {

                OpenAiData aiData = JSONUtil.toBean(data, OpenAiData.class);

                if (!aiData.getChoices().isEmpty()) {
                    for (OpenAiData.Choice choice : aiData.getChoices()) {
                        String string = choice.getDelta().getContent();
                        if (StrUtil.isNotBlank(string)) {
                            this.workUserWrite.setContent(this.workUserWrite.getContent() + string);
                            log.info("OpenAI返回数据：{}", string);
                            Map map = new HashMap();
                            map.put("message", string);
                            sseEmitter.send(map);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public void onClosed(EventSource eventSource) {
        log.info("OpenAI关闭sse连接...");
        had();
    }

    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        if (Objects.isNull(response)) {
            log.error("OpenAI  sse连接异常:{}", t);
            eventSource.cancel();
            hadError();
//            sseEmitter.completeWithError(t);
            return;
        }
        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", body.string(), t);
        } else {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", response, t);
        }
        eventSource.cancel();
        hadError();
//        sseEmitter.completeWithError(t);
    }

}

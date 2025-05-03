package com.lance017.workbench.utils;

import cn.hutool.core.util.StrUtil;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;

import me.vacuity.ai.sdk.openai.OpenaiClient;
import me.vacuity.ai.sdk.openai.entity.ChatFunction;
import me.vacuity.ai.sdk.openai.entity.ChatMessage;
import me.vacuity.ai.sdk.openai.request.ChatRequest;
import me.vacuity.ai.sdk.openai.response.StreamChatResponse;
import me.vacuity.ai.sdk.openai.service.FunctionExecutor;
import okhttp3.sse.EventSourceListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@Slf4j
public class ClaudeHttpUtils {

    public static final String API_URL = "https://api.claude-Plus.top";

    public static final String API_KEY = "sk-P051YWyONAUq3K167290B27f1e924e11BeF447Cf196d90C4";

    public static final String API_MODEL = "claude-3-5-sonnet-20240620";

    public static OpenaiClient client = new OpenaiClient(API_KEY, Duration.ofSeconds(60), API_URL);


    /**
     * 计算tokens
     */
    public static void request(String systemPrompt, List<String> userPrompts, SseEmitter sseEmitter) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemPrompt));
        for (String userPrompt : userPrompts) {
            messages.add(new ChatMessage("user", userPrompt));
        }


        ChatRequest request = ChatRequest.builder()
                .model(API_MODEL)
                .messages(messages)
                .maxTokens(1024)
                .build();
        Flowable<StreamChatResponse> response = null;
//        StringBuilder content = new StringBuilder();

            response = client.streamChat(request);
            response.doOnNext(s -> {
                if (s != null) {
                    log.info("data:{}", s.getChoices().get(0).getDelta().getContent());
                    log.info("usage:{}", s.getUsage());
                }

            }).blockingSubscribe();
//        }
    }
}

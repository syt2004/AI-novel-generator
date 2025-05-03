package com.lance017.web;

import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.sse.ConsoleEventSourceListener;
import okhttp3.OkHttpClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ST {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
//                .proxy(proxy)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        OpenAiStreamClient client = OpenAiStreamClient.builder()
                .apiHost("https://api.claude-Plus.top/")
                .apiKey(Collections.singletonList("sk-P051YWyONAUq3K167290B27f1e924e11BeF447Cf196d90C4"))
                .build();

        ConsoleEventSourceListener eventSourceListener = new ConsoleEventSourceListener();
        Message message = Message.builder().role(Message.Role.USER).content("请帮我写一篇作文，1000字左右，内容为森林的风景").build();
        Message message1 = Message.builder().role(Message.Role.SYSTEM).content("You are a helpful assistant.").build();
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .messages(Arrays.asList(message, message1))
                .model("claude-3-5-sonnet-20240620")
                .stream(true)
                .build();
        client.streamChatCompletion(chatCompletion, eventSourceListener);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

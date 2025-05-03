package com.lance017.workbench.utils;

import com.lance017.workbench.listener.BreakEventSourceListener;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.BaseMessage;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import com.unfbx.chatgpt.interceptor.OpenAiResponseInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSourceListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Slf4j
public class OpenAIStreamHttpUtils {

    public static final String API_URL = "https://api.siliconflow.cn/";

    public static final String API_KEY = "sk-xayyxxknegmwthlghiukgsquygbsgxjanzxnlvuqwxcngqzo";

    public static final String API_MODEL = "deepseek-ai/DeepSeek-V3";

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient
                .Builder()
//                .proxy(proxy)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new OpenAiResponseInterceptor())
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build();
    }


    private static OpenAiStreamClient getOpenAiClient() {
        return OpenAiStreamClient.builder()
                //支持多key传入，请求时候随机选择
                .apiKey(Arrays.asList(API_KEY))
                //自定义key的获取策略：默认KeyRandomStrategy
                //.keyStrategy(new KeyRandomStrategy())
                .okHttpClient(getOkHttpClient())
                //自己做了代理就传代理地址，没有可不不传,(关注公众号回复：openai ，获取免费的测试代理地址)
                .apiHost(API_URL)
                .build();
    }


    /**
     * 计算tokens
     */
    public static void request(String systemPrompt, List<String> userPrompts, EventSourceListener eventSourceListener) {
        List<Message> messages = new ArrayList<>(userPrompts.size() + 1);
        messages.add(Message.builder().role(BaseMessage.Role.SYSTEM).content(systemPrompt).build());
        for (String userPrompt : userPrompts) {
            messages.add(Message.builder().role(BaseMessage.Role.USER).content(userPrompt).build());
        }

        ChatCompletion chatCompletion = ChatCompletion
                .builder()
                .messages(messages)
                .model(API_MODEL)
                .stream(true)
                .build();
        getOpenAiClient().streamChatCompletion(chatCompletion, eventSourceListener);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

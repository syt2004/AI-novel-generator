package com.lance017;


import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import com.unfbx.chatgpt.sse.ConsoleEventSourceListener;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WriterApplication.class)
public class RequestTest {


    /**
     * 中转api地址
     */
    public static final String URL = "https://api.siliconflow.cn/";
    /**
     * KEY
     */
    public static final String KEY = "sk-xayyxxknegmwthlghiukgsquygbsgxjanzxnlvuqwxcngqzo";


    public static final String API_MODEL = "deepseek-ai/DeepSeek-V3";

    /**
     * 请求测试
     */
    @Test
    public void test() {



        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
//        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
//        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
//                .proxy(proxy)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        OpenAiStreamClient client = OpenAiStreamClient.builder()
                .apiHost(URL)
                .apiKey(Collections.singletonList(KEY))
                .okHttpClient(okHttpClient)
                .build();

        SSEventSourceListener eventSourceListener = new SSEventSourceListener();
        Message message = Message.builder().role(Message.Role.USER).content("请帮我写一篇作文，1000字左右，内容为森林的风景").build();
        Message message1 = Message.builder().role(Message.Role.SYSTEM).content("You are a helpful assistant.").build();
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .messages(Arrays.asList(message, message1))
                .model(API_MODEL)
                .stream(true)
                .build();
        client.streamChatCompletion(chatCompletion, eventSourceListener);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        Map<String, Object> objectMap = new HashMap<>();
//        // 模型名称
//        objectMap.put("model", "claude-3-5-sonnet-20240620");
//
//        List<Map<String, String>> messages = new LinkedList<>();
//        messages.add(
//                MapUtil.builder(new HashMap<String, String>())
//                        .put("role", "system")
//                        .put("content", "You are a helpful assistant.")
//                        .build()
//        );
//        messages.add(
//                MapUtil.builder(new HashMap<String, String>())
//                        .put("role", "user")
//                        .put("content", "请帮我写一篇作文，1000字左右，内容为森林的风景")
//                        .build()
//        );
//
//        objectMap.put("messages", messages);
//        HttpRequest httpRequest = HttpRequest.post(URL)
//                .header("Accept", "application/json")
//                .header("Authorization", "Bearer " + KEY)
//                .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
//                .header("Content-Type", "application/json")
//                .body(JSONUtil.toJsonStr(objectMap));
//
//        HttpResponse httpResponse = httpRequest.execute();
//        System.out.println(httpResponse.body());


    }

}

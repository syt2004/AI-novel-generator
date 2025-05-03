package com.lance017.workbench.utils;

import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.chat.BaseMessage;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import com.unfbx.chatgpt.interceptor.OpenAiResponseInterceptor;
import com.unfbx.chatgpt.utils.TikTokensUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
public class OpenAIHttpUtils {

    public static final String API_URL = "https://api.claude-Plus.top/";

    public static final String API_KEY = "sk-P051YWyONAUq3K167290B27f1e924e11BeF447Cf196d90C4";

    public static final String API_MODEL = "claude-3-5-sonnet-20240620";

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
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }


    private static OpenAiClient getOpenAiClient() {
        return OpenAiClient.builder()
                //支持多key传入，请求时候随机选择
                .apiKey(Arrays.asList(API_KEY))
                //自定义key的获取策略：默认KeyRandomStrategy
                //.keyStrategy(new KeyRandomStrategy())
                .okHttpClient(getOkHttpClient())
                //自己做了代理就传代理地址，没有可不不传,(关注公众号回复：openai ，获取免费的测试代理地址)
                .apiHost(API_URL)
                .build();
    }



    public static String request(String systemPrompt, List<String> userPrompts) {
        List<Message> messages = new ArrayList<>(userPrompts.size() + 1);
        messages.add(Message.builder().role(BaseMessage.Role.SYSTEM).content(systemPrompt).build());
        for (String userPrompt : userPrompts) {
            messages.add(Message.builder().role(BaseMessage.Role.USER).content(userPrompt).build());
        }

        ChatCompletion chatCompletion = ChatCompletion
                .builder()
                .messages(messages)
                .model(API_MODEL)
                .stream(false)
                .build();
        ChatCompletionResponse chatCompletionResponse = getOpenAiClient().chatCompletion(chatCompletion);
        StringBuilder sb = new StringBuilder();
        chatCompletionResponse.getChoices().forEach(e -> {
            sb.append(e.getMessage().getContent());
        });
        log.info("OpenAI response: {}", sb);
        return sb.toString();
    }

}

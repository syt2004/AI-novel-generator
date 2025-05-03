package com.lance017.workbench.service;

import com.lance017.workbench.domain.request.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ApiAIService {

    SseEmitter aiwrite(AIWriteRequest request);


    SseEmitter aibreak(AIBreakRequest request);

    SseEmitter polish(AIPolishRequest request);

}

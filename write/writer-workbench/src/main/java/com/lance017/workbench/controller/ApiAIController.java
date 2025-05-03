package com.lance017.workbench.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.lance017.common.core.domain.AjaxResult;
import com.lance017.workbench.annotation.WorkbenchSign;
import com.lance017.workbench.domain.request.*;
import com.lance017.workbench.service.ApiAIService;
import com.lance017.workbench.service.ApiBookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
@RequestMapping("/api/ai")
@AllArgsConstructor
@RestController
public class ApiAIController {

    private final ApiAIService apiAIService;
    @WorkbenchSign
    @PostMapping(value = "/write", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> write(@RequestBody @Valid AIWriteRequest request) {
        return ResponseEntity.ok(apiAIService.aiwrite(request));
    }


    @WorkbenchSign
    @PostMapping(value = "/break", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> aibreak(@RequestBody @Valid AIBreakRequest request) {
        return ResponseEntity.ok(apiAIService.aibreak(request));
    }

    @WorkbenchSign
    @PostMapping(value = "/polish", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> polish(@RequestBody @Valid AIPolishRequest request) {
        return ResponseEntity.ok(apiAIService.polish(request));
    }

}

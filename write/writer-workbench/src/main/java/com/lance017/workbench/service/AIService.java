package com.lance017.workbench.service;

import com.lance017.system.domain.WorkBookPart;
import com.lance017.system.domain.WorkBookRole;
import com.lance017.system.domain.WorkUserWrite;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface AIService {

    void aiwrite(String bg,
                 String relation,
                 String plot,
                 String style,
                 String requires,
                 List<WorkBookRole> workBookRoles,
                 WorkUserWrite workUserWrite,
                 SseEmitter sseEmitter);

    void aibreak(String requires, WorkUserWrite workUserWrite, SseEmitter sseEmitter, List<WorkBookPart> finalWorkBookParts);


    void aipolish(String content,
                 String relation,
                 String requires,
                 List<WorkBookRole> workBookRoles,
                 WorkUserWrite workUserWrite,
                 SseEmitter sseEmitter);


    /**
     * AI添加角色
     * @param info
     * @return
     */
    String aiAddRoleRole(String info);


}

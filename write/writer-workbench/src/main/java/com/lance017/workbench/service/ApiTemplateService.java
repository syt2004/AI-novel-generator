package com.lance017.workbench.service;

import com.lance017.workbench.domain.request.*;

public interface ApiTemplateService {


    /**
     * 模板消息
     * @param request
     * @return
     */
    Object info(TemplateInfoRequest request);

    /**
     * 更新模板信息
     * @param request
     */
    Object update(TemplateUpdateRequest request);




}

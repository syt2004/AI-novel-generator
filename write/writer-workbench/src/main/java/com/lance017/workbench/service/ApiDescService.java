package com.lance017.workbench.service;

import com.lance017.workbench.domain.request.MyDescListRequest;
import com.lance017.workbench.domain.request.TemplateInfoRequest;
import com.lance017.workbench.domain.request.TemplateUpdateRequest;

public interface ApiDescService {


    /**
     * 模板消息
     * @param request
     * @return
     */
    Object list(MyDescListRequest request);

}

package com.lance017.workbench.service;

import com.lance017.workbench.domain.request.ExchangeRequest;

public interface ApiCodeService {

    /**
     * 兑换兑换码
     * @param request
     */
    void exchange(ExchangeRequest request);

}

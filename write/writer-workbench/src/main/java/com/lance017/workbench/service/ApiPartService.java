package com.lance017.workbench.service;

import com.lance017.workbench.domain.request.*;

public interface ApiPartService {

    /**
     * 添加作品章节
     * @param request
     */
    Object add(PartAddRequest request);

    /**
     * 获取作品章节信息
     * @return
     */
    Object info(PartInfoRequest request);

    /**
     * 排序
     * @param request
     */
    void sort(PartSortRequest request);


    /**
     * 更新作品章节信息
     * @param request
     */
    Object update(PartUpdateRequest request);

    /**
     * 删除作品章节
     * @param request
     */
    void delete(PartDeleteRequest request);


    /**
     * 获取作品章节列表
     * @return
     */
    Object list(PartListRequest request);

}

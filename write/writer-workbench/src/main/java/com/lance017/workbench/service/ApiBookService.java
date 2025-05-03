package com.lance017.workbench.service;

import com.lance017.workbench.domain.request.*;

public interface ApiBookService {

    /**
     * 创建作品
     * @param addBook
     */
    void addBook(BookAddRequest addBook);

    /**
     * 获取作品信息
     * @return
     */
    Object infoBook(BookInfoRequest request);

    /**
     * 更新作品信息
     * @param request
     */
    void update(BookUpdateRequest request);

    /**
     * 删除作品
     * @param request
     */
    void delete(BookDeleteRequest request);


    /**
     * 获取作品列表
     * @return
     */
    Object list();

    /**
     * 导出作品
     * @param request
     * @return
     */
    Object export(BookExportRequest request);

}

package com.lance017.system.domain.excel;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lance017.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class WorkCodeExcel {

    @Excel(name = "ID")
    private Long id;

    @Excel(name = "密钥")
    private String code;

    /** 字数 */
    @Excel(name = "字数", dictType = "work_code_words")
    private Long words;

    /** 类型 */
    @Excel(name = "类型", dictType = "word_code_type")
    private Integer type;

    /** 状态 */
    @Excel(name = "状态", dictType = "work_code_status")
    private Integer status;

    /** 创建者 */
    @Excel(name = "创建者")
    private String createBy;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}

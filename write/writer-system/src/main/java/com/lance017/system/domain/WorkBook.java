package com.lance017.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lance017.common.annotation.Excel;

/**
 * 作品对象 work_book
 *
 * @author writer
 * @date 2024-10-05
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@TableName("work_book")
public class WorkBook {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /** ID */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String title;

    /** 简介 */
    @Excel(name = "简介")
    private String synopsis;

    /** 状态1正常 2删除 */
    @Excel(name = "状态1正常 2删除")
    private Integer status;

    /** 创建用户id */
    @Excel(name = "创建用户id")
    private Long userId;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;

    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;


    @TableField(exist = false)
    private Integer words;

}

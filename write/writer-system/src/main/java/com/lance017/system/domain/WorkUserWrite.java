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
 * 用户生成对象 work_user_write
 *
 * @author writer
 * @date 2024-10-09
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@TableName("work_user_write")
public class WorkUserWrite {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /** 用户ID */
    private Long id;

    /** 使用用户id */
    @Excel(name = "使用用户id")
    private Long userId;

    /** 小说id */
    @Excel(name = "小说id")
    private Long bookId;

    /** 字数 */
    @Excel(name = "字数")
    private Long words;

    /** 提示词 */
    @Excel(name = "提示词")
    private String des;

    /** 介绍 */
    @Excel(name = "介绍")
    private String content;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    /** 创建者 */
    private String createBy;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

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


    /** 剧情 */
    @Excel(name = "剧情")
    private String plot;


    /** 风格 */
    @Excel(name = "风格")
    private String style;


}

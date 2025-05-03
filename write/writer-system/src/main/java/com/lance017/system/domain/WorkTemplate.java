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
 * 小说模板对象 work_template
 *
 * @author writer
 * @date 2024-10-07
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@TableName("work_template")
public class WorkTemplate {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /** ID */
    private Long id;

    /** 小说id */
    @Excel(name = "小说id")
    private Long bookId;

//    @Excel(name = "小说id")
    private Long userId;

    /** 背景 */
    @Excel(name = "背景")
    private String bg;

    /** 角色关系 */
    @Excel(name = "角色关系")
    private String relation;

    /** 剧情id */
    @Excel(name = "剧情id")
    private Long plotId;

    /** 剧情 */
    @Excel(name = "剧情")
    private String plot;

    /** 风格id */
    @Excel(name = "风格id")
    private Long styleId;

    private Integer styleType;

    /** 风格 */
    @Excel(name = "风格")
    private String style;

    /** 要求id */
    @Excel(name = "要求id")
    private Long requiresId;

    private Integer requiresType;

    /** 要求 */
    @Excel(name = "要求")
    private String requires;

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

    private String roles;


}

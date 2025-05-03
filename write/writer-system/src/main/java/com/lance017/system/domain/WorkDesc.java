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
 * 提示词对象 work_desc
 *
 * @author writer
 * @date 2024-10-07
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@TableName("work_desc")
public class WorkDesc {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /** ID */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 提示词 */
    @Excel(name = "提示词")
    private String des;

    /** 介绍 */
    @Excel(name = "介绍")
    private String content;

    /** 精选状态 */
    @Excel(name = "精选状态")
    private Long choice;

    /** 使用次数 */
    @Excel(name = "使用次数")
    private Long useCount;

    /** 生成字数 */
    @Excel(name = "生成字数")
    private Long boundWords;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    private Integer official;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 公开状态 */
    @Excel(name = "公开状态")
    private Integer openStatus;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

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


}

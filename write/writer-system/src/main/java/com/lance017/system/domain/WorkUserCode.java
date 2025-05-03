package com.lance017.system.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lance017.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.Map;

/**
 * 用户兑换码对象 work_user_code
 *
 * @author writer
 * @date 2024-10-05
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@TableName("work_user_code")
public class WorkUserCode {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /** 用户ID */
    private Long id;

    /** 字数 */
    @Excel(name = "字数")
    private Long words;

    /** 剩余字数 */
    @Excel(name = "剩余字数")
    private Long remainingWords;

    /** 使用截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "使用截止时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;

    /** 使用用户id */
    @Excel(name = "使用用户id")
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


}

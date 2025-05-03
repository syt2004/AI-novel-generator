package com.lance017.system.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lance017.common.annotation.Excel;

/**
 * workUser对象 work_user
 *
 * @author writer
 * @date 2024-09-28
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@TableName("work_user")
public class WorkUser {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户账号 */
    @Excel(name = "用户账号")
    private String username;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickname;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 用户性别（1男 2女 0未知） */
    @Excel(name = "用户性别", readConverterExp = "1=男,2=女,0=未知")
    private Integer sex;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 帐号状态（1正常 0停用） */
    @Excel(name = "帐号状态", readConverterExp = "1=正常,0=停用")
    private Integer status;

    /** 最后登录IP */
    @Excel(name = "最后登录IP")
    private String loginIp;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

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

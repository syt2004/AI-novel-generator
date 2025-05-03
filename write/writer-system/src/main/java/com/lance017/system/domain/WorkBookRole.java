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
 * 小说角色对象 work_book_role
 *
 * @author writer
 * @date 2024-10-05
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@TableName("work_book_role")
public class WorkBookRole {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /** ID */
    private Long id;

    /** 小说id */
    @Excel(name = "小说id")
    private Long bookId;

    private Long groupId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 创建用户id */
    @Excel(name = "创建用户id")
    private Long userId;

    /** 用户性别 */
    @Excel(name = "用户性别")
    private Integer sex;

    /** 角色性格 */
    @Excel(name = "角色性格")
    private String characte;

    /** 角色信息 */
    @Excel(name = "角色信息")
    private String plot;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    @Excel(name = "是否显示 1:显示 0:不显示")
    private Integer isShow;

    /** 状态1正常 0删除 */
    @Excel(name = "状态1正常 0删除")
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

package com.lance017.workbench.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lance017.common.exception.ServiceException;
import com.lance017.system.domain.WorkBook;
import com.lance017.system.domain.WorkBookRole;
import com.lance017.system.domain.WorkUser;
import com.lance017.system.enums.WorkBookStatus;
import com.lance017.system.enums.WorkShowStatus;
import com.lance017.system.service.IWorkBookRoleService;
import com.lance017.system.service.IWorkBookService;
import com.lance017.workbench.context.WorkbenchContextUtil;
import com.lance017.workbench.domain.request.*;
import com.lance017.workbench.service.AIService;
import com.lance017.workbench.service.ApiRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ApiRoleServiceImpl implements ApiRoleService {

    private final IWorkBookRoleService workBookRoleService;

    private final IWorkBookService workBookService;

    private final AIService aiService;

    /**
     * 添加作品角色
     * @param request
     */
    @Override
    public Object add(RoleAddRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkBookRole workBookRole = workBookRoleService.getOne(
                new LambdaQueryWrapper<WorkBookRole>()
                        .eq(WorkBookRole::getName, request.getName())
                        .eq(WorkBookRole::getUserId, workUser.getId())
                        .eq(WorkBookRole::getBookId, request.getBookId())
                        .eq(WorkBookRole::getStatus, WorkBookStatus.NORMAL.getCode())
        );




        if (ObjectUtil.isNotEmpty(workBookRole)) {
            throw new RuntimeException("已存在同名的角色");
        }
        workBookRole = new WorkBookRole();
        workBookRole.setBookId(request.getBookId());
        workBookRole.setName(request.getName());
        workBookRole.setUserId(workUser.getId());
        workBookRole.setSex(request.getSex());
        workBookRole.setCharacte(request.getCharacte());
        workBookRole.setPlot(request.getPlot());
        workBookRole.setSort(0);
        workBookRole.setIsShow(request.getIsShow());
        workBookRole.setStatus(WorkBookStatus.NORMAL.getCode());
        workBookRole.setCreateBy(workUser.getUsername());
        workBookRole.setCreateTime(DateUtil.date());
        workBookRoleService.save(workBookRole);
        return MapUtil.of("id", workBookRole.getId());
    }





    /**
     * 更新作品角色
     * @param request
     */
    @Override
    public void update(RoleUpdateRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkBookRole workBookRole = workBookRoleService.getOne(
                new LambdaQueryWrapper<WorkBookRole>()
                        .eq(WorkBookRole::getId, request.getId())
                        .eq(WorkBookRole::getUserId, workUser.getId())
                        .eq(WorkBookRole::getBookId, request.getBookId())
                        .eq(WorkBookRole::getStatus, WorkBookStatus.NORMAL.getCode())
        );
        if (ObjectUtil.isEmpty(workBookRole)) {
            throw new RuntimeException("未找到该角色");
        }
        workBookRole.setName(request.getName());
        workBookRole.setSex(request.getSex());
        workBookRole.setCharacte(request.getCharacte());
        workBookRole.setPlot(request.getPlot());
        workBookRole.setIsShow(request.getIsShow());
        workBookRole.setUpdateBy(workUser.getUsername());
        workBookRole.setUpdateTime(DateUtil.date());
        workBookRoleService.saveOrUpdate(workBookRole);
    }

    /**
     * 删除角色
     *
     * @param request
     */
    @Override
    public void delete(RoleDeleteRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkBookRole workBookRole = workBookRoleService.getOne(
                new LambdaQueryWrapper<WorkBookRole>()
                        .eq(WorkBookRole::getId, request.getId())
                        .eq(WorkBookRole::getUserId, workUser.getId())
                        .eq(WorkBookRole::getBookId, request.getBookId())
                        .eq(WorkBookRole::getStatus, WorkBookStatus.NORMAL.getCode())
        );
        if (ObjectUtil.isEmpty(workBookRole)) {
            throw new RuntimeException("未找到该角色");
        }
        workBookRole.setStatus(WorkBookStatus.DELETED.getCode());
        workBookRoleService.saveOrUpdate(workBookRole);
    }

    /**
     * 获取作品角色列表
     *
     * @return
     */
    @Override
    public Object list(RoleListRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        return workBookRoleService.list(
                new LambdaQueryWrapper<WorkBookRole>()
                        .eq(WorkBookRole::getUserId, workUser.getId())
                        .eq(WorkBookRole::getBookId, request.getId())
                        .eq(WorkBookRole::getStatus, WorkBookStatus.NORMAL.getCode())
                        .orderByAsc(WorkBookRole::getId, WorkBookRole::getSort)


        );
    }

    /**
     * 获取角色列表
     *
     * @param request
     * @return
     */
    @Override
    public Object listByAi(RoleListRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        return workBookRoleService.list(
                new LambdaQueryWrapper<WorkBookRole>()
                        .eq(WorkBookRole::getUserId, workUser.getId())
                        .eq(WorkBookRole::getBookId, request.getId())
                        .eq(WorkBookRole::getStatus, WorkBookStatus.NORMAL.getCode())
                        .eq(WorkBookRole::getIsShow, WorkShowStatus.YES.getCode())
                        .orderByAsc(WorkBookRole::getId, WorkBookRole::getSort)
        );
    }

    @Override
    public int aiAdd(RoleAIAddRequest request) {
        String string = aiService.aiAddRoleRole(request.getInfo());
        string = string.replaceAll("\n", "");
        String[] roles = string.split("&&");
        List<WorkBookRole> workBookRoles = new ArrayList<>();
        for (String role : roles) {
            String[] split = role.split(";");
            WorkBookRole workBookRole = new WorkBookRole();
            for (String s : split) {
                if (StrUtil.startWith(s, "角色名称:")) {
                    String name = StrUtil.subAfter(s, "角色名称:", true);
                    workBookRole.setName(name);
                }
                if (StrUtil.startWith(s, "性别:")) {
                    String sex = StrUtil.subAfter(s, "性别:", true);
                    if (StrUtil.equals(sex, "男")) {
                        workBookRole.setSex(1);
                    } else if (StrUtil.equals(sex, "女")) {
                        workBookRole.setSex(2);
                    }
                }
                if (StrUtil.startWith(s, "角色性格:")) {
                    String characte = StrUtil.subAfter(s, "角色性格:", true);
                    workBookRole.setCharacte(characte);
                }
                if (StrUtil.startWith(s, "角色信息:")) {
                    String plot = StrUtil.subAfter(s, "角色信息:", true);
                    workBookRole.setPlot(plot);
                }
            }
            if (StrUtil.isNotEmpty(workBookRole.getName())) {
                if (ObjectUtil.isEmpty(workBookRole.getSex())) {
                    workBookRole.setSex(0);
                }
                if (StrUtil.isEmpty(workBookRole.getCharacte())) {
                    workBookRole.setCharacte("无");
                }
                if (StrUtil.isEmpty(workBookRole.getPlot())) {
                    workBookRole.setPlot("无");
                }
                workBookRole.setCreateTime(DateUtil.date());
                workBookRole.setBookId(request.getBookId());
                workBookRole.setUserId(WorkbenchContextUtil.getWorkUser().getId());
                workBookRole.setCreateBy(WorkbenchContextUtil.getWorkUser().getUsername());
                workBookRoles.add(workBookRole);
            }
        }

        if (ObjectUtil.isNotEmpty(workBookRoles)) {
            workBookRoleService.saveBatch(workBookRoles);
        }


        return workBookRoles.size();


    }


    public static void main(String[] args) {
//        String string = "角色名称:张平凡;性别:男;角色性格:坚韧、认真、善于思考;角色信息:主角、穿越者、获得熟练度面板;&&角色名称:李青云;性别:男;角色性格:天赋出众、欣赏与嫉妒并存;角色信息:青云门大师兄;&&角色名称:苏灵儿;性别:女;角色性格:美丽聪慧;角色信息:青云门女弟子、与张平凡互生情愫;&&角色名称:王长老;性别:男;角色性格:温和;角色信息:青云门长老、张平凡的引路人;&&角色名称:赵无极;性别:男;角色性格:天才;角色信息:敌对宗门弟子、与张平凡多次交锋;";
//        String[] roles = string.split("&&");
//
//        List<WorkBookRole> workBookRoles = new ArrayList<>();
//        for (String role : roles) {
//            log.info("role:{}", role);
//            String[] split = role.split(";");
//            WorkBookRole workBookRole = new WorkBookRole();
//            for (String s : split) {
//                if (StrUtil.startWith(s, "角色名称:")) {
//                    String name = StrUtil.subAfter(s, "角色名称:", true);
//                    workBookRole.setName(name);
//                }
//                if (StrUtil.startWith(s, "性别:")) {
//                    String sex = StrUtil.subAfter(s, "性别:", true);
//                    if (StrUtil.equals(sex, "男")) {
//                        workBookRole.setSex(1);
//                    } else if (StrUtil.equals(sex, "女")) {
//                        workBookRole.setSex(2);
//                    }
//                }
//                if (StrUtil.startWith(s, "角色性格:")) {
//                    String characte = StrUtil.subAfter(s, "角色性格:", true);
//                    workBookRole.setCharacte(characte);
//                }
//                if (StrUtil.startWith(s, "角色信息:")) {
//                    String plot = StrUtil.subAfter(s, "角色信息:", true);
//                    workBookRole.setPlot(plot);
//                }
//            }
//            log.info("workBookRole:{}", workBookRole);
//            if (StrUtil.isNotEmpty(workBookRole.getName())) {
//                if (ObjectUtil.isEmpty(workBookRole.getSex())) {
//                    workBookRole.setSex(0);
//                }
//                if (StrUtil.isEmpty(workBookRole.getCharacte())) {
//                    workBookRole.setCharacte("无");
//                }
//                if (StrUtil.isEmpty(workBookRole.getPlot())) {
//                    workBookRole.setPlot("无");
//                }
//                workBookRole.setCreateTime(DateUtil.date());
//                workBookRole.setCreateBy(WorkbenchContextUtil.getWorkUser().getUsername());
//                workBookRoles.add(workBookRole);
//            }
//        }
//
//        log.info("workBookRoles:{}", workBookRoles);

//        if (ObjectUtil.isNotEmpty(workBookRoles)) {
//            throw new ServiceException("请检查角色信息是否正确");
//        }
    }


}

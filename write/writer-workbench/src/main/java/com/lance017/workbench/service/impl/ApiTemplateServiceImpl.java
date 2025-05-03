package com.lance017.workbench.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lance017.common.core.redis.RedisCache;
import com.lance017.common.utils.bean.BeanUtils;
import com.lance017.system.domain.WorkTemplate;
import com.lance017.system.domain.WorkUser;
import com.lance017.system.service.IWorkTemplateService;
import com.lance017.workbench.constant.WorkBenchConstant;
import com.lance017.workbench.context.WorkbenchContextUtil;
import com.lance017.workbench.domain.request.TemplateInfoRequest;
import com.lance017.workbench.domain.request.TemplateUpdateRequest;
import com.lance017.workbench.domain.response.TemplateInfoResponse;
import com.lance017.workbench.service.ApiTemplateService;
import com.lance017.workbench.utils.JudgeBookUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;


@Slf4j
@Service
@AllArgsConstructor
public class ApiTemplateServiceImpl implements ApiTemplateService {

    private final IWorkTemplateService workTemplateService;


    private final RedisCache redisCache;

    /**
     * 模板消息
     *
     * @param request
     * @return
     */
    @Override
    public Object info(TemplateInfoRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        String key = WorkBenchConstant.WORKBENCH_TEMPLATE_PREFIX + ":" + workUser.getId() + ":" + request.getBookId();
        TemplateInfoResponse response = redisCache.getCacheObject(key);

        if (ObjectUtil.isNotEmpty(response)) {
            return response;
        }
        JudgeBookUtil.judgeBook(request.getBookId(), workUser.getId());
        WorkTemplate template = workTemplateService.getOne(
                new LambdaQueryWrapper<WorkTemplate>()
                        .eq(WorkTemplate::getBookId, request.getBookId())
                        .eq(WorkTemplate::getUserId, workUser.getId())
        );

        if (ObjectUtil.isNotEmpty(template)) {
            response = new TemplateInfoResponse();
            BeanUtil.copyProperties(template, response);
            redisCache.setCacheObject(key, response);
            return response;
        }
        return ObjectUtil.isEmpty(template) ? new HashMap<>() : template;
    }

    /**
     * 更新模板信息
     *
     * @param request
     */
    @Override
    @Transactional
    public Object update(TemplateUpdateRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        String key = WorkBenchConstant.WORKBENCH_TEMPLATE_PREFIX + ":" + workUser.getId() + ":" + request.getBookId();
        JudgeBookUtil.judgeBook(request.getBookId(), workUser.getId());
        WorkTemplate template = workTemplateService.getOne(
                new LambdaQueryWrapper<WorkTemplate>()
                        .eq(WorkTemplate::getBookId, request.getBookId())
                        .eq(WorkTemplate::getUserId, workUser.getId())
        );

        if (ObjectUtil.isEmpty(template)) {
            template = new WorkTemplate();
            template.setBookId(request.getBookId());
            template.setBg(request.getBg());
            template.setCreateBy(workUser.getUsername());
            template.setCreateTime(DateUtil.date());
            template.setUserId(workUser.getId());
        } else {
            template.setBg(request.getBg());
            template.setUpdateBy(workUser.getUsername());
            template.setUpdateTime(DateUtil.date());
        }
        template.setRelation(request.getRelation());
        template.setPlotId(request.getPlotId());
        template.setPlot(request.getPlot());
        template.setStyleId(request.getStyleId());
        template.setStyle(request.getStyle());
        template.setRequiresId(request.getRequiresId());
        template.setRequires(request.getRequires());
        template.setRoles(request.getRoles());
        workTemplateService.saveOrUpdate(template);


        TemplateInfoResponse response = new TemplateInfoResponse();
        BeanUtils.copyProperties(template, response);
        redisCache.setCacheObject(key, response);
        return response;
    }
}

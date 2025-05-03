package com.lance017.workbench.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lance017.common.core.redis.RedisCache;
import com.lance017.system.domain.WorkDesc;
import com.lance017.system.domain.WorkTemplate;
import com.lance017.system.domain.WorkUser;
import com.lance017.system.domain.WorkUserDesc;
import com.lance017.system.enums.WorkBookStatus;
import com.lance017.system.service.IWorkDescService;
import com.lance017.system.service.IWorkTemplateService;
import com.lance017.system.service.IWorkUserDescService;
import com.lance017.workbench.constant.WorkBenchConstant;
import com.lance017.workbench.context.WorkbenchContextUtil;
import com.lance017.workbench.domain.request.MyDescListRequest;
import com.lance017.workbench.domain.request.TemplateInfoRequest;
import com.lance017.workbench.domain.request.TemplateUpdateRequest;
import com.lance017.workbench.domain.response.TemplateInfoResponse;
import com.lance017.workbench.service.ApiDescService;
import com.lance017.workbench.service.ApiTemplateService;
import com.lance017.workbench.utils.JudgeBookUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class ApiDescServiceImpl implements ApiDescService {

    private final IWorkDescService workDescService;

    private final IWorkUserDescService workUserDescService;


    private final RedisCache redisCache;


    @Override
    public Object list(MyDescListRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();

        String key = WorkBenchConstant.WORKBENCH_DESC_PREFIX + ":" + workUser.getId() + ":" + request.getType();

        List<WorkDesc> cacheList = redisCache.getCacheList(key);

        if (ObjectUtil.isNotEmpty(cacheList)) {
            return cacheList;
        }

        List<Long> descIds = workUserDescService.listObjs(
                new LambdaQueryWrapper<WorkUserDesc>()
                        .select(WorkUserDesc::getDescId)
                        .eq(WorkUserDesc::getUserId, workUser.getId())
        );

        LambdaQueryWrapper<WorkDesc> workDescLambdaQueryWrapper = new LambdaQueryWrapper<WorkDesc>()
                .eq(WorkDesc::getType, request.getType())
                .eq(WorkDesc::getStatus, WorkBookStatus.NORMAL.getCode())
                .orderByDesc(WorkDesc::getCreateTime);

        if (ObjectUtil.isEmpty(descIds)) {
            workDescLambdaQueryWrapper = workDescLambdaQueryWrapper.eq(WorkDesc::getOfficial, WorkBookStatus.NORMAL.getCode());
        } else {
            workDescLambdaQueryWrapper = workDescLambdaQueryWrapper.and(i ->
                    i.in(WorkDesc::getId, descIds)
                            .or().eq(WorkDesc::getOfficial, WorkBookStatus.NORMAL.getCode()));
        }

        List<WorkDesc> list = workDescService.list(workDescLambdaQueryWrapper);
        if (ObjectUtil.isNotEmpty(list)) {
            redisCache.setCacheList(key, list);
        }
        return list;
    }


}

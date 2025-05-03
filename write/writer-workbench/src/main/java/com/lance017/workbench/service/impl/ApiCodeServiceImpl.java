package com.lance017.workbench.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.lance017.common.exception.ServiceException;
import com.lance017.common.utils.RedissonLockUtils;
import com.lance017.system.domain.WorkCode;
import com.lance017.system.domain.WorkUser;
import com.lance017.system.domain.WorkUserCode;
import com.lance017.system.enums.WorkCodeStatus;
import com.lance017.system.enums.WorkCodeType;
import com.lance017.system.service.IWorkCodeService;
import com.lance017.system.service.IWorkUserCodeService;
import com.lance017.workbench.context.WorkbenchContextUtil;
import com.lance017.workbench.domain.request.ExchangeRequest;
import com.lance017.workbench.service.ApiCodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@AllArgsConstructor
public class ApiCodeServiceImpl implements ApiCodeService {


    private final IWorkCodeService workCodeService;

    private final IWorkUserCodeService workUserCodeService;

    /**
     * 兑换兑换码
     *
     * @param request
     */
    @Override
    @Transactional
    public void exchange(ExchangeRequest request) {

        RLock lock = RedissonLockUtils.getLock("MANAGE_SETTLE_ACTION:" + request.getCode());
        try {
            if (lock.tryLock(10, 30, TimeUnit.SECONDS)) {
                WorkCode code = workCodeService.selectWorkCodeByCode(request.getCode());
                if (ObjectUtil.isEmpty(code)) {
                    throw new ServiceException("兑换码错误");
                }
                if (code.getStatus().equals(WorkCodeStatus.USED.getCode())) {
                    throw new ServiceException("兑换码已使用");
                }
                if (code.getStatus().equals(WorkCodeStatus.EXPIRED.getCode())) {
                    throw new ServiceException("兑换码错误");
                }

                WorkUser workUser = WorkbenchContextUtil.getWorkUser();
                code.setStatus(WorkCodeStatus.USED.getCode());
                code.setUserId(workUser.getId());
                code.setUpdateTime(DateUtil.date());
                code.setUpdateBy(workUser.getUsername());


                WorkUserCode userCode = new WorkUserCode();
                userCode.setWords(code.getWords());
                userCode.setRemainingWords(userCode.getWords());
                if (code.getType().equals(WorkCodeType.PERMANENT.getCode())) {
                    userCode.setDeadline(DateUtil.parse("2099-12-31"));
                } else if (code.getType().equals(WorkCodeType.MONTH_ONE.getCode())) {
                    userCode.setDeadline(DateUtil.offsetMonth(DateUtil.date(), 1));
                } else {
                    throw new ServiceException("兑换码错误");
                }
                userCode.setUserId(code.getUserId());
                userCode.setCreateBy(code.getCreateBy());
                userCode.setCreateTime(DateUtil.date());

                workCodeService.updateWorkCode(code);
                workUserCodeService.insertWorkUserCode(userCode);

            } else {
                log.error("---------------正在操作，勿重复点击");
                throw new ServiceException("操作超时，请重试");
            }
        } catch (ServiceException e) {
            log.error("操作超时，请重试: ", e);
            throw e;
        } catch (Exception e) {
            log.error("操作超时，请重试:", e);
            throw new ServiceException("系统异常");
        } finally {
            RedissonLockUtils.unLock(lock);
        }

    }
}

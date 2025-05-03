package com.lance017.workbench.service.impl;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lance017.common.core.redis.RedisCache;
import com.lance017.common.exception.ServiceException;
import com.lance017.common.utils.RedissonLockUtils;
import com.lance017.common.utils.SecurityUtils;
import com.lance017.system.domain.WorkCode;
import com.lance017.system.domain.WorkUser;
import com.lance017.system.domain.WorkUserCode;
import com.lance017.system.domain.WorkUserWrite;
import com.lance017.system.enums.WorkCodeStatus;
import com.lance017.system.enums.WorkCodeType;
import com.lance017.system.service.ISysConfigService;
import com.lance017.system.service.IWorkUserCodeService;
import com.lance017.system.service.IWorkUserService;
import com.lance017.system.service.IWorkUserWriteService;
import com.lance017.workbench.constant.WorkBenchConstant;
import com.lance017.workbench.context.WorkbenchContextUtil;
import com.lance017.workbench.domain.request.LoginRequest;
import com.lance017.workbench.domain.request.RegisterRequest;
import com.lance017.workbench.domain.request.UserCodeRequest;
import com.lance017.workbench.domain.request.UserInfoUpdateRequest;
import com.lance017.workbench.domain.response.UserinfoResponse;
import com.lance017.workbench.service.ApiLoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class ApiLoginServiceImpl implements ApiLoginService {

    private final RedisCache redisCache;

    private final IWorkUserService workUserService;

    private final IWorkUserCodeService workUserCodeService;

    private final IWorkUserWriteService workUserWriteService;

    private final ISysConfigService sysConfigService;
    /**
     * 注册
     * @param register 注册信息
     */
    @Override
    public void register(RegisterRequest register) {
        WorkUser workUser = workUserService.selectWorkUserByUsername(register.getUsername());
        if (ObjectUtil.isNotEmpty(workUser)) {
            throw new ServiceException("该账户名已存在，请更换注册");
        }
        workUser = new WorkUser();
        workUser.setUsername(register.getUsername());
        workUser.setNickname("用户" + RandomUtil.randomStringUpper(6));
        workUser.setEmail("");
        workUser.setSex(0);
        workUser.setAvatar("");
        workUser.setPassword(SecurityUtils.encryptPassword(register.getPassword()));
        workUser.setStatus(1);
        workUser.setCreateBy("");
        workUser.setCreateTime(new Date());
        workUserService.insertWorkUser(workUser);

        String giftWords = sysConfigService.getRegisterGiftWords();

        WorkUserCode workUserCode = new WorkUserCode();
        workUserCode.setWords(Long.parseLong(giftWords));
        workUserCode.setRemainingWords(workUserCode.getWords());
        workUserCode.setDeadline(DateUtil.parse("2099-12-31"));
        workUserCode.setUserId(workUser.getId());
        workUserCode.setCreateBy(workUser.getUsername());
        workUserCode.setCreateTime(DateUtil.date());
        workUserCode.setRemark("用户注册赠送" + giftWords + "字");
        workUserCodeService.save(workUserCode);



    }

    /**
     * 登录
     *
     * @param login 登录信息
     * @return token
     */
    @Override
    public Object login(LoginRequest login) {
        WorkUser workUser = workUserService.selectWorkUserByUsername(login.getUsername());
        if (ObjectUtil.isEmpty(workUser)) {
            throw new ServiceException("账户不存在");
        }
        if (!SecurityUtils.matchesPassword(login.getPassword(), workUser.getPassword())) {
            throw new ServiceException("账户或密码错误");
        }
        if (!ObjectUtil.equal(workUser.getStatus(), 1)) {
            throw new ServiceException("账户已停用");
        }
        String token = IdUtil.fastSimpleUUID();
        redisCache.setCacheObject(WorkBenchConstant.WORKBENCH_USER_KEY + token, workUser.getId(), 7, TimeUnit.DAYS);
        return MapUtil.of("token", token);
    }

    /**
     * 修改用户信息
     *
     * @param request
     */
    @Override
    public void userInfoUpdate(UserInfoUpdateRequest request) {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        WorkUser workUser1 = workUserService.selectWorkUserById(workUser.getId());
        if (ObjectUtil.isNotEmpty(workUser1) && !ObjectUtil.equal(workUser1.getId(), workUser.getId())) {
            throw new ServiceException("昵称已存在");
        }
        workUser.setNickname(request.getNickname());
        workUser.setUpdateBy(workUser.getUsername());
        workUser.setUpdateTime(DateUtil.date());
        workUserService.updateWorkUser(workUser);
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @Override
    public Object userinfo() {
        WorkUser workUser = WorkbenchContextUtil.getWorkUser();
        UserinfoResponse userinfoResponse = new UserinfoResponse();
        userinfoResponse.setAvatar(workUser.getAvatar());
        userinfoResponse.setNickname(workUser.getNickname());
        userinfoResponse.setUsername(workUser.getUsername());
        userinfoResponse.setSex(workUser.getSex());
        List<WorkUserCode> list = workUserCodeService.list(
                new LambdaQueryWrapper<WorkUserCode>()
                        .eq(WorkUserCode::getUserId, workUser.getId())
                        .gt(WorkUserCode::getRemainingWords, 0)
                        .ge(WorkUserCode::getDeadline, new Date())
        );
//        Map<Date, List<WorkUserCode>> collect = list.stream().collect(Collectors.groupingBy(WorkUserCode::getDeadline));
//        List<Map<String, Object>> list111 = new LinkedList<>();
//        List<Date> collect1 = collect.keySet().stream().sorted().collect(Collectors.toList());
//        for (Date date : collect1) {
//            List<WorkUserCode> workUserCodes = collect.get(date);
//            int year = DateUtil.year(date);
//            Map<String, Object> map = new HashMap<>();
//            if (year == 2099) {
//                map.put("date", "永久");
//            } else{
//                map.put("date", DateUtil.format(date, "yyyy-MM-dd"));
//            }
//            map.put("words", workUserCodes.stream().mapToLong(WorkUserCode::getWords).sum());
//            list111.add(map);
//        }
        userinfoResponse.setWords(list.stream().mapToLong(WorkUserCode::getRemainingWords).sum());
        userinfoResponse.setRigisterGiftWords(sysConfigService.getRegisterGiftWords());
        return userinfoResponse;
    }


    @Override
    public void subCode(Long workUserId, Long words) {
        RLock lock = RedissonLockUtils.getLock("MANAGE_SETTLE_ACTION_ID:" + workUserId);
        try {
            if (lock.tryLock(10, 30, TimeUnit.SECONDS)) {
                List<WorkUserCode> list = workUserCodeService.list(
                        new LambdaQueryWrapper<WorkUserCode>()
                                .eq(WorkUserCode::getUserId, workUserId)
                                .gt(WorkUserCode::getRemainingWords, 0)
                                .ge(WorkUserCode::getDeadline, new Date())
                );
                List<WorkUserCode> workUserCodes = list.stream().sorted(Comparator.comparing(WorkUserCode::getDeadline)).collect(Collectors.toList());

                for (WorkUserCode workUserCode : workUserCodes) {
                    Long workUserCodeWords = workUserCode.getRemainingWords();
                    if (workUserCodeWords >= words) {
                        workUserCode.setRemainingWords(workUserCodeWords - words);
                        words = 0L;
                        workUserCode.setUpdateTime(DateUtil.date());
                        workUserCodeService.updateById(workUserCode);
                        break;
                    } else {
                        workUserCode.setRemainingWords(0L);
                        words = words - workUserCodeWords;
                        workUserCode.setUpdateTime(DateUtil.date());
                        workUserCodeService.updateById(workUserCode);
                    }
                }

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


    @Override
    public Object usercode(UserCodeRequest request) {

        if (ObjectUtil.isNull(request.getPage())) {
            request.setPage(1);
        }

        if (ObjectUtil.isNull(request.getPageSize())) {
            request.setPageSize(10);
        }
        Page<WorkUserWrite> workUserWritePage = new Page<>(request.getPage(), request.getPageSize());
        LambdaQueryWrapper<WorkUserWrite> queryWrapper = new LambdaQueryWrapper<WorkUserWrite>()
                .eq(WorkUserWrite::getUserId, WorkbenchContextUtil.getWorkUser().getId())
                .eq(WorkUserWrite::getStatus, 1)
                .orderByDesc(WorkUserWrite::getCreateTime);

        return workUserWriteService.page(workUserWritePage, queryWrapper);
    }
}

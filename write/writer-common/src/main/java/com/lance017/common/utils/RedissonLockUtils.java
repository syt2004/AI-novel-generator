package com.lance017.common.utils;


import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

@Slf4j
//@Component
public class RedissonLockUtils {


//    @Autowired
//    private static RedissonClient redissonClient;
//
//    @Autowired
//    public static void setRedissonClient(RedissonClient redissonClient) {
//        RedissonLockUtils.redissonClient = redissonClient;
//    }

    public static RLock getLock(String lockStr) {
        return SpringUtil.getBean(RedissonClient.class).getLock(lockStr);
    }

    public static void unLock(RLock lock) {
        lock.unlock();
    }



}

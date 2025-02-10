package top.mingempty.distributed.lock.aspect;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.commons.util.ReflectionUtil;
import top.mingempty.distributed.lock.annotation.ResubmitLock;
import top.mingempty.distributed.lock.api.DistributedLock;
import top.mingempty.distributed.lock.enums.RealizeEnum;
import top.mingempty.distributed.lock.enums.TierEnum;
import top.mingempty.distributed.lock.exception.ResubmitLockException;
import top.mingempty.distributed.lock.exception.ResubmitUnLockException;
import top.mingempty.distributed.lock.factory.LockFactory;
import top.mingempty.domain.base.MeRsp;
import top.mingempty.domain.enums.YesOrNoEnum;
import top.mingempty.util.AspectUtil;
import top.mingempty.util.EnvironmentUtil;
import top.mingempty.util.ExpressionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 幂等校验拦截器
 *
 * @author zzhao
 */
@Slf4j
@Aspect
public class ResubmitLockAspect {


    // 定义切点Pointcut
    @Pointcut("@within(top.mingempty.distributed.lock.annotation.ResubmitLock)" +
            "|| @annotation(top.mingempty.distributed.lock.annotation.ResubmitLock)")
    public void executeService() {
    }

    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = AspectUtil.getMethod(joinPoint);
        ResubmitLock resubmitLock = method.getAnnotation(ResubmitLock.class);
        Object[] args = joinPoint.getArgs();
        String enabled = EnvironmentUtil.resolvePlaceholders(resubmitLock.enabled());
        if (!Boolean.TRUE.toString().equals(enabled)) {
            return joinPoint.proceed(args);
        }
        Class aClass = AspectUtil.getClass(joinPoint);
        DistributedLock distributedLock = gainDistributedLock(joinPoint, aClass, method, resubmitLock, args);
        try {
            if (distributedLock.tryLock(resubmitLock.waitTime(), resubmitLock.leaseTime(), TimeUnit.MILLISECONDS)) {
                return joinPoint.proceed(args);
            }
            throw new ResubmitLockException();
        } catch (Exception exception) {
            if (YesOrNoEnum.YES.equals(resubmitLock.isThrow())) {
                if (exception instanceof ResubmitLockException) {
                    return exception;
                }
                if (exception instanceof ResubmitUnLockException) {
                    return exception;
                }

                throw new ResubmitLockException(exception);
            }

            if (MeRsp.class.equals(resubmitLock.resultClass())) {
                return MeRsp.resubmit();
            }
            Class<?> resultClass = resubmitLock.resultClass();
            Constructor<?> cs = resultClass.getDeclaredConstructor();
            cs.setAccessible(true);
            Object instance = cs.newInstance();
            if (StrUtil.isNotEmpty(resubmitLock.errorCodeField())) {
                String errorCodeFieldStr = EnvironmentUtil.resolvePlaceholders(resubmitLock.errorCodeField());
                String errorCode = EnvironmentUtil.resolvePlaceholders(resubmitLock.errorCode());
                ReflectionUtil.setValue(errorCodeFieldStr, instance, errorCode);
            }
            if (StrUtil.isNotEmpty(resubmitLock.errorMsgField())) {
                String errorMsgFieldStr = EnvironmentUtil.resolvePlaceholders(resubmitLock.errorMsgField());
                String errorMsg = EnvironmentUtil.resolvePlaceholders(resubmitLock.errorMsg());
                ReflectionUtil.setValue(errorMsgFieldStr, instance, errorMsg);
            }
            return instance;
        } finally {
            try {
                distributedLock.unlock();
            } catch (Throwable throwable) {
                throw new ResubmitUnLockException(throwable);
            }
        }
    }


    static DistributedLock gainDistributedLock(ProceedingJoinPoint joinPoint,
                                               Class aClass, Method method, ResubmitLock resubmitLock, Object[] args) {
        String key = gainKey(resubmitLock.realize(), aClass, method, resubmitLock, args);
        return switch (resubmitLock.realize()) {
            case Redis -> redisDistributedLock(key, resubmitLock);
            case Zookeeper -> zookeeperDistributedLock(key, resubmitLock);
        };
    }

    private static DistributedLock zookeeperDistributedLock(String key, ResubmitLock resubmitLock) {
        return switch (resubmitLock.readWrite()) {
            case Read -> LockFactory.zookeeperLock(resubmitLock.instanceName()).readWriteLock(key).readLock().lock();
            case Write -> LockFactory.zookeeperLock(resubmitLock.instanceName()).readWriteLock(key).writeLock().lock();
            case None -> LockFactory.zookeeperLock(resubmitLock.instanceName()).key(key).lock();
        };
    }

    private static DistributedLock redisDistributedLock(String key, ResubmitLock resubmitLock) {
        return switch (resubmitLock.readWrite()) {
            case Read -> LockFactory.redisLock(resubmitLock.instanceName()).readWriteLock(key).readLock().lock();
            case Write -> LockFactory.redisLock(resubmitLock.instanceName()).readWriteLock(key).writeLock().lock();
            case None -> LockFactory.redisLock(resubmitLock.instanceName()).key(key).lock();
        };
    }


    private static String gainKey(RealizeEnum realize,
                                  Class aClass, Method method, ResubmitLock resubmitLock, Object[] args) {
        String key = realize.getKeyPrefix();
        if (TierEnum.Interface.equals(resubmitLock.tier())) {
            //说明是接口层级，使用当前类+方法名作为防重锁前缀
            String className = aClass.getName();
            String methodName = method.getName();
            key = key.concat(className.replaceAll("\\.", realize.getSeparator())).concat(realize.getSeparator()).concat(methodName);
        } else {
            String keyPrefixByBuss = resubmitLock.keyPrefix();
            if (keyPrefixByBuss.startsWith(realize.getSeparator())) {
                keyPrefixByBuss = keyPrefixByBuss.substring(1);
            }
            if (keyPrefixByBuss.endsWith(realize.getSeparator())) {
                keyPrefixByBuss = keyPrefixByBuss.substring(0, keyPrefixByBuss.length() - 1);
            }
            key = key.concat(keyPrefixByBuss);
        }
        key = key.concat(realize.getSeparator());

        if (resubmitLock.keys() != null
                && resubmitLock.keys().length > 0) {
            Object[] expressionValue = ExpressionUtils.gainExpressionValue(method, args, resubmitLock.keys());
            if (log.isDebugEnabled()) {
                log.debug("分布式锁key前缀的业务取值为:{}", JsonUtil.toStr(expressionValue));
            }
            key = key.concat(Arrays.stream(expressionValue).map(JsonUtil::toStr).collect(Collectors.joining(realize.getSeparator())));
        }
        if (log.isDebugEnabled()) {
            log.debug("分布式锁key为:[{}]", key);
        }
        return key;
    }
}

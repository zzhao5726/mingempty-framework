package top.mingempty.distributed.lock.annotation;


import io.swagger.v3.oas.annotations.media.Schema;
import top.mingempty.distributed.lock.enums.ReadWriteEnum;
import top.mingempty.distributed.lock.enums.RealizeEnum;
import top.mingempty.distributed.lock.enums.TierEnum;
import top.mingempty.domain.base.IRsp;
import top.mingempty.domain.enums.YesOrNoEnum;
import top.mingempty.domain.other.GlobalConstant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交注解
 * realize：锁实现机制 Database  redis zookeeper
 * tier：锁层级 业务层级（Business）  接口层级（Interface）
 * waitTime：接口获取锁等待时间（默认毫秒级别，并不需要等待）
 * leaseTime：防重复提交校验的时间间隔（默认毫秒）。
 * keyPrefix：当锁层级为业务级别时，需要指定的业务key前缀。如果为业务级别锁，则此key不能为空。
 * keys：可以作为防重参数的字段(通过Spring Expression表达式，可以做到多参数时，具体取哪个参数的值)。
 * resultClass：返回体的对象类
 * errorCodeField：异常提示编码所在字段
 * errorCode：异常提示编码
 * errorMsgField：异常提示信息所在字段
 * errorMsg：异常提示信息
 *
 * @author zzhao
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Schema(title = "防止重复提交注解")
public @interface ResubmitLock {

    /**
     * 当前注解是否启用
     */
    @Schema(title = "当前注解是否启用")
    String enabled() default "true";


    /**
     * 实例名称
     */
    @Schema(title = "实例名称")
    String instanceName() default GlobalConstant.DEFAULT_INSTANCE_NAME;


    /**
     * 锁实现机制
     */
    @Schema(title = "锁实现机制")
    RealizeEnum realize() default RealizeEnum.Redis;


    /**
     * 读写锁类型
     * <p>
     * 当改值不为空时，才设置读写锁
     */
    @Schema(title = "读写锁类型", description = "当改值不为空时，才设置读写锁")
    ReadWriteEnum readWrite() default ReadWriteEnum.None;

    /**
     * 锁层级
     */
    @Schema(title = "锁层级")
    TierEnum tier() default TierEnum.Interface;

    /**
     * 获取锁等待时间
     * <p>
     * 毫秒级别
     * <p>
     * 默认不需要等待
     */
    @Schema(title = "获取锁等待时间", description = "毫秒级别，默认不需要等待")
    int waitTime() default -1;

    /**
     * 防重复提交校验的时间间隔
     * <p>
     * 毫秒级别
     * <p>
     * {@code  realize}值为{@link RealizeEnum#Zookeeper}时,该参数不生效
     */
    @Schema(title = "防重复提交校验的时间间隔", description = "毫秒级别。{@code  realize}值为{@link RealizeEnum#zookeeper}时,该参数不生效")
    int leaseTime() default -1;


    /**
     * 锁key的前缀
     * <p>
     * {@code  tier}值为{@link TierEnum#Interface}时,该参数不生效
     */
    @Schema(title = "锁key的前缀", description = "{@code  tier}值为{@link TierEnum#Interface}时,该参数不生效")
    String keyPrefix() default "";

    /**
     * 参数Spring EL表达式
     * 例如 #orderDO. id;表达式的值作为防重复校验key的一部分
     */
    @Schema(title = "参数Spring EL表达式", description = "例如 #orderDO. id;表达式的值作为防重复校验key的一部分。")
    String[] keys();

    /**
     * 是否抛出异常
     */
    @Schema(title = "是否抛出异常", description = "抛出异常时，下列参数不生效")
    YesOrNoEnum isThrow() default YesOrNoEnum.YES;


    /**
     * 返回体的对象类
     * 默认使用{@link IRsp}。使用默认值时，下列参数不生效。
     * 使用其它的实体类时，该实体类需要一个无参构造。
     */
    @Schema(title = "返回体的对象类", description = "默认使用IRsp。" +
            "使用默认值时，下列参数不生效。" +
            "使用其它的实体类时，该实体类需要一个无参构造。")
    Class<?> resultClass() default IRsp.class;

    /**
     * 异常提示编码所在字段
     */
    @Schema(title = "异常提示编码所在字段")
    String errorCodeField() default "";

    /**
     * 异常提示编码
     */
    @Schema(title = "异常提示编码")
    String errorCode() default "";

    /**
     * 异常提示信息所在字段
     */
    @Schema(title = "异常提示信息所在字段")
    String errorMsgField() default "";

    /**
     * 异常提示信息
     */
    @Schema(title = "异常提示信息")
    String errorMsg() default "";

}
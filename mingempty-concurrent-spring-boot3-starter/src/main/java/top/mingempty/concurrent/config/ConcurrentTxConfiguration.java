package top.mingempty.concurrent.config;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;

/**
 * 基于事务并发配置类
 * <br>
 * 使用时，请继承
 * {@link top.mingempty.concurrent.thread.AbstractTxDelegatingCallable}
 * 或者{@link top.mingempty.concurrent.thread.AbstractTxDelegatingRunnable}，
 * 不可使用匿名内部类
 *
 * @author zzhao
 */
@Slf4j
@AutoConfigureAfter(value = {ConcurrentConfiguration.class})
@ConditionalOnClass(name = {"org.apache.ibatis.annotations.Mapper"})
@MapperScan(basePackages = {"top.mingempty.concurrent.tx.mapper"})
@ComponentScan(basePackages = {"top.mingempty.concurrent.tx"})
@ConditionalOnProperty(prefix = "me.concurrent", name = "enabled-tx", havingValue = "true", matchIfMissing = true)
public class ConcurrentTxConfiguration {

}

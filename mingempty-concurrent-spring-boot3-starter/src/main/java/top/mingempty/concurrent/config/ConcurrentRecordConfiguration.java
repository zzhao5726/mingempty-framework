package top.mingempty.concurrent.config;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import top.mingempty.concurrent.thread.AbstractRecordDelegatingCallable;
import top.mingempty.concurrent.thread.AbstractRecordDelegatingRunnable;

/**
 * 并发记录配置类
 * <br>
 * 使用时，请继承
 * {@link AbstractRecordDelegatingCallable}
 * 或者{@link AbstractRecordDelegatingRunnable}，
 * 不可使用匿名内部类
 *
 * @author zzhao
 */
@Slf4j
@AutoConfigureAfter(value = {ConcurrentConfiguration.class})
@ConditionalOnClass(name = {"org.apache.ibatis.annotations.Mapper"})
@MapperScan(basePackages = {"top.mingempty.concurrent.record.mapper"})
@ComponentScan(basePackages = {"top.mingempty.concurrent.record"})
@ConditionalOnProperty(prefix = "me.concurrent", name = "enabled-record", havingValue = "true", matchIfMissing = true)
public class ConcurrentRecordConfiguration {

}

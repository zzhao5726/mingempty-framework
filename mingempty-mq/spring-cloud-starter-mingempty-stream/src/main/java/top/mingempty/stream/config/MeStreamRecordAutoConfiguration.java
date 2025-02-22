package top.mingempty.stream.config;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import top.mingempty.stream.record.MeRecordInterceptor;
import top.mingempty.stream.record.RedordStreamBridge;
import top.mingempty.stream.record.service.MqRecordService;

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
@AutoConfigureBefore(value = {MeStreamAutoConfiguration.class})
@ConditionalOnClass(name = {"org.apache.ibatis.annotations.Mapper"})
@MapperScan(basePackages = {"top.mingempty.stream.record.mapper"})
@ComponentScan(basePackages = {"top.mingempty.stream.record"})
@ConditionalOnProperty(prefix = "me.stream", name = "enabled-record", havingValue = "true", matchIfMissing = true)
public class MeStreamRecordAutoConfiguration {

    @Bean
    public MeRecordInterceptor.RecordOutStartChannelInterceptor recordOutStartChannelInterceptor(MqRecordService mqRecordService) {
        return new MeRecordInterceptor.RecordOutStartChannelInterceptor(mqRecordService);
    }

    @Bean
    public MeRecordInterceptor.RecordOutEndChannelInterceptor recordOutEndChannelInterceptor(MqRecordService mqRecordService) {
        return new MeRecordInterceptor.RecordOutEndChannelInterceptor(mqRecordService);
    }

    @Bean
    public MeRecordInterceptor.RecordInStartChannelInterceptor recordInStartChannelInterceptor(MqRecordService mqRecordService) {
        return new MeRecordInterceptor.RecordInStartChannelInterceptor(mqRecordService);
    }

    @Bean
    public MeRecordInterceptor.RecordInEndChannelInterceptor recordInEndChannelInterceptor(MqRecordService mqRecordService) {
        return new MeRecordInterceptor.RecordInEndChannelInterceptor(mqRecordService);
    }

    @Bean
    public RedordStreamBridge redordStreamBridge(StreamBridge streamBridge) {
        return new RedordStreamBridge(streamBridge);
    }
}

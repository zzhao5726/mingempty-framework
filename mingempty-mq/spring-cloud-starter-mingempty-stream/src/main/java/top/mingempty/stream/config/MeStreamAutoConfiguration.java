package top.mingempty.stream.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.config.GlobalChannelInterceptor;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.stream.interceptor.MeChannelInterceptor;
import top.mingempty.stream.interceptor.base.MeAfterReceiveCompletion;
import top.mingempty.stream.interceptor.base.MeAfterSendCompletion;
import top.mingempty.stream.interceptor.base.MePostReceive;
import top.mingempty.stream.interceptor.base.MePostSend;
import top.mingempty.stream.interceptor.base.MePreReceive;
import top.mingempty.stream.interceptor.base.MePreSend;

import java.util.List;

@Slf4j
@ComponentScan(basePackages = {"top.mingempty.stream"})
public class MeStreamAutoConfiguration {

    @Bean
    @GlobalChannelInterceptor(patterns = {"*-out-*"})
    public MeChannelInterceptor meInChannelInterceptor(@Autowired List<MePreSend> preSends,
                                                       @Autowired List<MePostSend> postSends,
                                                       @Autowired List<MeAfterSendCompletion> afterSendCompletions,
                                                       @Autowired List<MePreReceive> preReceives,
                                                       @Autowired List<MePostReceive> postReceives,
                                                       @Autowired List<MeAfterReceiveCompletion> afterReceiveCompletions) {
        return new MeChannelInterceptor(ZeroOrOneEnum.ZERO, preSends, postSends, afterSendCompletions,
                preReceives, postReceives, afterReceiveCompletions);
    }


    @Bean
    @GlobalChannelInterceptor(patterns = {"*-in-*"})
    public MeChannelInterceptor meOutChannelInterceptor(@Autowired List<MePreSend> preSends,
                                                        @Autowired List<MePostSend> postSends,
                                                        @Autowired List<MeAfterSendCompletion> afterSendCompletions,
                                                        @Autowired List<MePreReceive> preReceives,
                                                        @Autowired List<MePostReceive> postReceives,
                                                        @Autowired List<MeAfterReceiveCompletion> afterReceiveCompletions) {
        return new MeChannelInterceptor(ZeroOrOneEnum.ONE, preSends, postSends, afterSendCompletions,
                preReceives, postReceives, afterReceiveCompletions);
    }
}

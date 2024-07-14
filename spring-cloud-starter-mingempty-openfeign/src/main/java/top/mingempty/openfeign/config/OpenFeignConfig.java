package top.mingempty.openfeign.config;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.domain.enums.YesOrNoEnum;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.trace.util.TraceIdGenerator;

/**
 * openfeign配置类
 *
 * @author zzhao
 * @date 2023/3/7 14:59
 */
@EnableFeignClients
public class OpenFeignConfig {


    /**
     * 请求拦截器
     * </p>
     * 用于传递链路数据和权限验证信息
     *
     * @return 请求拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            // 设置链路ID
            requestTemplate.header(TraceConstant.TRACE_ID, TraceIdGenerator.generateTraceId());
            requestTemplate.header(TraceConstant.SPAN_ID, TraceIdGenerator.generateSpanId());

            //内部访问，统一增加标识,标识当前请求来源于内部
            requestTemplate.header(GlobalConstant.REQUEST_FROM_INNER, YesOrNoEnum.YES.getItemCode());

            // TODO 预留传递权限信息


        };
    }


}
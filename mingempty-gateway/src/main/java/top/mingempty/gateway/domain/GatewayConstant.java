package top.mingempty.gateway.domain;

/**
 * 网关常量类
 *
 * @author zzhao
 */
public interface GatewayConstant {


    /**
     * Access-Control-Expose-Headers
     */
    String ALL = "*";

    /**
     * Access-Control-Max-Age
     */
    Long MAX_AGE = 18000L;

    /**
     * 请求开始时间常量
     */
    String START_TIME = "startTime";


    /**
     * nginx需要配置的IP常量
     */
    String X_REAL_IP = "X-Real-IP";
}

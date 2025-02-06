package top.mingempty.logging.loader;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.logging.log4j.Log4JUtil;
import top.mingempty.logging.logback.LogBackUtil;

/**
 * 配置加载工具
 *
 * @author zzhao
 */
@Slf4j
public class LoaderUtil {

    public static void reLoad(String configInfo) {
        if (StrUtil.isEmpty(configInfo)) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("获取到日志配置文件为：\n{}", configInfo);
        }
        try {
            Class.forName("ch.qos.logback.classic.LoggerContext");
            LogBackUtil.init(configInfo);
            return;
        } catch (ClassNotFoundException e) {
            log.warn("ch.qos.logback.classic.LoggerContext not found");
        }

        try {
            Class.forName("org.apache.logging.log4j.core.LoggerContext");
            Log4JUtil.init(configInfo);
        } catch (ClassNotFoundException e) {
            log.warn("org.apache.logging.log4j.core.LoggerContext not found");
        }


    }

}

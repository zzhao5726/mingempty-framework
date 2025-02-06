package top.mingempty.commons.definer;

import ch.qos.logback.core.PropertyDefinerBase;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.commons.util.IpUtils;

/***
 * 将本地IP拼接到日志文件名中，以区分不同实例，避免存储到同一位置时的覆盖冲突问题
 * @author zzhao
 */
@Slf4j
public class IPLogDefiner extends PropertyDefinerBase {

    @Override
    public String getPropertyValue() {
        return IpUtils.getServerIp();
    }

}
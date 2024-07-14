package top.mingempty.commons.definer;

import ch.qos.logback.core.PropertyDefinerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/***
 * 将本地IP拼接到日志文件名中，以区分不同实例，避免存储到同一位置时的覆盖冲突问题
 * @author zzhao
 */

public class IPLogDefiner extends PropertyDefinerBase {

    private static final Logger LOG = LoggerFactory.getLogger(IPLogDefiner.class);

    private String getUniqName() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "un-ip";
        }

    }

    @Override

    public String getPropertyValue() {

        return getUniqName();

    }

}
package top.mingempty.commons.util;

/**
 * 进程工具类
 *
 * @author zzhao
 */
public class ProcessUtil {

    /**
     * 获取进程ID
     *
     * @return 进程ID
     */
    public static int processId() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        if (processName.contains("@")) {
            return Integer.parseInt(processName.split("@")[0]);
        } else {
            return processName.hashCode();
        }
    }
}

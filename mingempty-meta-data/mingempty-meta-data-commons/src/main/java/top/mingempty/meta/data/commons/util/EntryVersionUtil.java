package top.mingempty.meta.data.commons.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 条目版本工具类
 *
 * @author zzhao
 */
public class EntryVersionUtil {


    /**
     * 条目版本线程变量
     */
    private final static ThreadLocal<Map<String, Long>> ENTRY_VERSION_THREAD_LOCAL = ThreadLocal.withInitial(() -> new ConcurrentHashMap<>(2));


    public static Long gainVersion(String entryCode) {
        return ENTRY_VERSION_THREAD_LOCAL.get().get(entryCode);
    }

    public static Map<String, Long> gainVersion() {
        return ENTRY_VERSION_THREAD_LOCAL.get();
    }

    public static void settingVersion(String entryCode, Long entryVersion) {
        ENTRY_VERSION_THREAD_LOCAL.get().put(entryCode, entryVersion);
    }

    public static void settingVersion(Map<String, Long> entryVersionMap) {
        if (entryVersionMap == null) {
            return;
        }
        ENTRY_VERSION_THREAD_LOCAL.get().putAll(entryVersionMap);
    }


    public static void removeVersion(String entryCode) {
        ENTRY_VERSION_THREAD_LOCAL.get().remove(entryCode);
    }


    public static void remove() {
        if (!ENTRY_VERSION_THREAD_LOCAL.get().isEmpty()) {
            ENTRY_VERSION_THREAD_LOCAL.get().clear();
        }
        ENTRY_VERSION_THREAD_LOCAL.remove();
    }


}

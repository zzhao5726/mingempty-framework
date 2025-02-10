package top.mingempty.mybatis.tool;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表名称替换工具类
 */
public class ReplacedTableNameTool {

    private final static ThreadLocal<Map<String, String>> REPLACED_TABLE_NAME_THREAD_LOCAL = new ThreadLocal<>();


    public static void addReplacedTableName(String tableName, String replacedTableName) {
        Map<String, String> map = init();
        map.put(tableName, replacedTableName);
    }

    public static void addReplacedTableName(Map<String, String> replacedTableNameMap) {
        if (ObjectUtil.isEmpty(replacedTableNameMap)) {
            return;
        }
        Map<String, String> map = init();
        map.putAll(replacedTableNameMap);
    }

    private static Map<String, String> init() {
        Map<String, String> map = REPLACED_TABLE_NAME_THREAD_LOCAL.get();
        if (ObjUtil.isEmpty(map)) {
            synchronized (Thread.currentThread()) {
                if (ObjUtil.isEmpty(map)) {
                    map = new ConcurrentHashMap<>();
                    REPLACED_TABLE_NAME_THREAD_LOCAL.set(map);
                }
            }
        }
        return map;
    }

    public static void removeReplacedTableName() {
        Map<String, String> map = REPLACED_TABLE_NAME_THREAD_LOCAL.get();
        if (ObjUtil.isNotEmpty(map)) {
            map.clear();
        }
        REPLACED_TABLE_NAME_THREAD_LOCAL.remove();
    }

    public static Map<String, String> gainReplacedTableName() {
        Map<String, String> map = REPLACED_TABLE_NAME_THREAD_LOCAL.get();
        if (ObjUtil.isEmpty(map)) {
            return Map.of();
        }
        return Map.copyOf(REPLACED_TABLE_NAME_THREAD_LOCAL.get());
    }
}

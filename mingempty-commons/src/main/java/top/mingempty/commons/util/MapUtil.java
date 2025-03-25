package top.mingempty.commons.util;

import java.util.Map;
import java.util.Objects;

/**
 * Map工具类
 */
public class MapUtil {

    /**
     * 判断两个Map是否相等
     *
     * @param map1
     * @param map2
     * @return
     */
    public static boolean equals(Map<?, ?> map1, Map<?, ?> map2) {
        if (map1 == null && map2 == null) {
            return true;
        }

        if (map1 == null
                || map2 == null) {
            return false;
        }
        if (Objects.equals(map1, map2)) {
            return true;
        }

        if (map1.size() != map2.size()) {
            return false;
        }

        return map1.entrySet()
                .parallelStream()
                .anyMatch(entry -> {
                    Object key = entry.getKey();
                    Object value1 = entry.getValue();
                    if (!map2.containsKey(key)) {
                        return false;
                    }
                    Object value2 = map2.get(key);
                    return Objects.equals(value1, value2);
                });
    }

}

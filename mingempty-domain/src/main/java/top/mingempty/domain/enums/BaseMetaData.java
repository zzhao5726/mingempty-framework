package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public interface BaseMetaData<E extends Enum<E> & BaseMetaData<E, K>, K> {

    /**
     * 获取字典项编码
     *
     * @return
     */
    K getItemCode();

    /**
     * 获取字典项名称
     *
     * @return
     */
    String getItemName();


    /**
     * 辅助类来管理 ENUM_MAP
     */
    final class EnumHelper {

        public final static EnumHelper INSTANCE = new EnumHelper();

        private final Map<Class<? extends BaseMetaData<?, ?>>, Map<?, ? extends BaseMetaData<?, ?>>> ENUM_MAP = new ConcurrentHashMap<>();

        private EnumHelper() {
        }

        /**
         * 根据枚举类获取枚举集合
         *
         * @param clazz
         * @return
         */
        @Schema(title = "根据枚举类获取枚举集合")
        public <E extends Enum<E> & BaseMetaData<E, K>, K> Map<K, E> getMap(Class<E> clazz) {
            if (!Enum.class.isAssignableFrom(clazz)) {
                //只要不是枚举 就返回
                return null;
            }
            return (Map<K, E>) ENUM_MAP.computeIfAbsent(clazz, key -> {
                E[] enumConstants = clazz.getEnumConstants();
                if (enumConstants == null) {
                    return null;
                }
                Map<K, E> map = new ConcurrentHashMap<>();
                for (E enumConstant : enumConstants) {
                    map.put(enumConstant.getItemCode(), enumConstant);
                }
                return map;
            });
        }

        /**
         * 根据字典项编码获取枚举
         *
         * @param clazz
         * @param itemCode
         * @return
         */
        @Schema(title = "根据字典项编码获取枚举")
        public <E extends Enum<E> & BaseMetaData<E, K>, K> E findOne(Class<E> clazz, K itemCode) {
            return Optional.ofNullable(getMap(clazz))
                    .map(map -> map.get(itemCode))
                    .orElse(null);
        }

        /**
         * 根据字典项编码获取枚举
         *
         * @param clazz
         * @param itemCode
         * @return
         */
        @Schema(title = "根据字典项编码获取枚举")
        public <E extends Enum<E> & BaseMetaData<E, K>, K> Optional<E> findOptional(Class<E> clazz, K itemCode) {
            return Optional.ofNullable(getMap(clazz)).map(map -> map.get(itemCode));
        }

    }

}

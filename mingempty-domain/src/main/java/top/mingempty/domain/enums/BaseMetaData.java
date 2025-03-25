package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public interface BaseMetaData<E extends Enum<E>, K> {

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

        private final Map<Class<?>, Map<?, ?>> ENUM_MAP = new ConcurrentHashMap<>();

        private EnumHelper() {
        }

        /**
         * 根据枚举类获取枚举集合
         *
         * @param clazz
         * @param <E>
         * @param <K>
         * @param <B>
         * @return
         */
        @Schema(title = "根据枚举类获取枚举集合")
        public <E extends BaseMetaData<B, K>, K, B extends Enum<B>> Map<K, B> getMap(Class<E> clazz) {
            if (!Enum.class.isAssignableFrom(clazz)) {
                //只要不是枚举 就返回
                return null;
            }
            return (Map<K, B>) ENUM_MAP.computeIfAbsent(clazz, key -> {
                E[] enumConstants = clazz.getEnumConstants();
                if (enumConstants == null) {
                    return null;
                }
                Map<K, B> map = new ConcurrentHashMap<>();
                for (E enumConstant : enumConstants) {
                    map.put(enumConstant.getItemCode(), (B) enumConstant);
                }
                return map;
            });
        }

        /**
         * 根据字典项编码获取枚举
         *
         * @param clazz
         * @param itemCode
         * @param <E>
         * @param <K>
         * @param <B>
         * @return
         */
        @Schema(title = "根据字典项编码获取枚举")
        public <E extends BaseMetaData<B, K>, K, B extends Enum<B>> B findOne(Class<E> clazz, K itemCode) {
            return Optional.ofNullable(getMap(clazz))
                    .map(map -> map.get(itemCode))
                    .orElse(null);
        }

        /**
         * 根据字典项编码获取枚举
         *
         * @param clazz
         * @param itemCode
         * @param <E>
         * @param <K>
         * @param <B>
         * @return
         */
        @Schema(title = "根据字典项编码获取枚举")
        public <E extends BaseMetaData<B, K>, K, B extends Enum<B>> Optional<B> findOptional(Class<E> clazz, K itemCode) {
            return Optional.ofNullable(getMap(clazz)).map(map -> map.get(itemCode));
        }

    }

}

package top.mingempty.commons.util;

import cn.hutool.core.util.ObjUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 *
 * @author zzhao
 */
@Slf4j
public class JsonUtil {

    /**
     * 对外暴露用于redis中序列化使用
     */
    public volatile static ObjectMapper DEFAULT_OBJECT_MAPPER;


    static {
        DEFAULT_OBJECT_MAPPER = JacksonUtil.build();
    }

    /**
     * JSON反序列化
     */
    public static <V> V toObj(Object object, Class<V> vClass) {
        return toObj(DEFAULT_OBJECT_MAPPER, object, vClass);
    }

    /**
     * JSON反序列化
     */
    public static <V> V toObj(ObjectMapper objectMapper, Object object, Class<V> vClass) {
        if (objectMapper == null) {
            throw new NullPointerException("objectMapper is null");
        }

        if (null == object) {
            return null;
        }

        if (object.getClass().equals(vClass)) {
            return (V) object;
        }

        return toObj(objectMapper, object, new TypeReference<V>() {
            @Override
            public Type getType() {
                return vClass;
            }
        });
    }

    /**
     * JSON反序列化
     */
    public static <V> List<V> toList(Object object, Class<V> vClass) {
        return toList(DEFAULT_OBJECT_MAPPER, object, vClass);
    }

    /**
     * JSON反序列化
     */
    public static <V> List<V> toList(ObjectMapper objectMapper, Object object, Class<V> vClass) {
        TypeReference<List<V>> typeReference = new JacksonUtil.ListTypeReference<>(vClass);
        return toList(objectMapper, object, typeReference);
    }

    /**
     * JSON反序列化
     */
    public static Map<String, Object> toMap(Object object) {
        return toMap(DEFAULT_OBJECT_MAPPER, object);
    }

    /**
     * JSON反序列化
     */
    public static Map<String, Object> toMap(ObjectMapper objectMapper, Object object) {
        TypeReference<Map<String, Object>> typeReference = new JacksonUtil.MapTypeReference<>();
        return toObj(objectMapper, object, typeReference);
    }


    /**
     * JSON反序列化
     */
    public static <V> List<V> toList(Object object, TypeReference<List<V>> vTypeReference) {
        return toList(DEFAULT_OBJECT_MAPPER, object, vTypeReference);
    }

    /**
     * JSON反序列化
     */
    public static <V> List<V> toList(ObjectMapper objectMapper, Object object, TypeReference<List<V>> vTypeReference) {
        return toObj(objectMapper, object, vTypeReference);
    }


    /**
     * JSON反序列化
     */
    public static <V> V toObj(Object object, TypeReference<V> vTypeReference) {
        return toObj(DEFAULT_OBJECT_MAPPER, object, vTypeReference);
    }

    /**
     * JSON反序列化
     */
    public static <V> V toObj(ObjectMapper objectMapper, Object object, TypeReference<V> vTypeReference) {
        if (objectMapper == null) {
            throw new NullPointerException("objectMapper is null");
        }

        if (null == object) {
            return null;
        }

        try {
            switch (object) {
                case JsonParser jsonParser -> {
                    return objectMapper.readValue(jsonParser, vTypeReference);
                }
                case InputStream inputStream -> {
                    return objectMapper.readValue(inputStream, vTypeReference);
                }
                case Reader reader -> {
                    return objectMapper.readValue(reader, vTypeReference);
                }
                case String str -> {
                    return objectMapper.readValue(str, vTypeReference);
                }
                case File file -> {
                    return objectMapper.readValue(file, vTypeReference);
                }
                case URL url -> {
                    return objectMapper.readValue(url, vTypeReference);
                }
                case byte[] bytes -> {
                    return objectMapper.readValue(bytes, vTypeReference);
                }
                default -> {
                    return objectMapper.readValue(toStr(objectMapper, object), vTypeReference);
                }
            }
        } catch (IOException ioException) {
            log.error("json to obj error, object: {}", object, ioException);
            return null;
        }
    }


    /**
     * 序列化为JSON
     */
    public static <V> String toStr(V v) {
        return toStr(DEFAULT_OBJECT_MAPPER, v);
    }

    /**
     * 序列化为JSON
     */
    public static <V> String toStr(ObjectMapper objectMapper, V v) {
        try {
            if (ObjUtil.isEmpty(v)) {
                return null;
            }
            if (String.class.equals(v.getClass())) {
                return (String) v;
            }
            return objectMapper.writeValueAsString(v);
        } catch (JsonProcessingException e) {
            log.error("jackson to error, obj: {}", v, e);
            return null;
        }
    }

    /**
     * 序列化为JSON并写入文件
     */
    public static <V> void toFile(String path, V v) {
        toFile(DEFAULT_OBJECT_MAPPER, path, v);
    }

    /**
     * 序列化为JSON并写入文件
     */
    public static <V> void toFile(String path, V v, boolean append) {
        toFile(DEFAULT_OBJECT_MAPPER, path, v, append);
    }

    /**
     * 序列化为JSON并写入文件
     */
    public static <V> void toFile(ObjectMapper objectMapper, String path, V v) {
        toFile(DEFAULT_OBJECT_MAPPER, path, v, false);
    }


    /**
     * 序列化为JSON并写入文件
     *
     * @param path   文件路径
     * @param v      要序列化的对象
     * @param append 如果 true，则数据将写入文件的末尾而不是开头
     */
    public static <V> void toFile(ObjectMapper objectMapper, String path, V v, boolean append) {
        try (Writer writer = new FileWriter(path, append)) {
            objectMapper.writer().writeValues(writer).write(v);
            writer.flush();
        } catch (Exception e) {
            log.error("jackson to file error, path: {}, obj: {}", path, v, e);
        }
    }

    /**
     * 将对象转换为JsonNode
     *
     * @param object 要转换的对象
     * @return JsonNode
     */
    public static JsonNode toNode(Object object) {
        return toNode(DEFAULT_OBJECT_MAPPER, object);
    }

    /**
     * 将对象转换为JsonNode
     *
     * @param objectMapper 对象映射器
     * @param object       要转换的对象
     * @return JsonNode
     */
    public static JsonNode toNode(ObjectMapper objectMapper, Object object) {
        if (objectMapper == null) {
            throw new NullPointerException("objectMapper is null");
        }

        if (null == object) {
            return null;
        }
        try {
            switch (object) {
                case JsonParser jsonParser -> {
                    return objectMapper.readTree(jsonParser);
                }
                case InputStream inputStream -> {
                    return objectMapper.readTree(inputStream);
                }
                case Reader reader -> {
                    return objectMapper.readTree(reader);
                }
                case String str -> {
                    return objectMapper.readTree(str);
                }
                case File file -> {
                    return objectMapper.readTree(file);
                }
                case URL url -> {
                    return objectMapper.readTree(url);
                }
                case byte[] bytes -> {
                    return objectMapper.readTree(bytes);
                }
                default -> {
                    return objectMapper.readTree(toStr(objectMapper, object));
                }
            }
        } catch (IOException ioException) {
            log.error("json to node error, object: {}", object, ioException);
            return new ObjectNode(objectMapper.getNodeFactory());
        }
    }

    /**
     * 获取某一个节点
     *
     * @param object
     * @param key
     * @return
     */
    public static JsonNode toNodeByKey(Object object, String key) {
        return toNodeByKey(DEFAULT_OBJECT_MAPPER, object, key);
    }

    /**
     * 获取某一个节点
     *
     * @param objectMapper
     * @param object
     * @param key
     * @return
     */
    public static JsonNode toNodeByKey(ObjectMapper objectMapper, Object object, String key) {
        if (ObjUtil.isEmpty(object)) {
            return null;
        }
        JsonNode jsonNode = toNode(objectMapper, object);
        if (ObjUtil.isEmpty(jsonNode)) {
            return null;
        }
        return jsonNode.get(key);
    }


    /**
     * 从对象中获取某个字段
     *
     * @return String
     */
    public static String getString(Object object, String key) {
        return getString(DEFAULT_OBJECT_MAPPER, object, key);
    }

    /**
     * 从对象中获取某个字段
     *
     * @return String
     */
    public static String getString(ObjectMapper objectMapper, Object object, String key) {
        JsonNode jsonNode = toNodeByKey(objectMapper, object, key);
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.toString();
    }

    /**
     * 从对象中获取某个字段
     *
     * @return int
     */
    public static Integer getInt(Object object, String key) {
        return getInt(DEFAULT_OBJECT_MAPPER, object, key);
    }

    /**
     * 从对象中获取某个字段
     *
     * @return int
     */
    public static Integer getInt(ObjectMapper objectMapper, Object object, String key) {
        JsonNode jsonNode = toNodeByKey(objectMapper, object, key);
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.intValue();
    }

    /**
     * 从对象中获取某个字段
     *
     * @return long
     */
    public static Long getLong(Object object, String key) {
        return getLong(DEFAULT_OBJECT_MAPPER, object, key);
    }

    /**
     * 从对象中获取某个字段
     *
     * @return long
     */
    public static Long getLong(ObjectMapper objectMapper, Object object, String key) {
        JsonNode jsonNode = toNodeByKey(objectMapper, object, key);
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.longValue();
    }

    /**
     * 从对象中获取某个字段
     *
     * @return double
     */
    public static Double getDouble(Object object, String key) {
        return getDouble(DEFAULT_OBJECT_MAPPER, object, key);
    }

    /**
     * 从对象中获取某个字段
     *
     * @return double
     */
    public static Double getDouble(ObjectMapper objectMapper, Object object, String key) {
        JsonNode jsonNode = toNodeByKey(objectMapper, object, key);
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.doubleValue();
    }

    /**
     * 从对象中获取某个字段
     *
     * @return double
     */
    public static BigInteger getBigInteger(Object object, String key) {
        return getBigInteger(DEFAULT_OBJECT_MAPPER, object, key);
    }

    /**
     * 从对象中获取某个字段
     *
     * @return double
     */
    public static BigInteger getBigInteger(ObjectMapper objectMapper, Object object, String key) {
        JsonNode jsonNode = toNodeByKey(objectMapper, object, key);
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.bigIntegerValue();
    }

    /**
     * 从对象中获取某个字段
     *
     * @return double
     */
    public static BigDecimal getBigDecimal(Object object, String key) {
        return getBigDecimal(DEFAULT_OBJECT_MAPPER, object, key);
    }

    /**
     * 从对象中获取某个字段
     *
     * @return double
     */
    public static BigDecimal getBigDecimal(ObjectMapper objectMapper, Object object, String key) {
        JsonNode jsonNode = toNodeByKey(objectMapper, object, key);
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.decimalValue();
    }

    /**
     * 从对象中获取某个字段
     *
     * @return boolean
     */
    public static Boolean getBoolean(Object object, String key) {
        return getBoolean(DEFAULT_OBJECT_MAPPER, object, key);
    }

    /**
     * 从对象中获取某个字段
     *
     * @return boolean
     */
    public static Boolean getBoolean(ObjectMapper objectMapper, Object object, String key) {
        JsonNode jsonNode = toNodeByKey(objectMapper, object, key);
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.booleanValue();
    }

    /**
     * 从对象中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static byte[] getByte(Object object, String key) {
        return getByte(DEFAULT_OBJECT_MAPPER, object, key);
    }

    /**
     * 从对象中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static byte[] getByte(ObjectMapper objectMapper, Object object, String key) {
        try {
            JsonNode jsonNode = toNodeByKey(objectMapper, object, key);
            if (jsonNode == null) {
                return null;
            }
            return jsonNode.binaryValue();
        } catch (IOException e) {
            log.error("jackson get byte error, object: {}, pullWaybillKey: {}", object, key, e);
            return null;
        }
    }

    /**
     * 从对象中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static <T> List<T> getList(Object object, String key, Class<T> tClass) {
        return getList(DEFAULT_OBJECT_MAPPER, object, key, tClass);
    }

    /**
     * 从对象中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static <T> List<T> getList(ObjectMapper objectMapper, Object object, String key, Class<T> tClass) {
        JsonNode jsonNode = toNodeByKey(objectMapper, object, key);
        if (jsonNode == null) {
            return null;
        }
        String string = getString(objectMapper, object, key);
        return toList(string, tClass);
    }

    /**
     * 向json中添加属性
     *
     * @return json
     */
    public static <T> String add(String jsonStr, String key, T value) {
        return add(DEFAULT_OBJECT_MAPPER, jsonStr, key, value);
    }

    /**
     * 向json中添加属性
     *
     * @return json
     */
    public static <T> String add(ObjectMapper objectMapper, String jsonStr, String key, T value) {
        if (ObjUtil.isEmpty(jsonStr)) {
            return jsonStr;
        }
        JsonNode jsonNode = toNode(objectMapper, jsonStr);
        if (ObjUtil.isEmpty(jsonNode)) {
            return jsonStr;
        }
        add(objectMapper, jsonNode, key, value);
        return jsonNode.toString();
    }

    /**
     * 向json中添加属性
     */
    private static <T> void add(ObjectMapper objectMapper, JsonNode jsonNode, String key, T value) {
        switch (value) {
            case String s -> ((ObjectNode) jsonNode).put(key, s);
            case Short i -> ((ObjectNode) jsonNode).put(key, i);
            case Integer i -> ((ObjectNode) jsonNode).put(key, i);
            case Long l -> ((ObjectNode) jsonNode).put(key, l);
            case Float v -> ((ObjectNode) jsonNode).put(key, v);
            case Double v -> ((ObjectNode) jsonNode).put(key, v);
            case BigDecimal bigDecimal -> ((ObjectNode) jsonNode).put(key, bigDecimal);
            case BigInteger bigInteger -> ((ObjectNode) jsonNode).put(key, bigInteger);
            case Boolean b -> ((ObjectNode) jsonNode).put(key, b);
            case byte[] bytes -> ((ObjectNode) jsonNode).put(key, bytes);
            case null, default -> ((ObjectNode) jsonNode).put(key, toStr(objectMapper, value));
        }
    }

    /**
     * 除去json中的某个属性
     *
     * @return json
     */
    public static String remove(String jsonStr, String key) {
        return remove(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 除去json中的某个属性
     *
     * @return json
     */
    public static String remove(ObjectMapper objectMapper, String jsonStr, String key) {
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            ((ObjectNode) node).remove(key);
            return node.toPrettyString();
        } catch (IOException e) {
            log.error("jackson remove error, json: {}, pullWaybillKey: {}", jsonStr, key, e);
            return jsonStr;
        }
    }

    /**
     * 修改json中的属性
     */
    public static <T> String update(String jsonStr, String key, T value) {
        return update(DEFAULT_OBJECT_MAPPER, jsonStr, key, value);
    }

    /**
     * 修改json中的属性
     */
    public static <T> String update(ObjectMapper objectMapper, String jsonStr, String key, T value) {
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            ((ObjectNode) node).remove(key);
            add(objectMapper, node, key, value);
            return node.toPrettyString();
        } catch (IOException e) {
            log.error("jackson update error, json: {}, pullWaybillKey: {}, value: {}", jsonStr, key, value, e);
            return jsonStr;
        }
    }

    /**
     * 格式化Json(美化)
     *
     * @return json
     */
    public static String formatPretty(String jsonStr) {
        return formatPretty(DEFAULT_OBJECT_MAPPER, jsonStr);
    }

    /**
     * 格式化Json(美化)
     *
     * @return json
     */
    public static String formatPretty(ObjectMapper objectMapper, String jsonStr) {
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (IOException e) {
            log.error("jackson format json error, json: {}", jsonStr, e);
            return jsonStr;
        }
    }

    /**
     * 判断字符串是否是json
     *
     * @return json
     */
    public static boolean isJson(String jsonStr) {
        return isJson(DEFAULT_OBJECT_MAPPER, jsonStr);
    }

    /**
     * 判断字符串是否是json
     *
     * @return json
     */
    public static boolean isJson(ObjectMapper objectMapper, String jsonStr) {
        if (jsonStr == null) {
            return false;
        }

        String trimmedJsonStr = jsonStr.trim();
        if (!isJsonStartEndValid(trimmedJsonStr)) {
            return false;
        }
        try {
            objectMapper.readTree(trimmedJsonStr);
            return true;
        } catch (JsonProcessingException e) {
            // 这里捕获更具体的异常类型，比如JsonParseException和JsonMappingException
            // 通过日志级别和异常类型提供更详细的错误信息
            logJsonProcessingException(e);
            return false;
        }

    }

    private static boolean isJsonStartEndValid(String str) {
        // 优化判断逻辑的有效性，使其更清晰
        return str.startsWith("{") && str.endsWith("}")
                || str.startsWith("[") && str.endsWith("]");
    }

    private static void logJsonProcessingException(JsonProcessingException e) {
        // 根据异常类型进行更细致的日志记录
        if (e instanceof UnrecognizedPropertyException) {
            log.warn("Unrecognized property in JSON.", e);
        } else {
            if (log.isDebugEnabled()) {
                log.error("Jackson JSON parsing error.", e);
            } else {
                log.warn("Jackson JSON parsing error.", e);
            }
        }
    }
}


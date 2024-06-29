package top.mingempty.commons.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.domain.other.DatePattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JacksonUtil {

    /**
     * 对外暴露用于redis中序列化使用
     */
    public volatile static ObjectMapper DEFAULT_OBJECT_MAPPER;

    /**
     * 序列化级别，默认只序列化属性值发生过改变的字段
     * NON_NULL：序列化非空的字段
     * NON_EMPTY：序列化非空字符串和非空的字段
     * NON_DEFAULT：序列化属性值发生过改变的字段
     */
    private volatile static JsonInclude.Include DEFAULT_PROPERTY_INCLUSION = JsonInclude.Include.NON_NULL;

    /**
     * 是否缩进JSON格式
     */
    private volatile static boolean IS_ENABLE_INDENT_OUTPUT = false;

    /**
     * 是否携带类路径
     */
    private volatile static boolean ACTIVATE_DEFAULT_TYPING = false;

    static {
        DEFAULT_OBJECT_MAPPER = build();
    }

    public static ObjectMapper build() {
        return build(IS_ENABLE_INDENT_OUTPUT, DEFAULT_PROPERTY_INCLUSION);
    }

    public static ObjectMapper build(boolean isEnableIndentOutput, JsonInclude.Include include) {
        return build(isEnableIndentOutput, include, ACTIVATE_DEFAULT_TYPING);
    }

    private static ObjectMapper build(boolean isEnableIndentOutput, JsonInclude.Include include, boolean activateDefaultTyping) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));


        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));


        JsonMapper.Builder builder = JsonMapper.builder()
                .configure(SerializationFeature.INDENT_OUTPUT, isEnableIndentOutput)
                .configure(MapperFeature.USE_ANNOTATIONS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                //识别Java8时间
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(javaTimeModule)
                .serializationInclusion(include)
                //序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
                .disable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                //允许将JSON空字符串强制转换为null对象值
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                //允许单个数值当做数组处理
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                //禁止重复键, 抛出异常
                .enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)
                //禁止使用int代表Enum的order()來反序列化Enum, 抛出异常
                .enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)
                //有属性不能映射的时候不报错
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                //使用null表示集合类型字段是时不抛异常
                .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                //对象为空时不抛异常
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                //允许在JSON中使用c/vClass++风格注释
                .enable(JsonParser.Feature.ALLOW_COMMENTS)
                //强制转义非ascii字符
                .disable(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature())
                //允许未知字段
                .enable(JsonGenerator.Feature.IGNORE_UNKNOWN)
                //在JSON中允许未引用的字段名
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                //识别单引号
                .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
                //时间格式
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .defaultDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN))
                //识别Guava包的类
                .addModule(new GuavaModule())
                //允许更改基础{@link VisibilityChecker}配置的便捷方法，以更改自动检测的属性类型的详细信息。
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        if (activateDefaultTyping) {
            // 解决反序列化时变成LinkedHashMap问题
            builder.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                    ObjectMapper.DefaultTyping.NON_FINAL,
                    JsonTypeInfo.As.PROPERTY);
        }
        return builder.build();
    }

    /**
     * 设置序列化级别
     * NON_NULL：序列化非空的字段
     * NON_EMPTY：序列化非空字符串和非空的字段
     * NON_DEFAULT：序列化属性值发生过改变的字段
     */
    public static void setSerializationInclusion(JsonInclude.Include inclusion) {
        DEFAULT_PROPERTY_INCLUSION = inclusion;
        DEFAULT_OBJECT_MAPPER = build(IS_ENABLE_INDENT_OUTPUT, DEFAULT_PROPERTY_INCLUSION, ACTIVATE_DEFAULT_TYPING);

    }

    /**
     * 设置是否开启JSON格式美化
     *
     * @param isEnableIndentOutput 为true表示开启, 默认false
     */
    public static void setIndentOutput(boolean isEnableIndentOutput) {
        IS_ENABLE_INDENT_OUTPUT = isEnableIndentOutput;
        DEFAULT_OBJECT_MAPPER = build(IS_ENABLE_INDENT_OUTPUT, DEFAULT_PROPERTY_INCLUSION, ACTIVATE_DEFAULT_TYPING);
    }

    /**
     * 是否携带类路径
     *
     * @param activateDefaultTyping 为true表示开启, 默认false
     */
    public static void setDefaultTyping(boolean activateDefaultTyping) {
        ACTIVATE_DEFAULT_TYPING = activateDefaultTyping;
        DEFAULT_OBJECT_MAPPER = build(IS_ENABLE_INDENT_OUTPUT, DEFAULT_PROPERTY_INCLUSION, ACTIVATE_DEFAULT_TYPING);
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
        try {
            switch (object) {
                case JsonParser jsonParser -> {
                    return objectMapper.readValue(jsonParser, vClass);
                }
                case InputStream inputStream -> {
                    return objectMapper.readValue(inputStream, vClass);
                }
                case Reader reader -> {
                    return objectMapper.readValue(reader, vClass);
                }
                case String str -> {
                    return objectMapper.readValue(str, vClass);
                }
                case File file -> {
                    return objectMapper.readValue(file, vClass);
                }
                case URL url -> {
                    return objectMapper.readValue(url, vClass);
                }
                case byte[] bytes -> {
                    return objectMapper.readValue(bytes, vClass);
                }
                default -> {
                    return objectMapper.readValue(toStr(object), vClass);
                }
            }
        } catch (IOException ioException) {
            log.error("jsonStr to node error, object: {}", object, ioException);
            return null;
        }
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
                    return objectMapper.readValue(toStr(object), vTypeReference);
                }
            }
        } catch (IOException ioException) {
            log.error("jsonStr to node error, object: {}", object, ioException);
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
                    return objectMapper.readTree(toStr(object));
                }
            }
        } catch (IOException ioException) {
            log.error("jsonStr to node error, object: {}", object, ioException);
            return new ObjectNode(objectMapper.getNodeFactory());
        }
    }


    /**
     * 从json串中获取某个字段
     *
     * @return String
     */
    public static String getString(String jsonStr, String key) {
        return getString(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 从json串中获取某个字段
     *
     * @return String
     */
    public static String getString(ObjectMapper objectMapper, String jsonStr, String key) {
        if (StrUtil.isEmpty(jsonStr)) {
            return null;
        }
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            if (null != node) {
                return node.get(key).toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("jackson get string error, json: {}, pullWaybillKey: {}", jsonStr, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return int
     */
    public static Integer getInt(String jsonStr, String key) {
        return getInt(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 从json串中获取某个字段
     *
     * @return int
     */
    public static Integer getInt(ObjectMapper objectMapper, String jsonStr, String key) {
        if (StrUtil.isEmpty(jsonStr)) {
            return null;
        }
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            if (null != node) {
                return node.get(key).intValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("jackson get int error, json: {}, pullWaybillKey: {}", jsonStr, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return long
     */
    public static Long getLong(String jsonStr, String key) {
        return getLong(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 从json串中获取某个字段
     *
     * @return long
     */
    public static Long getLong(ObjectMapper objectMapper, String jsonStr, String key) {
        if (StrUtil.isEmpty(jsonStr)) {
            return null;
        }
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            if (null != node) {
                return node.get(key).longValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("jackson get long error, json: {}, pullWaybillKey: {}", jsonStr, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return double
     */
    public static Double getDouble(String jsonStr, String key) {
        return getDouble(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 从json串中获取某个字段
     *
     * @return double
     */
    public static Double getDouble(ObjectMapper objectMapper, String jsonStr, String key) {
        if (StrUtil.isEmpty(jsonStr)) {
            return null;
        }
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            if (null != node) {
                return node.get(key).doubleValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("jackson get double error, json: {}, pullWaybillKey: {}", jsonStr, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return double
     */
    public static BigInteger getBigInteger(String jsonStr, String key) {
        return getBigInteger(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 从json串中获取某个字段
     *
     * @return double
     */
    public static BigInteger getBigInteger(ObjectMapper objectMapper, String jsonStr, String key) {
        if (StrUtil.isEmpty(jsonStr)) {
            return new BigInteger(String.valueOf(0.00));
        }
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            if (null != node) {
                return node.get(key).bigIntegerValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("jackson get biginteger error, json: {}, pullWaybillKey: {}", jsonStr, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return double
     */
    public static BigDecimal getBigDecimal(String jsonStr, String key) {
        return getBigDecimal(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 从json串中获取某个字段
     *
     * @return double
     */
    public static BigDecimal getBigDecimal(ObjectMapper objectMapper, String jsonStr, String key) {
        if (StrUtil.isEmpty(jsonStr)) {
            return null;
        }
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            if (null != node) {
                return node.get(key).decimalValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("jackson get bigdecimal error, json: {}, pullWaybillKey: {}", jsonStr, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return boolean
     */
    public static Boolean getBoolean(String jsonStr, String key) {
        return getBoolean(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 从json串中获取某个字段
     *
     * @return boolean
     */
    public static Boolean getBoolean(ObjectMapper objectMapper, String jsonStr, String key) {
        if (StrUtil.isEmpty(jsonStr)) {
            return false;
        }
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            if (null != node) {
                return node.get(key).booleanValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("jackson get boolean error, json: {}, pullWaybillKey: {}", jsonStr, key, e);
            return false;
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static byte[] getByte(String jsonStr, String key) {
        return getByte(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 从json串中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static byte[] getByte(ObjectMapper objectMapper, String jsonStr, String key) {
        if (StrUtil.isEmpty(jsonStr)) {
            return null;
        }
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            if (null != node) {
                return node.get(key).binaryValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("jackson get byte error, json: {}, pullWaybillKey: {}", jsonStr, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static <T> List<T> getList(String jsonStr, String key) {
        return getList(DEFAULT_OBJECT_MAPPER, jsonStr, key);
    }

    /**
     * 从json串中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static <T> List<T> getList(ObjectMapper objectMapper, String jsonStr, String key) {
        if (StrUtil.isEmpty(jsonStr)) {
            return null;
        }
        String string = getString(objectMapper, jsonStr, key);
        return toObj(string, new TypeReference<ArrayList<T>>() {
        });
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
        try {
            JsonNode node = objectMapper.readTree(jsonStr);
            add(node, key, value);
            return node.toString();
        } catch (IOException e) {
            log.error("jackson add error, json: {}, key: {}, value: {}", jsonStr, key, value, e);
            return jsonStr;
        }
    }

    /**
     * 向json中添加属性
     */
    private static <T> void add(JsonNode jsonNode, String key, T value) {
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
            case null, default -> ((ObjectNode) jsonNode).put(key, toStr(value));
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
            add(node, key, value);
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
        if (isJsonStartEndValid(trimmedJsonStr)) {
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
        return false;
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


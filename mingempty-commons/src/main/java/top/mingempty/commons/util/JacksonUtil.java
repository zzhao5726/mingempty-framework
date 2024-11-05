package top.mingempty.commons.util;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
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
import top.mingempty.domain.other.DatePattern;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Jackson工具类
 *
 * @author zzhao
 */
public class JacksonUtil {

    public static final class MapTypeReference<V> extends TypeReference<Map<String, Object>> {
        public MapTypeReference() {
            MapType mapType = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);
            ReflectionUtil.setValue("_type", this, mapType);
        }
    }

    /**
     * 泛型集合类型
     *
     * @param <V>
     */
    public static final class ListTypeReference<V> extends TypeReference<List<V>> {
        public ListTypeReference(Class<V> vClass) {
            CollectionLikeType collectionLikeType = TypeFactory.defaultInstance().constructCollectionLikeType(List.class, vClass);
            ReflectionUtil.setValue("_type", this, collectionLikeType);

        }
    }

    /**
     * 序列化级别，默认只序列化属性值发生过改变的字段
     * NON_NULL：序列化非空的字段
     * NON_EMPTY：序列化非空字符串和非空的字段
     * NON_DEFAULT：序列化属性值发生过改变的字段
     */
    private final static JsonInclude.Include DEFAULT_PROPERTY_INCLUSION = JsonInclude.Include.NON_NULL;

    /**
     * 是否缩进JSON格式
     */
    private final static boolean IS_ENABLE_INDENT_OUTPUT = false;

    /**
     * 是否携带类路径
     */
    private final static boolean ACTIVATE_DEFAULT_TYPING = false;

    /**
     * 根据配置参数构建一个定制化的ObjectMapper实例。
     * ObjectMapper是Jackson库中用于序列化和反序列化JSON的主要类。
     * 这些方法提供了多种方式来配置ObjectMapper的行为，例如是否启用缩进输出、如何处理null值、是否启用默认类型化等。
     */
    public static ObjectMapper build() {
        return build(IS_ENABLE_INDENT_OUTPUT, DEFAULT_PROPERTY_INCLUSION);
    }

    /**
     * 根据是否启用缩进输出和JSON属性的包含策略构建ObjectMapper实例。
     *
     * @param isEnableIndentOutput 是否启用缩进输出，用于美化JSON格式
     * @param include              JSON属性的包含策略，决定如何处理null值
     * @return 配置后的ObjectMapper实例
     */
    public static ObjectMapper build(boolean isEnableIndentOutput, JsonInclude.Include include) {
        return build(isEnableIndentOutput, include, ACTIVATE_DEFAULT_TYPING);
    }

    /**
     * 根据是否启用缩进输出、JSON属性的包含策略和是否激活默认类型化构建ObjectMapper实例。
     *
     * @param isEnableIndentOutput  是否启用缩进输出，用于美化JSON格式
     * @param include               JSON属性的包含策略，决定如何处理null值
     * @param activateDefaultTyping 是否激活默认类型化，用于自动处理泛型和继承类型
     * @return 配置后的ObjectMapper实例
     */
    public static ObjectMapper build(boolean isEnableIndentOutput, JsonInclude.Include include,
                                     boolean activateDefaultTyping) {
        return build(isEnableIndentOutput, include, activateDefaultTyping, Collections.emptyMap());
    }

    /**
     * 根据是否启用缩进输出、JSON属性的包含策略、是否激活默认类型化以及日期格式映射表构建ObjectMapper实例。
     * 此方法允许对日期时间的序列化和反序列化进行细粒度控制。
     *
     * @param isEnableIndentOutput  是否启用缩进输出，用于美化JSON格式
     * @param include               JSON属性的包含策略，决定如何处理null值
     * @param activateDefaultTyping 是否激活默认类型化，用于自动处理泛型和继承类型
     * @param datePatternMap        日期格式映射表，关联日期类型和对应的格式字符串
     * @return 配置后的ObjectMapper实例
     */
    public static ObjectMapper build(boolean isEnableIndentOutput, JsonInclude.Include include,
                                     boolean activateDefaultTyping, Map<Class<?>, String> datePatternMap) {
        return build(isEnableIndentOutput, include, Collections.emptyList(), activateDefaultTyping, datePatternMap);
    }

    /**
     * 根据是否启用缩进输出、JSON属性的包含策略、是否激活默认类型化以及日期格式映射表构建ObjectMapper实例。
     * 此方法允许对日期时间的序列化和反序列化进行细粒度控制。
     *
     * @param isEnableIndentOutput  是否启用缩进输出，用于美化JSON格式
     * @param include               JSON属性的包含策略，决定如何处理null值
     * @param modules               添加注册的模块
     * @param activateDefaultTyping 是否激活默认类型化，用于自动处理泛型和继承类型
     * @param datePatternMap        日期格式映射表，关联日期类型和对应的格式字符串
     * @return 配置后的ObjectMapper实例
     */
    public static ObjectMapper build(boolean isEnableIndentOutput, JsonInclude.Include include, List<Module> modules,
                                     boolean activateDefaultTyping, Map<Class<?>, String> datePatternMap) {
        // 处理日期格式映射表为空的情况
        if (datePatternMap == null) {
            datePatternMap = Collections.emptyMap();
        }
        String dateSerializer = datePatternMap.getOrDefault(Date.class, DatePattern.PURE_DATETIME_MS_PATTERN);
        // 使用Builder模式构建ObjectMapper实例，配置各种序列化和反序列化选项
        JsonMapper.Builder builder = JsonMapper.builder()
                .configure(SerializationFeature.INDENT_OUTPUT, isEnableIndentOutput)
                .configure(MapperFeature.USE_ANNOTATIONS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                //识别Java8时间
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(getJavaTimeModule(datePatternMap))
                .serializationInclusion(include)
                //设置日期格式
                .defaultDateFormat(new SimpleDateFormat(dateSerializer))
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
                //禁用某些特性，如：不输出null值
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                //识别Guava包的类
                .addModule(new GuavaModule())
                //允许更改基础{@link VisibilityChecker}配置的便捷方法，以更改自动检测的属性类型的详细信息。
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                ;

        // 根据配置决定是否激活默认类型化
        if (activateDefaultTyping) {
            // 可以解决反序列化时变成LinkedHashMap问题
            builder.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                    ObjectMapper.DefaultTyping.NON_FINAL,
                    JsonTypeInfo.As.PROPERTY);
        }

        //添加注册模块
        if (CollUtil.isNotEmpty(modules)) {
            builder.addModules(modules);
        }

        return builder.build();
    }

    /**
     * 配置JDK8日期时间模块
     *
     * @param datePatternMap
     * @return
     */
    private static JavaTimeModule getJavaTimeModule(Map<Class<?>, String> datePatternMap) {
        // 从日期格式映射表中获取或默认指定日期时间的序列化格式
        String localDateTimeSerializer = datePatternMap.getOrDefault(LocalDateTime.class, DatePattern.PURE_DATETIME_MS_PATTERN);
        String localTimeSerializer = datePatternMap.getOrDefault(LocalDate.class, DatePattern.PURE_DATE_PATTERN);
        String localDateSerializer = datePatternMap.getOrDefault(LocalTime.class, DatePattern.PURE_TIME_MS_PATTERN);

        // 配置Java 8日期时间模块
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(localDateTimeSerializer)));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(localTimeSerializer)));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(localDateSerializer)));

        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(localDateTimeSerializer)));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(localTimeSerializer)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(localDateSerializer)));
        return javaTimeModule;
    }
}

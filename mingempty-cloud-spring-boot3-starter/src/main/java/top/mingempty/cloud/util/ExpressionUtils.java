package top.mingempty.cloud.util;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring Expression 工具类
 *
 * @author zzhao
 */
public class ExpressionUtils {

    /**
     * Spel表达式解析器
     */
    @Getter
    private final static SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * 方法参数名解析器
     */
    @Getter
    private final static DefaultParameterNameDiscoverer DEFAULT_PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private static final Map<String, SpelExpression> EXPRESSION_CACHE = new ConcurrentHashMap<>(2);


    /**
     * 获取Expression对象
     *
     * @param expressionString Spring EL 表达式字符串 例如 #{param.id}
     * @return SpelExpression
     */
    public static SpelExpression getExpressionValue(@Nullable String expressionString) {
        return EXPRESSION_CACHE.get(expressionString);
    }

    /**
     * 获取Expression对象
     *
     * @param expressionString Spring EL 表达式字符串 例如 #{param.id}
     * @return Expression
     */
    public static Expression getExpression(@Nullable String expressionString) {
        if (StrUtil.isBlank(expressionString)) {
            return null;
        }
        return EXPRESSION_CACHE.computeIfAbsent(expressionString, SPEL_EXPRESSION_PARSER::parseRaw);
    }

    /**
     * 根据方法获取参数值
     *
     * @param method           对象方法
     * @param args             请求参数
     * @param expressionString el表达式
     * @param <T>              泛型 这里的泛型要慎用,大多数情况下要使用Object接收避免出现转换异常
     * @return
     */
    @Nullable
    public static <T> T getExpressionValue(@Nullable Method method,
                                           @Nullable Object[] args,
                                           @Nullable String expressionString) {
        Object[] expressionValue = getExpressionValue(method, args, new String[]{expressionString});
        if (ArrayUtil.isEmpty(expressionValue)) {
            return null;
        }
        return (T) expressionValue[0];
    }

    /**
     * 根据方法获取参数值
     *
     * @param method            对象方法
     * @param args              请求参数
     * @param expressionStrings EL表达式数组
     * @return 结果集
     */
    public static Object[] getExpressionValue(@Nullable Method method,
                                              @Nullable Object[] args,
                                              @Nullable String... expressionStrings) {
        if (method == null) {
            return null;
        }
        return getExpressionValue(DEFAULT_PARAMETER_NAME_DISCOVERER.getParameterNames(method), args, expressionStrings);
    }

    /**
     * 根据方法获取参数值
     *
     * @param parameterNames    对象名称的集合
     * @param args              请求参数
     * @param expressionStrings EL表达式数组
     * @return 结果集
     */
    public static Object[] getExpressionValue(@Nullable String[] parameterNames,
                                              @Nullable Object[] args,
                                              @Nullable String... expressionStrings) {
        if (ArrayUtil.isEmpty(parameterNames)
                || ArrayUtil.isEmpty(args)
                || ArrayUtil.isEmpty(expressionStrings)) {
            return null;
        }

        EvaluationContext context = new StandardEvaluationContext();
        // 给上下文赋值变量
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        Object[] values = new Object[expressionStrings.length];
        for (int i = 0; i < expressionStrings.length; i++) {
            Expression expression = getExpression(expressionStrings[i]);
            if (expression == null) {
                values[i] = null;
                continue;
            }
            values[i] = expression.getValue(context);
        }
        return values;
    }


    /**
     * 计算EL表达式的值
     *
     * @param root             根对象
     * @param expressionString Spring EL表达式
     * @param <T>              泛型 这里的泛型要慎用,大多数情况下要使用Object接收避免出现转换异常
     * @return 结果
     */
    @Nullable
    public static <T> T getExpressionValue(@Nullable Object root, @Nullable String expressionString) {
        Object[] expressionValue = getExpressionValue(root, new String[]{expressionString});
        if (ArrayUtil.isEmpty(expressionValue)) {
            return null;
        }
        return (T) expressionValue[0];
    }

    /**
     * 计算EL表达式的值
     *
     * @param root              根对象
     * @param expressionStrings Spring EL表达式
     * @return 结果集
     */
    public static Object[] getExpressionValue(@Nullable Object root, @Nullable String... expressionStrings) {
        if (root == null) {
            return null;
        }
        if (ArrayUtil.isEmpty(expressionStrings)) {
            return null;
        }
        //noinspection ConstantConditions
        Object[] values = new Object[expressionStrings.length];
        for (int i = 0; i < expressionStrings.length; i++) {
            values[i] = getExpressionValue(root, expressionStrings[i]);
        }
        //noinspection unchecked
        return values;
    }


    /**
     * 表达式条件求值
     * 如果为值为null则返回false,
     * 如果为布尔类型直接返回,
     * 如果为数字类型则判断是否大于0
     *
     * @param root             根对象
     * @param expressionString Spring EL表达式
     * @return 值
     */
    @Nullable
    public static boolean getConditionValue(@Nullable Object root, @Nullable String expressionString) {
        Object value = getExpressionValue(root, expressionString);
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue() > 0;
        }
        return true;
    }

    /**
     * 表达式条件求值
     *
     * @param root              根对象
     * @param expressionStrings Spring EL表达式数组
     * @return 值
     */
    @Nullable
    public static boolean getConditionValue(@Nullable Object root, @Nullable String... expressionStrings) {
        if (root == null) {
            return false;
        }
        if (ArrayUtil.isEmpty(expressionStrings)) {
            return false;
        }
        for (String expressionString : expressionStrings) {
            expressionString = EnvironmentUtil.resolvePlaceholders(expressionString);
            if (!getConditionValue(root, expressionString)) {
                return false;
            }
        }
        return true;
    }
}
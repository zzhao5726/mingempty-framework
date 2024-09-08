package top.mingempty.util;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring Expression 工具类
 *
 *
 * <p>
 * 使用示例如下
 * <p>
 * 商品实体
 * <p>
 * {@snippet :
 * import lombok.Builder;
 * import lombok.Data;
 * import java.util.List;
 *
 * @author zzhao
 * @Data
 * @Builder public class OrderDO {
 * //订单编号
 * private Integer id;
 * //产品列表
 * private List<PorductDO> products;
 * // ... 省略 setter/getter 方法
 * }
 *}
 * <p>
 * 订单实体
 * <p>
 * {@snippet :
 * import lombok.Builder;
 * import lombok.Data;
 * @Data
 * @Builder public class PorductDO {
 * //  商品编号
 * private Integer id;
 * // 价格
 * private Integer price;
 * // ... 省略 setter/getter 方法
 * }
 *}
 * <p>
 * 示例方法
 * <p>
 * {@snippet :
 * public static void main(String[] args) {
 * List<PorductDO> porductDOList = new ArrayList<>();
 * PorductDO build = PorductDO.builder().id(213532).build();
 * porductDOList.add(build);
 * OrderDO orderDO = OrderDO.builder().id(1111).products(porductDOList).build();
 * Object expressionValue = ExpressionUtils.gainExpressionValue(new String[]{"orderDO"}, new Object[]{orderDO}, "{#orderDO.id}", "{#orderDO.products}");
 * System.out.println(expressionValue);
 *
 * Object[] objects = ExpressionUtils.gainExpressionValue(orderDO, "#root.id", "#root.products[0].id");
 * System.out.println(objects);
 * }
 *}
 */
public class ExpressionUtils {

    /**
     * Spel表达式解析器
     */
    private final static SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * 方法参数名解析器
     */
    private final static DefaultParameterNameDiscoverer DEFAULT_PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private static final Map<String, SpelExpression> EXPRESSION_CACHE = new ConcurrentHashMap<>(2);


    /**
     * 获取Expression对象
     *
     * @param expressionString Spring EL 表达式字符串 例如 #{param.id}
     * @return Expression
     */
    public static Expression gainExpression(@Nullable String expressionString) {
        if (StrUtil.isBlank(expressionString)) {
            return null;
        }
        return EXPRESSION_CACHE.computeIfAbsent(expressionString, SPEL_EXPRESSION_PARSER::parseRaw);
    }

    /**
     * 根据方法获取参数值
     *
     * @param method            对象方法
     * @param args              请求参数
     * @param expressionStrings EL表达式数组
     * @return 结果集
     */
    public static Object[] gainExpressionValue(@Nullable Method method,
                                               @Nullable Object[] args,
                                               @Nullable String... expressionStrings) {
        if (method == null) {
            return null;
        }
        return gainExpressionValue(DEFAULT_PARAMETER_NAME_DISCOVERER.getParameterNames(method), args, expressionStrings);
    }

    /**
     * 根据方法获取参数值
     *
     * @param parameterNames    对象名称的集合
     * @param args              请求参数
     * @param expressionStrings EL表达式数组
     * @return 结果集
     */
    public static Object[] gainExpressionValue(@Nullable String[] parameterNames,
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
            Object value = gainExpressionValue(context, expressionStrings[i]);
            if (value instanceof Collection collation) {
                values[i] = collation.stream().findFirst().orElse(null);
            } else {
                values[i] = value;
            }
        }
        return values;
    }

    /**
     * 计算EL表达式的值
     *
     * @param root              根对象
     * @param expressionStrings Spring EL表达式
     * @return 结果集
     */
    public static Object[] gainExpressionValue(@Nullable Object root, @Nullable String... expressionStrings) {
        if (root == null) {
            return null;
        }
        if (ArrayUtil.isEmpty(expressionStrings)) {
            return null;
        }
        //noinspection ConstantConditions
        Object[] values = new Object[expressionStrings.length];
        for (int i = 0; i < expressionStrings.length; i++) {
            EvaluationContext context = new StandardEvaluationContext(root);
            values[i] = gainExpressionValue(context, expressionStrings[i]);
        }
        return values;
    }

    /**
     * 获取值
     *
     * @param expressionString
     * @param context
     * @return
     */
    public static Object gainExpressionValue(EvaluationContext context, String expressionString) {
        Expression expression = gainExpression(expressionString);
        if (expression == null) {
            return null;
        }
        return expression.getValue(context);
    }


}
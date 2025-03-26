package top.mingempty.domain.other;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.enums.DirectionEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 公共条件参数
 *
 * @author zzhao
 */
@Data
@Schema(description = "公共条件参数")
public class MePubConditions {

    /**
     * 要查询的字段
     */
    @Schema(title = "要查询的字段")
    private Set<String> selectColumns;

    /**
     * 排序字段集合
     */
    @Schema(title = "排序字段集合")
    private List<OrderColumn> orderColumns;

    /**
     * 分组字段集合
     */
    @Schema(title = "分组字段集合")
    private List<String> groupColumns;

    /**
     * 条件
     */
    @Schema(title = "条件")
    private List<ValueCondition> conditions = new ArrayList<>();

    /**
     * 排序
     */
    @Data
    @Schema(title = "排序")
    public static class OrderColumn {
        /**
         * 排序字段
         */
        @Schema(title = "排序字段")
        private String column;

        /**
         * 排序方式
         */
        @Schema(title = "排序方式")
        private DirectionEnum direction;
    }

    /**
     * 值条件
     */
    @Data
    @Schema(title = "值条件")
    public static class ValueCondition {
        /**
         * 条件操作符
         */
        @Schema(title = "条件操作符")
        private ConditionsType type;
        /**
         * 字段名
         */
        @Schema(title = "字段名")
        private String column;
        /**
         * 值
         */
        @Schema(title = "值")
        private Object value;

        /**
         * between 值
         */
        @Schema(title = "between 值")
        private Between between;

        /**
         * in 值
         */
        @Schema(title = "in 值")
        private List<Object> in;

        /**
         * or条件
         */
        @Schema(title = " or条件")
        private List<ValueCondition> conditions = new ArrayList<>();
    }


    /**
     * between条件值范围
     */
    @Data
    @Schema(title = "between条件值范围")
    public static class Between {
        /**
         * 开始值
         */
        @Schema(title = "开始值")
        private Object start;

        /**
         * 结束值
         */
        @Schema(title = "结束值")
        private Object end;
    }

    /**
     * 条件操作符
     */
    @Schema(title = "条件操作符")
    public enum ConditionsType {
        /**
         * 等于条件
         */
        @Schema(title = "等于条件")
        eq,
        /**
         * 不等于条件
         */
        @Schema(title = "不等于条件")
        ne,
        /**
         * is null条件
         */
        @Schema(title = "is null条件")
        isNull,
        /**
         * is not null条件
         */
        @Schema(title = "is not null条件")
        isNotNull,
        /**
         * like条件
         */
        @Schema(title = "like条件")
        like,
        /**
         * not like条件
         */
        @Schema(title = "not like条件")
        notLike,
        /**
         * like Left条件
         */
        @Schema(title = "like Left条件")
        likeLeft,
        /**
         * not like Left条件
         */
        @Schema(title = "not like Left条件")
        notLikeLeft,
        /**
         * like Right条件
         */
        @Schema(title = "like Right条件")
        likeRight,
        /**
         * not like Right条件
         */
        @Schema(title = "not like Right条件")
        notLikeRight,
        /**
         * 大于条件
         */
        @Schema(title = "大于条件")
        gt,
        /**
         * 小于条件
         */
        @Schema(title = "小于条件")
        lt,
        /**
         * 大于等于条件
         */
        @Schema(title = "大于等于条件")
        ge,
        /**
         * 小于等于条件
         */
        @Schema(title = "小于等于条件")
        le,
        /**
         * between条件
         */
        @Schema(title = "between条件")
        between,
        /**
         * not between条件
         */
        @Schema(title = "not between条件")
        notBetween,
        /**
         * in条件
         */
        @Schema(title = "in条件")
        in,
        /**
         * not in条件
         */
        @Schema(title = "not in条件")
        notIn,
        /**
         * or条件
         */
        @Schema(title = "or条件")
        or,
        ;

    }

    /**
     * 获取条件sql
     *
     * @param valueConditions 条件集合
     * @return 条件sql
     */
    public static String gainConditionsSql(Collection<ValueCondition> valueConditions) {
        return gainConditionsSql(valueConditions, null);
    }

    /**
     * 获取条件sql
     *
     * @param valueConditions 条件集合
     * @param tableAliasName  表别名
     * @return 条件sql
     */
    public static String gainConditionsSql(Collection<ValueCondition> valueConditions, String tableAliasName) {
        return gainConditionsSql(valueConditions, tableAliasName, false, null);
    }

    /**
     * 获取条件sql
     *
     * @param valueConditions 条件集合
     * @param isJson          是否是json字段
     * @param jsonColumnName  json字段名
     * @return 条件sql
     */
    public static String gainConditionsSql(Collection<ValueCondition> valueConditions,
                                           boolean isJson,
                                           String jsonColumnName) {
        return gainConditionsSql(valueConditions, null, isJson, jsonColumnName);
    }

    /**
     * 获取条件sql
     *
     * @param valueConditions 条件集合
     * @param tableAliasName  表别名
     * @param isJson          是否是json字段
     * @param jsonColumnName  json字段名
     * @return 条件sql
     */
    public static String gainConditionsSql(Collection<ValueCondition> valueConditions,
                                           String tableAliasName,
                                           boolean isJson,
                                           String jsonColumnName) {
        if (valueConditions == null
                || valueConditions.isEmpty()) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        AtomicBoolean isFirst = new AtomicBoolean(true);
        valueConditions.forEach(valueCondition -> {

            String columnName = Optional.ofNullable(tableAliasName).map(name -> name + ".").orElse("")
                    + (isJson ? jsonColumnName + "->>'$." + valueCondition.getColumn() + "'" : valueCondition.getColumn());
            if (isFirst.get()) {
                isFirst.set(false);
                if (ConditionsType.or.equals(valueCondition.getType())) {
                    //说明第一个就是or 那么就拼接个1!=1
                    sb.append(" 1 != 1 ");
                } else {
                    sb.append(columnName);
                }
            } else {
                if (!ConditionsType.or.equals(valueCondition.getType())) {
                    sb.append(" and ").append(columnName);
                }
            }
            String value = valueCondition.getValue() != null && valueCondition.getValue() instanceof Number
                    ? "" + valueCondition.getValue() : "'" + valueCondition.getValue() + "'";

            String betweenStart = valueCondition.getBetween() == null ?
                    null : valueCondition.getBetween().getStart() != null
                    && valueCondition.getBetween().getStart() instanceof Number
                    ? "" + valueCondition.getBetween().getStart() : "'" + valueCondition.getBetween().getStart() + "'";

            String betweenEnd = valueCondition.getBetween() == null ?
                    null : valueCondition.getBetween().getEnd() != null
                    && valueCondition.getBetween().getEnd() instanceof Number
                    ? "" + valueCondition.getBetween().getEnd() : "'" + valueCondition.getBetween().getEnd() + "'";

            String inValue = Optional.ofNullable(valueCondition.getIn())
                    .orElse(List.of())
                    .stream()
                    .map(in -> {
                                if (in instanceof Number) {
                                    return "" + in;
                                } else {
                                    return "'" + in + "'";
                                }
                            }
                    ).collect(Collectors.joining(","));


            switch (valueCondition.getType()) {
                case eq -> sb.append(" = ").append(value);
                case ne -> sb.append(" != ").append(value);
                case isNull -> sb.append(" is null");
                case isNotNull -> sb.append(" is not null");
                case like -> sb.append(" like ").append("'%").append(valueCondition.getValue()).append("%'");
                case notLike -> sb.append(" not like ").append("'%").append(valueCondition.getValue()).append("%'");
                case likeLeft -> sb.append(" like ").append("'%").append(valueCondition.getValue()).append("'");
                case notLikeLeft -> sb.append(" not like ").append("'%").append(valueCondition.getValue()).append("'");
                case likeRight -> sb.append(" like ").append("'").append(valueCondition.getValue()).append("%'");
                case notLikeRight -> sb.append(" not like ").append("'").append(valueCondition.getValue()).append("%'");
                case gt -> sb.append(" > ").append(value);
                case lt -> sb.append(" < ").append(value);
                case ge -> sb.append(" >= ").append(value);
                case le -> sb.append(" <= ").append(value);
                case between -> sb.append(" between ").append(betweenStart).append(" and ").append(betweenEnd);
                case notBetween -> sb.append(" not between ").append(betweenStart).append(" and ").append(betweenEnd);
                case in -> sb.append(" in ").append("('").append(inValue).append("')");
                case notIn -> sb.append(" not in ").append("('").append(inValue).append("')");
                case or -> {
                    String conditionsSqlBySon = gainConditionsSql(valueCondition.getConditions(), tableAliasName, isJson, jsonColumnName);
                    if (conditionsSqlBySon != null) {
                        sb.append(" or ").append(conditionsSqlBySon);
                    }
                }
            }
        });
        sb.append(")");
        return sb.toString().replaceAll(" 1 != 1  or ", "");
    }

    public static ValueCondition cloneValueCondition(ValueCondition extraFieldCondition) {
        if (extraFieldCondition == null) {
            return null;
        }
        ValueCondition valueCondition = new ValueCondition();
        valueCondition.setType(extraFieldCondition.getType());
        valueCondition.setColumn(extraFieldCondition.getColumn());
        valueCondition.setValue(extraFieldCondition.getValue());
        if (extraFieldCondition.getBetween() != null) {
            Between between = new Between();
            between.setStart(extraFieldCondition.getBetween().getStart());
            between.setEnd(extraFieldCondition.getBetween().getEnd());
            valueCondition.setBetween(between);
        }
        if (extraFieldCondition.getIn() != null) {
            valueCondition.setIn(List.copyOf(extraFieldCondition.getIn()));
        }
        if (extraFieldCondition.getConditions() != null) {
            valueCondition.setConditions(new ArrayList<>(cloneValueConditions(extraFieldCondition.getConditions())));
        }

        return valueCondition;
    }

    public static Collection<ValueCondition> cloneValueConditions(Collection<ValueCondition> extraFieldConditions) {
        if (extraFieldConditions == null) {
            return List.of();
        }
        return extraFieldConditions
                .parallelStream()
                .map(MePubConditions::cloneValueCondition)
                .collect(Collectors.toList());
    }

}

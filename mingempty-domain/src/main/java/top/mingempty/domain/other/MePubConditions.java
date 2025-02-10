package top.mingempty.domain.other;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.enums.DirectionEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

}

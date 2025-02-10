package top.mingempty.mybatis.plus.tool;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.mingempty.domain.other.MePubConditions;

import java.util.List;
import java.util.Set;

/**
 * 公共条件参数转Wrapper
 *
 * @author zzhao
 */
public class MpWrapperTool {

    /**
     * 公共条件参数转QueryWrapper
     *
     * @param mePubConditions 公共条件参数
     * @param <T>
     * @return
     */
    public static <T> QueryWrapper<T> entityToWrapper(MePubConditions mePubConditions) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        if (CollUtil.isEmpty(mePubConditions.getConditions())) {
            return queryWrapper;
        }
        Set<String> selectColumns = mePubConditions.getSelectColumns();
        if (CollUtil.isNotEmpty(selectColumns)) {
            queryWrapper.select(selectColumns.toArray(new String[0]));
        }

        List<MePubConditions.OrderColumn> orderColumns = mePubConditions.getOrderColumns();
        if (CollUtil.isNotEmpty(orderColumns)) {
            // TODO 暂时先这样写
            orderColumns.forEach(orderColumn -> {
                if (orderColumn.getDirection().isAscending()) {
                    queryWrapper.orderByAsc(orderColumn.getColumn());
                } else {
                    queryWrapper.orderByDesc(orderColumn.getColumn());
                }
            });
        }
        List<String> groupColumns = mePubConditions.getGroupColumns();
        if (CollUtil.isNotEmpty(groupColumns)) {
            queryWrapper.groupBy(groupColumns);
        }

//        if (CollUtil.isNotEmpty(orderColumns)) {
//            String columnS = orderColumns.stream()
//                    .map(orderColumn -> orderColumn.getColumn()
//                            + " "
//                            + (orderColumn.getDirection().isAscending() ? "ASC" : "DESC"))
//                    .collect(Collectors.joining(","));
//            queryWrapper.last("ORDER BY " + columnS);
//        }
        return entityToWrapper(mePubConditions.getConditions(), queryWrapper);
    }

    private static <T> QueryWrapper<T> entityToWrapper(List<MePubConditions.ValueCondition> conditions, final QueryWrapper<T> wrapper) {
        conditions.forEach(conditionSon -> {
            switch (conditionSon.getType()) {
                case eq -> {
                }
                case ne -> wrapper.ne(conditionSon.getColumn(), conditionSon.getValue());
                case isNull -> wrapper.isNull(conditionSon.getColumn());
                case isNotNull -> wrapper.isNotNull(conditionSon.getColumn());
                case like -> wrapper.like(conditionSon.getColumn(), conditionSon.getValue());
                case notLike -> wrapper.notLike(conditionSon.getColumn(), conditionSon.getValue());
                case likeLeft -> wrapper.likeLeft(conditionSon.getColumn(), conditionSon.getValue());
                case notLikeLeft -> wrapper.notLikeLeft(conditionSon.getColumn(), conditionSon.getValue());
                case likeRight -> wrapper.likeRight(conditionSon.getColumn(), conditionSon.getValue());
                case notLikeRight -> wrapper.notLikeRight(conditionSon.getColumn(), conditionSon.getValue());
                case gt -> wrapper.gt(conditionSon.getColumn(), conditionSon.getValue());
                case lt -> wrapper.lt(conditionSon.getColumn(), conditionSon.getValue());
                case ge -> wrapper.ge(conditionSon.getColumn(), conditionSon.getValue());
                case le -> wrapper.le(conditionSon.getColumn(), conditionSon.getValue());
                case between ->
                        wrapper.between(conditionSon.getColumn(), conditionSon.getBetween().getStart(), conditionSon.getBetween().getEnd());
                case notBetween ->
                        wrapper.notBetween(conditionSon.getColumn(), conditionSon.getBetween().getStart(), conditionSon.getBetween().getEnd());
                case in -> wrapper.in(conditionSon.getColumn(), conditionSon.getIn());
                case notIn -> wrapper.notIn(conditionSon.getColumn(), conditionSon.getIn());
                case or -> wrapper.or(orWrapper -> {
                    entityToWrapper(conditionSon.getConditions(), orWrapper);
                });
            }
        });
        return wrapper;
    }

}

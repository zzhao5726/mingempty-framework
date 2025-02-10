package top.mingempty.mybatis.flex.tool;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.query.QueryWrapper;
import top.mingempty.domain.other.MePubConditions;

import java.util.List;
import java.util.Set;


/**
 * 公共条件参数转Wrapper
 *
 * @author zzhao
 */
public class MfWrapperTool {

    /**
     * 公共条件参数转QueryWrapper
     *
     * @param mePubConditions 公共条件参数
     * @return
     */
    public static QueryWrapper entityToWrapper(MePubConditions mePubConditions) {
        if (CollUtil.isEmpty(mePubConditions.getConditions())) {
            return new QueryWrapper();
        }

        return entityToWrapper(mePubConditions.getSelectColumns(), mePubConditions.getConditions(), new QueryWrapper());
    }

    private static QueryWrapper entityToWrapper(Set<String> selectColumns,
                                                List<MePubConditions.ValueCondition> conditions, final QueryWrapper wrapper) {
        if (CollUtil.isNotEmpty(selectColumns)) {
            wrapper.select(selectColumns.toArray(new String[0]));

        }

        conditions.forEach(conditionSon -> {
            switch (conditionSon.getType()) {
                case eq -> wrapper.eq(conditionSon.getColumn(), conditionSon.getValue());
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
                    entityToWrapper(null, conditionSon.getConditions(), orWrapper);
                });
            }
        });
        return wrapper;
    }

}

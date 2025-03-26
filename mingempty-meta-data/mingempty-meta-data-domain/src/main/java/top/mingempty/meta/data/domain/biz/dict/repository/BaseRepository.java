package top.mingempty.meta.data.domain.biz.dict.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.BaseQuery;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 基础数据仓库
 */
public interface BaseRepository<M, Q extends BaseQuery<Q>> {
    /**
     * 根据条目编码查询
     *
     * @param entryCode 条目编码
     * @return M
     */
    default M query(String entryCode) {
        if (StrUtil.isEmpty(entryCode)) {
            return null;
        }
        List<M> ms = Optional.ofNullable(query(gainQuery(entryCode)))
                .orElse(List.of());
        if (ms.isEmpty()) {
            return null;
        }
        return ms.getFirst();
    }

    /**
     * 根据条目编码集合查询
     *
     * @param entryCodes 条目编码集合
     * @return M
     */
    default List<M> query(Collection<String> entryCodes) {
        if (CollUtil.isEmpty(entryCodes)) {
            return List.of();
        }

        return query(gainQuery(entryCodes));
    }

    /**
     * 根据查询条件查询
     *
     * @param query 查询条件
     * @return M
     */
    default List<M> query(Q query) {
        if (ObjUtil.isEmpty(query)) {
            return List.of();
        }
        return query(query, null);
    }

    /**
     * 根据查询条件查询
     *
     * @param query 查询条件
     * @param page  分页
     * @return M
     */
    List<M> query(Q query, final MePage page);

    /**
     * 根据条目编码获取查询条件
     *
     * @param entryCode 条目编码
     * @return Q
     */
    Q gainQuery(String entryCode);

    /**
     * 根据条目编码集合获取查询条件
     *
     * @param entryCodes 条目编码集合
     * @return Q
     */
    Q gainQuery(Collection<String> entryCodes);

}

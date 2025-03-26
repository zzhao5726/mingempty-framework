package top.mingempty.meta.data.repository.service;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.core.service.IService;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.ExtraFieldQuery;
import top.mingempty.meta.data.repository.model.po.ExtraFieldPo;

import java.util.Collection;
import java.util.List;

/**
 * 字典扩展字段信息表 服务层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public interface ExtraFieldService extends IService<ExtraFieldPo> {

    /**
     * 转移变更数据
     *
     * @param entryCode
     */
    default void transferChange(String entryCode) {
        if (ObjUtil.isEmpty(entryCode)) {
            return;
        }
        transferChange(List.of(entryCode));
    }

    /**
     * 转移变更数据
     *
     * @param entryCodes
     */
    void transferChange(Collection<String> entryCodes);

    /**
     * 条件查询字典扩展字段信息
     *
     * @param extraFieldQuery 查询条件
     * @return
     */
    default List<ExtraFieldPo> query(ExtraFieldQuery extraFieldQuery) {
        return query(extraFieldQuery, null);
    }

    /**
     * 条件查询字典扩展字段信息
     *
     * @param extraFieldQuery 查询条件
     * @param mePage          分页参数
     * @return
     */
    List<ExtraFieldPo> query(ExtraFieldQuery extraFieldQuery, final MePage mePage);
}

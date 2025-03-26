package top.mingempty.meta.data.repository.service;

import com.mybatisflex.core.service.IService;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;
import top.mingempty.meta.data.repository.model.po.ChangeEntryPo;

import java.util.List;

/**
 * 字典条目变化流水表 服务层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public interface ChangeEntryService extends IService<ChangeEntryPo> {

    /**
     * 条件查询条目数据
     *
     * @param entryQuery 查询条件
     * @return
     */
    default List<ChangeEntryPo> query(EntryQuery entryQuery) {
        return query(entryQuery, null);
    }

    /**
     * 条件查询条目数据
     *
     * @param entryQuery 查询条件
     * @param mePage     分页参数
     * @return
     */
    List<ChangeEntryPo> query(EntryQuery entryQuery, final MePage mePage);
}

package top.mingempty.meta.data.service;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.service.IService;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.model.bo.EntryBo;
import top.mingempty.meta.data.model.bo.EntryQueryBo;
import top.mingempty.meta.data.model.po.ChangeEntryPo;

import java.util.Collection;
import java.util.List;

/**
 * 字典条目变化流水表 服务层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
public interface ChangeEntryService extends IService<ChangeEntryPo> {

    /**
     * 通过条目编码查询最新数据
     *
     * @param entryCodes
     * @return
     */
    default List<EntryBo> queryNew(Collection<String> entryCodes) {
        return queryByVersion(entryCodes, null);
    }

    /**
     * 通过条目编码查询指定版本数据
     *
     * @param entryCode
     * @param entryVersion
     * @return
     */
    default EntryBo queryByVersion(String entryCode, Long entryVersion) {
        EntryQueryBo entryQueryBo = new EntryQueryBo();
        entryQueryBo.setEntryCode(entryCode);
        entryQueryBo.setEntryVersion(entryVersion);
        List<EntryBo> entryBos = query(entryQueryBo);
        if (CollUtil.isEmpty(entryBos)) {
            return null;
        }
        return entryBos.getFirst();
    }

    /**
     * 通过条目编码查询指定版本数据
     *
     * @param entryCodes
     * @param entryVersion
     * @return
     */
    default List<EntryBo> queryByVersion(Collection<String> entryCodes, Long entryVersion) {
        EntryQueryBo entryQueryBo = new EntryQueryBo();
        entryQueryBo.setEntryCodes(entryCodes);
        entryQueryBo.setEntryVersion(entryVersion);
        return query(entryQueryBo);
    }

    /**
     * 条目查询
     *
     * @param entryQueryBo
     * @return
     */
    default List<EntryBo> query(EntryQueryBo entryQueryBo) {
        return query(entryQueryBo, null);
    }

    /**
     * 条目查询
     *
     * @param entryQueryBo
     * @param mePage
     * @return
     */
    List<EntryBo> query(EntryQueryBo entryQueryBo, final MePage mePage);
}

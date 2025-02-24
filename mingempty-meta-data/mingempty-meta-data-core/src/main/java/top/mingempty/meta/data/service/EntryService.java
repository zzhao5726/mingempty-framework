package top.mingempty.meta.data.service;

import com.mybatisflex.core.service.IService;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.model.bo.EntryBo;
import top.mingempty.meta.data.model.bo.EntryQueryBo;
import top.mingempty.meta.data.model.po.EntryPo;
import top.mingempty.meta.data.model.vo.in.EntryCreateVo;
import top.mingempty.meta.data.model.vo.in.EntryQueryVo;
import top.mingempty.meta.data.model.vo.in.EntryStatusChangeVo;
import top.mingempty.meta.data.model.vo.in.EntryUpdateVo;
import top.mingempty.meta.data.model.vo.out.EntryBaseResultVo;

import java.util.Collection;
import java.util.List;

/**
 * 字典条目表 服务层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
public interface EntryService extends IService<EntryPo> {

    /**
     * 添加一个条目
     *
     * @param entryCreateVo
     */
    void create(EntryCreateVo entryCreateVo);

    /**
     * 添加多个条目
     *
     * @param entryCreateVos
     */
    void create(List<EntryCreateVo> entryCreateVos);

    /**
     * 转移变更数据
     *
     * @param entryCode
     */
    void transferChange(String entryCode);

    /**
     * 转移变更数据
     *
     * @param entryCodes
     */
    void transferChange(Collection<String> entryCodes);

    /**
     * 修改一个条目
     *
     * @param entryUpdateVo
     */
    void change(EntryUpdateVo entryUpdateVo);

    /**
     * 修改多个条目
     *
     * @param entryUpdateVos
     */
    void change(List<EntryUpdateVo> entryUpdateVos);

    /**
     * 修改条目状态
     *
     * @param entryStatusChangeVo
     */
    void statusChange(EntryStatusChangeVo entryStatusChangeVo);

    /**
     * 查询条目列表
     *
     * @param entryQueryVo
     * @param mePage
     * @return
     */
    List<EntryBaseResultVo> list(EntryQueryVo entryQueryVo, final MePage mePage);


    /**
     * 条件查询条目信息
     *
     * @param entryQueryBo
     * @return
     */
    default List<EntryBo> query(EntryQueryBo entryQueryBo) {
        return this.query(entryQueryBo, null);
    }

    /**
     * 条件查询条目信息
     *
     * @param entryQueryBo
     * @param mePage
     * @return
     */
    List<EntryBo> query(EntryQueryBo entryQueryBo, final MePage mePage);

    /**
     * 条件查询最新版本条目信息
     *
     * @param entryQueryBo
     * @return
     */
    default List<EntryBo> queryNew(EntryQueryBo entryQueryBo) {
        return this.queryNew(entryQueryBo, null);
    }

    /**
     * 条件查询最新版本条目信息
     *
     * @param entryQueryBo
     * @param mePage
     * @return
     */
    List<EntryBo> queryNew(EntryQueryBo entryQueryBo, final MePage mePage);
}

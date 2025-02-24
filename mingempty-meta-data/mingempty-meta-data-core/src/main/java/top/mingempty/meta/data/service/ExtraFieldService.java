package top.mingempty.meta.data.service;

import com.mybatisflex.core.service.IService;
import top.mingempty.meta.data.model.po.ExtraFieldPo;
import top.mingempty.meta.data.model.vo.in.EntryCreateVo;

import java.util.Collection;
import java.util.Map;

/**
 * 字典扩展字段信息表 服务层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
public interface ExtraFieldService extends IService<ExtraFieldPo> {

    /**
     * 创建字典扩展字段信息
     *
     * @param entryCreateVo
     */
    void create(EntryCreateVo entryCreateVo);

    /**
     * 批量创建字典扩展字段信息
     *
     * @param entryCreateVos
     */
    void create(Collection<EntryCreateVo> entryCreateVos);

    /**
     * 批量创建字典扩展字段信息
     *
     * @param entryCreateVos
     * @param entryVersionMap
     */
    void create(Collection<EntryCreateVo> entryCreateVos, Map<String, Long> entryVersionMap);
}

package top.mingempty.meta.data.repository.service;

import com.mybatisflex.core.service.IService;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.ExtraFieldQuery;
import top.mingempty.meta.data.repository.model.po.ChangeExtraFieldPo;

import java.util.List;

/**
 * 字典扩展字段信息变化流水表 服务层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public interface ChangeExtraFieldService extends IService<ChangeExtraFieldPo> {

    /**
     * 条件查询字典扩展字段信息
     *
     * @param extraFieldQuery 查询条件
     * @return
     */
    default List<ChangeExtraFieldPo> query(ExtraFieldQuery extraFieldQuery) {
        return query(extraFieldQuery, null);
    }

    /**
     * 条件查询字典扩展字段信息
     *
     * @param extraFieldQuery 查询条件
     * @param mePage          分页参数
     * @return
     */
    List<ChangeExtraFieldPo> query(ExtraFieldQuery extraFieldQuery, final MePage mePage);

}

package top.mingempty.meta.data.repository.service;

import com.mybatisflex.core.service.IService;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.AuthorizationQuery;
import top.mingempty.meta.data.repository.model.po.ChangeAuthorizationPo;

import java.util.List;

/**
 * 条目授权变化流水表 服务层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public interface ChangeAuthorizationService extends IService<ChangeAuthorizationPo> {

    /**
     * 条件查询条目授权数据
     *
     * @param authorizationQuery 查询条件
     * @return
     */
    default List<ChangeAuthorizationPo> query(AuthorizationQuery authorizationQuery) {
        return query(authorizationQuery, null);
    }

    /**
     * 条件查询条目授权数据
     *
     * @param authorizationQuery 查询条件
     * @param mePage     分页参数
     * @return
     */
    List<ChangeAuthorizationPo> query(AuthorizationQuery authorizationQuery, final MePage mePage);

}

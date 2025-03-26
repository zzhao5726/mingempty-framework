package top.mingempty.meta.data.domain.biz.dict.repository;

import top.mingempty.meta.data.domain.biz.dict.model.AuthorizationModel;
import top.mingempty.meta.data.domain.biz.dict.query.AuthorizationQuery;

import java.util.Collection;

/**
 * 字典条目权限仓储接口
 *
 * @author zzhao
 */
public interface AuthorizationRepository extends BaseRepository<AuthorizationModel, AuthorizationQuery> {

    /**
     * 条目权限信息修改
     *
     * @param authorization
     */
    void modifies(AuthorizationModel authorization);

    @Override
    default AuthorizationQuery gainQuery(String entryCode) {
        return AuthorizationQuery.builder().entryCode(entryCode).build();
    }

    @Override
    default AuthorizationQuery gainQuery(Collection<String> entryCodes) {
        return AuthorizationQuery.builder().entryCodes(entryCodes).build();
    }
}

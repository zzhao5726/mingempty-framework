package top.mingempty.meta.data.repository.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.domain.biz.dict.query.AuthorizationQuery;
import top.mingempty.meta.data.repository.model.po.AuthorizationPo;

import java.util.List;

/**
 * 条目授权表 映射层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Mapper
public interface AuthorizationMapper extends BaseMapper<AuthorizationPo> {

    /**
     * 删除变更数据
     *
     * @param authorizationQuery
     */
    void deleteChange(@Param("authorizationQuery") AuthorizationQuery authorizationQuery);

    /**
     * 迁移条目数据
     *
     * @param authorizationQuery
     */
    void transferChange(@Param("authorizationQuery") AuthorizationQuery authorizationQuery);


    long queryCount(@Param("authorizationQuery") AuthorizationQuery authorizationQuery);

    List<AuthorizationPo> query(@Param("authorizationQuery") AuthorizationQuery authorizationQuery,
                                @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

}

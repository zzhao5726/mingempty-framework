package top.mingempty.meta.data.repository.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.domain.biz.dict.query.AuthorizationQuery;
import top.mingempty.meta.data.repository.model.po.ChangeAuthorizationPo;

import java.util.List;

/**
 * 条目授权变化流水表 映射层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Mapper
public interface ChangeAuthorizationMapper extends BaseMapper<ChangeAuthorizationPo> {

    long queryCount(@Param("authorizationQuery") AuthorizationQuery authorizationQuery);

    List<ChangeAuthorizationPo> query(@Param("authorizationQuery") AuthorizationQuery authorizationQuery,
                              @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

}

package top.mingempty.meta.data.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.model.bo.AuthorizationQueryBo;
import top.mingempty.meta.data.model.po.AuthorizationPo;

/**
 * 条目授权表 映射层。
 *
 * @author zzhao
 * @since 2025-03-19 23:49:55
 */
@Mapper
public interface AuthorizationMapper extends BaseMapper<AuthorizationPo> {

    /**
     * 迁移条目权限数据
     *
     * @param authorizationQueryBo
     */
    void transferChange(@Param("authorizationQueryBo") AuthorizationQueryBo authorizationQueryBo);

    /**
     * 删除已经标记删除的条目权限数据
     *
     * @param authorizationQueryBo
     */
    void deleteChange(@Param("authorizationQueryBo") AuthorizationQueryBo authorizationQueryBo);
}

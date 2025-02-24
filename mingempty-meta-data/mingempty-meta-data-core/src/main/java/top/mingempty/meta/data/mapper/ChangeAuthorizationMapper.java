package top.mingempty.meta.data.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mingempty.meta.data.model.po.ChangeAuthorizationPo;

/**
 * 条目授权变化流水表 映射层。
 *
 * @author zzhao
 * @since 2025-03-19 23:49:55
 */
@Mapper
public interface ChangeAuthorizationMapper extends BaseMapper<ChangeAuthorizationPo> {

}

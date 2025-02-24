package top.mingempty.meta.data.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mingempty.meta.data.model.po.ExtraFieldPo;

/**
 * 字典扩展字段信息表 映射层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Mapper
public interface ExtraFieldMapper extends BaseMapper<ExtraFieldPo> {

}

package top.mingempty.meta.data.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mingempty.meta.data.model.po.ItemPo;

/**
 * 字典项表 映射层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Mapper
public interface ItemMapper extends BaseMapper<ItemPo> {

}

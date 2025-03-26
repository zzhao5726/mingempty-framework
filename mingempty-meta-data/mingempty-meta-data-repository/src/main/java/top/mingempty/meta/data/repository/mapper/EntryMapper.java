package top.mingempty.meta.data.repository.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mingempty.meta.data.repository.model.po.EntryPo;

/**
 * 字典条目表 映射层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Mapper
public interface EntryMapper extends BaseMapper<EntryPo> {

}

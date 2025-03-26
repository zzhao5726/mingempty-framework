package top.mingempty.meta.data.repository.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.repository.model.po.ChangeItemPo;

import java.util.List;

/**
 * 字典项变化流水表 映射层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Mapper
public interface ChangeItemMapper extends BaseMapper<ChangeItemPo> {

    long queryCount(@Param("itemQuery") ItemQuery itemQuery);

    List<ChangeItemPo> query(@Param("itemQuery") ItemQuery itemQuery,
                             @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);
}

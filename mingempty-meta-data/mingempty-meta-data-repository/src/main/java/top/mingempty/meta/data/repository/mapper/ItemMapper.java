package top.mingempty.meta.data.repository.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.repository.model.po.ItemPo;

import java.util.List;

/**
 * 字典项表 映射层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Mapper
public interface ItemMapper extends BaseMapper<ItemPo> {

    void transferChange(@Param("itemQuery") ItemQuery itemQuery);

    long queryCount(@Param("itemQuery") ItemQuery itemQuery);

    List<ItemPo> query(@Param("itemQuery") ItemQuery itemQuery,
                       @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);
}

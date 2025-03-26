package top.mingempty.meta.data.repository.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;
import top.mingempty.meta.data.repository.model.po.ChangeEntryPo;

import java.util.List;

/**
 * 字典条目变化流水表 映射层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Mapper
public interface ChangeEntryMapper extends BaseMapper<ChangeEntryPo> {

    long queryCount(@Param("entryQuery") EntryQuery entryQuery);

    List<ChangeEntryPo> query(@Param("entryQuery") EntryQuery entryQuery,
                              @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

}

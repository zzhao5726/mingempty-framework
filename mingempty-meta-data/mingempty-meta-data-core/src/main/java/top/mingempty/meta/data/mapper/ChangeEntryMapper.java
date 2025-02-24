package top.mingempty.meta.data.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.model.bo.EntryQueryBo;
import top.mingempty.meta.data.model.po.ChangeEntryPo;

import java.util.List;

/**
 * 字典条目变化流水表 映射层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Mapper
public interface ChangeEntryMapper extends BaseMapper<ChangeEntryPo> {

    long queryCount(@Param("entryQueryBo") EntryQueryBo entryQueryBo);

    List<ChangeEntryPo> queryLimit(@Param("entryQueryBo") EntryQueryBo entryQueryBo,
                              @Param("startIndex") long startIndex, @Param("pageSize") long pageSize);

    List<ChangeEntryPo> query(@Param("entryQueryBo") EntryQueryBo entryQueryBo);
}

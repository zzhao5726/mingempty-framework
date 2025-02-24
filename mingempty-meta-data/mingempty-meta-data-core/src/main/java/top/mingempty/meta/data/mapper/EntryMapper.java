package top.mingempty.meta.data.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.model.bo.EntryQueryBo;
import top.mingempty.meta.data.model.po.EntryPo;

import java.util.List;

/**
 * 字典条目表 映射层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Mapper
public interface EntryMapper extends BaseMapper<EntryPo> {

    /**
     * 迁移条目数据
     *
     * @param entryQueryBo
     */
    void transferChange(@Param("entryQueryBo") EntryQueryBo entryQueryBo);

    long queryCount(@Param("entryQueryBo") EntryQueryBo entryQueryBo);

    List<EntryPo> queryLimit(@Param("entryQueryBo") EntryQueryBo entryQueryBo,
                                   @Param("startIndex") long startIndex, @Param("pageSize") long pageSize);

    List<EntryPo> query(@Param("entryQueryBo") EntryQueryBo entryQueryBo);
}

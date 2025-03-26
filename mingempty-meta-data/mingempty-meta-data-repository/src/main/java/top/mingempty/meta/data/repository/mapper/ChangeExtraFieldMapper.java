package top.mingempty.meta.data.repository.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.domain.biz.dict.query.ExtraFieldQuery;
import top.mingempty.meta.data.repository.model.po.ChangeExtraFieldPo;

import java.util.List;

/**
 * 字典扩展字段信息变化流水表 映射层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Mapper
public interface ChangeExtraFieldMapper extends BaseMapper<ChangeExtraFieldPo> {

    long queryCount(@Param("extraFieldQuery") ExtraFieldQuery extraFieldQuery);

    List<ChangeExtraFieldPo> query(@Param("extraFieldQuery") ExtraFieldQuery extraFieldQuery,
                                      @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

}

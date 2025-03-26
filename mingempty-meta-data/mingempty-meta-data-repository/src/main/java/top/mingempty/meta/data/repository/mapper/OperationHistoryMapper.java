package top.mingempty.meta.data.repository.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.meta.data.domain.biz.dict.query.OperationHistoryQuery;
import top.mingempty.meta.data.repository.model.po.OperationHistoryPo;

import java.util.List;

/**
 * 字典操作历史表 映射层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Mapper
public interface OperationHistoryMapper extends BaseMapper<OperationHistoryPo> {

    /**
     * 获取最大版本号
     *
     * @param entryCode 条目编码
     * @return
     */
    Long gainMaxVersion(String entryCode);


    long queryCount(@Param("operationHistoryQuery") OperationHistoryQuery operationHistoryQuery);

    List<OperationHistoryPo> query(@Param("operationHistoryQuery") OperationHistoryQuery operationHistoryQuery,
                                   @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);
}

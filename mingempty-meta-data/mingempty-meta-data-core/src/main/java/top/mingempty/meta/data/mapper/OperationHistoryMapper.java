package top.mingempty.meta.data.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mingempty.meta.data.model.po.OperationHistoryPo;

/**
 * 字典操作历史表 映射层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Mapper
public interface OperationHistoryMapper extends BaseMapper<OperationHistoryPo> {

    /**
     * 获取当前最高版本
     *
     * @param entryCode
     * @return
     */
    Long gainMaxVersion(String entryCode);
}

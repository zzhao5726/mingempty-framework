package top.mingempty.meta.data.domain.biz.dict.query;

import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 查询基础字段
 *
 * @author zzhao
 */
public interface BaseQuery<T extends BaseQuery<T>> {

    T setEntryCode(String entryCode);

    T setEntryCodeLike(String entryCodeLike);

    T setEntryCodes(Collection<String> entryCodes);

    T setEntryVersion(Long entryVersion);

    T setDeleteStatus(ZeroOrOneEnum deleteStatus);

    T setDeleteTime(LocalDateTime deleteTime);

    T setDeleteOperator(String deleteOperator);
}

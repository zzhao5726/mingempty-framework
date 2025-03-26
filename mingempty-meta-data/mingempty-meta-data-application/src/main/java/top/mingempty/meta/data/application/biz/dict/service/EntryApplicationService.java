package top.mingempty.meta.data.application.biz.dict.service;

import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryBaseInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.EntryBaseModifiesInfo;

import java.util.List;

/**
 * 字典条目应用层服务接口
 *
 * @author zzhao
 */
public interface EntryApplicationService {

    void modifies(EntryBaseModifiesInfo entryBaseModifiesInfo);

    List<EntryInfo> list(EntryQuery entryQuery, final MePage mePage);

    EntryBaseInfo details(EntryQuery entryQuery);
}

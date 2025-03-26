package top.mingempty.meta.data.application.biz.dict.service;

import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.domain.biz.dict.service.info.ItemInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemBaseModifiesInfo;

import java.util.List;

/**
 * 字典项应用层服务接口
 *
 * @author zzhao
 */
public interface ItemApplicationService {

    void modifies(ItemBaseModifiesInfo itemBaseModifiesInfo);

    List<ItemInfo> list(ItemQuery itemQuery, final MePage mePage);
}

package top.mingempty.meta.data.repository.impl.dict;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.model.ItemModel;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.ItemRepository;
import top.mingempty.meta.data.repository.impl.dict.converter.ItemRepositoryConverter;
import top.mingempty.meta.data.repository.model.po.ChangeItemPo;
import top.mingempty.meta.data.repository.service.ChangeItemService;
import top.mingempty.meta.data.repository.service.ItemService;

import java.util.List;
import java.util.Optional;

/**
 * 字典项仓储接口实现
 *
 * @author zzhao
 */
@Slf4j
@Component
@AllArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final ItemService itemService;

    private final ChangeItemService changeItemService;

    @Override
    public void modifies(ItemModel item) {
        if (ObjUtil.isEmpty(item)
                || StrUtil.isEmpty(item.getEntryCode())
                || CollUtil.isEmpty(item.getItemsByChange())) {
            return;
        }
        List<ChangeItemPo> changeItemPos = ItemRepositoryConverter.modelToChangePos(item);
        changeItemService.saveBatch(changeItemPos);
        itemService.transferChange(item.getEntryCode());
    }

    @Override
    public List<ItemModel> query(ItemQuery itemQuery, final MePage mePage) {
        if (ObjUtil.isEmpty(itemQuery)) {
            return List.of();
        }
        return Optional.ofNullable(itemQuery.getEntryVersion())
                .map(query -> changeItemService.query(itemQuery, mePage))
                .map(ItemRepositoryConverter::changePoToModels)
                .orElseGet(() -> ItemRepositoryConverter.poToModels(itemService.query(itemQuery, mePage)));
    }


}

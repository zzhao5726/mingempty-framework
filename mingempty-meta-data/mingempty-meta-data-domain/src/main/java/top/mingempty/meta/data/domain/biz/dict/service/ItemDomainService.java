package top.mingempty.meta.data.domain.biz.dict.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.model.ItemModel;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.ItemRepository;
import top.mingempty.meta.data.domain.biz.dict.service.converter.ItemDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.info.ItemInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemModifiesInfo;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 字典项领域服务
 */
@Slf4j
@Service
@AllArgsConstructor
public class ItemDomainService implements BaseDomainService<ItemInfo, ItemQuery> {

    private final ItemRepository itemRepository;

    /**
     * 修改字典扩展字段信息
     *
     * @param entryCode          条目编码
     * @param entryChangeVertion 当前修改版本
     * @param items              字典项信息变更改内部传输对象集合
     * @return
     */
    public void modifies(String entryCode, Long entryChangeVertion,
                         Collection<ItemModifiesInfo> items) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(entryChangeVertion)) {
            throw new MetaDataException("meta-data-domain-0000000008");
        }
        ItemModel itemModel = Optional.ofNullable(itemRepository.query(entryCode))
                .orElse(new ItemModel(entryCode, List.of()));
        if (CollUtil.isEmpty(items)) {
            return;
        }
        ItemModel itemModelByClone = ItemModel.cloneOne(entryCode, itemModel);
        itemModelByClone.setEntryVersion(entryChangeVertion);
        items.parallelStream()
                .map(ItemDomainConverter::modifiesInfoToVo)
                .filter(Objects::nonNull)
                .forEach(itemVo -> itemModelByClone.modifies(itemVo, itemVo.deleteStatus()));
        itemRepository.modifies(itemModelByClone);
    }

    /**
     * 修改字典扩展字段信息
     *
     * @param entryCode          条目编码
     * @param entryChangeVertion 当前修改版本
     * @param itemModifiesInfos  字典项信息变更改内部传输对象集合
     * @return
     */
    public void modifiesAll(String entryCode, Long entryChangeVertion,
                            Collection<ItemModifiesInfo> itemModifiesInfos) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(entryChangeVertion)) {
            throw new MetaDataException("meta-data-domain-0000000009");
        }
        ItemModel itemModelByClone = Optional.ofNullable(itemRepository.query(entryCode))
                .map(ItemModel::cloneOne)
                .orElse(new ItemModel(entryCode, List.of()));
        itemModelByClone.setEntryVersion(entryChangeVertion);
        itemModelByClone.modifies(ItemDomainConverter.modifiesInfoToVos(itemModifiesInfos));
        itemRepository.modifies(itemModelByClone);
    }

    @Override
    public List<ItemInfo> query(ItemQuery itemQuery, final MePage page) {
        List<ItemModel> itemModels = itemRepository.query(itemQuery, page);
        return ItemDomainConverter.modelToInfos(itemModels);
    }

    @Override
    public ItemQuery gainQuery(String entryCode) {
        return ItemQuery.builder().entryCode(entryCode).build();
    }

    @Override
    public ItemQuery gainQuery(Collection<String> entryCodes) {
        return ItemQuery.builder().entryCodes(entryCodes).build();
    }
}

package top.mingempty.meta.data.domain.biz.dict.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.domain.enums.BaseMetaData;
import top.mingempty.domain.enums.BuiltInDictEntryEnum;
import top.mingempty.event.record.publisher.MeRecordEventPublisher;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.event.EntryModifiesRecordApplicationEvent;
import top.mingempty.meta.data.domain.biz.dict.model.AuthorizationModel;
import top.mingempty.meta.data.domain.biz.dict.model.EntryModel;
import top.mingempty.meta.data.domain.biz.dict.model.ExtraFieldModel;
import top.mingempty.meta.data.domain.biz.dict.model.ItemModel;
import top.mingempty.meta.data.domain.biz.dict.model.LabelModel;
import top.mingempty.meta.data.domain.biz.dict.model.OperationHistoryModel;
import top.mingempty.meta.data.domain.biz.dict.query.AuthorizationQuery;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;
import top.mingempty.meta.data.domain.biz.dict.query.ExtraFieldQuery;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.domain.biz.dict.query.LabelQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.AuthorizationRepository;
import top.mingempty.meta.data.domain.biz.dict.repository.EntryRepository;
import top.mingempty.meta.data.domain.biz.dict.repository.ExtraFieldRepository;
import top.mingempty.meta.data.domain.biz.dict.repository.ItemRepository;
import top.mingempty.meta.data.domain.biz.dict.repository.LabelRepository;
import top.mingempty.meta.data.domain.biz.dict.repository.OperationHistoryRepository;
import top.mingempty.meta.data.domain.biz.dict.service.converter.AuthorizationDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.converter.EntryDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.converter.ExtraFieldDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.converter.ItemDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.converter.LabelDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.converter.OperationHistoryDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.info.AuthorizationInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryAllInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryBaseInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.ExtraFieldInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.ItemInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.LabelInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.OperationHistoryInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.EntryModifiesInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 字典领域服务
 */
@Slf4j
@Service
@AllArgsConstructor
public class EntryDomainService implements BaseDomainService<EntryInfo, EntryQuery> {

    private final EntryRepository entryRepository;

    @Lazy
    @Resource
    private AuthorizationRepository authorizationRepository;

    @Lazy
    @Resource
    private ExtraFieldRepository extraFieldRepository;

    @Lazy
    @Resource
    private ItemRepository itemRepository;

    @Lazy
    @Resource
    private LabelRepository labelRepository;

    @Lazy
    @Resource
    private OperationHistoryRepository operationHistoryRepository;

    /**
     * 条目创建或修改领域服务
     *
     * @param entryCode          条目编码
     * @param entryChangeVertion 当前修改版本
     * @param entryModifiesInfo  条目信息变更改内部传输对象
     */
    public void modifies(String entryCode, Long entryChangeVertion, EntryModifiesInfo entryModifiesInfo) {
        if (ObjUtil.isEmpty(entryModifiesInfo)
                || StrUtil.isEmpty(entryCode)) {
            throw new MetaDataException("meta-data-domain-0000000001");
        }
        //内部条目禁止修改
        Map<String, BuiltInDictEntryEnum> builtInDictEntryEnumMap
                = BaseMetaData.EnumHelper.INSTANCE.getMap(BuiltInDictEntryEnum.class);
        if (builtInDictEntryEnumMap.containsKey(entryCode)) {
            throw new MetaDataException("meta-data-domain-0000000010");
        }

        EntryModel entryModelByOld = entryRepository.query(entryCode);
        EntryModel entryModelByNew = EntryDomainConverter.infoToModel(entryCode, entryChangeVertion, entryModifiesInfo);

        //说明完全相同，不需要在保存了
        if (ObjUtil.isNotEmpty(entryModelByOld)
                && entryModelByOld.checkSame(entryModelByNew)) {
            return;
        }
        EntryModel entryModel = Optional.ofNullable(entryModelByOld)
                .map(entryModel1 -> EntryDomainConverter.modifies(entryModel1, entryModelByNew))
                .orElse(entryModelByNew);
        entryRepository.modifies(entryModel);
        MeRecordEventPublisher.publishEventStatic(
                new EntryModifiesRecordApplicationEvent(EntryDomainConverter.modelToEventInfo(entryModel)));
    }


    @Override
    public List<EntryInfo> query(EntryQuery entryQuery, final MePage page) {
        List<EntryModel> entryModels = entryRepository.query(entryQuery, page);
        return EntryDomainConverter.modelToInfos(entryModels);
    }

    @Override
    public EntryQuery gainQuery(String entryCode) {
        return EntryQuery.builder().entryCode(entryCode).build();
    }

    @Override
    public EntryQuery gainQuery(Collection<String> entryCodes) {
        return EntryQuery.builder().entryCodes(entryCodes).build();
    }


    /**
     * 通过条目编码查询条目基础数据
     *
     * @param entryCode 字典条目编码
     * @return
     */
    public EntryBaseInfo queryBase(String entryCode) {
        if (ObjUtil.isEmpty(entryCode)) {
            return null;
        }

        List<EntryBaseInfo> entryBaseInfos = Optional.ofNullable(queryBase(List.of(entryCode)))
                .orElse(List.of());
        if (entryBaseInfos.isEmpty()) {
            return null;
        }
        return entryBaseInfos.getFirst();
    }


    /**
     * 通过条目编码查询条目基础数据集合
     *
     * @param entryCodes 字典条目编码集合
     * @return
     */
    public List<EntryBaseInfo> queryBase(Collection<String> entryCodes) {
        return queryBase(EntryQuery.builder().entryCodes(entryCodes).build());
    }

    /**
     * 条目基础数据查询
     *
     * @param entryQuery 条目查询对象
     * @return
     */
    public List<EntryBaseInfo> queryBase(EntryQuery entryQuery) {
        return queryBase(entryQuery, null);
    }

    /**
     * 条目基础数据查询
     *
     * @param entryQuery 条目查询对象
     * @param mePage     分页参数对象
     * @return
     */
    public List<EntryBaseInfo> queryBase(EntryQuery entryQuery, final MePage mePage) {
        if (ObjUtil.isEmpty(entryQuery)) {
            return List.of();
        }
        List<EntryInfo> entryModels = query(entryQuery, mePage);
        if (CollUtil.isEmpty(entryModels)) {
            return List.of();
        }
        Map<String, EntryBaseInfo> entryBaseInfoMap = entryModels.parallelStream()
                .map(entryModel -> EntryBaseInfo.builder()
                        .entryCode(entryModel.getEntryCode()).entry(entryModel)
                        .build())
                .collect(Collectors.toMap(EntryBaseInfo::getEntryCode, Function.identity()));

        //查询字典权限信息
        List<AuthorizationModel> authorizationModels = authorizationRepository.query(AuthorizationQuery.builder()
                .entryCodes(entryBaseInfoMap.keySet())
                .entryVersion(entryQuery.getEntryVersion())
                .build());
        Map<String, List<AuthorizationInfo>> authorizationInfosMap = Optional.ofNullable(authorizationModels)
                .orElse(List.of())
                .parallelStream()
                .map(AuthorizationDomainConverter::modelToInfos)
                .flatMap(Collection::parallelStream)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(AuthorizationInfo::getEntryCode));

        //查询字典扩展字段信息
        List<ExtraFieldModel> extraFieldModels = extraFieldRepository.query(ExtraFieldQuery.builder()
                .entryCodes(entryBaseInfoMap.keySet())
                .entryVersion(entryQuery.getEntryVersion())
                .build());
        Map<String, List<ExtraFieldInfo>> extraFieldInfosMap = Optional.ofNullable(extraFieldModels)
                .stream()
                .flatMap(Collection::parallelStream)
                .map(ExtraFieldDomainConverter::modelToInfos)
                .flatMap(Collection::parallelStream)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(ExtraFieldInfo::getEntryCode));

        //查询字典历史记录
        List<OperationHistoryModel> operationHistoryModels = operationHistoryRepository.query(entryBaseInfoMap.keySet());
        Map<String, List<OperationHistoryInfo>> operationHistoryInfosMap = Optional.ofNullable(operationHistoryModels)
                .stream()
                .flatMap(Collection::parallelStream)
                .map(OperationHistoryDomainConverter::modelToInfos)
                .flatMap(Collection::parallelStream)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(OperationHistoryInfo::getEntryCode));
        return entryBaseInfoMap
                .entrySet()
                .parallelStream()
                .peek(entry -> entry.getValue()
                        .setAuthorizations(authorizationInfosMap.getOrDefault(entry.getKey(), List.of()))
                        .setExtraFields(extraFieldInfosMap.getOrDefault(entry.getKey(), List.of()))
                        .setOperationHistorys(operationHistoryInfosMap.getOrDefault(entry.getKey(), List.of())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }


    /**
     * 通过条目编码查询条目全量数据
     *
     * @param entryCode 字典条目编码
     * @return
     */
    public EntryAllInfo queryAll(String entryCode) {
        if (ObjUtil.isEmpty(entryCode)) {
            return null;
        }

        List<EntryAllInfo> entryAllInfos = Optional.ofNullable(queryAll(List.of(entryCode)))
                .orElse(List.of());
        if (entryAllInfos.isEmpty()) {
            return null;
        }
        return entryAllInfos.getFirst();
    }


    /**
     * 通过条目编码查询条目全量数据集合
     *
     * @param entryCodes 字典条目编码集合
     * @return
     */
    public List<EntryAllInfo> queryAll(Collection<String> entryCodes) {
        return queryAll(EntryQuery.builder().entryCodes(entryCodes).build(), null);
    }

    /**
     * 条目数据查询
     *
     * @param entryQuery 条目查询对象
     * @return
     */
    public List<EntryAllInfo> queryAll(EntryQuery entryQuery) {
        return queryAll(entryQuery, null);
    }

    /**
     * 条目全量数据查询
     *
     * @param entryQuery 条目查询对象
     * @param mePage     分页参数对象
     * @return
     */
    public List<EntryAllInfo> queryAll(EntryQuery entryQuery, final MePage mePage) {
        if (ObjUtil.isEmpty(entryQuery)) {
            return List.of();
        }
        List<EntryBaseInfo> entryBaseInfos = queryBase(entryQuery, mePage);
        if (CollUtil.isEmpty(entryBaseInfos)) {
            return List.of();
        }
        Map<String, EntryAllInfo> entryAllModelMap = entryBaseInfos.parallelStream()
                .map(EntryBaseInfo::toEntryAllInfo)
                .collect(Collectors.toMap(EntryAllInfo::getEntryCode, Function.identity()));

        //查询字典项信息
        List<ItemModel> itemModels = itemRepository.query(ItemQuery.builder()
                .entryCodes(entryAllModelMap.keySet())
                .entryVersion(entryQuery.getEntryVersion())
                .build());
        Map<String, List<ItemInfo>> itemModelsMap = Optional.ofNullable(itemModels)
                .stream()
                .flatMap(Collection::parallelStream)
                .map(ItemDomainConverter::modelToInfos)
                .flatMap(Collection::parallelStream)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(ItemInfo::getEntryCode));

        //查询字典项标签信息
        List<LabelModel> labelModels = labelRepository.query(LabelQuery.builder()
                .entryCodes(entryAllModelMap.keySet())
                .entryVersion(entryQuery.getEntryVersion())
                .build());
        Map<String, List<LabelInfo>> labelModelsMap = Optional.ofNullable(labelModels)
                .stream()
                .flatMap(Collection::parallelStream)
                .map(LabelDomainConverter::modelToInfos)
                .flatMap(Collection::parallelStream)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(LabelInfo::getEntryCode));

        return entryAllModelMap
                .entrySet()
                .parallelStream()
                .peek(entry -> entry.getValue()
                        .setItems(itemModelsMap.getOrDefault(entry.getKey(), List.of()))
                        .setLabels(labelModelsMap.getOrDefault(entry.getKey(), List.of())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}

package top.mingempty.meta.data.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.mapper.EntryMapper;
import top.mingempty.meta.data.mapstruct.EntryMapstruct;
import top.mingempty.meta.data.model.bo.EntryBo;
import top.mingempty.meta.data.model.bo.EntryQueryBo;
import top.mingempty.meta.data.model.enums.DictOperationEnum;
import top.mingempty.meta.data.model.mq.EntryChange;
import top.mingempty.meta.data.model.po.ChangeEntryPo;
import top.mingempty.meta.data.model.po.EntryPo;
import top.mingempty.meta.data.model.vo.in.EntryCreateVo;
import top.mingempty.meta.data.model.vo.in.EntryQueryVo;
import top.mingempty.meta.data.model.vo.in.EntryStatusChangeVo;
import top.mingempty.meta.data.model.vo.in.EntryUpdateVo;
import top.mingempty.meta.data.model.vo.out.EntryBaseResultVo;
import top.mingempty.meta.data.service.AuthorizationService;
import top.mingempty.meta.data.service.ChangeEntryService;
import top.mingempty.meta.data.service.EntryService;
import top.mingempty.meta.data.service.ExtraFieldService;
import top.mingempty.meta.data.service.OperationHistoryService;
import top.mingempty.meta.data.util.TransactionUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 字典条目表 服务层实现。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Service
public class EntryServiceImpl extends ServiceImpl<EntryMapper, EntryPo> implements EntryService {

    @Autowired
    private ChangeEntryService changeEntryService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ExtraFieldService extraFieldService;

    @Autowired
    private OperationHistoryService operationHistoryService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(EntryCreateVo entryCreateVo) {
        if (ObjUtil.isEmpty(entryCreateVo)) {
            return;
        }
        create(List.of(entryCreateVo));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(List<EntryCreateVo> entryCreateVos) {
        if (CollUtil.isEmpty(entryCreateVos)) {
            return;
        }
        Collection<String> entryCodes = entryCreateVos.parallelStream()
                .map(EntryCreateVo::getEntryCode)
                .collect(Collectors.toCollection(Set::of));

        EntryQueryBo entryQueryBo = new EntryQueryBo();
        entryQueryBo.setEntryCodes(entryCodes);
        List<EntryBo> entryBos = queryNew(entryQueryBo);
        Map<String, EntryBo> entryBoMap = entryBos.parallelStream()
                .collect(Collectors.toMap(EntryBo::getEntryCode, Function.identity(), (v1, v2) -> v2));


        //存储当前变更版本信息
        Map<String, Long> entryVersionMap = new ConcurrentHashMap<>(entryCreateVos.size());
        EntryMapstruct entryMapstruct = EntryMapstruct.INSTANCE;
        List<ChangeEntryPo> changeEntryPos = entryCreateVos
                .parallelStream()
                .peek(this::createCheck)
                .map(entryMapstruct::createVoToChangePo)
                .peek(changeEntryPo
                        -> changeEntryPo.setEntryVersion(entryVersionMap
                        .computeIfAbsent(changeEntryPo.getEntryCode(),
                                entryCode -> operationHistoryService.gainVersion(entryCode))))
                .filter(changeEntryPo -> checkNoChange(changeEntryPo, entryBoMap.get(changeEntryPo.getEntryCode())))
                .collect(Collectors.toList());

        // 保存变更记录
        changeEntryService.saveBatch(changeEntryPos);

        //处理权限信息
        authorizationService.create(entryCreateVos, entryVersionMap);

        //处理扩展字段信息
        extraFieldService.create(entryCreateVos, entryVersionMap);

        record(entryVersionMap, DictOperationEnum.code_01);
    }

    private boolean checkNoChange(ChangeEntryPo changeEntryPo, EntryBo entryBo) {
        if (ObjUtil.isEmpty(entryBo)) {
            return true;
        }
        if (ObjUtil.notEqual(changeEntryPo.getEntryName(), entryBo.getEntryName())) {
            return true;
        }
        if (ObjUtil.notEqual(changeEntryPo.getEntryType(), entryBo.getEntryType())) {
            return true;
        }

        if (ObjUtil.notEqual(changeEntryPo.getEntrySharding(), entryBo.getEntrySharding())) {
            return true;
        }

        if (ObjUtil.notEqual(changeEntryPo.getDeleteStatus(), entryBo.getDeleteStatus())) {
            return true;
        }

        if (ObjUtil.notEqual(changeEntryPo.getSort(), entryBo.getSort())) {
            return true;
        }
        return false;
    }


    /**
     * 码值新增时校验
     *
     * @param entryCreateVo
     */
    private void createCheck(EntryCreateVo entryCreateVo) {
        //关键字段非空校验

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transferChange(String entryCode) {
        if (ObjUtil.isEmpty(entryCode)) {
            return;
        }
        transferChange(List.of(entryCode));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transferChange(Collection<String> entryCodes) {
        EntryQueryBo entryQueryBo = new EntryQueryBo();
        entryQueryBo.setEntryCodes(entryCodes);
        this.mapper.transferChange(entryQueryBo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void change(EntryUpdateVo entryUpdateVo) {
        change(List.of(entryUpdateVo));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void change(List<EntryUpdateVo> entryUpdateVos) {
        if (CollUtil.isEmpty(entryUpdateVos)) {
            return;
        }
        Map<String, EntryUpdateVo> entryUpdateVoMap = entryUpdateVos.parallelStream()
                .collect(Collectors.toMap(EntryUpdateVo::getEntryCode, Function.identity(), (v1, v2) -> v2));
        List<EntryBo> changeEntryBos = changeEntryService.queryNew(entryUpdateVoMap.keySet());
        List<ChangeEntryPo> changeEntryPos = changeEntryBos
                .parallelStream()
                .peek(changeChangeEntryBo
                        -> EntryMapstruct.INSTANCE.updateBoFromVo(entryUpdateVoMap.get(changeChangeEntryBo.getEntryCode()), changeChangeEntryBo))
                .map(EntryMapstruct.INSTANCE::changeBoToPo)
                .peek(changeEntryPo -> {
                    changeEntryPo.setEntryVersion(operationHistoryService.gainVersion(changeEntryPo.getEntryCode()));
                    changeEntryPo.setCreateTime(LocalDateTime.now());
                    changeEntryPo.setUpdateTime(LocalDateTime.now());
                })
                .collect(Collectors.toList());
        changeEntryService.saveBatch(changeEntryPos);
        Map<String, Long> entryVersionMap = changeEntryPos
                .parallelStream()
                .collect(Collectors.toMap(ChangeEntryPo::getEntryCode, ChangeEntryPo::getEntryVersion));
        record(entryVersionMap, DictOperationEnum.code_02);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void statusChange(EntryStatusChangeVo entryStatusChangeVo) {
        List<EntryBo> changeEntryBos = changeEntryService.queryNew(entryStatusChangeVo.getEntryCodes());
        List<ChangeEntryPo> changeEntryPos = changeEntryBos
                .parallelStream()
                .peek(changeChangeEntryBo -> changeChangeEntryBo.setDeleteStatus(entryStatusChangeVo.getDeleteStatus()))
                .map(EntryMapstruct.INSTANCE::changeBoToPo)
                .peek(changeEntryPo -> {
                    changeEntryPo.setEntryVersion(operationHistoryService.gainVersion(changeEntryPo.getEntryCode()));
                    changeEntryPo.setCreateTime(LocalDateTime.now());
                    changeEntryPo.setUpdateTime(LocalDateTime.now());
                })
                .collect(Collectors.toList());
        changeEntryService.saveBatch(changeEntryPos);
        Map<String, Long> entryVersionMap = changeEntryPos
                .parallelStream()
                .collect(Collectors.toMap(ChangeEntryPo::getEntryCode, ChangeEntryPo::getEntryVersion));
        record(entryVersionMap, DictOperationEnum.code_03);
    }

    private void record(Map<String, Long> entryVersionMap, DictOperationEnum dictOperationType) {
        transferChange(entryVersionMap.keySet());
        operationHistoryService.record(entryVersionMap, dictOperationType);
        EntryChange entryChange = new EntryChange();
        entryChange.setEntryCodes(entryVersionMap.keySet());
        entryChange.setDictOperationType(dictOperationType);
        TransactionUtil.registerSynchronization(entryChange);
    }

    @Override
    public List<EntryBaseResultVo> list(EntryQueryVo entryQueryVo, final MePage mePage) {
        return EntryMapstruct.INSTANCE.queryBoToResultVo(this.query(EntryMapstruct.INSTANCE.queryVoToBo(entryQueryVo), mePage));
    }

    @Override
    public List<EntryBo> query(EntryQueryBo entryQueryBo, final MePage mePage) {
        if (ObjUtil.isEmpty(entryQueryBo.getEntryVersion())) {
            //说明没有版本问题，直接查询最新的
            return this.queryNew(entryQueryBo, mePage);
        }
        return changeEntryService.query(entryQueryBo, mePage);
    }

    @Override
    public List<EntryBo> queryNew(EntryQueryBo entryQueryBo, final MePage mePage) {
        if (ObjUtil.isNotEmpty(mePage)) {
            if (mePage.isSearchCount()) {
                //查询数量
                mePage.setTotal(mapper.queryCount(entryQueryBo));
            }
            return EntryMapstruct.INSTANCE.poToBo(mapper.queryLimit(entryQueryBo, mePage.getStartIndex(), mePage.getPageSize()));
        }
        return EntryMapstruct.INSTANCE.poToBo(mapper.query(entryQueryBo));
    }

}

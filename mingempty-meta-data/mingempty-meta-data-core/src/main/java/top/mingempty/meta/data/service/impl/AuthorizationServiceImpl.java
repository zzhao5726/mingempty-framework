package top.mingempty.meta.data.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.domain.enums.BaseMetaData;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.mapper.AuthorizationMapper;
import top.mingempty.meta.data.model.MetaDataConstant;
import top.mingempty.meta.data.model.bo.AuthorizationQueryBo;
import top.mingempty.meta.data.model.enums.AuthorizationTypeEnum;
import top.mingempty.meta.data.model.enums.DictOperationEnum;
import top.mingempty.meta.data.model.exception.MetaDataException;
import top.mingempty.meta.data.model.mq.EntryChange;
import top.mingempty.meta.data.model.po.AuthorizationPo;
import top.mingempty.meta.data.model.po.ChangeAuthorizationPo;
import top.mingempty.meta.data.model.table.AuthorizationTableDef;
import top.mingempty.meta.data.model.vo.AuthorizationVo;
import top.mingempty.meta.data.model.vo.in.EntryCreateVo;
import top.mingempty.meta.data.service.AuthorizationService;
import top.mingempty.meta.data.service.ChangeAuthorizationService;
import top.mingempty.meta.data.service.OperationHistoryService;
import top.mingempty.meta.data.util.TransactionUtil;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.factory.SequenceFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 条目授权表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-19 23:49:55
 */
@Slf4j
@Service
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationMapper, AuthorizationPo> implements AuthorizationService {


    @Autowired
    private ChangeAuthorizationService changeAuthorizationService;

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
    public void create(Collection<EntryCreateVo> entryCreateVos) {
        create(entryCreateVos, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(Collection<EntryCreateVo> entryCreateVos, Map<String, Long> entryVersionMap) {
        if (CollUtil.isEmpty(entryCreateVos)) {
            return;
        }

        Set<String> entryCodes = entryCreateVos.parallelStream()
                .map(EntryCreateVo::getEntryCode)
                .collect(Collectors.toSet());
        Map<String, Map<AuthorizationTypeEnum, Set<String>>> authorizationMap = quertAuthorization(entryCodes);

        Collection<AuthorizationVo> addAuthorizationVos = entryCreateVos.parallelStream()
                .filter(entryCreateVo -> entryCreateVo.getAuthorizationVo() != null)
                .peek(entryCreateVo -> entryCreateVo.getAuthorizationVo().setEntryCode(entryCreateVo.getEntryCode()))
                .map(entryCreateVo -> {
                    Map<AuthorizationTypeEnum, Set<String>> entryAuthorizationMap = authorizationMap.get(entryCreateVo.getEntryCode());
                    AuthorizationVo authorizationVo = entryCreateVo.getAuthorizationVo();
                    AuthorizationVo authorizationVoByNew = new AuthorizationVo();
                    authorizationVoByNew.setEntryCode(authorizationVo.getEntryCode());
                    if (MapUtil.isEmpty(entryAuthorizationMap)) {
                        authorizationVoByNew.setRoleAuthorizations(new ArrayList<>(authorizationVo.getRoleAuthorizations()));
                        authorizationVoByNew.setUserAuthorizations(new ArrayList<>(authorizationVo.getUserAuthorizations()));
                        return entryCreateVo.getAuthorizationVo();
                    }
                    authorizationVoByNew.setRoleAuthorizations(
                            CollUtil.subtract(entryAuthorizationMap.get(AuthorizationTypeEnum.ONE),
                                    authorizationVo.getRoleAuthorizations()));
                    authorizationVoByNew.setUserAuthorizations(
                            CollUtil.subtract(entryAuthorizationMap.get(AuthorizationTypeEnum.TWO),
                                    authorizationVo.getUserAuthorizations()));
                    return authorizationVoByNew;
                })
                .collect(Collectors.toList());

        Collection<AuthorizationVo> deleteAuthorizationVos = entryCreateVos.parallelStream()
                .filter(entryCreateVo -> entryCreateVo.getAuthorizationVo() != null)
                .peek(entryCreateVo -> entryCreateVo.getAuthorizationVo().setEntryCode(entryCreateVo.getEntryCode()))
                .map(entryCreateVo -> {
                    Map<AuthorizationTypeEnum, Set<String>> entryAuthorizationMap = authorizationMap.get(entryCreateVo.getEntryCode());
                    if (MapUtil.isEmpty(entryAuthorizationMap)) {
                        return null;
                    }
                    AuthorizationVo authorizationVo = entryCreateVo.getAuthorizationVo();
                    AuthorizationVo authorizationVoByNew = new AuthorizationVo();
                    authorizationVoByNew.setEntryCode(authorizationVo.getEntryCode());
                    authorizationVoByNew.setRoleAuthorizations(
                            CollUtil.subtract(authorizationVo.getRoleAuthorizations(),
                                    entryAuthorizationMap.get(AuthorizationTypeEnum.ONE)));
                    authorizationVoByNew.setUserAuthorizations(
                            CollUtil.subtract(authorizationVo.getUserAuthorizations(),
                                    entryAuthorizationMap.get(AuthorizationTypeEnum.TWO)));
                    return authorizationVoByNew;
                })
                .filter(ObjUtil::isNotEmpty)
                .collect(Collectors.toList());

        this.add(addAuthorizationVos, entryVersionMap, false);
        this.delete(deleteAuthorizationVos, entryVersionMap, false);
        Set<String> transferEntryCodes =
                addAuthorizationVos.parallelStream()
                        .map(AuthorizationVo::getEntryCode)
                        .collect(Collectors.toSet());
        transferEntryCodes.addAll(deleteAuthorizationVos.parallelStream().map(AuthorizationVo::getEntryCode).collect(Collectors.toSet()));
        this.transferChange(transferEntryCodes);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(AuthorizationVo authorizationVo) {
        add(authorizationVo, null);
    }

    @Override
    public void add(AuthorizationVo authorizationVo, Long entryVersion) {
        if (ObjUtil.isEmpty(authorizationVo)) {
            return;
        }
        add(List.of(authorizationVo), ObjUtil.isEmpty(entryVersion)
                ? null : new ConcurrentHashMap<>(Map.of(authorizationVo.getEntryCode(), entryVersion)), true);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Collection<AuthorizationVo> authorizationVos) {
        add(authorizationVos, null, true);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Collection<AuthorizationVo> authorizationVos, Map<String, Long> entryVersionMap, boolean newRecord) {
        processing_1(authorizationVos, entryVersionMap, newRecord, ZeroOrOneEnum.ZERO, DictOperationEnum.code_04);
    }

    @Override
    public void delete(AuthorizationVo authorizationVo) {
        delete(authorizationVo, null);
    }

    @Override
    public void delete(AuthorizationVo authorizationVo, Long entryVersion) {
        if (ObjUtil.isEmpty(authorizationVo)) {
            return;
        }
        delete(List.of(authorizationVo), ObjUtil.isEmpty(entryVersion)
                ? null : new ConcurrentHashMap<>(Map.of(authorizationVo.getEntryCode(), entryVersion)), true);
    }

    @Override
    public void delete(Collection<AuthorizationVo> authorizationVos) {
        add(authorizationVos, null, true);
    }

    @Override
    public void delete(Collection<AuthorizationVo> authorizationVos, Map<String, Long> entryVersionMap, boolean newRecord) {
        processing_1(authorizationVos, entryVersionMap, newRecord, ZeroOrOneEnum.ONE, DictOperationEnum.code_05);
    }

    private void processing_1(Collection<AuthorizationVo> authorizationVos, Map<String, Long> entryVersionMap,
                              boolean newRecord, ZeroOrOneEnum deleteStatus, DictOperationEnum dictOperationType) {
        if (CollUtil.isEmpty(authorizationVos)) {
            return;
        }
        AuthorizationResult authorizationResult = processing_2(authorizationVos, entryVersionMap, newRecord);
        List<ChangeAuthorizationPo> changeAuthorizationPos = authorizationVos.stream()
                .parallel()
                .flatMap(authorizationVo -> {
                    List<ChangeAuthorizationPo> authorizationPos = new ArrayList<>();
                    //处理角色
                    authorizationPos.addAll(processing_3(authorizationVo, AuthorizationTypeEnum.ONE,
                            authorizationResult, deleteStatus));
                    //处理用户
                    authorizationPos.addAll(processing_3(authorizationVo, AuthorizationTypeEnum.TWO,
                            authorizationResult, deleteStatus));
                    return authorizationPos.parallelStream();
                })
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(changeAuthorizationPos)) {
            return;
        }
        changeAuthorizationService.saveBatch(changeAuthorizationPos);
        if (newRecord) {
            record(authorizationResult.entryVersionMap(), dictOperationType);
        }
    }

    private AuthorizationResult processing_2(Collection<AuthorizationVo> authorizationVos,
                                             Map<String, Long> entryVersionMap,
                                             boolean newRecord) {
        List<String> entryCodes = authorizationVos.parallelStream()
                .map(AuthorizationVo::getEntryCode)
                .collect(Collectors.toList());
        if (newRecord) {
            //校验权限
            checkAuthorization(entryCodes);
        }
        //查询已有数据
        Map<String, Map<AuthorizationTypeEnum, Set<String>>> authorizationMap = quertAuthorization(entryCodes);
        Map<String, Long> finalEntryVersionMap = entryVersionMap == null
                ? new ConcurrentHashMap<>(authorizationVos.size()) : (entryVersionMap instanceof ConcurrentHashMap
                ? entryVersionMap : new ConcurrentHashMap<>(entryVersionMap));
        return new AuthorizationResult(authorizationMap, finalEntryVersionMap);
    }

    private record AuthorizationResult(Map<String, Map<AuthorizationTypeEnum, Set<String>>> authorizationMap,
                                       Map<String, Long> entryVersionMap) {
    }

    private List<ChangeAuthorizationPo> processing_3(AuthorizationVo authorizationVo,
                                                     AuthorizationTypeEnum authorizationType,
                                                     AuthorizationResult authorizationResult,
                                                     ZeroOrOneEnum deleteStatus) {
        Collection<String> authorizationCodes;
        if (AuthorizationTypeEnum.ONE.equals(authorizationType)) {
            authorizationCodes = authorizationVo.getRoleAuthorizations();
        } else {
            authorizationCodes = authorizationVo.getUserAuthorizations();
        }
        if (CollUtil.isEmpty(authorizationCodes)) {
            return List.of();
        }
        Map<AuthorizationTypeEnum, Set<String>> entryAuthorizationMap
                = Optional.ofNullable(authorizationResult.authorizationMap.get(authorizationVo.getEntryCode()))
                .orElse(new ConcurrentHashMap<>(0));
        return authorizationCodes
                .parallelStream()
                .filter(authorizationCode
                        -> ZeroOrOneEnum.ZERO.equals(deleteStatus)
                        == CollUtil.safeContains(entryAuthorizationMap.get(authorizationType), authorizationCode))
                .map(authorizationCode -> processing_4(authorizationResult.entryVersionMap,
                        authorizationVo.getEntryCode(),
                        authorizationType,
                        authorizationCode,
                        deleteStatus))
                .collect(Collectors.toList());
    }

    private ChangeAuthorizationPo processing_4(Map<String, Long> entryVersionMap,
                                               String entryCode,
                                               AuthorizationTypeEnum authorizationType,
                                               String authorizationCode,
                                               ZeroOrOneEnum deleteStatus) {
        ChangeAuthorizationPo changeAuthorizationPo = new ChangeAuthorizationPo();
        changeAuthorizationPo.setAuthorizationId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next());
        changeAuthorizationPo.setEntryVersion(entryVersionMap
                .computeIfAbsent(entryCode,
                        entryCode1 -> operationHistoryService.gainVersion(entryCode)));
        changeAuthorizationPo.setEntryCode(entryCode);
        changeAuthorizationPo.setAuthorizationType(authorizationType.getItemCode());
        changeAuthorizationPo.setAuthorizationCode(authorizationCode);
        changeAuthorizationPo.setDeleteStatus(deleteStatus.getItemCode());
        return changeAuthorizationPo;
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
        AuthorizationQueryBo authorizationQueryBo = new AuthorizationQueryBo();
        authorizationQueryBo.setEntryCodes(entryCodes);

        //删除已经标记删除的条目权限数据
        this.mapper.deleteChange(authorizationQueryBo);

        //迁移条目权限数据
        this.mapper.transferChange(authorizationQueryBo);
    }

    @Override
    public Map<AuthorizationTypeEnum, Set<String>> quertAuthorization(String entryCode) {
        if (ObjUtil.isEmpty(entryCode)) {
            return Map.of();
        }

        Map<String, Map<AuthorizationTypeEnum, Set<String>>> authorizationMap = quertAuthorization(List.of(entryCode));
        return Optional.ofNullable(authorizationMap.get(entryCode))
                .orElse(Map.of());
    }

    @Override
    public Map<String, Map<AuthorizationTypeEnum, Set<String>>> quertAuthorization(Collection<String> entryCodes) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in(AuthorizationPo::getEntryCode, entryCodes);
        queryWrapper.select(AuthorizationTableDef.AUTHORIZATION_PO.ENTRY_CODE,
                AuthorizationTableDef.AUTHORIZATION_PO.AUTHORIZATION_TYPE,
                AuthorizationTableDef.AUTHORIZATION_PO.AUTHORIZATION_CODE);
        List<AuthorizationPo> authorizationPosByOld = mapper.selectListByQuery(queryWrapper);
        return authorizationPosByOld
                .parallelStream()
                .collect(Collectors.groupingBy(
                        AuthorizationPo::getEntryCode,
                        Collectors.groupingBy(
                                authorizationPo
                                        -> BaseMetaData.EnumHelper.INSTANCE
                                        .findOptional(AuthorizationTypeEnum.class, authorizationPo.getAuthorizationType())
                                        .orElse(AuthorizationTypeEnum.ONE),
                                Collectors.mapping(AuthorizationPo::getAuthorizationCode, Collectors.toSet()))
                ));
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkAuthorization(String entryCode) {
        if (ObjUtil.isEmpty(entryCode)) {
            return;
        }
        checkAuthorization(List.of(entryCode));
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkAuthorization(Collection<String> entryCodes) {
        if (CollUtil.isEmpty(entryCodes)) {
            return;
        }

        // TODO 获取当前用户的角色
        List<String> userRoleCodes = new ArrayList<>();
        userRoleCodes.add("dict_test");

        //TODO 获取当前用户
        String userCode = "mingempty";

        //先判断是否是管理员或码值管理员
        Collection<String> adminAuthorization
                = CollUtil.intersection(userRoleCodes, MetaDataConstant.DEFAUT_AUTHORIZATION_ROLE);

        if (CollUtil.containsAny(userRoleCodes, adminAuthorization)) {
            return;
        }

        Map<String, Map<AuthorizationTypeEnum, Set<String>>> authorizationMap = quertAuthorization(entryCodes);

        //找到未配置过权限的条目
        Collection<String> subtractEntryCodes = CollUtil.subtract(entryCodes, authorizationMap.keySet());
        if (CollUtil.isNotEmpty(subtractEntryCodes)) {
            log.warn("权限校验时，以下条目未配置权限。\n[{}]",
                    subtractEntryCodes.parallelStream().collect(Collectors.joining("\n")));
            throw new MetaDataException("meta-data-core-0000000004", subtractEntryCodes.stream().findFirst().get());
        }

        authorizationMap.entrySet()
                .parallelStream()
                .forEach(entry -> {
                    //判断用户
                    Set<String> userAuthorizationCode = entry.getValue().get(AuthorizationTypeEnum.TWO);
                    if (CollUtil.contains(userAuthorizationCode, userCode)) {
                        return;
                    }
                    //判断角色
                    Set<String> roleAuthorizationCode = entry.getValue().get(AuthorizationTypeEnum.ONE);
                    if (CollUtil.containsAny(userRoleCodes, roleAuthorizationCode)) {
                        return;
                    }
                    // 上述条件都不满足，抛出异常
                    throw new MetaDataException("meta-data-core-0000000004", entry.getKey());
                });
    }

}

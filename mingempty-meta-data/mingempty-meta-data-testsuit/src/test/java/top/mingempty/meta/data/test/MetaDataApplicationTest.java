package top.mingempty.meta.data.test;

import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.domain.other.MePubConditions;
import top.mingempty.meta.data.MetaDataApplication;
import top.mingempty.meta.data.commons.util.EntryVersionUtil;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.domain.biz.dict.service.AuthorizationDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.EntryDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.ExtraFieldDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.ItemDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.OperationHistoryDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryAllInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.AuthorizationModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.EntryModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ExtraFieldModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.OperationRecordInfo;
import top.mingempty.meta.data.domain.enums.AuthorizationTypeEnum;
import top.mingempty.meta.data.domain.enums.DictOperationEnum;
import top.mingempty.meta.data.domain.enums.EntryTypeEnum;
import top.mingempty.meta.data.repository.mapper.ChangeItemMapper;
import top.mingempty.meta.data.repository.model.po.ChangeItemPo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(classes = {MetaDataApplication.class})
public class MetaDataApplicationTest {

    @Resource
    private OperationHistoryDomainService operationHistoryDomainService;

    @Resource
    private EntryDomainService entryDomainService;


    @Resource
    private AuthorizationDomainService authorizationDomainService;


    @Resource
    private ExtraFieldDomainService extraFieldDomainService;

    @Autowired
    private ChangeItemMapper changeItemMapper;

    @Autowired
    private ItemDomainService itemDomainService;


    @Test
    void entryQuery() {
        List<EntryAllInfo> entryAllInfos = entryDomainService.queryAll(EntryQuery.builder().deleteStatus(ZeroOrOneEnum.ZERO).build());
        log.info("{}", JsonUtil.toStr(entryAllInfos));
    }

    @Test
    @SneakyThrows
    void entryVersion() {
        Long version = operationHistoryDomainService.gainVersion("test");
        log.info("test:{}", version);
        version = operationHistoryDomainService.gainVersion("test");
        log.info("test:{}", version);

        version = operationHistoryDomainService.gainVersion("demo");
        log.info("demo:{}", version);
        Runnable runnable = () -> {
            Long version1 = operationHistoryDomainService.gainVersion("demo");
            log.info("sync demo:{}", version1);
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Map<String, Long> stringLongMap = EntryVersionUtil.gainVersion();
        Runnable runnable2 = () -> {
            EntryVersionUtil.settingVersion(stringLongMap);
            Long version1 = operationHistoryDomainService.gainVersion("demo");
            log.info("sync2 demo:{}", version1);
        };
        Thread thread2 = new Thread(runnable2);
        thread2.start();
        version = operationHistoryDomainService.gainVersion("demo");
        log.info("demo:{}", version);
        TimeUnit.SECONDS.sleep(3L);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    void dictBaseModifies() {
        String entryCode = "test";
        authorizationDomainService.checkAuthorization(entryCode);

        Long entryChangeVertion = operationHistoryDomainService.gainVersion(entryCode);

        AuthorizationModifiesInfo authorizationModifiesInfo1 = AuthorizationModifiesInfo.builder()
                .authorizationType(AuthorizationTypeEnum.ONE)
                .authorizationCode("test")
                .build();

        AuthorizationModifiesInfo authorizationModifiesInfo2 = AuthorizationModifiesInfo.builder()
                .authorizationType(AuthorizationTypeEnum.TWO)
                .authorizationCode("user")
                .deleteStatus(ZeroOrOneEnum.ZERO)
                .build();

        AuthorizationModifiesInfo authorizationModifiesInfo3 = AuthorizationModifiesInfo.builder()
                .authorizationType(AuthorizationTypeEnum.TWO)
                .authorizationCode("zzhao")
                .deleteStatus(ZeroOrOneEnum.ZERO)
                .build();

        AuthorizationModifiesInfo authorizationModifiesInfo4 = AuthorizationModifiesInfo.builder()
                .authorizationType(AuthorizationTypeEnum.ONE)
                .authorizationCode("demo_role")
                .build();

        ExtraFieldModifiesInfo extraFieldModifiesInfo1 = ExtraFieldModifiesInfo.builder()
                .extraFieldCode("test4")
                .extraFieldName("test4")
                .otherDictFlag(ZeroOrOneEnum.ZERO)
                .extraFieldSort(BigDecimal.ONE)
                .build();

        ExtraFieldModifiesInfo extraFieldModifiesInfo2 = ExtraFieldModifiesInfo.builder()
                .extraFieldCode("test2")
                .extraFieldName("test2.1")
                .otherDictFlag(ZeroOrOneEnum.ZERO)
                .extraFieldSort(BigDecimal.ZERO)
                .build();

        ExtraFieldModifiesInfo extraFieldModifiesInfo3 = ExtraFieldModifiesInfo.builder()
                .extraFieldCode("test3")
                .extraFieldName("test3")
                .otherDictFlag(ZeroOrOneEnum.ZERO)
                .extraFieldSort(BigDecimal.valueOf(325235.33))
                .build();

        EntryModifiesInfo entryModifiesInfo = EntryModifiesInfo.builder()
                .entryName("test")
                .entryType(EntryTypeEnum.TWO)
                .entrySharding(ZeroOrOneEnum.ZERO)
                .sort(BigDecimal.ONE)
                .deleteStatus(ZeroOrOneEnum.ZERO)
                .build();
        entryDomainService.modifies(entryCode, entryChangeVertion, entryModifiesInfo);
        authorizationDomainService.modifies(entryCode, entryChangeVertion,
                List.of(authorizationModifiesInfo1, authorizationModifiesInfo2,
                        authorizationModifiesInfo3, authorizationModifiesInfo4));

        extraFieldDomainService.modifies(entryCode, entryChangeVertion,
                List.of(extraFieldModifiesInfo1, extraFieldModifiesInfo2, extraFieldModifiesInfo3));
        OperationRecordInfo operationRecordInfo = OperationRecordInfo.builder()
                .entryCode(entryCode)
                .entryVersion(entryChangeVertion)
                .operationType(DictOperationEnum.code_1)
                .build();
        operationHistoryDomainService.record(operationRecordInfo);
    }

    @Test
    void authorizationModifies() {
        Long entryChangeVertion = operationHistoryDomainService.gainVersion("test");
        AuthorizationModifiesInfo authorizationModifiesInfo1 = AuthorizationModifiesInfo.builder()
                .authorizationType(AuthorizationTypeEnum.ONE)
                .authorizationCode("test")
                .build();
        AuthorizationModifiesInfo authorizationModifiesInfo2 = AuthorizationModifiesInfo.builder()
                .authorizationType(AuthorizationTypeEnum.TWO)
                .authorizationCode("user")
                .deleteStatus(ZeroOrOneEnum.ZERO)
                .build();
        AuthorizationModifiesInfo authorizationModifiesInfo3 = AuthorizationModifiesInfo.builder()
                .authorizationType(AuthorizationTypeEnum.TWO)
                .authorizationCode("zzhao")
                .deleteStatus(ZeroOrOneEnum.ZERO)
                .build();
        authorizationDomainService.modifiesAll("test", entryChangeVertion,
                List.of(authorizationModifiesInfo1, authorizationModifiesInfo2, authorizationModifiesInfo3));
    }

    @Test
    void conditionitionTest() {
        ItemQuery itemQuery = new ItemQuery();
//        itemQuery.setEntryCode("test");
        MePubConditions.ValueCondition valueCondition = new MePubConditions.ValueCondition();
        valueCondition.setType(MePubConditions.ConditionsType.eq);
        valueCondition.setColumn("entry_code");
        valueCondition.setValue("1");

        MePubConditions.ValueCondition valueCondition2 = new MePubConditions.ValueCondition();
        valueCondition2.setType(MePubConditions.ConditionsType.or);

        MePubConditions.ValueCondition valueCondition3 = new MePubConditions.ValueCondition();
        valueCondition3.setType(MePubConditions.ConditionsType.eq);
        valueCondition3.setColumn("entry_code");
        valueCondition3.setValue("3");

        MePubConditions.ValueCondition valueCondition4 = new MePubConditions.ValueCondition();
        valueCondition4.setType(MePubConditions.ConditionsType.eq);
        valueCondition4.setColumn("item_code");
        valueCondition4.setValue("4");
        valueCondition2.setConditions(List.of(valueCondition3, valueCondition4));

        MePubConditions.ValueCondition valueCondition5 = new MePubConditions.ValueCondition();
        valueCondition5.setType(MePubConditions.ConditionsType.le);
        valueCondition5.setColumn("entry_version");
        valueCondition5.setValue(14);


        MePubConditions.ValueCondition valueCondition6 = new MePubConditions.ValueCondition();
        valueCondition6.setType(MePubConditions.ConditionsType.or);
        valueCondition6.setConditions(List.of(valueCondition5));

        itemQuery.setItemExtraFieldConditions(List.of(valueCondition, valueCondition2, valueCondition6));
        List<ChangeItemPo> query = changeItemMapper.query(itemQuery, 0L, 10L);
        log.info("{}", JsonUtil.toStr(query));
    }

    @Test
    void itemModifies() {
        String entryCode = "test";
        authorizationDomainService.checkAuthorization(entryCode);
        Long entryChangeVertion = operationHistoryDomainService.gainVersion(entryCode);
        ItemModifiesInfo itemModifiesInfo1 = ItemModifiesInfo.builder()
                .itemCode("1")
                .itemName("test_1")
                .itemParentCode("#")
                .itemSort(BigDecimal.ONE)
                .itemLevel(1L)
                .deleteStatus(ZeroOrOneEnum.ZERO)
                .extraFields(Map.of("test2", "1243214", "test4", 3252))
                .build();

        ItemModifiesInfo itemModifiesInfo2 = ItemModifiesInfo.builder()
                .itemCode("2")
                .itemName("test_2")
                .itemParentCode("#")
                .itemSort(BigDecimal.ONE)
                .itemLevel(1L)
                .deleteStatus(ZeroOrOneEnum.ZERO)
                .build();

        ItemModifiesInfo itemModifiesInfo3 = ItemModifiesInfo.builder()
                .itemCode("3")
                .itemName("test_3")
                .itemParentCode("#")
                .itemSort(BigDecimal.ONE)
                .itemLevel(1L)
                .deleteStatus(ZeroOrOneEnum.ZERO)
                .build();
        itemDomainService.modifies(entryCode, entryChangeVertion, List.of(itemModifiesInfo1, itemModifiesInfo2, itemModifiesInfo3));
//        entryChangeVertion = operationHistoryDomainService.gainVersion(entryCode);
//        itemDomainService.modifiesAll(entryCode, entryChangeVertion, List.of());
        OperationRecordInfo operationRecordInfo = OperationRecordInfo.builder()
                .entryCode(entryCode)
                .entryVersion(entryChangeVertion)
                .operationType(DictOperationEnum.code_2)
                .build();
        operationHistoryDomainService.record(operationRecordInfo);
    }
}

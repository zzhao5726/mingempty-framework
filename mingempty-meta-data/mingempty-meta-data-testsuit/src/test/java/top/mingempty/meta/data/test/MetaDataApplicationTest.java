package top.mingempty.meta.data.test;

import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.meta.data.MetaDataApplication;
import top.mingempty.meta.data.commons.util.EntryVersionUtil;
import top.mingempty.meta.data.domain.biz.dict.repository.EntryRepository;
import top.mingempty.meta.data.repository.model.po.EntryPo;
import top.mingempty.meta.data.repository.service.EntryService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(classes = {MetaDataApplication.class})
public class MetaDataApplicationTest {

    @Resource
    private EntryService entryService;

    @Resource
    private EntryRepository entryRepository;


    @Test
    void aaa() {
        List<EntryPo> list = entryService.list();
        log.info("{}", JsonUtil.toStr(list));
    }

    @Test
    @SneakyThrows
    void entryVersion() {
        Long version = entryRepository.gainVersion("test");
        log.info("test:{}", version);
        version = entryRepository.gainVersion("test");
        log.info("test:{}", version);

        version = entryRepository.gainVersion("demo");
        log.info("demo:{}", version);
        Runnable runnable = () -> {
            Long version1 = entryRepository.gainVersion("demo");
            log.info("sync demo:{}", version1);
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Map<String, Long> stringLongMap = EntryVersionUtil.gainVersion();
        Runnable runnable2 = () -> {
            EntryVersionUtil.settingVersion(stringLongMap);
            Long version1 = entryRepository.gainVersion("demo");
            log.info("sync2 demo:{}", version1);
        };
        Thread thread2 = new Thread(runnable2);
        thread2.start();
        version = entryRepository.gainVersion("demo");
        log.info("demo:{}", version);
        TimeUnit.SECONDS.sleep(3L);
    }


}

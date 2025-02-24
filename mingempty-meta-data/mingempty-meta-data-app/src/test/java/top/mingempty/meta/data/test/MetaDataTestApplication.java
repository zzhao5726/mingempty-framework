package top.mingempty.meta.data.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.mingempty.meta.data.MetaDataApplication;
import top.mingempty.meta.data.service.OperationHistoryService;

@Slf4j
@SpringBootTest(classes = MetaDataApplication.class)
public class MetaDataTestApplication {
    @Autowired
    private OperationHistoryService operationHistoryService;


    @Test
    void gainVersion() {
        for (int i = 0; i < 10;  i++) {
            Long nextVersion = operationHistoryService.gainVersion("demo");
            log.info("获取的版本为:{}", nextVersion);
        }
    }
}

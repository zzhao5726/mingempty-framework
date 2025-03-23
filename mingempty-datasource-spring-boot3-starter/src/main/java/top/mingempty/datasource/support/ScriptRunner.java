package top.mingempty.datasource.support;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * @author zzhao
 * @since 2020/1/21
 */
@Slf4j
@AllArgsConstructor
public class ScriptRunner {

    /**
     * 错误是否继续
     */
    private final boolean continueOnError;
    /**
     * 分隔符
     */
    private final String separator;

    /**
     * 执行数据库脚本
     *
     * @param dataSource 连接池
     * @param location   脚本位置
     */
    public void runScript(DataSource dataSource, String location) {
        if (StrUtil.isNotEmpty(location)) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.setContinueOnError(continueOnError);
            populator.setSeparator(separator);
            try {
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                populator.addScripts(resolver.getResources(location));
                DatabasePopulatorUtils.execute(populator, dataSource);
            } catch (DataAccessException e) {
                log.warn("execute sql error", e);
            } catch (Exception e) {
                log.warn("failed to initialize dataSource from schema file {} ", location, e);
            }
        }
    }

}

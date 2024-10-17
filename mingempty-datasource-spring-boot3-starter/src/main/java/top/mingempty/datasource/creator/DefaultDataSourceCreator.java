package top.mingempty.datasource.creator;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.datasource.creator.wrap.DataSourceCreatorWrapper;
import top.mingempty.datasource.enums.ClusterMode;
import top.mingempty.datasource.model.DataSourceConfig;
import top.mingempty.datasource.model.DataSourceProperty;
import top.mingempty.datasource.model.DatasourceInitProperty;
import top.mingempty.datasource.model.DynamicDatasource;
import top.mingempty.datasource.model.MasterSlaveDynamicDatasource;
import top.mingempty.datasource.support.ScriptRunner;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 数据创建工具接口
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
public class DefaultDataSourceCreator {

    private final List<DataSourceCreator> dataSourceCreators;
    private final List<DataSourceCreatorWrapper> dataSourceCreatorsWrappers;
    private final List<DataSourceExtended> dataSourceExtendeds;


    /**
     * 创建数据源
     *
     * @param dataSourceProperty 配置类
     * @return
     */
    public DataSource createDataSource(DataSourceProperty dataSourceProperty) {

        if (!dataSourceProperty.isOtherEnabled()) {
            DataSource dataSource = this.createDataSource(dataSourceProperty.def());
            if (dataSource == null) {
                Assert.notNull(dataSource, "dataSource init can not be null");
            }
            DynamicDatasource dynamicDatasource = new DynamicDatasource(dataSourceProperty, dataSource);
            dataSourceExtended(dataSourceProperty, dynamicDatasource);
            return dataSource;
        }
        Preconditions.checkArgument(CollUtil.isNotEmpty(dataSourceProperty.getOther()),
                "dataSources init can not be null");

        Map<String, Map<ClusterMode, List<DataSourceConfig>>> configCmMap = dataSourceProperty.getOther()
                .parallelStream()
                .collect(Collectors.groupingBy(DataSourceConfig::getName,
                        Collectors.groupingBy(DataSourceConfig::getClusterMode)));

        Preconditions.checkArgument(configCmMap.containsKey(dataSourceProperty.getPrimary()),
                "dataSource name must have primary");

        Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(configCmMap.size());
        configCmMap.forEach((name, configMap) -> {
            List<DataSource> masterDatasources = Optional.ofNullable(configMap.get(ClusterMode.MASTER))
                    .orElse(List.of()).stream()
                    .map(this::createDataSource)
                    .filter(Objects::nonNull)
                    .toList();
            List<DataSource> slaveDatasources = Optional.ofNullable(configMap.get(ClusterMode.SLAVE))
                    .orElse(List.of()).stream()
                    .map(this::createDataSource)
                    .filter(Objects::nonNull)
                    .toList();
            MasterSlaveDynamicDatasource masterSlaveDynamicDatasource = new MasterSlaveDynamicDatasource(masterDatasources, slaveDatasources);
            dataSourceMap.put(name, masterSlaveDynamicDatasource);
        });
        DataSource dataSource = dataSourceMap.get(dataSourceProperty.getPrimary());
        DynamicDatasource dynamicDatasource = new DynamicDatasource(dataSourceProperty, dataSource);
        dynamicDatasource.putAll(dataSourceMap);
        dataSourceMap.clear();
        dataSourceExtended(dataSourceProperty, dynamicDatasource);
        return dynamicDatasource;
    }

    /**
     * 扩展数据源
     *
     * @param dataSourceProperty 配置文件
     * @param dynamicDatasource  基于配置初始化后的数据源
     */
    private void dataSourceExtended(DataSourceProperty dataSourceProperty,
                                    final DynamicDatasource dynamicDatasource) {
        if (dataSourceExtendeds != null) {
            dataSourceExtendeds.forEach(dataSourceExtended
                    -> dataSourceExtended.dataSourceExtended(dataSourceProperty, dynamicDatasource));
        }
        dynamicDatasource.doAfterPropertiesSet();
    }

    /**
     * 通过属性创建数据源
     *
     * @param dataSourceConfig 数据源属性
     * @return 被创建的数据源
     */
    public DataSource createDataSource(DataSourceConfig dataSourceConfig) {
        if (CollUtil.isEmpty(dataSourceCreators)) {
            log.warn("未配置数据源创建器，无法创建数据源");
            return null;
        }
        DataSource dataSource = dataSourceCreators.stream()
                .filter(dataSourceCreator -> dataSourceCreator.support(dataSourceConfig))
                .map(dataSourceCreator -> dataSourceCreator.createDataSource(dataSourceConfig))
                .findFirst()
                .orElse(null);

        if (dataSource == null) {
            return null;
        }
        AtomicReference<DataSource> dataSourceAtomicReference = new AtomicReference<>(dataSource);
        //对数据源进行包装
        if (CollUtil.isNotEmpty(dataSourceCreatorsWrappers)) {
            dataSourceCreatorsWrappers.stream()
                    .filter(dataSourceCreatorWrapper -> dataSourceCreatorWrapper.support(dataSourceConfig))
                    .sorted((o1, o2) -> o2.getOrder() - o1.getOrder())
                    .forEach(dataSourceCreatorWrapper
                            -> dataSourceAtomicReference.set(dataSourceCreatorWrapper
                            .createDataSourceWrapper(dataSourceAtomicReference.get(), dataSourceConfig)));
        }
        //执行初始化操作
        runScrip(dataSourceAtomicReference.get(), dataSourceConfig);

        return dataSourceAtomicReference.get();
    }


    /**
     * 执行初始化脚本
     *
     * @param dataSource       数据源
     * @param dataSourceConfig 数据源参数
     */
    private void runScrip(DataSource dataSource, DataSourceConfig dataSourceConfig) {
        DatasourceInitProperty dataSourceConfigInit = dataSourceConfig.getInit();
        String schema = dataSourceConfigInit.getSchema();
        String data = dataSourceConfigInit.getData();
        if (StrUtil.isNotEmpty(schema) || StrUtil.isNotEmpty(data)) {
            ScriptRunner scriptRunner = new ScriptRunner(dataSourceConfigInit.isContinueOnError(), dataSourceConfigInit.getSeparator());
            if (StrUtil.isNotEmpty(schema)) {
                scriptRunner.runScript(dataSource, schema);
            }
            if (StrUtil.isNotEmpty(data)) {
                scriptRunner.runScript(dataSource, data);
            }
        }
    }


}
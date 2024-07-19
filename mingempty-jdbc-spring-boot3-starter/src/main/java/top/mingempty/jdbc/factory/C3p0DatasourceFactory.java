package top.mingempty.jdbc.factory;

import lombok.SneakyThrows;
import top.mingempty.jdbc.domain.C3p0Config;
import top.mingempty.jdbc.mapstruct.C3p0Mapstruct;

import javax.sql.DataSource;

/**
 * C3p0数据源工厂
 *
 * @author zzhao
 */
public class C3p0DatasourceFactory implements DatasourceFactory<C3p0Config> {


    /**
     * 是否支持
     *
     * @param clazz 数据源配置类
     * @return true/false
     */
    @Override
    public boolean support(Class<?> clazz) {
        return C3p0Config.class.equals(clazz);
    }

    /**
     * 将配置转换为数据源
     *
     * @param properties 数据源配置
     * @return 数据源
     */
    @Override
    @SneakyThrows
    public DataSource createDataSource(C3p0Config properties) {
        return C3p0Mapstruct.INSTANCE.toDataSource(properties);

    }
}

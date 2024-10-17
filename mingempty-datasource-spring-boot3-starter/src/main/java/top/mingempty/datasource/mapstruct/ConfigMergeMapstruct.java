package top.mingempty.datasource.mapstruct;

import org.mapstruct.MappingTarget;

/**
 * 配置文件属性转换
 */
public interface ConfigMergeMapstruct<T> {

    /**
     * 属性拷贝
     *
     * @param global 全局配置
     */
    T clone(T global);

    /**
     * 属性转换
     *
     * @param source 配置文件配置
     * @param target 目标配置
     */
    void merge(T source, @MappingTarget T target);


    /**
     * 获取转换后的实例
     *
     * @param source 配置文件配置
     * @param global 全局配置
     * @return 最终配置
     */
    default T newT(T source, T global) {
        T clone = clone(global);
        merge(source, clone);
        return clone;
    }

}

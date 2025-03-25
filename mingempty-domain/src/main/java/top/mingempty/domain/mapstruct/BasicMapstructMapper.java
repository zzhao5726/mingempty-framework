package top.mingempty.domain.mapstruct;

import org.mapstruct.IterableMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;

/**
 * Mapstruct类拷贝基础方法接口
 * <p>
 * 针对数据库实体类，需要增加四类实体类
 * PO：直接和数据库交互的实体
 * BO：业务处理时数据实体
 * VO：传给前端的实体（时间类型格式化为字符串）
 * DTO：服务间交互的实体
 * <p>
 * BO可以与PO、VO、DTO进行转换
 * PO仅仅可以与BO进行转换
 * VO仅仅可以与BO进行转换
 * DTO仅仅可以与BO进行转换
 *
 * @param <F>
 * @param <T>
 */
public interface BasicMapstructMapper<F, T> {

    /**
     * 将From转换为To
     *
     * @param from
     * @return
     */
    @Named("to")
    @Mappings(value = {})
    T to(F from);

    /**
     * 将To转换为From
     *
     * @param to
     * @return
     */
    @Named("from")
    @Mappings(value = {})
    F from(T to);

    /**
     * 拷贝To对象
     *
     * @param to
     * @return
     */
    @Named("toCopy")
    @Mappings(value = {})
    T toCopy(T to);

    /**
     * 拷贝From对象
     *
     * @param from
     * @return
     */
    @Named("boCopy")
    @Mappings(value = {})
    F boCopy(F from);

    /**
     * 将From集合转换为To集合
     *
     * @param bos
     * @return
     */
    @Named("toFroms")
    @Mappings(value = {})
    @IterableMapping(qualifiedByName = "to")
    Collection<T> toFroms(Collection<F> bos);

    /**
     * 将To集合转换为From集合
     *
     * @param tos
     * @return
     */
    @Named("boTos")
    @Mappings(value = {})
    @IterableMapping(qualifiedByName = "from")
    Collection<F> boTos(Collection<T> tos);

    /**
     * 拷贝To对象集合
     *
     * @param tos
     * @return
     */
    @Named("toTos")
    @Mappings(value = {})
    @IterableMapping(qualifiedByName = "toCopy")
    Collection<T> toTos(Collection<T> tos);

    /**
     * 拷贝From对象集合
     *
     * @param bos
     * @return
     */
    @Named("boFromS")
    @Mappings(value = {})
    @IterableMapping(qualifiedByName = "boCopy")
    Collection<F> boFromS(Collection<F> bos);

    /**
     * 基于From对象数据更新To，并返回To
     *
     * @param from
     * @param to
     * @return
     */
    @Named("toUpdateWithFrom")
    @Mappings(value = {})
    T toUpdateWithFrom(F from, @MappingTarget T to);

    /**
     * 基于To对象数据更新From，并返回From
     *
     * @param to
     * @param from
     * @return
     */
    @Named("boUpdateWithTo")
    @Mappings(value = {})
    F boUpdateWithTo(T to, @MappingTarget F from);

    /**
     * 基于To对象数据更新ToTarget，并返回ToTarget
     *
     * @param toSource
     * @param toTarget
     * @return
     */
    @Named("toUpdateWithTo")
    @Mappings(value = {})
    T toUpdateWithTo(T toSource, @MappingTarget T toTarget);

    /**
     * 基于From对象数据更新FromTarget，并返回FromTarget
     *
     * @param boSource
     * @param boTarget
     * @return
     */
    @Named("boUpdateWithFrom")
    @Mappings(value = {})
    F boUpdateWithFrom(F boSource, @MappingTarget F boTarget);


    @Retention(RetentionPolicy.CLASS)
    @Qualifier
    @interface NonNullMapping {
    }
}

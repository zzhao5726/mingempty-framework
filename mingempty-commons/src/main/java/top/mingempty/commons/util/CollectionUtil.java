package top.mingempty.commons.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author zzhao
 * @date 2023/2/17 11:36
 */
public class CollectionUtil {

    /**
     * 将集合按照一定的大小进行截取
     *
     * @param sourceList 目标集合
     * @param batchCount 目标大小
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> batchSubList(List<T> sourceList, Integer batchCount) {
        if (CollUtil.isEmpty(sourceList)) {
            return new ArrayList<>();
        }

        List<List<T>> returnList = new ArrayList<>();

        if (ObjUtil.isEmpty(batchCount)
                || batchCount < 1) {
            returnList.add(sourceList);
            return returnList;
        }
        // 从第0个下标开始
        int startIndex = 0;
        while (startIndex < sourceList.size()) {
            int endIndex = 0;
            if (sourceList.size() - batchCount < startIndex) {
                endIndex = sourceList.size();
            } else {
                endIndex = startIndex + batchCount;
            }
            returnList.add(sourceList.subList(startIndex, endIndex));
            startIndex = startIndex + batchCount;
        }
        return returnList;
    }


    /**
     * 集合截取
     *
     * @param sourceList 数据
     * @param page       页码 从1开始
     * @param batchCount 集合数量
     * @param <T>
     * @return
     */
    public static <T> List<T> batchSubList(List<T> sourceList, Integer page, Integer batchCount) {
        if (CollUtil.isEmpty(sourceList)) {
            return new ArrayList<>();
        }

        if (ObjUtil.isEmpty(batchCount)
                || batchCount < 1) {
            return sourceList;
        }

        if (ObjUtil.isEmpty(page) || page < 1) {
            page = 1;
        }

        int startIndex = (page - 1) * batchCount;
        List<T> returnList = new ArrayList<>();
        if (startIndex < sourceList.size()) {
            int endIndex = 0;
            if (sourceList.size() - batchCount < startIndex) {
                endIndex = sourceList.size();
            } else {
                endIndex = startIndex + batchCount;
            }
            returnList.addAll(sourceList.subList(startIndex, endIndex));
        }
        return returnList;
    }

    /**
     * 判断集合中是否存在重复元素
     *
     * @param objectList 目标集合
     * @param function   指定元素
     * @return
     */
    public static <T, M> boolean existRepeatedElements(List<T> objectList, Function<T, M> function) {
        if (CollUtil.isEmpty(objectList)) {
            return false;
        }
        return !Long.valueOf(objectList.stream().map(function)
                .distinct().count()).equals(Long.valueOf(objectList.size()));
    }

    /**
     * 比较两个字符集合是否相等
     *
     * @param objectList1 Number集合1
     * @param objectList2 Number集合2
     * @return
     */
    public static boolean sameCollectionNumber(List<Number> objectList1, List<Number> objectList2) {
        return sameCollection(objectList1, objectList2, Function.identity(), Function.identity());
    }

    /**
     * 比较两个字符集合是否相等
     *
     * @param objectList1 字符串集合1
     * @param objectList2 字符串集合2
     * @return
     */
    public static boolean sameCollection(List<String> objectList1, List<String> objectList2) {
        return sameCollection(objectList1, objectList2, String::toString, String::toString);
    }

    /**
     * 比较两个集合中某个字段是否完全相等
     *
     * @param objectList1 要比较的集合1
     * @param objectList2 要比较的集合2
     * @param function1   集合1的比较字段
     * @param function2   集合2的比较字段
     * @return
     */
    public static <T, R, M, N> boolean sameCollection(List<T> objectList1, List<R> objectList2,
                                                      Function<T, M> function1, Function<R, N> function2) {
        if (CollUtil.isEmpty(objectList1) && CollUtil.isEmpty(objectList2)) {
            return true;
        }

        if (CollUtil.isEmpty(objectList1)
                || CollUtil.isEmpty(objectList2)
                || objectList1.size() != objectList2.size()) {
            return false;
        }
        return objectList1.stream().map(function1).filter(Objects::nonNull)
                .sorted().map(String::valueOf).collect(Collectors.joining())
                .equals(objectList2.stream().map(function2)
                        .filter(Objects::nonNull).sorted().map(String::valueOf).collect(Collectors.joining()));
    }


    /**
     * 判断集合中指定字段的任何元素与指定匹配条件是否相等
     *
     * @param objectList 指定集合
     * @param function   要匹配的字段
     * @param predicate  匹配条件
     * @return
     */
    public static <T, R> boolean isExist(List<T> objectList, Function<T, R> function, Predicate<R> predicate) {
        if (objectList == null) {
            return false;
        }
        return objectList.stream().map(function).anyMatch(predicate);
    }

    /**
     * 传入一个集合，按照指定格式转换为树形结构
     *
     * @param nodes            指定的结合
     * @param nodeKeyFunction  子节点取值方法
     * @param superKeyFunction 父节点取值方法
     * @param fieldBiConsumer  设置子节点结果集方法
     * @param superFieldValue  顶级父节点的值
     * @param <T>              node类型
     * @param <K>              node的编码类型
     * @return 转换后的树
     * @throws IllegalStateException    传入的顶级父节点值在集合中不存在
     * @throws IllegalArgumentException 传入的顶级父节点值为空
     */
    public static <T, K> List<T> bulidTree(List<T> nodes,
                                           Function<T, K> nodeKeyFunction,
                                           Function<T, K> superKeyFunction,
                                           BiConsumer<T, List<T>> fieldBiConsumer,
                                           @NotNull K superFieldValue) {
        if (CollUtil.isEmpty(nodes)) {
            return Collections.emptyList();
        }

        if (Objects.isNull(superFieldValue)) {
            throw new IllegalArgumentException("传入的顶级父节点值为空");
        }

        Map<? extends K, List<T>> treeNodeMapBySuperKey = nodes.parallelStream()
                .filter(t -> Objects.nonNull(superKeyFunction.apply(t)))
                .collect(Collectors.groupingBy(superKeyFunction));
        List<T> tList = treeNodeMapBySuperKey.get(superFieldValue);
        if (CollUtil.isEmpty(treeNodeMapBySuperKey)) {
            throw new IllegalStateException("传入的顶级父节点值在集合中不存在");
        }
        nodes.parallelStream().forEach(t
                -> fieldBiConsumer.accept(t, treeNodeMapBySuperKey.get(nodeKeyFunction.apply(t))));
        return tList;
    }


}

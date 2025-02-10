package top.mingempty.commons.util;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.domain.base.MePage;

/**
 * 分页工具类
 *
 * @author zzhao
 * @date 2023/5/25 17:02
 */
public class PageUtil {

    /**
     * 默认的分页参数
     */
    private static final Pair<Long, Long> DEFAULT_PAIR = new Pair<>(0L, -1L);


    /**
     * 计算分页的起始和终止索引
     *
     * @param mePage
     * @return
     */
    public static Pair<Long, Long> calculationStartAndEndIndex(MePage mePage) {
        if (ObjUtil.isEmpty(mePage)) {
            return DEFAULT_PAIR;
        }

        return calculationStartAndEndIndex(mePage.getTotal(), mePage.getPageSize(), mePage.getStartIndex());
    }

    /**
     * 以当前索引在中间为限，计算分页的起始和终止索引
     * <p>
     * 设 总数为 x  分页取 y  当前位置在 z
     * <p>
     * 起始条件         起始索引    终止条件           终止索引
     * <p>
     * y<=x             0         无                -1
     * <p>
     * z>=x&&x-y>=0    x-y        无                -1
     * <p>
     * z>=x&&x-y<0      0         无                -1
     * <p>
     * z-y/2 >= 0     z-y/2     z+y/2 >= x            -1
     * <p>
     * z-y/2 >= 0     z-y/2      z+y/2 < x	       z+y/2
     * <p>
     * z-y/2 < 0	    0       z+y/2+(y/2-z) >= x    -1
     * <p>
     * z-y/2 < 0	    0       z+y/2+ (y/2-z) < x     y
     * <p>
     *
     * @param sumCount 总数
     * @param pageSize 分页大小
     * @param index    当前索引
     * @return 返回带有起始和截止索引的不可变Pair
     * <p>
     * key： 起始索引
     * <p>
     * value:   终止索引
     */
    public static Pair<Long, Long> calculationStartAndEndIndex(long sumCount, long pageSize, long index) {
        // 参数验证
        if (pageSize <= 0 || index < 0 || sumCount < 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        if (pageSize <= sumCount) {
            return DEFAULT_PAIR;
        }

        if (index >= sumCount) {
            long startIndex = sumCount - pageSize;
            return DEFAULT_PAIR;
        }

        long halfPageSize = pageSize / 2;
        long adjustedIndex = index - halfPageSize;
        long endIndex = index + halfPageSize;

        if (adjustedIndex >= 0) {
            if (endIndex >= sumCount) {
                return new Pair<>(adjustedIndex, -1L);
            } else {
                return new Pair<>(adjustedIndex, endIndex);
            }
        } else {
            long adjustedRange = endIndex - adjustedIndex;
            if (adjustedRange >= sumCount) {
                return DEFAULT_PAIR;
            } else {
                return new Pair<>(0L, pageSize);
            }
        }
    }
}

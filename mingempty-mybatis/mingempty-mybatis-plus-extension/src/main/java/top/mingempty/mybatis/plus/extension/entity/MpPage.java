package top.mingempty.mybatis.plus.extension.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.mingempty.domain.base.MePage;

import java.util.Optional;

public class MpPage<T> extends Page<T> {
    private final MePage mePage;

    /**
     * 无参构造
     */
    public MpPage() {
        this(1, 10, -1, true);
    }

    public MpPage(MePage mePage) {
        this(mePage.getPageNo(), mePage.getPageSize(), mePage.getTotal(), mePage.isSearchCount(), mePage);
    }


    public MpPage(long current, long size) {
        this(current, size, -1);
    }


    public MpPage(long current, long size, long total) {
        this(current, size, total, true);
    }


    public MpPage(long current, long size, boolean searchCount) {
        this(current, size, -1, searchCount);
    }


    public MpPage(long current, long size, long total, boolean searchCount) {
        this(current, size, total, searchCount, null);
    }

    /**
     * 全量有参构造
     *
     * @param current     当前页
     * @param size        页大小
     * @param total       总数
     * @param searchCount 是否全量查询
     * @param mePage       原始分页对象
     */
    public MpPage(long current, long size, long total, boolean searchCount, MePage mePage) {
        super();
        if (current < 1) {
            current = 1;
            //条件不对，禁止查询总量
            searchCount = false;
        }
        if (size < 0) {
            size = 10;
            //条件不对，禁止查询总量
            searchCount = false;
        }
        super.setCurrent(current);
        super.setSize(size);
        super.setTotal(total);
        this.setSearchCount(searchCount);
        this.mePage = mePage;
    }


    /**
     * @param size
     * @return
     */
    @Override
    public Page<T> setSize(long size) {
        if (size < 0) {
            size = 10;
            //条件不对，禁止查询总量
            this.setSearchCount(false);
        }
        long finalSize = size;
        Optional.ofNullable(this.mePage)
                .ifPresent(iPage -> iPage.setPageNo(finalSize));
        return super.setSize(size);
    }

    /**
     * @param current
     * @return
     */
    @Override
    public Page<T> setCurrent(long current) {
        if (current < 1) {
            current = 1;
            //条件不对，禁止查询总量
            this.setSearchCount(false);
        }
        long finalCurrent = current;
        Optional.ofNullable(this.mePage)
                .ifPresent(iPage -> iPage.setPageNo(finalCurrent));
        return super.setCurrent(current);
    }

    /**
     * @param total
     * @return
     */
    @Override
    public Page<T> setTotal(long total) {
        Optional.ofNullable(this.mePage)
                .ifPresent(iPage -> iPage.setTotal(total));
        return super.setTotal(total);
    }

    @Override
    public Page<T> setSearchCount(boolean searchCount) {
        Optional.ofNullable(this.mePage)
                .ifPresent(iPage -> iPage.setSearchCount(searchCount));
        return super.setSearchCount(searchCount);
    }
}

package top.mingempty.mybatis.flex.extension.entity;

import com.mybatisflex.core.paginate.Page;
import top.mingempty.domain.base.MePage;

import java.util.Optional;

public class MfPage<T> extends Page<T> {
    private final MePage mePage;

    /**
     * 无参构造
     */
    public MfPage() {
        this(1, 10, -1, null);
    }

    public MfPage(MePage mePage) {
        this(mePage.getPageNo(), mePage.getPageSize(), mePage.isSearchCount() ? -1L : mePage.getTotal(), mePage);
    }


    public MfPage(long pageNumber, long pageSize) {
        this(pageNumber, pageSize, -1);
    }


    public MfPage(long pageNumber, long pageSize, long totalRow) {
        this(pageNumber, pageSize, totalRow, null);
    }

    /**
     * 全量有参构造
     *
     * @param pageNumber 当前页
     * @param pageSize   页大小
     * @param totalRow   总数
     * @param mePage     原始分页对象
     */
    public MfPage(long pageNumber, long pageSize, long totalRow, MePage mePage) {
        super();
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageSize < 0) {
            pageSize = 10;
        }
        this.setPageNumber(pageNumber);
        this.setPageSize(pageSize);
        this.setTotalRow(totalRow);
        this.mePage = mePage;
    }


    @Override
    public void setPageSize(long pageSize) {
        if (pageSize < 0) {
            pageSize = 10;
        }
        long finalSize = pageSize;
        Optional.ofNullable(this.mePage)
                .ifPresent(iPage -> iPage.setPageNo(finalSize));
        super.setPageSize(pageSize);
    }

    @Override
    public void setPageNumber(long pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        long finalCurrent = pageNumber;
        Optional.ofNullable(this.mePage)
                .ifPresent(iPage -> iPage.setPageNo(finalCurrent));
        super.setPageNumber(pageNumber);
    }

    @Override
    public void setTotalRow(long totalRow) {
        Optional.ofNullable(this.mePage)
                .ifPresent(iPage -> iPage.setTotal(totalRow));
        super.setTotalRow(totalRow);
    }
}

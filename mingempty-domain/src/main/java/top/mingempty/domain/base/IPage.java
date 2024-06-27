package top.mingempty.domain.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import top.mingempty.domain.enums.DirectionEnum;


/**
 * 分页参数数据模型
 *
 * @author zzhao
 */
@Data
@EqualsAndHashCode
@Schema(title = "分页参数数据模型", description = "分页参数数据模型")
public class IPage {

    private IPage() {
    }


    public static IPage build(long pageNo, long pageSize) {
        IPage IPage = new IPage();
        IPage.setPageNo(pageNo);
        IPage.setPageSize(pageSize);
        return IPage;
    }

    /**
     * 页码（查询的页码， 默认为1）
     */
    @NotNull(message = "分页数据页码为空")
    @Min(value = 1, message = "页码最小值为1")
    @Schema(title = "页码", description = "查询的页码， 默认为1")
    private long pageNo = 1;

    /**
     * 页大小（每页多少条，默认为10）
     * 如果页大小为-1时，则全量查询
     */
    @NotNull(message = "分页数据页大小为空")
    @Min(value = 1, message = "页大小最小值为1")
    @Schema(title = "页大小", description = "每页多少条，默认为10。如果页大小为-1时，则全量查询")
    private long pageSize = 10;

    /**
     * 查询起始索引
     */
    @Schema(title = "查询起始索引", description = "查询起始索引")
    private long startSize = 0;
    /**
     * 查询起始索引
     */
    @Schema(title = "查询终止索引", description = "查询终止索引")
    private long endSize = -1;

    /**
     * 是否查询总数量
     */
    @Getter
    @Schema(title = "是否查询总数量", description = "是否查询总数量")
    protected boolean searchCount = true;

    /**
     * 条件查询数据总数
     */
    @Schema(title = "条件查询数据总数", description = "值为-1时，表示未查询")
    private long total = -1;

    /**
     * 排序方式
     */
    @Schema(title = "排序方式", description = "排序方式")
    private DirectionEnum directionEnum = DirectionEnum.ASC;

    /**
     * 是否是最后一页
     */
    @Schema(title = "是否是最后一页", description = "是否是最后一页")
    private boolean lastPage;


    /**
     * 设置查询起始索引
     * 私有化以禁止赋值
     *
     * @param startSize 起始索引
     */
    private void setStartSize(long startSize) {
        this.startSize = startSize;
    }

    /**
     * 设置是否是最后一页标识
     * 私有化以禁止赋值
     *
     * @param lastPage 是否最后一页
     */
    private void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }


    public long getPageNo() {
        if (pageNo < 1) {
            this.pageNo = 1;
        }

        return this.pageNo;
    }

    public long getPageSize() {
        if (pageSize < 1) {
            this.pageSize = 10;
        }
        return this.pageSize;
    }

    /**
     * 获取查询起始索引
     */
    public long getStartSize() {
        if (this.startSize < 0) {
            this.startSize = (this.getPageNo() - 1) * this.getPageSize();
        }
        if (this.startSize < 0) {
            this.startSize = 0;
        }
        return this.startSize;
    }

    public long getEndSize() {
        if (this.endSize == 0) {
            this.endSize = this.getStartSize() + this.getPageSize() - 1;
        }
        if (this.endSize < -1) {
            this.endSize = -1L;
        }
        return this.endSize;
    }

    public long getTotal() {
        if (!this.isSearchCount()) {
            return -1L;
        }
        return total;
    }

    public boolean isLastPage() {
        if (this.getTotal() == 0) {
            //只要总数等于0，则表示最后一页
            return Boolean.TRUE;
        }

        if (!this.isSearchCount()
                && this.getTotal() < 0) {
            // 说明是不需要查询总数的
            return Boolean.FALSE;
        }

        if (this.getPageSize() < 0) {
            return Boolean.TRUE;
        }

        return (this.getTotal() + this.getPageSize() - 1) / this.getPageSize() == this.getPageNo();
    }

}

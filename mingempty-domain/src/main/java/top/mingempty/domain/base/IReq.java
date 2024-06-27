package top.mingempty.domain.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 外部包装类请求数据模型
 *
 * @author zzhao
 */
@Data
@Schema(title = "外部包装类请求数据模型", description = "外部包装类请求数据模型")
@EqualsAndHashCode(callSuper = true)
public class IReq<T> extends BaseHeaderModel {

    /**
     * 外部包装类请求数据
     */
    @Schema(title = "外部包装类请求数据", description = "外部包装类请求数据")
    private T data;

    /**
     * 外部包装类请求数据集合
     */
    @Schema(title = "外部包装类请求数据集合", description = "外部包装类请求数据集合")
    private List<T> dataList;

    private IReq() {
    }

    public static <T> IReq<T> build(T data) {
        IReq<T> iReq = new IReq<>();
        iReq.setData(data);
        return iReq;
    }

    public static <T> IReq<T> build(List<T> dataList) {
        IReq<T> iReq = new IReq<>();
        iReq.setDataList(dataList);
        return iReq;
    }

    public static <T> IReq<T> build(T data, IPage IPage) {
        IReq<T> iReq = new IReq<>();
        iReq.setData(data);
        iReq.setIPage(IPage);
        return iReq;
    }

    public static <T> IReq<T> build(List<T> dataList, IPage IPage) {
        IReq<T> iReq = new IReq<>();
        iReq.setDataList(dataList);
        iReq.setIPage(IPage);
        return iReq;
    }

    public static <T> IReq<T> build(T data, String uniqueBusinessNo) {
        IReq<T> iReq = new IReq<>();
        iReq.setData(data);
        iReq.setUniqueBusinessNo(uniqueBusinessNo);
        return iReq;
    }

    public static <T> IReq<T> build(List<T> dataList, String uniqueBusinessNo) {
        IReq<T> iReq = new IReq<>();
        iReq.setDataList(dataList);
        iReq.setUniqueBusinessNo(uniqueBusinessNo);
        return iReq;
    }

    public static <T> IReq<T> build(T data, String uniqueBusinessNo, IPage IPage) {
        IReq<T> iReq = new IReq<>();
        iReq.setData(data);
        iReq.setUniqueBusinessNo(uniqueBusinessNo);
        iReq.setIPage(IPage);
        return iReq;
    }

    public static <T> IReq<T> build(List<T> dataList, String uniqueBusinessNo, IPage IPage) {
        IReq<T> iReq = new IReq<>();
        iReq.setDataList(dataList);
        iReq.setUniqueBusinessNo(uniqueBusinessNo);
        iReq.setIPage(IPage);
        return iReq;
    }

    public static <T> IReq<T> build(T data, String uniqueBusinessNo, String version) {
        IReq<T> iReq = new IReq<>();
        iReq.setData(data);
        iReq.setUniqueBusinessNo(uniqueBusinessNo);
        iReq.setVersion(version);
        return iReq;
    }

    public static <T> IReq<T> build(List<T> dataList, String uniqueBusinessNo, String version) {
        IReq<T> iReq = new IReq<>();
        iReq.setDataList(dataList);
        iReq.setUniqueBusinessNo(uniqueBusinessNo);
        iReq.setVersion(version);
        return iReq;
    }

    public static <T> IReq<T> build(T data, String uniqueBusinessNo, String version, IPage IPage) {
        IReq<T> iReq = new IReq<>();
        iReq.setData(data);
        iReq.setUniqueBusinessNo(uniqueBusinessNo);
        iReq.setVersion(version);
        iReq.setIPage(IPage);
        return iReq;
    }

    public static <T> IReq<T> build(List<T> dataList, String uniqueBusinessNo, String version, IPage IPage) {
        IReq<T> iReq = new IReq<>();
        iReq.setDataList(dataList);
        iReq.setUniqueBusinessNo(uniqueBusinessNo);
        iReq.setVersion(version);
        iReq.setIPage(IPage);
        return iReq;
    }

}

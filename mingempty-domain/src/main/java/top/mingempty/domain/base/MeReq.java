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
public class MeReq<T> extends BaseHeaderModel {

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

    private MeReq() {
    }

    public static <T> MeReq<T> build(T data) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setData(data);
        return meReq;
    }

    public static <T> MeReq<T> build(List<T> dataList) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setDataList(dataList);
        return meReq;
    }

    public static <T> MeReq<T> build(T data, MePage MePage) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setData(data);
        meReq.setMePage(MePage);
        return meReq;
    }

    public static <T> MeReq<T> build(List<T> dataList, MePage MePage) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setDataList(dataList);
        meReq.setMePage(MePage);
        return meReq;
    }

    public static <T> MeReq<T> build(T data, String uniqueBusinessNo) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setData(data);
        meReq.setUniqueBusinessNo(uniqueBusinessNo);
        return meReq;
    }

    public static <T> MeReq<T> build(List<T> dataList, String uniqueBusinessNo) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setDataList(dataList);
        meReq.setUniqueBusinessNo(uniqueBusinessNo);
        return meReq;
    }

    public static <T> MeReq<T> build(T data, String uniqueBusinessNo, MePage MePage) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setData(data);
        meReq.setUniqueBusinessNo(uniqueBusinessNo);
        meReq.setMePage(MePage);
        return meReq;
    }

    public static <T> MeReq<T> build(List<T> dataList, String uniqueBusinessNo, MePage MePage) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setDataList(dataList);
        meReq.setUniqueBusinessNo(uniqueBusinessNo);
        meReq.setMePage(MePage);
        return meReq;
    }

    public static <T> MeReq<T> build(T data, String uniqueBusinessNo, String version) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setData(data);
        meReq.setUniqueBusinessNo(uniqueBusinessNo);
        meReq.setVersion(version);
        return meReq;
    }

    public static <T> MeReq<T> build(List<T> dataList, String uniqueBusinessNo, String version) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setDataList(dataList);
        meReq.setUniqueBusinessNo(uniqueBusinessNo);
        meReq.setVersion(version);
        return meReq;
    }

    public static <T> MeReq<T> build(T data, String uniqueBusinessNo, String version, MePage MePage) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setData(data);
        meReq.setUniqueBusinessNo(uniqueBusinessNo);
        meReq.setVersion(version);
        meReq.setMePage(MePage);
        return meReq;
    }

    public static <T> MeReq<T> build(List<T> dataList, String uniqueBusinessNo, String version, MePage MePage) {
        MeReq<T> meReq = new MeReq<>();
        meReq.setDataList(dataList);
        meReq.setUniqueBusinessNo(uniqueBusinessNo);
        meReq.setVersion(version);
        meReq.setMePage(MePage);
        return meReq;
    }

}

package top.mingempty.domain.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.mingempty.domain.enums.DefaultResultEnum;

import java.util.List;

/**
 * 响应数据模型
 *
 * @author zzhao
 */
@Data
@Schema(title = "响应数据模型")
@EqualsAndHashCode(callSuper = true)
public class MeRsp<T> extends BaseHeaderModel {
    /**
     * 响应编码
     */
    @Schema(title = "响应编码", description = "响应编码")
    private String code;

    /**
     * 响应信息
     */
    @Schema(title = "响应信息", description = "响应信息")
    private String message;

    /**
     * 响应数据
     */
    @Schema(title = "响应数据", description = "响应数据")
    private T data;

    /**
     * 响应数据集合
     */
    @Schema(title = "响应数据集合", description = "响应数据集合")
    private List<T> dataList;

    /**
     * 私有化构造器
     */
    private MeRsp() {
    }


    public static <T> MeRsp<T> success() {
        return build(DefaultResultEnum.SUCCESS.getCode(), DefaultResultEnum.SUCCESS.getMessage());
    }

    public static <T> MeRsp<T> success(T data) {
        return build(DefaultResultEnum.SUCCESS.getCode(), DefaultResultEnum.SUCCESS.getMessage(), data);
    }

    public static <T> MeRsp<T> success(List<T> dataList) {
        return build(DefaultResultEnum.SUCCESS.getCode(), DefaultResultEnum.SUCCESS.getMessage(), dataList);
    }

    public static <T> MeRsp<T> success(T data, MePage MePage) {
        return build(DefaultResultEnum.SUCCESS.getCode(), DefaultResultEnum.SUCCESS.getMessage(), data, MePage);
    }


    public static <T> MeRsp<T> success(List<T> dataList, MePage MePage) {
        return build(DefaultResultEnum.SUCCESS.getCode(), DefaultResultEnum.SUCCESS.getMessage(), dataList, MePage);
    }

    public static <T> MeRsp<T> success(String message, T data) {
        return build(DefaultResultEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> MeRsp<T> success(String message, List<T> dataList) {
        return build(DefaultResultEnum.SUCCESS.getCode(), message, dataList);
    }

    public static <T> MeRsp<T> success(String message, T data, MePage MePage) {
        return build(DefaultResultEnum.SUCCESS.getCode(), message, data, MePage);
    }

    public static <T> MeRsp<T> success(String message, List<T> dataList, MePage MePage) {
        return build(DefaultResultEnum.SUCCESS.getCode(), message, dataList, MePage);
    }

    public static <T> MeRsp<T> failed() {
        return build(DefaultResultEnum.FAILED.getCode(), DefaultResultEnum.FAILED.getMessage());
    }

    public static <T> MeRsp<T> failed(String message) {
        return build(DefaultResultEnum.FAILED.getCode(), message);
    }

    public static <T> MeRsp<T> failed(String message, T data) {
        return build(DefaultResultEnum.FAILED.getCode(), message, data);
    }

    public static <T> MeRsp<T> failed(String message, List<T> dataList) {
        return build(DefaultResultEnum.FAILED.getCode(), message, dataList);
    }

    public static <T> MeRsp<T> resubmit() {
        return build(DefaultResultEnum.RESUBMIT.getCode(), DefaultResultEnum.RESUBMIT.getMessage());
    }

    public static <T> MeRsp<T> resubmit(String message) {
        return build(DefaultResultEnum.RESUBMIT.getCode(), message);
    }

    public static <T> MeRsp<T> resubmit(String message, T data) {
        return build(DefaultResultEnum.RESUBMIT.getCode(), message, data);
    }

    public static <T> MeRsp<T> resubmit(String message, List<T> dataList) {
        return build(DefaultResultEnum.RESUBMIT.getCode(), message, dataList);
    }

    public static <T> MeRsp<T> notFound() {
        return build(DefaultResultEnum.NOT_FOUND.getCode(), DefaultResultEnum.NOT_FOUND.getMessage());
    }

    public static <T> MeRsp<T> notFound(String message) {
        return build(DefaultResultEnum.NOT_FOUND.getCode(), message);
    }

    public static <T> MeRsp<T> notFound(String message, T data) {
        return build(DefaultResultEnum.NOT_FOUND.getCode(), message, data);
    }

    public static <T> MeRsp<T> notFound(String message, List<T> dataList) {
        return build(DefaultResultEnum.NOT_FOUND.getCode(), message, dataList);
    }

    public static <T> MeRsp<T> loginError() {
        return build(DefaultResultEnum.LOGIN_ERROR.getCode(), DefaultResultEnum.LOGIN_ERROR.getMessage());
    }

    public static <T> MeRsp<T> loginError(String message) {
        return build(DefaultResultEnum.LOGIN_ERROR.getCode(), message);
    }

    public static <T> MeRsp<T> loginError(String message, T data) {
        return build(DefaultResultEnum.LOGIN_ERROR.getCode(), message, data);
    }

    public static <T> MeRsp<T> loginError(String message, List<T> dataList) {
        return build(DefaultResultEnum.LOGIN_ERROR.getCode(), message, dataList);
    }

    public static <T> MeRsp<T> validateFailed() {
        return build(DefaultResultEnum.VALIDATE_FAILED.getCode(), DefaultResultEnum.VALIDATE_FAILED.getMessage());
    }

    public static <T> MeRsp<T> validateFailed(String message) {
        return build(DefaultResultEnum.VALIDATE_FAILED.getCode(), message);
    }

    public static <T> MeRsp<T> validateFailed(String message, T data) {
        return build(DefaultResultEnum.VALIDATE_FAILED.getCode(), message, data);
    }

    public static <T> MeRsp<T> validateFailed(String message, List<T> dataList) {
        return build(DefaultResultEnum.VALIDATE_FAILED.getCode(), message, dataList);
    }

    public static <T> MeRsp<T> forbidden() {
        return build(DefaultResultEnum.FORBIDDEN.getCode(), DefaultResultEnum.FORBIDDEN.getMessage());
    }

    public static <T> MeRsp<T> forbidden(String message) {
        return build(DefaultResultEnum.FORBIDDEN.getCode(), message);
    }

    public static <T> MeRsp<T> forbidden(String message, T data) {
        return build(DefaultResultEnum.FORBIDDEN.getCode(), message, data);
    }

    public static <T> MeRsp<T> forbidden(String message, List<T> dataList) {
        return build(DefaultResultEnum.FORBIDDEN.getCode(), message, dataList);
    }

    public static <T> MeRsp<T> noLogin() {
        return build(DefaultResultEnum.NO_LOGIN.getCode(), DefaultResultEnum.NO_LOGIN.getMessage());
    }

    public static <T> MeRsp<T> noLogin(String message) {
        return build(DefaultResultEnum.NO_LOGIN.getCode(), message);
    }

    public static <T> MeRsp<T> noLogin(String message, T data) {
        return build(DefaultResultEnum.NO_LOGIN.getCode(), message, data);
    }

    public static <T> MeRsp<T> noLogin(String message, List<T> dataList) {
        return build(DefaultResultEnum.NO_LOGIN.getCode(), message, dataList);
    }

    public static <T> MeRsp<T> tokenOverdue() {
        return build(DefaultResultEnum.TOKEN_OVERDUE.getCode(), DefaultResultEnum.TOKEN_OVERDUE.getMessage());
    }

    public static <T> MeRsp<T> tokenOverdue(String message) {
        return build(DefaultResultEnum.TOKEN_OVERDUE.getCode(), message);
    }

    public static <T> MeRsp<T> tokenOverdue(String message, T data) {
        return build(DefaultResultEnum.TOKEN_OVERDUE.getCode(), message, data);
    }

    public static <T> MeRsp<T> tokenOverdue(String message, List<T> dataList) {
        return build(DefaultResultEnum.TOKEN_OVERDUE.getCode(), message, dataList);
    }


    public static <T> MeRsp<T> build(MeResult meResult) {
        return build(meResult.getCode(), meResult.getMessage());
    }

    public static <T> MeRsp<T> build(MeResult meResult, T data) {
        return build(meResult.getCode(), meResult.getMessage(), data);
    }

    public static <T> MeRsp<T> build(MeResult meResult, List<T> dataList) {
        return build(meResult.getCode(), meResult.getMessage(), dataList);
    }

    /**
     * 提供构造方法
     *
     * @param code
     * @param message
     * @param
     * @return
     */
    public static MeRsp build(String code, String message) {
        return build(code, message, null);
    }

    /**
     * 提供构造方法
     *
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> MeRsp<T> build(String code, String message, T data) {
        MeRsp<T> meRsp = new MeRsp<>();
        meRsp.setCode(code);
        meRsp.setMessage(message);
        meRsp.setData(data);
        return meRsp;
    }

    /**
     * 提供构造方法
     *
     * @param code
     * @param message
     * @param dataList
     * @param <T>
     * @return
     */
    public static <T> MeRsp<T> build(String code, String message, List<T> dataList) {
        MeRsp<T> meRsp = new MeRsp<>();
        meRsp.setCode(code);
        meRsp.setMessage(message);
        meRsp.setDataList(dataList);
        return meRsp;
    }

    /**
     * 提供构造方法
     *
     * @param code
     * @param message
     * @param data
     * @param MePage
     * @param <T>
     * @return
     */
    public static <T> MeRsp<T> build(String code, String message, T data, MePage MePage) {
        MeRsp<T> meRsp = new MeRsp<>();
        meRsp.setCode(code);
        meRsp.setMessage(message);
        meRsp.setData(data);
        meRsp.setMePage(MePage);
        return meRsp;
    }

    /**
     * 提供构造方法
     *
     * @param code
     * @param message
     * @param dataList
     * @param MePage
     * @param <T>
     * @return
     */
    public static <T> MeRsp<T> build(String code, String message, List<T> dataList, MePage MePage) {
        MeRsp<T> meRsp = new MeRsp<>();
        meRsp.setCode(code);
        meRsp.setMessage(message);
        meRsp.setDataList(dataList);
        meRsp.setMePage(MePage);
        return meRsp;
    }

}

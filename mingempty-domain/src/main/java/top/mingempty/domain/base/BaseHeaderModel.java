package top.mingempty.domain.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.mingempty.domain.other.DatePattern;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求响应公共参数
 *
 * @author zzhao
 */
@Data
@EqualsAndHashCode
@Schema(title = "请求响应公共参数", description = "请求响应公共参数")
public class BaseHeaderModel implements Serializable {

    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = 5179976721286297752L;


    /**
     * 外部交易流水号,唯一业务号,用于业务方幂等处理,可选
     */
    @Schema(title = "唯一业务号", description = "用于业务方幂等处理,可选；默认生成UUID")
    private String uniqueBusinessNo = UUID.randomUUID().toString();

    /**
     * 外部请求时间 yyyy-MM-dd HH:mm:ss.SSS
     */
    @Schema(title = "外部请求时间", description = "默认格式为：yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_MS_PATTERN, timezone = "GMT+8")
    private final LocalDateTime requestTime = LocalDateTime.now();

    /**
     * 匹配版本号
     */
    @Schema(title = "匹配版本号", description = "匹配版本号")
    private String version;

    /**
     * 分页参数数据模型
     */
    @Schema(title = "分页参数数据模型", description = "分页参数数据模型")
    private MePage mePage;

    /**
     * 其他参数
     */
    @Schema(title = "其他参数", description = "其他参数")
    private Map<String, Object> parameters = new ConcurrentHashMap<>(2);


    /**
     * 设置参数
     *
     * @param key   key
     * @param value value
     */
    public void putParameter(String key, Object value) {
        parameters.put(key, value);
    }

    /**
     * 获取参数
     *
     * @param key key
     * @return value
     */
    public Object getParameter(String key) {
        return parameters.get(key);
    }
}

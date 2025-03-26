package top.mingempty.meta.data.domain.biz.dict.service.info.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 扩展字段值信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExtraFieldValueInfo {

    /**
     * 扩展字段编码
     */
    private String extraFieldCode;

    /**
     * 扩展字段值
     */
    private Object extraFieldValue;
}

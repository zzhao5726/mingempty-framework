package top.mingempty.meta.data.facade.domain.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 条目详情查询的DTO
 */
@Data
@Schema(title = "条目详情查询的DTO")
public class EntryDetailsQueryDto {

    /**
     * 条目编号
     */
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 条目版本（默认1）
     */
    @Schema(title = "条目版本（默认1）")
    private Long entryVersion;

}

package top.mingempty.meta.data.facade.domain.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collection;

/**
 * 创建或修改条目基础信息的DTO
 */
@Data
@Schema(title = "创建或修改条目基础信息的DTO")
public class EntryBaseModifiesDto {

    /**
     * 条目编号
     */
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 条目信息
     */
    @Schema(title = "条目信息")
    private EntryModifiesDto entry;

    /**
     * 条目权限信息
     */
    @Schema(title = "条目权限信息")
    private Collection<AuthorizationModifiesDto> authorizations;

    /**
     * 条目扩展字段信息
     */
    @Schema(title = "条目扩展字段信息")
    private Collection<ExtraFieldModifiesDto> extraFields;
}

package top.mingempty.meta.data.facade.domain.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collection;

/**
 *
 * 创建或修改字典项基础信息的DTO
 */
@Data
@Schema(title = "创建或修改字典项基础信息的DTO")
public class ItemBaseModifiesDto {

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 字典项信息
     */
    private Collection<ItemModifiesDto> items;
}

package top.mingempty.meta.data.domain.biz.dict.service.info.modifies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * 条目基础信息变更改内部传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ItemBaseModifiesInfo {

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 字典项信息
     */
    private Collection<ItemModifiesInfo> items;
}

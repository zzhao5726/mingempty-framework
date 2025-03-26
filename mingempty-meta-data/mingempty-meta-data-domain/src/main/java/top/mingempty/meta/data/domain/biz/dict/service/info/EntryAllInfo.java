package top.mingempty.meta.data.domain.biz.dict.service.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 字典全量内部传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "allBuilder")
public class EntryAllInfo extends EntryBaseInfo {

    /**
     * 字典项信息
     */
    private List<ItemInfo> items;

    /**
     * 字典项标签信息
     */
    private List<LabelInfo> labels;


}

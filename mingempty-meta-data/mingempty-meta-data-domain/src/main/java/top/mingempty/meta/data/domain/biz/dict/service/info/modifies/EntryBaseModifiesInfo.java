package top.mingempty.meta.data.domain.biz.dict.service.info.modifies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 条目基础信息变更改内部传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EntryBaseModifiesInfo {

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 字典条目信息
     */
    private EntryModifiesInfo entry;

    /**
     * 字典权限信息
     */
    private List<AuthorizationModifiesInfo> authorizations;

    /**
     * 字典扩展字段信息
     */
    private List<ExtraFieldModifiesInfo> extraFields;
}

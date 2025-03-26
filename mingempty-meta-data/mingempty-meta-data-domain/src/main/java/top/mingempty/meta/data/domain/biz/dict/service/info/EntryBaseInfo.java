package top.mingempty.meta.data.domain.biz.dict.service.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 字典基础内部传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EntryBaseInfo {

    /**
     * 字典条目编码
     */
    private String entryCode;

    /**
     * 字典条目信息
     */
    private EntryInfo entry;

    /**
     * 字典权限信息
     */
    private List<AuthorizationInfo> authorizations;

    /**
     * 字典扩展字段信息
     */
    private List<ExtraFieldInfo> extraFields;

    /**
     * 字典条目操作历史信息
     */
    private List<OperationHistoryInfo> operationHistorys;


    public EntryAllInfo toEntryAllInfo() {
        return (EntryAllInfo) EntryAllInfo.builder().build()
                .setEntryCode(this.getEntryCode())
                .setEntry(this.getEntry())
                .setAuthorizations(this.getAuthorizations())
                .setExtraFields(this.getExtraFields())
                .setOperationHistorys(this.getOperationHistorys());

    }
}

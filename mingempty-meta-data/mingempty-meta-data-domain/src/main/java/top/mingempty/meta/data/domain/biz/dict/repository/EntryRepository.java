package top.mingempty.meta.data.domain.biz.dict.repository;

/**
 * 字典条目仓储接口
 *
 * @author zzhao
 */
public interface EntryRepository {


    /**
     * 获取条目最新版本
     *
     * @param entryCode 条目编码
     * @return
     */
    Long gainVersion(String entryCode);

}

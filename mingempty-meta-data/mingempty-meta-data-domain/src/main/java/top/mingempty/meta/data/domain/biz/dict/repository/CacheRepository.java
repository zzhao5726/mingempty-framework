package top.mingempty.meta.data.domain.biz.dict.repository;

/**
 * 基于redis的缓存仓储接口
 *
 * @author zzhao
 */
public interface CacheRepository {

    /**
     * 获取条目最新版本
     *
     * @param entryCode 条目编码
     * @return
     */
    Long gainVersion(String entryCode);


    /**
     * 设置条目最新版本
     *
     * @param entryCode     条目编码
     * @param entrytVersion 条目版本
     * @return
     */
    Long settingVersion(String entryCode, Long entrytVersion);

}

package top.mingempty.meta.data.service;

import com.mybatisflex.core.service.IService;
import top.mingempty.meta.data.model.enums.AuthorizationTypeEnum;
import top.mingempty.meta.data.model.po.AuthorizationPo;
import top.mingempty.meta.data.model.vo.AuthorizationVo;
import top.mingempty.meta.data.model.vo.in.EntryCreateVo;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 条目授权表 服务层。
 *
 * @author zzhao
 * @since 2025-03-19 23:49:55
 */
public interface AuthorizationService extends IService<AuthorizationPo> {

    /**
     * 创建用户权限信息
     *
     * @param entryCreateVo
     */
    void create(EntryCreateVo entryCreateVo);

    /**
     * 批量创建用户权限信息
     *
     * @param entryCreateVos
     */
    void create(Collection<EntryCreateVo> entryCreateVos);

    /**
     * 批量创建用户权限信息
     *
     * @param entryCreateVos
     * @param entryVersionMap
     */
    void create(Collection<EntryCreateVo> entryCreateVos, Map<String, Long> entryVersionMap);

    /**
     * 创建用户权限信息
     *
     * @param authorizationVo
     */
    void add(AuthorizationVo authorizationVo);

    /**
     * 创建用户权限信息
     *
     * @param authorizationVo
     * @param entryVersion
     */
    void add(AuthorizationVo authorizationVo, Long entryVersion);

    /**
     * 批量创建用户权限信息
     *
     * @param authorizationVos
     */
    void add(Collection<AuthorizationVo> authorizationVos);

    /**
     * 批量创建用户权限信息
     *
     * @param authorizationVos
     * @param entryVersionMap
     * @param newRecord
     */
    void add(Collection<AuthorizationVo> authorizationVos, Map<String, Long> entryVersionMap, boolean newRecord);

    /**
     * 删除用户权限信息
     *
     * @param authorizationVo
     */
    void delete(AuthorizationVo authorizationVo);

    /**
     * 删除用户权限信息
     *
     * @param authorizationVo
     * @param entryVersion
     */
    void delete(AuthorizationVo authorizationVo, Long entryVersion);

    /**
     * 批量删除用户权限信息
     *
     * @param authorizationVos
     */
    void delete(Collection<AuthorizationVo> authorizationVos);

    /**
     * 批量删除用户权限信息
     *
     * @param authorizationVos
     * @param entryVersionMap
     * @param newRecord
     */
    void delete(Collection<AuthorizationVo> authorizationVos, Map<String, Long> entryVersionMap, boolean newRecord);

    /**
     * 转移变更数据
     *
     * @param entryCode
     */
    void transferChange(String entryCode);

    /**
     * 转移变更数据
     *
     * @param entryCodes
     */
    void transferChange(Collection<String> entryCodes);

    /**
     * 查询权限信息
     *
     * @param entryCode
     * @return
     */
    Map<AuthorizationTypeEnum, Set<String>> quertAuthorization(String entryCode);


    /**
     * 查询权限信息
     *
     * @param entryCodes
     * @return
     */
    Map<String, Map<AuthorizationTypeEnum, Set<String>>> quertAuthorization(Collection<String> entryCodes);

    /**
     * 校验权限信息
     *
     * @param entryCode
     */
    void checkAuthorization(String entryCode);

    /**
     * 校验权限信息
     *
     * @param entryCodes
     */
    void checkAuthorization(Collection<String> entryCodes);
}

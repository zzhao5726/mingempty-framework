package top.mingempty.meta.data.domain.biz.dict.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import top.mingempty.commons.util.CollectionUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.commons.exception.DictNoAuthorizationModifiesException;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.value.AuthorizationVo;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;
import top.mingempty.meta.data.domain.enums.AuthorizationTypeEnum;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 字典条目权限领域模型
 */
public class AuthorizationModel {

    /**
     * 是否需要权限校验，默认需要
     */
    private final static ThreadLocal<Integer> CHECK_AUTHORIZATION_FLAG = ThreadLocal.withInitial(() -> 1);

    /**
     * 禁用权限校验
     */
    public static void disableCheckAuthorization() {
        CHECK_AUTHORIZATION_FLAG.set(0);
    }


    /**
     * 是否需要权限校验
     *
     * @return
     */
    public static boolean isCheckAuthorization() {
        return CHECK_AUTHORIZATION_FLAG.get() == 1;
    }

    /**
     * 清除权限校验禁用标识
     */
    public static void clearDisableCheckAuthorization() {
        CHECK_AUTHORIZATION_FLAG.remove();
    }

    /**
     * 条目编号
     */
    @Getter
    private final String entryCode;

    /**
     * 条目版本（默认1）
     */
    @Setter
    @Getter
    private Long entryVersion;


    /**
     * 字典条目权限信息值对象
     */
    private final List<AuthorizationVo> authorizationVos = new CopyOnWriteArrayList<>();

    /**
     * 字典条目权限信息值对象备份数据
     */
    private final List<AuthorizationVo> authorizationVosByBack = new CopyOnWriteArrayList<>();

    /**
     * 字典条目权限信息值对象修改数据
     */
    private final Collection<AuthorizationVo> authorizationsByChange = new CopyOnWriteArraySet<>();

    /**
     * 字典条目权限信息值对象
     */
    private final Map<AuthorizationVo, AuthorizationVo> authorizations = new ConcurrentHashMap<>();

    /**
     * 字典条目权限信息值对象备份数据
     */
    private final Map<AuthorizationVo, AuthorizationVo> authorizationsByBack = new ConcurrentHashMap<>();


    public AuthorizationModel(String entryCode, Collection<AuthorizationVo> authorizationVos) {
        this.entryCode = entryCode;
        if (CollUtil.isEmpty(authorizationVos)) {
            return;
        }
        authorizationVos.parallelStream()
                .forEach(authorization -> {
                    this.authorizationVos.add(authorization);
                    this.authorizationVosByBack.add(authorization);
                    this.authorizations.put(authorization, authorization);
                    this.authorizationsByBack.put(authorization, authorization);
                });
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AuthorizationModel that)) return false;
        return Objects.equals(getEntryCode(), that.getEntryCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEntryCode());
    }

    public List<AuthorizationVo> getAuthorizations() {
        return List.copyOf(this.authorizationVos);
    }

    public List<AuthorizationVo> getAuthorizationsByBack() {
        return List.copyOf(this.authorizationVosByBack);
    }

    public List<AuthorizationVo> getAuthorizationsByChange() {
        return List.copyOf(this.authorizationsByChange);
    }

    /**
     * 获取某一个条目的权限信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param authorizationType 条目权限类型
     * @param authorizationCode 条目权限编码
     * @return
     */
    public AuthorizationVo gainOne(AuthorizationTypeEnum authorizationType, String authorizationCode) {
        if (!exists(authorizationType, authorizationCode)) {
            return null;
        }

        return gainOne(AuthorizationVo.builder()
                .authorizationType(authorizationType)
                .authorizationCode(authorizationCode)
                .build());
    }


    /**
     * 获取某一个条目的权限信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param authorizationVo 条目权限
     * @return
     */
    public AuthorizationVo gainOne(AuthorizationVo authorizationVo) {
        if (ObjUtil.isEmpty(authorizationVo)) {
            return null;
        }
        return authorizations.get(authorizationVo);
    }


    /**
     * 基于备份数据获取某一个条目的权限信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param authorizationType 条目权限类型
     * @param authorizationCode 条目权限编码
     * @return
     */
    public AuthorizationVo gainBackOne(AuthorizationTypeEnum authorizationType, String authorizationCode) {
        if (!existsBack(authorizationType, authorizationCode)) {
            return null;
        }

        return gainBackOne(AuthorizationVo.builder()
                .authorizationType(authorizationType)
                .authorizationCode(authorizationCode)
                .build());
    }


    /**
     * 基于备份数据获取某一个条目的权限信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param authorizationVo 条目权限
     * @return
     */
    public AuthorizationVo gainBackOne(AuthorizationVo authorizationVo) {
        if (ObjUtil.isEmpty(authorizationVo)) {
            return null;
        }
        return authorizationsByBack.get(authorizationVo);
    }


    /**
     * 添加条目权限
     *
     * @param authorizationType 条目权限类型
     * @param authorizationCode 条目权限编码
     */
    public void add(AuthorizationTypeEnum authorizationType, String authorizationCode) {
        modifies(authorizationType, authorizationCode, ZeroOrOneEnum.ZERO);
    }


    /**
     * 删除条目权限
     *
     * @param authorizationType 条目权限类型
     * @param authorizationCode 条目权限编码
     */
    public void delete(AuthorizationTypeEnum authorizationType, String authorizationCode) {
        modifies(authorizationType, authorizationCode, ZeroOrOneEnum.ONE);
    }

    /**
     * 修改条目权限
     *
     * @param authorizations 条目权限集合
     */
    public void modifies(Collection<AuthorizationVo> authorizations) {
        if (authorizations == null) {
            authorizations = new CopyOnWriteArraySet<>();
        }
        // 处理删除逻辑
        Collection<AuthorizationVo> toDelete = CollectionUtil.subtract(this.authorizationsByBack.values(), authorizations);
        toDelete.parallelStream()
                .filter(authorizationVo -> !isDelete(authorizationVo))
                .map(AuthorizationVo::delete)
                .forEach(authorizations::add);
        authorizations.parallelStream()
                .forEach(authorizationVo
                        -> modifies(authorizationVo.getAuthorizationType(),
                        authorizationVo.getAuthorizationCode(),
                        authorizationVo.deleteStatus())
                );
    }

    /**
     * 修改条目权限
     *
     * @param authorizationType 条目权限类型
     * @param authorizationCode 条目权限编码
     * @param deleteStatus      条目权限逻辑删除状态
     */
    private void modifies(AuthorizationTypeEnum authorizationType, String authorizationCode, ZeroOrOneEnum deleteStatus) {
        checkModifies(authorizationType, authorizationCode);
        AuthorizationVo authorizationVo = AuthorizationVo.builder()
                .authorizationType(authorizationType)
                .authorizationCode(authorizationCode)
                .deleteStatus(DeleteVo.builder()
                        .deleteStatus(Optional.ofNullable(deleteStatus).orElse(ZeroOrOneEnum.ZERO))
                        .build())
                .build();
        if (checkSame(authorizationVo)) {
            return;
        }
        authorizationVos.add(authorizationVo);
        authorizationVosByBack.add(authorizationVo);
        authorizations.put(authorizationVo, authorizationVo);
        authorizationsByChange.add(authorizationVo);
    }


    public void checkModifies(AuthorizationTypeEnum authorizationType, String authorizationCode) {
        if (ObjUtil.isEmpty(authorizationType)
                || StrUtil.isEmpty(authorizationCode)) {
            throw new MetaDataException("meta-data-domain-0000000006");
        }
    }

    /**
     * 判断条目权限是否存在
     *
     * @param authorizationType 条目权限类型
     * @param authorizationCode 条目权限编码
     * @return
     */
    public boolean exists(AuthorizationTypeEnum authorizationType, String authorizationCode) {
        if (ObjUtil.isEmpty(authorizationType)
                || StrUtil.isEmpty(authorizationCode)) {
            return false;
        }
        return exists(AuthorizationVo.builder()
                .authorizationType(authorizationType)
                .authorizationCode(authorizationCode)
                .build());
    }

    /**
     * 判断条目权限是否存在
     *
     * @param authorizationVo 条目权限
     * @return
     */
    public boolean exists(AuthorizationVo authorizationVo) {
        if (ObjUtil.isEmpty(authorizationVo)) {
            return false;
        }
        return authorizations.containsKey(authorizationVo);
    }

    /**
     * 基于备份数据判断条目权限是否存在
     *
     * @param authorizationType 条目权限类型
     * @param authorizationCode 条目权限编码
     * @return
     */
    public boolean existsBack(AuthorizationTypeEnum authorizationType, String authorizationCode) {
        if (ObjUtil.isEmpty(authorizationType)
                || StrUtil.isEmpty(authorizationCode)) {
            return false;
        }
        return existsBack(AuthorizationVo.builder()
                .authorizationType(authorizationType)
                .authorizationCode(authorizationCode)
                .build());
    }

    /**
     * 基于备份数据判断条目权限是否存在
     *
     * @param authorizationVo 条目权限
     * @return
     */
    public boolean existsBack(AuthorizationVo authorizationVo) {
        if (ObjUtil.isEmpty(authorizationVo)) {
            return false;
        }
        return authorizationsByBack.containsKey(authorizationVo);
    }

    /**
     * 条目权限是否被删除
     * <p>
     * 当未找到当前条目权限数据时，默认为被删除
     *
     * @param authorizationType 条目权限类型
     * @param authorizationCode 条目权限编码
     * @return
     */
    public boolean isDelete(AuthorizationTypeEnum authorizationType, String authorizationCode) {
        if (ObjUtil.isEmpty(authorizationType)
                || StrUtil.isEmpty(authorizationCode)) {
            return true;
        }

        return isDelete(AuthorizationVo.builder()
                .authorizationType(authorizationType)
                .authorizationCode(authorizationCode)
                .build());
    }

    /**
     * 条目权限是否被删除
     * <p>
     * 当未找到当前条目权限数据时，默认为被删除
     *
     * @param authorizationVo 条目权限
     * @return
     */
    public boolean isDelete(AuthorizationVo authorizationVo) {
        if (ObjUtil.isEmpty(authorizationVo)) {
            return true;
        }
        return Optional.ofNullable(authorizations.get(authorizationVo))
                .map(AuthorizationVo::deleteStatus)
                .orElse(ZeroOrOneEnum.ONE)
                .equals(ZeroOrOneEnum.ONE);
    }

    /**
     * 检查数据是否完全一致
     *
     * @param authorizationVo
     * @return
     */
    public boolean checkSame(AuthorizationVo authorizationVo) {
        if (ObjUtil.isEmpty(authorizationVo)) {
            return false;
        }
        AuthorizationVo authorizationVoByExists = gainOne(authorizationVo);
        if (ObjUtil.isEmpty(authorizationVoByExists)) {
            return false;
        }
        return checkSame(authorizationVo, authorizationVoByExists);
    }


    /**
     * 检查数据是否完全一致
     *
     * @param authorizationVo
     * @param authorizationVo2
     * @return
     */
    public boolean checkSame(AuthorizationVo authorizationVo, AuthorizationVo authorizationVo2) {
        return Objects.equals(authorizationVo, authorizationVo2)
                && Objects.equals(authorizationVo.getDeleteStatus(), authorizationVo2.getDeleteStatus());
    }


    /**
     * 克隆一个对象
     *
     * @return
     */
    public AuthorizationModel cloneOne() {
        AuthorizationModel authorizationModel = new AuthorizationModel(this.getEntryCode(), this.getAuthorizations());
        authorizationModel.setEntryVersion(this.getEntryVersion());
        return authorizationModel;
    }


    /**
     * 克隆一个对象
     *
     * @return
     */
    public static AuthorizationModel cloneOne(String entryCode, AuthorizationModel authorizationModel) {
        if (ObjUtil.isEmpty(authorizationModel)) {
            return new AuthorizationModel(entryCode, List.of());
        }
        return authorizationModel.cloneOne();
    }

    /**
     * 检查条目权限
     *
     * @param userCode
     * @param roleCodes
     */
    public void checkAuthorization(String userCode, Set<String> roleCodes) throws DictNoAuthorizationModifiesException {
        if (CollUtil.isEmpty(this.getAuthorizations())) {
            return;
        }
        Set<String> roleCodesByNew = Optional.ofNullable(roleCodes)
                .orElse(Set.of());
        if (this.getAuthorizations()
                .parallelStream()
                .noneMatch(authorizationModel -> switch (authorizationModel.getAuthorizationType()) {
                    case ONE -> authorizationModel.getAuthorizationCode().equals(userCode);
                    case TWO -> roleCodesByNew.contains(authorizationModel.getAuthorizationCode());
                })) {
            //没有找到就抛出异常
            throw new DictNoAuthorizationModifiesException();
        }
    }
}

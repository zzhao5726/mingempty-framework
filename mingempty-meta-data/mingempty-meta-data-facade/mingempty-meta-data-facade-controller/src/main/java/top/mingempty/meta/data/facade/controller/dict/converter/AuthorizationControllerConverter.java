package top.mingempty.meta.data.facade.controller.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.AuthorizationModifiesInfo;
import top.mingempty.meta.data.facade.domain.dict.AuthorizationModifiesDto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 条目相关控制器转换器
 *
 * @author zzhao
 */
public class AuthorizationControllerConverter {


    public static AuthorizationModifiesInfo modifiesDtoToInfo(AuthorizationModifiesDto authorizationModifiesDto) {
        if (ObjUtil.isEmpty(authorizationModifiesDto)) {
            return null;
        }
        return AuthorizationModifiesInfo.builder()
                .authorizationType(BaseControllerConverter.authorizationTypeWithDefault(authorizationModifiesDto.getAuthorizationType()))
                .authorizationCode(authorizationModifiesDto.getAuthorizationCode())
                .deleteStatus(BaseControllerConverter.zeroOrOneEnumWithDefault(authorizationModifiesDto.getDeleteStatus()))
                .build();
    }


    public static List<AuthorizationModifiesInfo> modifiesDtoToInfo(Collection<AuthorizationModifiesDto> authorizationModifiesDtos) {
        if (CollUtil.isEmpty(authorizationModifiesDtos)) {
            return List.of();
        }

        return authorizationModifiesDtos.parallelStream()
                .map(AuthorizationControllerConverter::modifiesDtoToInfo)
                .filter(ObjUtil::isNotNull)
                .collect(Collectors.toList());
    }
}

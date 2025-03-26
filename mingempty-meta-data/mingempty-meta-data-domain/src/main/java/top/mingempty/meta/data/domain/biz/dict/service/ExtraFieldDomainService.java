package top.mingempty.meta.data.domain.biz.dict.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.model.ExtraFieldModel;
import top.mingempty.meta.data.domain.biz.dict.query.ExtraFieldQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.ExtraFieldRepository;
import top.mingempty.meta.data.domain.biz.dict.service.converter.ExtraFieldDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.info.ExtraFieldInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ExtraFieldModifiesInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 字典扩展字段信息领域服务
 */
@Slf4j
@Service
@AllArgsConstructor
public class ExtraFieldDomainService implements BaseDomainService<ExtraFieldInfo, ExtraFieldQuery> {

    private final ExtraFieldRepository extraFieldRepository;

    /**
     * 修改字典扩展字段信息
     *
     * @param entryCode               条目编码
     * @param entryChangeVertion      当前修改版本
     * @param extraFieldModifiesInfos 字典扩展字段信息变更改内部传输对象集合
     * @return
     */
    public void modifies(String entryCode, Long entryChangeVertion,
                         Collection<ExtraFieldModifiesInfo> extraFieldModifiesInfos) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(entryChangeVertion)) {
            throw new MetaDataException("meta-data-domain-0000000004");
        }
        if (CollUtil.isEmpty(extraFieldModifiesInfos)) {
            return;
        }
        ExtraFieldModel extraFieldModels = Optional.ofNullable(extraFieldRepository.query(entryCode))
                .map(ExtraFieldModel::cloneOne)
                .orElse(new ExtraFieldModel(entryCode, List.of()));
        extraFieldModels.setEntryVersion(entryChangeVertion);
        extraFieldModels.modifies(ExtraFieldDomainConverter.modifiesInfoToVos(extraFieldModifiesInfos));
        extraFieldRepository.modifies(extraFieldModels);
    }

    @Override
    public List<ExtraFieldInfo> query(ExtraFieldQuery extraFieldQuery, final MePage page) {
        List<ExtraFieldModel> extraFieldModels = extraFieldRepository.query(extraFieldQuery, page);
        return ExtraFieldDomainConverter.modelToInfos(extraFieldModels);
    }

    @Override
    public ExtraFieldQuery gainQuery(String entryCode) {
        return ExtraFieldQuery.builder().entryCode(entryCode).build();
    }

    @Override
    public ExtraFieldQuery gainQuery(Collection<String> entryCodes) {
        return ExtraFieldQuery.builder().entryCodes(entryCodes).build();
    }
}

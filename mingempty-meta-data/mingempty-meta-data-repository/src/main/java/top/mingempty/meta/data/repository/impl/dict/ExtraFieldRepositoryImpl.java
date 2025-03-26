package top.mingempty.meta.data.repository.impl.dict;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.model.ExtraFieldModel;
import top.mingempty.meta.data.domain.biz.dict.query.ExtraFieldQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.ExtraFieldRepository;
import top.mingempty.meta.data.repository.impl.dict.converter.ExtraFieldRepositoryConverter;
import top.mingempty.meta.data.repository.model.po.ChangeExtraFieldPo;
import top.mingempty.meta.data.repository.model.po.ExtraFieldPo;
import top.mingempty.meta.data.repository.service.ChangeExtraFieldService;
import top.mingempty.meta.data.repository.service.ExtraFieldService;

import java.util.List;
import java.util.Optional;

/**
 * 字典扩展字段信息仓储接口实现
 *
 * @author zzhao
 */
@Slf4j
@Component
@AllArgsConstructor
public class ExtraFieldRepositoryImpl implements ExtraFieldRepository {

    private final ExtraFieldService extraFieldService;

    private final ChangeExtraFieldService changeExtraFieldService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifies(ExtraFieldModel extraField) {
        if (ObjUtil.isEmpty(extraField)
                || StrUtil.isEmpty(extraField.getEntryCode())
                || CollUtil.isEmpty(extraField.getExtraFieldsByChange())) {
            return;
        }

        List<ChangeExtraFieldPo> changeExtraFieldPos = ExtraFieldRepositoryConverter.modelToChangePos(extraField);
        changeExtraFieldService.saveBatch(changeExtraFieldPos);
        extraFieldService.transferChange(extraField.getEntryCode());
    }

    @Override
    public List<ExtraFieldModel> query(ExtraFieldQuery extraFieldQuery, final MePage mePage) {
        if (ObjUtil.isEmpty(extraFieldQuery)) {
            return List.of();
        }
        return Optional.ofNullable(extraFieldQuery.getEntryVersion())
                .map(query -> changeExtraFieldService.query(extraFieldQuery, mePage))
                .map(ExtraFieldRepositoryConverter::changePoToModels)
                .orElseGet(() -> {
                    List<ExtraFieldPo> extraFieldPos = extraFieldService.query(extraFieldQuery, mePage);
                    return ExtraFieldRepositoryConverter.poToModels(extraFieldPos);
                });
    }
}

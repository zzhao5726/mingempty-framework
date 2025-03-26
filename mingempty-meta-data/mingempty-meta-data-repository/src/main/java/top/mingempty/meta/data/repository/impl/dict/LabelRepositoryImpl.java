package top.mingempty.meta.data.repository.impl.dict;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.model.LabelModel;
import top.mingempty.meta.data.domain.biz.dict.query.LabelQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.LabelRepository;
import top.mingempty.meta.data.repository.service.ChangeLabelService;
import top.mingempty.meta.data.repository.service.LabelService;

import java.util.List;

/**
 * 字典项标签仓储接口实现
 *
 * @author zzhao
 */
@Slf4j
@Component
@AllArgsConstructor
public class LabelRepositoryImpl implements LabelRepository {

    private final LabelService labelService;

    private final ChangeLabelService changeLabelService;

    @Override
    public List<LabelModel> query(LabelQuery query, final MePage page) {
        return List.of();
    }
}

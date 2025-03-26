package top.mingempty.meta.data.domain.biz.dict.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.LabelQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.LabelRepository;
import top.mingempty.meta.data.domain.biz.dict.service.info.LabelInfo;

import java.util.Collection;
import java.util.List;

/**
 * 字典项标签签领域服务
 */
@Slf4j
@Service
@AllArgsConstructor
public class LabelDomainService implements BaseDomainService<LabelInfo, LabelQuery> {

    private final LabelRepository labelRepository;

    @Override
    public List<LabelInfo> query(LabelQuery labelQuery,final MePage page) {
        return List.of();
    }

    @Override
    public LabelQuery gainQuery(String entryCode) {
        return LabelQuery.builder().entryCode(entryCode).build();
    }

    @Override
    public LabelQuery gainQuery(Collection<String> entryCodes) {
        return LabelQuery.builder().entryCodes(entryCodes).build();
    }
}

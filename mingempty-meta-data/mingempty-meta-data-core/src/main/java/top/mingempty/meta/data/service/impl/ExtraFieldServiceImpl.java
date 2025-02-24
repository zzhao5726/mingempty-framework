package top.mingempty.meta.data.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.meta.data.mapper.ExtraFieldMapper;
import top.mingempty.meta.data.model.po.ExtraFieldPo;
import top.mingempty.meta.data.model.vo.in.EntryCreateVo;
import top.mingempty.meta.data.service.ExtraFieldService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 字典扩展字段信息表 服务层实现。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Service
public class ExtraFieldServiceImpl extends ServiceImpl<ExtraFieldMapper, ExtraFieldPo> implements ExtraFieldService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(EntryCreateVo entryCreateVo) {
        create(List.of(entryCreateVo));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(Collection<EntryCreateVo> entryCreateVos) {
        create(entryCreateVos, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(Collection<EntryCreateVo> entryCreateVos, Map<String, Long> entryVersionMap) {

    }
}

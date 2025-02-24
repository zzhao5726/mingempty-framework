package top.mingempty.meta.data.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.mapper.LabelMapper;
import top.mingempty.meta.data.model.po.LabelPo;
import top.mingempty.meta.data.service.LabelService;

/**
 * 字典项标签表 服务层实现。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, LabelPo>  implements LabelService{

}

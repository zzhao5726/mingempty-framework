package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.LabelMapper;
import top.mingempty.meta.data.repository.model.po.LabelPo;
import top.mingempty.meta.data.repository.service.LabelService;

/**
 * 字典项标签表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, LabelPo>  implements LabelService{

}

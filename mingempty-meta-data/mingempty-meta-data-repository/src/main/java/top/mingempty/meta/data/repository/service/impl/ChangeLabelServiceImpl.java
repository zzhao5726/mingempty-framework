package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.ChangeLabelMapper;
import top.mingempty.meta.data.repository.model.po.ChangeLabelPo;
import top.mingempty.meta.data.repository.service.ChangeLabelService;

/**
 * 字典项标签变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ChangeLabelServiceImpl extends ServiceImpl<ChangeLabelMapper, ChangeLabelPo>  implements ChangeLabelService{

}

package top.mingempty.meta.data.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.mapper.ChangeLabelMapper;
import top.mingempty.meta.data.model.po.ChangeLabelPo;
import top.mingempty.meta.data.service.ChangeLabelService;

/**
 * 字典项标签变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Service
public class ChangeLabelServiceImpl extends ServiceImpl<ChangeLabelMapper, ChangeLabelPo>  implements ChangeLabelService{

}

package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.ExtraFieldMapper;
import top.mingempty.meta.data.repository.model.po.ExtraFieldPo;
import top.mingempty.meta.data.repository.service.ExtraFieldService;

/**
 * 字典扩展字段信息表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ExtraFieldServiceImpl extends ServiceImpl<ExtraFieldMapper, ExtraFieldPo>  implements ExtraFieldService{

}

package top.mingempty.meta.data.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.mapper.ExtraFieldMapper;
import top.mingempty.meta.data.model.po.ExtraFieldPo;
import top.mingempty.meta.data.service.ExtraFieldService;

/**
 * 字典扩展字段信息表 服务层实现。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Service
public class ExtraFieldServiceImpl extends ServiceImpl<ExtraFieldMapper, ExtraFieldPo>  implements ExtraFieldService{

}

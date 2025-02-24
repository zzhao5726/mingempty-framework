package top.mingempty.meta.data.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.mapper.ChangeItemMapper;
import top.mingempty.meta.data.model.po.ChangeItemPo;
import top.mingempty.meta.data.service.ChangeItemService;

/**
 * 字典项变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Service
public class ChangeItemServiceImpl extends ServiceImpl<ChangeItemMapper, ChangeItemPo>  implements ChangeItemService{

}

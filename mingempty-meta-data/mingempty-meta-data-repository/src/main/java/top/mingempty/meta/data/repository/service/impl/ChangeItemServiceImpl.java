package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.ChangeItemMapper;
import top.mingempty.meta.data.repository.model.po.ChangeItemPo;
import top.mingempty.meta.data.repository.service.ChangeItemService;

/**
 * 字典项变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ChangeItemServiceImpl extends ServiceImpl<ChangeItemMapper, ChangeItemPo>  implements ChangeItemService{

}

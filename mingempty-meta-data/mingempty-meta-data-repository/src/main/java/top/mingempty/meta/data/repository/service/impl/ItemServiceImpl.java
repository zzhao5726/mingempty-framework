package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.ItemMapper;
import top.mingempty.meta.data.repository.model.po.ItemPo;
import top.mingempty.meta.data.repository.service.ItemService;

/**
 * 字典项表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, ItemPo>  implements ItemService{

}

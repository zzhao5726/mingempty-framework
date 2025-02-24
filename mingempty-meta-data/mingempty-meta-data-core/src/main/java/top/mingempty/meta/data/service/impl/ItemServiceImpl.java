package top.mingempty.meta.data.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.mapper.ItemMapper;
import top.mingempty.meta.data.model.po.ItemPo;
import top.mingempty.meta.data.service.ItemService;

/**
 * 字典项表 服务层实现。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, ItemPo>  implements ItemService{

}

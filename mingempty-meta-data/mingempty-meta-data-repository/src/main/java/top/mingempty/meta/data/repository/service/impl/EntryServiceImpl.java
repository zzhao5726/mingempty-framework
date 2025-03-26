package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.EntryMapper;
import top.mingempty.meta.data.repository.model.po.EntryPo;
import top.mingempty.meta.data.repository.service.EntryService;

/**
 * 字典条目表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class EntryServiceImpl extends ServiceImpl<EntryMapper, EntryPo>  implements EntryService{

}

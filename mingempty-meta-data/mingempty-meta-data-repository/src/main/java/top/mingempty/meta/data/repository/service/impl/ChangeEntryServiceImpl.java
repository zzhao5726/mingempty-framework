package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.ChangeEntryMapper;
import top.mingempty.meta.data.repository.model.po.ChangeEntryPo;
import top.mingempty.meta.data.repository.service.ChangeEntryService;

/**
 * 字典条目变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ChangeEntryServiceImpl extends ServiceImpl<ChangeEntryMapper, ChangeEntryPo>  implements ChangeEntryService{

}

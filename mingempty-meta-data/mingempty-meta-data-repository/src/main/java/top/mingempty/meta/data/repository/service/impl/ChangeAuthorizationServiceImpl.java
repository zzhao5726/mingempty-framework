package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.ChangeAuthorizationMapper;
import top.mingempty.meta.data.repository.model.po.ChangeAuthorizationPo;
import top.mingempty.meta.data.repository.service.ChangeAuthorizationService;

/**
 * 条目授权变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ChangeAuthorizationServiceImpl extends ServiceImpl<ChangeAuthorizationMapper, ChangeAuthorizationPo>  implements ChangeAuthorizationService{

}

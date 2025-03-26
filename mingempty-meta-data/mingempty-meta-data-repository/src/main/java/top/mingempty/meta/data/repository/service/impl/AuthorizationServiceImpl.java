package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.AuthorizationMapper;
import top.mingempty.meta.data.repository.model.po.AuthorizationPo;
import top.mingempty.meta.data.repository.service.AuthorizationService;

/**
 * 条目授权表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationMapper, AuthorizationPo>  implements AuthorizationService{

}

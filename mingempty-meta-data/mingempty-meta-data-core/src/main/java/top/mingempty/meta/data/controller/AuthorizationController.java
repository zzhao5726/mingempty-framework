package top.mingempty.meta.data.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.meta.data.model.po.AuthorizationPo;
import top.mingempty.meta.data.service.AuthorizationService;
import top.mingempty.mybatis.flex.extension.controller.EasyBaseController;

/**
 * 条目授权表 控制层。
 *
 * @author zzhao
 * @since 2025-03-19 23:49:55
 */
@RestController
@Tag(name = "条目授权表")
@RequestMapping("/authorization")
public class AuthorizationController extends EasyBaseController<AuthorizationService, AuthorizationPo> {



}

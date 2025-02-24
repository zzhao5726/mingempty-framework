package top.mingempty.meta.data.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.meta.data.model.po.ExtraFieldPo;
import top.mingempty.meta.data.service.ExtraFieldService;
import top.mingempty.mybatis.flex.extension.controller.EasyBaseController;

/**
 * 字典扩展字段信息表 控制层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@RestController
@Tag(name = "字典扩展字段信息表")
@RequestMapping("/extra-field")
public class ExtraFieldController extends EasyBaseController<ExtraFieldService, ExtraFieldPo> {



}

package top.mingempty.meta.data.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.meta.data.model.po.LabelPo;
import top.mingempty.meta.data.service.LabelService;
import top.mingempty.mybatis.flex.extension.controller.EasyBaseController;

/**
 * 字典项标签表 控制层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@RestController
@Tag(name = "字典项标签表")
@RequestMapping("/label")
public class LabelController extends EasyBaseController<LabelService, LabelPo> {



}

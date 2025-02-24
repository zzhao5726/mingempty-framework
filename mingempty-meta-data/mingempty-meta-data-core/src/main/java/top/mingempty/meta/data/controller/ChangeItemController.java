package top.mingempty.meta.data.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.meta.data.model.po.ChangeItemPo;
import top.mingempty.meta.data.service.ChangeItemService;
import top.mingempty.mybatis.flex.extension.controller.EasyBaseController;

/**
 * 字典项变化流水表 控制层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@RestController
@Tag(name = "字典项变化流水表")
@RequestMapping("/change-item")
public class ChangeItemController extends EasyBaseController<ChangeItemService, ChangeItemPo> {



}

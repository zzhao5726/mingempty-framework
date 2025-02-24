package top.mingempty.meta.data.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.meta.data.model.po.ItemPo;
import top.mingempty.meta.data.service.ItemService;
import top.mingempty.mybatis.flex.extension.controller.EasyBaseController;

/**
 * 字典项表 控制层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@RestController
@Tag(name = "字典项表")
@RequestMapping("/item")
public class ItemController extends EasyBaseController<ItemService, ItemPo> {



}

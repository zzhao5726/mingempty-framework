package top.mingempty.meta.data.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.meta.data.model.po.ChangeEntryPo;
import top.mingempty.meta.data.service.ChangeEntryService;
import top.mingempty.mybatis.flex.extension.controller.EasyBaseController;

/**
 * 字典条目变化流水表 控制层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@RestController
@Tag(name = "字典条目变化流水表")
@RequestMapping("/change-entry")
public class ChangeEntryController extends EasyBaseController<ChangeEntryService, ChangeEntryPo> {



}

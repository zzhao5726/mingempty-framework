package top.mingempty.meta.data.facade.controller.dict;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.domain.base.MeReq;
import top.mingempty.domain.base.MeRsp;
import top.mingempty.meta.data.application.biz.dict.service.ItemApplicationService;
import top.mingempty.meta.data.domain.biz.dict.service.info.ItemInfo;
import top.mingempty.meta.data.facade.controller.dict.converter.ItemControllerConverter;
import top.mingempty.meta.data.facade.domain.dict.ItemBaseModifiesDto;
import top.mingempty.meta.data.facade.domain.dict.ItemListDto;
import top.mingempty.meta.data.facade.domain.dict.ItemListQueryDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 字典项相关控制器
 */
@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "字典项相关控制器")
@RequestMapping("/item")
public class ItemController {
    private final ItemApplicationService itemApplicationService;

    /**
     * 创建或修改字典项信息
     *
     * @param meReq
     * @return
     */
    @PostMapping("/modifies")
    @Operation(summary = "创建或修改字典项信息")
    public MeRsp<Void> modifies(@RequestBody MeReq<ItemBaseModifiesDto> meReq) {
        itemApplicationService.modifies(ItemControllerConverter.modifiesDtoToInfo(meReq.getData()));
        return MeRsp.success();
    }

    /**
     * 查询字典项列表
     *
     * @param meReq
     * @return
     */
    @PostMapping("/list")
    @Operation(summary = "查询字典项列表")
    public MeRsp<Map<String, List<ItemListDto>>> list(@RequestBody MeReq<ItemListQueryDto> meReq) {
        List<ItemInfo> itemInfos = itemApplicationService.list(ItemControllerConverter.listQueryDtoToQueryInfo(meReq.getData()), meReq.getMePage());
        Map<String, List<ItemListDto>> map = ItemControllerConverter.infoToMap(itemInfos);
        if (Optional.ofNullable(meReq.getData())
                .map(ItemListQueryDto::getTreeFlag)
                .orElse(Boolean.FALSE)) {
            ItemControllerConverter.toTree(map);
        }
        return MeRsp.success(map, meReq.getMePage());
    }
}



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
import top.mingempty.meta.data.application.biz.dict.service.EntryApplicationService;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryBaseInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryInfo;
import top.mingempty.meta.data.facade.controller.dict.converter.EntryControllerConverter;
import top.mingempty.meta.data.facade.domain.dict.EntryBaseModifiesDto;
import top.mingempty.meta.data.facade.domain.dict.EntryDetailsDto;
import top.mingempty.meta.data.facade.domain.dict.EntryDetailsQueryDto;
import top.mingempty.meta.data.facade.domain.dict.EntryListDto;
import top.mingempty.meta.data.facade.domain.dict.EntryListQueryDto;

import java.util.List;


/**
 * 条目相关控制器
 */
@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "条目相关控制器")
@RequestMapping("/entry")
public class EntryController {
    private final EntryApplicationService entryApplicationService;

    /**
     * 创建或修改条目基础信息
     *
     * @param meReq
     * @return
     */
    @PostMapping("/modifies")
    @Operation(summary = "创建或修改条目基础信息")
    public MeRsp<Void> modifies(@RequestBody MeReq<EntryBaseModifiesDto> meReq) {
        entryApplicationService.modifies(EntryControllerConverter.modifiesDtoToInfo(meReq.getData()));
        return MeRsp.success();
    }

    /**
     * 查询条目列表
     *
     * @param meReq
     * @return
     */
    @PostMapping("/list")
    @Operation(summary = "查询条目列表")
    public MeRsp<EntryListDto> list(@RequestBody MeReq<EntryListQueryDto> meReq) {
        List<EntryInfo> entryInfoList = entryApplicationService
                .list(EntryControllerConverter.listQueryDtoToQueryInfo(meReq.getData()), meReq.getMePage());
        return MeRsp.success(EntryControllerConverter.infoToListDtos(entryInfoList), meReq.getMePage());
    }

    /**
     * 查询条目详情数据
     *
     * @param meReq
     * @return
     */
    @PostMapping("/details")
    @Operation(summary = "查询条目详情数据")
    public MeRsp<EntryDetailsDto> details(@RequestBody MeReq<EntryDetailsQueryDto> meReq) {
        EntryBaseInfo entryBaseInfo = entryApplicationService.details(EntryControllerConverter.detailsQueryDtoToQueryInfo(meReq.getData()));
        return MeRsp.success(EntryControllerConverter.baseInfoToDetailsDto(entryBaseInfo));
    }


}

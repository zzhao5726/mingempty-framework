package top.mingempty.meta.data.facade.feign.dict;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.mingempty.domain.base.MeReq;
import top.mingempty.domain.base.MeRsp;
import top.mingempty.meta.data.facade.domain.dict.EntryBaseModifiesDto;
import top.mingempty.meta.data.facade.domain.dict.EntryDetailsDto;
import top.mingempty.meta.data.facade.domain.dict.EntryDetailsQueryDto;
import top.mingempty.meta.data.facade.domain.dict.EntryListDto;
import top.mingempty.meta.data.facade.domain.dict.EntryListQueryDto;

/**
 * 条目信息的Feign
 */
@Tag(name = "条目信息的Feign", description = "条目信息的Feign")
@FeignClient(name = "${me.name}", contextId = "meta-data-entry-client", path = "/entry")
public interface EntryFeign {

    /**
     * 创建或修改条目基础信息
     *
     * @param meReq
     * @return
     */
    @PostMapping("/modifies")
    @Operation(summary = "创建或修改条目基础信息")
    MeRsp<Void> modifies(@RequestBody MeReq<EntryBaseModifiesDto> meReq);


    /**
     * 查询条目列表
     *
     * @param meReq
     * @return
     */
    @PostMapping("/list")
    @Operation(summary = "查询条目列表")
    MeRsp<EntryListDto> list(@RequestBody MeReq<EntryListQueryDto> meReq);

    /**
     * 查询条目详情数据
     *
     * @param meReq
     * @return
     */
    @PostMapping("/details")
    @Operation(summary = "查询条目详情数据")
    MeRsp<EntryDetailsDto> details(@RequestBody MeReq<EntryDetailsQueryDto> meReq);
}

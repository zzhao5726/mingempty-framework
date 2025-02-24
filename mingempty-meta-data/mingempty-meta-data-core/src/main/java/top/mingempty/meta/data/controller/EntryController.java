package top.mingempty.meta.data.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.domain.base.MeReq;
import top.mingempty.domain.base.MeRsp;
import top.mingempty.meta.data.api.EntryApi;
import top.mingempty.meta.data.model.exception.MetaDataException;
import top.mingempty.meta.data.model.vo.in.EntryCreateVo;
import top.mingempty.meta.data.model.vo.in.EntryQueryVo;
import top.mingempty.meta.data.model.vo.in.EntryStatusChangeVo;
import top.mingempty.meta.data.model.vo.in.EntryUpdateVo;
import top.mingempty.meta.data.model.vo.out.EntryBaseResultVo;
import top.mingempty.meta.data.service.EntryService;

import java.util.List;

/**
 * 字典条目相关接口
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@RestController
@AllArgsConstructor
@Tag(name = "字典条目相关接口")
@RequestMapping("/entry")
public class EntryController implements EntryApi {

    private final EntryService entryService;


    /**
     * 创建条目
     *
     * @param meReq
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建条目")
    public MeRsp<Void> create(@RequestBody MeReq<EntryCreateVo> meReq) {
        if (ObjUtil.isNotEmpty(meReq.getData())) {
            entryService.create(meReq.getData());
        } else if (CollUtil.isNotEmpty(meReq.getDataList())) {
            entryService.create(meReq.getDataList());
        } else {
            throw new MetaDataException("meta-data-core-0000000001");
        }
        return MeRsp.success();
    }

    /**
     * 更新条目信息
     *
     * @param meReq
     * @return
     */
    @PostMapping("/change")
    @Operation(summary = "更新条目信息")
    public MeRsp<Void> change(@RequestBody MeReq<EntryUpdateVo> meReq) {
        if (ObjUtil.isNotEmpty(meReq.getData())) {
            entryService.change(meReq.getData());
        } else if (CollUtil.isNotEmpty(meReq.getDataList())) {
            entryService.change(meReq.getDataList());
        } else {
            throw new MetaDataException("meta-data-core-0000000002");
        }
        return MeRsp.success();
    }

    /**
     * 更新条目状态信息
     *
     * @param meReq
     * @return
     */
    @PostMapping("/change/status")
    @Operation(summary = "更新条目状态信息")
    public MeRsp<Void> statusChange(@RequestBody MeReq<EntryStatusChangeVo> meReq) {
        if (ObjUtil.isNotEmpty(meReq.getData())) {
            entryService.statusChange(meReq.getData());
        } else {
            throw new MetaDataException("meta-data-core-0000000002");
        }
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
    public MeRsp<EntryBaseResultVo> list(@RequestBody MeReq<EntryQueryVo> meReq) {
        List<EntryBaseResultVo> entryBaseResultVos = entryService.list(meReq.getData(), meReq.getMePage());
        return MeRsp.success(entryBaseResultVos, meReq.getMePage());
    }

}

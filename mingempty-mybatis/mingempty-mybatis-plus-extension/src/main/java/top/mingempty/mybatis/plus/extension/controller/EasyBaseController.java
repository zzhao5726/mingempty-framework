package top.mingempty.mybatis.plus.extension.controller;


import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.mingempty.domain.base.MePage;
import top.mingempty.domain.base.MeReq;
import top.mingempty.domain.base.MeRsp;
import top.mingempty.domain.other.MePubConditions;
import top.mingempty.mybatis.plus.extension.entity.MpPage;
import top.mingempty.mybatis.plus.extension.service.EasyBaseService;
import top.mingempty.mybatis.plus.tool.MpWrapperTool;

import java.io.Serializable;

/**
 * 顶级controller
 *
 * @param <S>
 * @param <T>
 * @author zzhao
 */
public abstract class EasyBaseController<S extends EasyBaseService<T>, T> {

    @Autowired
    protected S baseService;


    public S getBaseService() {
        Assert.notNull(this.baseService, "baseService can not be null");
        return this.baseService;
    }

    @PostMapping("/one")
    @Operation(summary = "查询单个对象")
    public MeRsp<T> one(@RequestBody MeReq<Serializable> meReq) {
        return MeRsp.success(baseService.getById(meReq.getData()));
    }

    @PostMapping("/more")
    @Operation(summary = "查询多个对象")
    public MeRsp<T> more(@RequestBody MeReq<Serializable> meReq) {
        return MeRsp.success(baseService.listByIds(meReq.getDataList()));
    }

    @PostMapping("/list")
    @Operation(summary = "查询多个对象")
    public MeRsp<T> list(@RequestBody MeReq<MePubConditions> meReq) {
        QueryWrapper<T> queryWrapper = MpWrapperTool.entityToWrapper(meReq.getData());
        MePage mePage = meReq.getMePage();
        if (ObjUtil.isNotEmpty(mePage)) {
            MpPage<T> tMpPage = baseService.page(new MpPage<>(mePage), queryWrapper);
            return MeRsp.success(tMpPage.getRecords(), mePage);
        }
        return MeRsp.success(baseService.list(queryWrapper));
    }


    @PostMapping("/save")
    @Operation(summary = "保存对象")
    public MeRsp<T> save(@RequestBody MeReq<T> meReq) {
        if (baseService.save(meReq.getData())) {
            return MeRsp.success();
        }
        return MeRsp.failed("数据保存错误");
    }

    @PostMapping("/save/batch")
    @Operation(summary = "批量保存对象")
    public MeRsp<T> saveBatch(@RequestBody MeReq<T> meReq) {
        if (baseService.saveBatch(meReq.getDataList())) {
            return MeRsp.success();
        }
        return MeRsp.failed("数据批量保存错误");
    }

    @PostMapping("/update")
    @Operation(summary = "更新对象")
    public MeRsp<T> update(@RequestBody MeReq<T> meReq) {
        if (baseService.updateById(meReq.getData())) {
            return MeRsp.success();
        }
        return MeRsp.failed("数据更新错误");
    }

    @PostMapping("/update/batch")
    @Operation(summary = "批量更新对象")
    public MeRsp<T> updateBatch(@RequestBody MeReq<T> meReq) {
        if (baseService.updateBatchById(meReq.getDataList())) {
            return MeRsp.success();
        }
        return MeRsp.failed("数据批量更新错误");
    }

    @PostMapping("/del")
    @Operation(summary = "删除对象")
    public MeRsp<T> del(@RequestBody MeReq<Serializable> meReq) {
        if (baseService.removeById(meReq.getData())) {
            return MeRsp.success();
        }
        return MeRsp.failed("数据删除错误");
    }

    @PostMapping("/del/batch")
    @Operation(summary = "批量删除对象")
    public MeRsp<T> delBatch(@RequestBody MeReq<Serializable> meReq) {
        if (baseService.removeByIds(meReq.getDataList())) {
            return MeRsp.success();
        }
        return MeRsp.failed("数据批量删除错误");
    }

}

package top.mingempty.meta.data.domain.biz.dict.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import lombok.Getter;
import top.mingempty.meta.data.domain.biz.dict.value.OperationHistoryVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 操作历史信息领域模型
 */
public class OperationHistoryModel {

    /**
     * 条目编号
     */
    @Getter
    private final String entryCode;


    /**
     * 条目操作历史信息值对象
     */
    private final List<OperationHistoryVo> operationHistoryVos = new CopyOnWriteArrayList<>();

    /**
     * 条目操作历史信息值对象备份数据
     */
    private final List<OperationHistoryVo> operationHistoryVosByBack = new CopyOnWriteArrayList<>();

    /**
     * 条目操作历史信息值对象修改数据
     */
    private final Collection<OperationHistoryVo> operationHistorysByChange = new CopyOnWriteArraySet<>();

    /**
     * 条目操作历史信息值对象
     */
    private final Map<Long, OperationHistoryVo> operationHistorys = new ConcurrentHashMap<>();

    /**
     * 条目操作历史信息值对象备份数据
     */
    private final Map<Long, OperationHistoryVo> operationHistorysByBack = new ConcurrentHashMap<>();

    public OperationHistoryModel(String entryCode, Collection<OperationHistoryVo> operationHistoryVos) {
        this.entryCode = entryCode;
        if (CollUtil.isEmpty(operationHistoryVos)) {
            return;
        }
        operationHistoryVos.parallelStream()
                .forEach(operationHistoryVo -> {
                    this.operationHistoryVos.add(operationHistoryVo);
                    this.operationHistoryVosByBack.add(operationHistoryVo);
                    this.operationHistorys.put(operationHistoryVo.getEntryVersion(), operationHistoryVo);
                    this.operationHistorysByBack.put(operationHistoryVo.getEntryVersion(), operationHistoryVo);
                });

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OperationHistoryModel that)) return false;
        return Objects.equals(getEntryCode(), that.getEntryCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEntryCode());
    }

    public List<OperationHistoryVo> getOperationHistorys() {
        return List.copyOf(this.operationHistoryVos);
    }

    public List<OperationHistoryVo> getOperationHistorysByBack() {
        return List.copyOf(this.operationHistoryVosByBack);
    }

    public List<OperationHistoryVo> getOperationHistorysByChange() {
        return List.copyOf(this.operationHistorysByChange);
    }

    /**
     * 根据条目版本号获取一条操作历史信息值对象
     *
     * @param entryVersion 条目版本号
     * @return 操作历史信息值对象
     */
    public OperationHistoryVo operationHistoryVo(Long entryVersion) {
        return operationHistorys.get(entryVersion);
    }

    /**
     * 添加一条操作历史信息值对象
     *
     * @param operationHistoryVo 操作历史信息值对象
     */
    public void recordToModel(OperationHistoryVo operationHistoryVo) {
        if (ObjUtil.isEmpty(operationHistoryVo)) {
            return;
        }
        this.operationHistoryVos.add(operationHistoryVo);
        this.operationHistorys.put(operationHistoryVo.getEntryVersion(), operationHistoryVo);
        this.operationHistorysByChange.add(operationHistoryVo);

    }

    public OperationHistoryModel cloneOne() {
        return new OperationHistoryModel(this.getEntryCode(), this.getOperationHistorys());
    }

    public static OperationHistoryModel cloneOne(String entryCode, OperationHistoryModel operationHistoryModel) {
        if (ObjUtil.isEmpty(operationHistoryModel)) {
            return new OperationHistoryModel(entryCode, List.of());
        }
        return operationHistoryModel.cloneOne();

    }

}

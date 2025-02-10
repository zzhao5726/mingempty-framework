package top.mingempty.mybatis.plus.extension.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.commons.util.CollectionUtil;
import top.mingempty.mybatis.plus.extension.mapper.EasyBaseMapper;
import top.mingempty.mybatis.plus.extension.service.EasyBaseService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 对mybatis plus 的service进行扩展
 *
 * @param <M>
 * @param <T>
 */
@Slf4j
public abstract class EasyBaseServiceImpl<M extends EasyBaseMapper<T>, T> extends ServiceImpl<M, T> implements EasyBaseService<T> {


    /**
     * 重写批量插入方法
     *
     * @param entityList 数据集
     * @param batchSize  单次插入数量
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        if (ObjUtil.isEmpty(entityList)) {
            return Boolean.TRUE;
        }
        List<List<T>> lists = CollectionUtil.batchSubList(new ArrayList<>(entityList), batchSize);
        for (List<T> list : lists) {
            baseMapper.insertBatchSomeColumn(list);
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delAndsave(T entity) {
        this.baseMapper.deleteById(entity);
        return this.save(entity);
    }

}

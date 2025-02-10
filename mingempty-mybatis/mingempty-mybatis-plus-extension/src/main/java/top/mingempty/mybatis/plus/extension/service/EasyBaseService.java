package top.mingempty.mybatis.plus.extension.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 对mybatis plus 的service进行扩展
 *
 * @author zzhao
 */
public interface EasyBaseService<T> extends IService<T> {

    /**
     * 删除并保存
     *
     * @param entity
     * @return
     */
    boolean delAndsave(T entity);


}

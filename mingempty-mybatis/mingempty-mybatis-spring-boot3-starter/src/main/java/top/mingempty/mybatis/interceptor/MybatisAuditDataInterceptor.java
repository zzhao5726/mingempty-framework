package top.mingempty.mybatis.interceptor;


import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import top.mingempty.domain.base.BaseDeletePoModel;
import top.mingempty.domain.base.BasePoModel;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.mybatis.tool.AuditOperatorTool;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * Mybatis处理审计字段拦截器
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class MybatisAuditDataInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        SqlCommandType sqlCommandType = null;
        for (Object object : args) {
            // 如果没有参数，则直接放行
            if (ObjUtil.isEmpty(object)) {
                return invocation.proceed();
            }
            //获取操作类型
            if (object instanceof MappedStatement mappedStatement) {
                sqlCommandType = mappedStatement.getSqlCommandType();
                if (log.isDebugEnabled()) {
                    log.debug("操作类型:[{}]", sqlCommandType);
                }
                if (SqlCommandType.DELETE.equals(sqlCommandType)) {
                    // 如果是删除，则直接放行
                    return invocation.proceed();
                }
                continue;
            }
            //数据处理
            audiData(object, sqlCommandType, new CopyOnWriteArraySet<>());
        }
        return invocation.proceed();
    }

    private void audiData(Object object, SqlCommandType sqlCommandType, final Set<Object> objSet) {
        if (objSet.contains(object)) {
            return;
        }
        if (object instanceof BasePoModel basePoModel) {
            audiData(basePoModel, sqlCommandType);
            return;
        }
        if (object instanceof Collection collections) {
            collections.forEach(collection -> audiData(collection, sqlCommandType, objSet));
            return;
        }
        if (object instanceof Map maps) {
            maps.forEach((key, value) -> {
                audiData(key, sqlCommandType, objSet);
                audiData(value, sqlCommandType, objSet);
            });
        }
    }

    private void audiData(BasePoModel basePoModel, SqlCommandType sqlCommandType) {
        String operator = AuditOperatorTool.gainAuditOperator();
        LocalDateTime localDateTime = LocalDateTime.now();
        switch (sqlCommandType) {
            case INSERT -> {
                if (ObjUtil.isEmpty(basePoModel.getCreateTime())) {
                    basePoModel.setCreateTime(localDateTime);
                }
                if (ObjUtil.isEmpty(basePoModel.getUpdateTime())) {
                    basePoModel.setUpdateTime(localDateTime);
                }
                if (StrUtil.isEmpty(basePoModel.getCreateOperator())) {
                    basePoModel.setCreateOperator(operator);
                }
                if (StrUtil.isEmpty(basePoModel.getUpdateOperator())) {
                    basePoModel.setUpdateOperator(operator);
                }
            }
            case UPDATE -> {
                if (ObjUtil.isEmpty(basePoModel.getUpdateTime())) {
                    basePoModel.setUpdateTime(localDateTime);
                }
                if (StrUtil.isEmpty(basePoModel.getUpdateOperator())) {
                    basePoModel.setUpdateOperator(operator);
                }

                // 增加逻辑删除的处理
                if (basePoModel instanceof BaseDeletePoModel baseDeletePoModel
                        && ZeroOrOneEnum.ONE.getItemCode().equals(baseDeletePoModel.getDeleteStatus())) {
                    if (ObjUtil.isEmpty(baseDeletePoModel.getDeleteTime())) {
                        baseDeletePoModel.setDeleteTime(localDateTime);
                    }
                    if (StrUtil.isEmpty(baseDeletePoModel.getDeleteOperator())) {
                        baseDeletePoModel.setDeleteOperator(operator);
                    }
                }
            }
        }
    }
}
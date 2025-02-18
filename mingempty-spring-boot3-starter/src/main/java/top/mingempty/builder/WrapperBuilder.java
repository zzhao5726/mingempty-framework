package top.mingempty.builder;

import top.mingempty.commons.exception.BaseCommonException;
import top.mingempty.domain.function.IBuilder;
import top.mingempty.domain.other.AbstractRouter;
import top.mingempty.util.SpringContextUtil;

import java.util.Optional;

/**
 * spring包装类构造器
 *
 * @author zzhao
 */
public interface WrapperBuilder<T extends AbstractRouter<B>, B, P extends BuilderWrapperParent>
        extends IBuilder<T> {

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    default B build(String instanceName, P properties) {
        B bean = buildToSub(instanceName, properties);
        registerToSpring(bean, instanceName, properties.registerToSpring());
        return bean;
    }

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    B buildToSub(String instanceName, P properties);

    default void registerToSpring(B bean, String instanceName, boolean registerToSprin) {
        if (!registerToSprin) {
            return;
        }
        String beanName = Optional.ofNullable(instanceName)
                .flatMap((name -> Optional.of(SpringContextUtil.gainDefaultBeanName(instanceName, bean.getClass()))))
                .orElseThrow(() -> new BaseCommonException("di-0000000003"));
        SpringContextUtil.registerBean(beanName, (Class<B>) bean.getClass(), () -> bean);
    }


}



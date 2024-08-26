package top.mingempty.builder;

/**
 * @author zzhao
 */
public interface BuilderWrapperParent {

    /**
     * 是否注册到spring
     *
     * @return
     */
    default boolean registerToSpring() {
        return false;
    }

}

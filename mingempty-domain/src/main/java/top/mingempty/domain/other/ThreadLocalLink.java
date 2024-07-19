package top.mingempty.domain.other;

import lombok.Getter;
import lombok.Setter;

/**
 * 线程变量链表集
 *
 * @author zzhao
 */
public class ThreadLocalLink {

    private final ThreadLocal<LinkedNode> THREAD_LOCAL
            = ThreadLocal.withInitial(() -> new LinkedNode(GlobalConstant.DEFAULT_INSTANCE_NAME));

    /**
     * 获取当前线程Link切面名称
     */
    public String acquireName() {
        return THREAD_LOCAL.get().getName();
    }

    /**
     * 设置当前线程Link切面名称
     */
    public void putName(String name) {
        if (acquireName().equals(name)) {
            return;
        }
        LinkedNode dsLink = THREAD_LOCAL.get();
        LinkedNode dsLinkByNext = new LinkedNode(name);
        dsLink.setNext(dsLinkByNext);
        dsLinkByNext.setPrev(dsLink);
        THREAD_LOCAL.set(dsLinkByNext);
    }

    /**
     * 移除当前线程Link切面名称
     */
    public void removeName() {
        LinkedNode linkedNode = THREAD_LOCAL.get();
        if (linkedNode.getPrev() == null) {
            THREAD_LOCAL.remove();
        }
        LinkedNode dsLinkPrev = linkedNode.getPrev();
        dsLinkPrev.setNext(null);
        THREAD_LOCAL.set(dsLinkPrev);
    }


    /**
     * 链表节点
     *
     * @author zzhao
     */
    @Getter
    private static class LinkedNode {
        /**
         * 当前名称
         */
        private final String name;

        /**
         * 上一个
         */
        @Setter
        private LinkedNode prev;

        /**
         * 下一个
         */
        @Setter
        private LinkedNode next;

        public LinkedNode(String name) {
            this.name = name;
        }
    }

}

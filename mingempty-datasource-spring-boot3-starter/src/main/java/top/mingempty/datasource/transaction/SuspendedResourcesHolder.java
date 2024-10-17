package top.mingempty.datasource.transaction;

/**
 * SuspendedResourcesHolder
 *
 */
public class SuspendedResourcesHolder {
    /**
     * 事务ID
     */
    private String xid;

    /**
     * Instantiates a new Suspended resources holder.
     *
     * @param xid 事务ID
     */
    public SuspendedResourcesHolder(String xid) {
        if (xid == null) {
            throw new IllegalArgumentException("xid must be not null");
        }
        this.xid = xid;
    }

    /**
     * 获得事务ID.
     *
     * @return 事务ID
     */
    public String getXid() {
        return xid;
    }
}
package com.framework.process;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description Node——Node抽象
 */
public abstract class Node {
    /**
     * id
     */
    private String id;
    /**
     * name
     */
    private String name;
    /**
     * 是否根节点
     */
    private boolean isRoot;

    /**
     * 调用链
     * @param context
     * @throws Exception
     */
    abstract void invoke(JobContext context) throws Exception;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + this.id + ", name=" + this.name
                + "]";
    }
}

package com.framework.process;

import java.util.List;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/6
 * @Description 当前流程节点名称
 */
public abstract class Node {

    /**
     * 当前节点名称
     */
    private String name;
    /**
     * 子节点
     */
    private List<Node> nodes;
    /**
     * 父节点
     */
    private Node parentNode = null;

    /**
     *
     * @param context
     */
    public abstract void invoke(Context context);

    public void next() {

    }

    public abstract Node next(String name);

    public abstract Node child();

    public abstract Node child(String name);

    public abstract Node prev();


    /**
     * 获取子节点
     * @param name
     * @return
     */
    public Node getChild(String name) {
        for(Node node : nodes) {
            if(name.equals(node.getName())) {
                return node;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}

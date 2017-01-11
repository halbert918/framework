package com.framework.process.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description Node节点名称持有器，方便spring生成bean对象后重新构造nodewapper数据结构
 */
public class NodeNameHolder implements Serializable {
    /**
     * node名称
     */
    private String nodeName;
    /**
     * 是否根节点
     */
    private boolean isRoot;
    /**
     * 节点选择器
     */
    private DeciderHolder deciderHolder;

    private List<NodeNameHolder> nodeHolders = new ArrayList<NodeNameHolder>();

    public NodeNameHolder(String nodeName) {
        this(nodeName, false);
    }

    public NodeNameHolder(String nodeName, boolean isRoot) {
        this.nodeName = nodeName;
        this.isRoot = isRoot;
    }

    public List<NodeNameHolder> getNodeHolders() {
        return nodeHolders;
    }

    public void setNodeHolders(List<NodeNameHolder> nodeHolders) {
        this.nodeHolders = nodeHolders;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void addChild(NodeNameHolder nodeHolder) {
        nodeHolders.add(nodeHolder);
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public DeciderHolder getDeciderHolder() {
        return deciderHolder;
    }

    public void setDeciderHolder(DeciderHolder deciderHolder) {
        this.deciderHolder = deciderHolder;
    }
}

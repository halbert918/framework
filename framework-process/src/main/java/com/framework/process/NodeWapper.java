package com.framework.process;

import com.framework.process.support.NodeExecutionDecider;

import java.util.List;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description NodeWapper——Node节点包装器，主要构造流程执行数据结构
 */
public class NodeWapper {
    /**
     * node节点
     */
    private Node node;
    /**
     * 节点选择器
     */
    private NodeExecutionDecider decider;
    /**
     * 子节点包装器
     */
    private List<NodeWapper> nodeWappers;

    /**
     * 判断是否含有子节点
     * @return
     */
    public boolean hasChild() {
        if(null != nodeWappers && nodeWappers.size() > 0) {
            return true;
        }
        return false;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public List<NodeWapper> getNodeWappers() {
        return nodeWappers;
    }

    public void setNodeWappers(List<NodeWapper> nodeWappers) {
        this.nodeWappers = nodeWappers;
    }

    public NodeExecutionDecider getDecider() {
        return decider;
    }

    public void setDecider(NodeExecutionDecider decider) {
        this.decider = decider;
    }

    /**
     * 获取子节点
     * @param nodeName
     * @return
     */
    public NodeWapper findChild(String nodeName) {
        for (NodeWapper nodeWapper : nodeWappers) {
            Node node = nodeWapper.getNode();
            if(null != node && nodeName.equals(node.getName())) {
                return nodeWapper;
            }
            nodeWapper.findChild(nodeName);
        }
        return null;
    }
}

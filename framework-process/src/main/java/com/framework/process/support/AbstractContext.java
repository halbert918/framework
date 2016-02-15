package com.framework.process.support;

import com.framework.process.Context;
import com.framework.process.Node;

import java.util.List;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/10
 * @Description
 */
public abstract class AbstractContext implements Context {

    /**
     * 节点
     */
    private List<Node> nodes;
    /**
     * 当前节点
     */
    private Node currentNode;

    public void back(boolean parent) {

    }

    public void back(String name, boolean parent) {

    }

    public void next(boolean child) {
        next(null, child);
    }

    public void next(String name, boolean child) {
        Node childNode = currentNode.child(name);
        currentNode = childNode;
        childNode.invoke(this);
    }

    private Node getNode(String name) {

        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}

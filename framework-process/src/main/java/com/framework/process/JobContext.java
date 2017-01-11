package com.framework.process;

import com.framework.process.support.NodeExecutionDecider;
import com.framework.process.support.NodeNameHolder;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description JobContext——JobContext
 */
public abstract class JobContext {
    /**
     * context参数
     */
    protected Map<Object, Object> params = new HashMap<>();
    /**
     * job任务
     */
    private Job job;
    /**
     * context Name
     */
    private String name;
    /**
     * 执行Node的根节点
     */
    private NodeWapper rootNode;
    /**
     * 执行Node当前节点
     */
    private NodeWapper currentNode;

    private NodeNameHolder nodeHolder;
    /**
     * job是否执行完成
     */
    private boolean isDone;
    /**
     * 下一个执行的node名称
     */
    protected String nextNodeName;

    public void invoke() {
        //第一次执行
        if(null == currentNode) {
            currentNode = rootNode;
        } else {
            NodeExecutionDecider decider = currentNode.getDecider();
            if(null != decider) {
                currentNode = decider.decide(this, currentNode);
                if(null == currentNode) {
                    throw new RuntimeException("配置错误，不存在对应的选择分支[" + decider.getDecide() + "]");
                }
            } else {
                for(NodeWapper nodeWapper : currentNode.getNodeWappers()) {
                    currentNode = nodeWapper;
                    break;
                }
            }
            //跳转到对应的分支
            if(StringUtils.hasLength(nextNodeName)) {
                currentNode = currentNode.findChild(nextNodeName);
                if (null == currentNode) {
                    throw new RuntimeException("不存在对应的子节点[" + nextNodeName + "]");
                }
                nextNodeName = null;
            }

        }
        doInvoke(currentNode.getNode());
        //若当前节点下再无子节点，则设置当前job执行完成
        if (!currentNode.hasChild()) {
            setDone(true);
            return;
        }
    }

    public abstract void doInvoke(Node node);

    public abstract void next();

    public abstract void next(String name);

    public NodeWapper getRootNode() {
        return rootNode;
    }

    public void setRootNode(NodeWapper rootNode) {
        this.rootNode = rootNode;
    }

    public NodeWapper getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(NodeWapper currentNode) {
        this.currentNode = currentNode;
    }

    public NodeNameHolder getNodeHolder() {
        return nodeHolder;
    }

    public void setNodeHolder(NodeNameHolder nodeHolder) {
        this.nodeHolder = nodeHolder;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobContext buildParam(Object key, Object obj) {
        params.put(key, obj);
        return this;
    }

    public Map<Object, Object> getParams() {
        return params;
    }
}

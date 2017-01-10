package com.framework.process.support;

import com.framework.process.JobContext;
import com.framework.process.Node;
import com.framework.process.NodeWapper;

import java.util.Map;

/**
 * Created by Halbert on 2017/1/7.
 * node选择器，根据context中param传递的参数选择对应的node节点
 */
public class NodeExecutionDecider {

    private String id;
    /**
     * 配置分支选择路径
     */
    private String decide;

    /**
     * 根据参数选择分支
     * @param jobContext
     * @param prevNodeWapper
     * @return
     */
    public NodeWapper decide(JobContext jobContext, NodeWapper prevNodeWapper) {
        Map<Object, Object> params = jobContext.getParams();
        if(params.containsKey(decide)) {
            Object value = params.get(decide);
            for (NodeWapper nodeWapper : prevNodeWapper.getNodeWappers()) {
                Node node = nodeWapper.getNode();
                if(value.equals(node.getName())) {
                    return nodeWapper;
                }
            }
        }
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDecide(String decide) {
        this.decide = decide;
    }

    public String getDecide() {
        return decide;
    }
}

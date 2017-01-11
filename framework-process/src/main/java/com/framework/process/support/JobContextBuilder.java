package com.framework.process.support;

import com.framework.process.DefaultJobContext;
import com.framework.process.JobContext;
import com.framework.process.Node;
import com.framework.process.NodeWapper;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description JobContextBuilder——JobContext构造node节点
 */
public class JobContextBuilder {
    /**
     * nodeNameHolder
     */
    private NodeNameHolder nodeNameHolder;

    private ApplicationContext applicationContext;

    public synchronized JobContext build(JobContext jobContext) {
        if(null == jobContext) {
            try {
                jobContext = DefaultJobContext.class.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("生成默认[DefaultJobContext]实例失败...", e);
            }
        }
        //构建NodeWapper
        NodeWapper rootWapper = new NodeWapper();
        Object obj = applicationContext.getBean(nodeNameHolder.getNodeName());
        rootWapper.setNode(Node.class.cast(obj));
        rootWapper.setDecider(buildDecider(nodeNameHolder.getDeciderHolder()));
        buildChildrenWapper(rootWapper, nodeNameHolder.getNodeHolders());
        jobContext.setRootNode(rootWapper);
        return jobContext;
    }

    /**
     * 构建子NodeWapper
     * @param rootWapper
     * @param list
     */
    private void buildChildrenWapper(NodeWapper rootWapper, List<NodeNameHolder> list) {
        if(null == list) {
            return;
        }
        List<NodeWapper> wappers = new ArrayList<NodeWapper>();
        for(NodeNameHolder nodeHolder : list) {
            Node node = (Node) applicationContext.getBean(nodeHolder.getNodeName());
            NodeWapper nodeWapper = new NodeWapper();
            nodeWapper.setNode(node);
            nodeWapper.setDecider(buildDecider(nodeHolder.getDeciderHolder()));
            wappers.add(nodeWapper);
            buildChildrenWapper(nodeWapper, nodeHolder.getNodeHolders());
        }
        rootWapper.setNodeWappers(wappers);
    }

    /**
     * 构建Decider
     * @param deciderHolder
     * @return
     */
    private NodeExecutionDecider buildDecider(DeciderHolder deciderHolder) {
        NodeExecutionDecider decider = null;
        if(null != deciderHolder) {
            decider = new NodeExecutionDecider();
            decider.setDecide(deciderHolder.getDecide());
            decider.setId(deciderHolder.getName());
        }
        return decider;
    }

    public void setNodeNameHolder(NodeNameHolder nodeNameHolder) {
        this.nodeNameHolder = nodeNameHolder;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}

package com.framework.process;

import com.framework.process.domain.Domain;
import com.framework.process.result.Result;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description AbstractNode——所有的业务node节点需继承该节点，并在process中实现自己的逻辑
 */
public abstract class AbstractNode<D extends Domain, R extends Result> extends Node {

    void invoke(JobContext context) throws Exception {
        DefaultJobContext<D, R> defaultJobContext = (DefaultJobContext) context;
        process(defaultJobContext, defaultJobContext.getDomain());
    }

    public abstract void process(DefaultJobContext<D, R> context, D domain);

}

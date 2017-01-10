package com.framework.process;

import com.framework.process.domain.Domain;
import com.framework.process.result.Result;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/6
 * @Description DefaultJobContext——默认实现jobContext
 */
public class DefaultJobContext<D extends Domain, R extends Result> extends JobContext {

    private D domain;

    private R result;

    public void doInvoke(Node node) {
        try {
            node.invoke(this);
        } catch (Exception e) {
            throw new RuntimeException("执行节点[" + node.getName() + "]发生异常...", e);
        }
    }

    @Override
    public void next() {
        this.next(null);
    }

    @Override
    public void next(String name) {
        nextNodeName = name;
    }

    public D getDomain() {
        return domain;
    }

    public void setDomain(D domain) {
        this.domain = domain;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }
}

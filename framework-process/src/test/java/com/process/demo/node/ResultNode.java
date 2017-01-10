package com.process.demo.node;

import com.framework.process.AbstractNode;
import com.framework.process.DefaultJobContext;

/**
 * Created by admin on 2017/1/9.
 */
public class ResultNode extends AbstractNode<DemoDomain, DemoResult> {

    public void process(DefaultJobContext<DemoDomain, DemoResult> context, DemoDomain domain) {
        System.out.println("ResultNode：id = " + domain.getId() + ", name = " + domain.getName());
        DemoResult result = new DemoResult();
        result.setStatus("success");
        context.setResult(result);
//        context.setDone(true);
    }
}

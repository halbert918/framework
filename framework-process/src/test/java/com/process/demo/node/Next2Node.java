package com.process.demo.node;

import com.framework.process.AbstractNode;
import com.framework.process.DefaultJobContext;

/**
 * Created by admin on 2017/1/9.
 */
public class Next2Node extends AbstractNode<DemoDomain, DemoResult> {

    public void process(DefaultJobContext<DemoDomain, DemoResult> context, DemoDomain domain) {
        System.out.println("Next2Node：id = " + domain.getId() + ", name = " + domain.getName());
    }
}

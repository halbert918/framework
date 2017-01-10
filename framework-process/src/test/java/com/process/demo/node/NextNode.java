package com.process.demo.node;

import com.framework.process.AbstractNode;
import com.framework.process.DefaultJobContext;

/**
 * Created by admin on 2017/1/9.
 */
public class NextNode extends AbstractNode<DemoDomain, DemoResult> {

    public void process(DefaultJobContext<DemoDomain, DemoResult> context, DemoDomain domain) {
        System.out.println("NextNode：id = " + domain.getId() + ", name = " + domain.getName());
    }
}

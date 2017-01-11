package com.process.demo.node;

import com.framework.process.AbstractNode;
import com.framework.process.DefaultJobContext;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description
 */
public class NextNode extends AbstractNode<DemoDomain, DemoResult> {

    public void process(DefaultJobContext<DemoDomain, DemoResult> context, DemoDomain domain) {
        System.out.println("NextNode：id = " + domain.getId() + ", name = " + domain.getName());
    }
}

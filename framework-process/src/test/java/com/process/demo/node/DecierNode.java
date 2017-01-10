package com.process.demo.node;

import com.framework.process.AbstractNode;
import com.framework.process.DefaultJobContext;

/**
 * Created by admin on 2017/1/10.
 */
public class DecierNode extends AbstractNode<DemoDomain, DemoResult> {

    public void process(DefaultJobContext<DemoDomain, DemoResult> context, DemoDomain domain) {
        System.out.println("DecierNode：id = " + domain.getId() + ", name = " + domain.getName());
    }

}

package com.test;

import com.framework.process.DefaultJobContext;
import com.framework.process.SimpleJob;
import com.framework.process.support.SimpleJobFactory;
import com.process.demo.node.DemoDomain;
import com.process.demo.node.DemoResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by admin on 2017/1/9.
 */
public class JobTest {

    public static void main(String[] args) {
        DemoDomain domain = new DemoDomain();
        domain.setId("123456");
        domain.setName("test domian");

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
        SimpleJobFactory factoryBean = ctx.getBean("&simpleJob", SimpleJobFactory.class);
        SimpleJob simpleJob = ctx.getBean("simpleJob", SimpleJob.class);
        DefaultJobContext<DemoDomain, DemoResult> context = (DefaultJobContext<DemoDomain, DemoResult>)simpleJob.getJobContext();
        context.buildParam("decider", "decider")
                .buildParam("okey", "okey");
        context.setDomain(domain);
        simpleJob.execute();
        DemoResult result = context.getResult();
        System.out.println("job执行状态status:" + result.getStatus());


        SimpleJob simpleJob1 = ctx.getBean("simpleJob1", SimpleJob.class);
        DefaultJobContext<DemoDomain, DemoResult> context1 = (DefaultJobContext<DemoDomain, DemoResult>)simpleJob1.getJobContext();
        domain.setName("job1 test domain");
        context1.setDomain(domain);
        simpleJob1.execute();

        DemoResult result1 = context1.getResult();
        System.out.println("job1执行状态status:" + result1.getStatus());
    }

}

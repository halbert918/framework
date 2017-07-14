package com.test;

import com.framework.process.DefaultJobContext;
import com.framework.process.SimpleJob;
import com.framework.process.support.SimpleJobFactory;
import com.process.demo.node.DemoDomain;
import com.process.demo.node.DemoResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description
 */
public class JobTest {

    public static void main(String[] args) {
        DemoDomain domain = new DemoDomain();
        domain.setId("123456");
        domain.setName("test");

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-demo.xml");
        SimpleJobFactory factoryBean = ctx.getBean("&simpleJob", SimpleJobFactory.class);
        SimpleJob simpleJob = ctx.getBean("simpleJob", SimpleJob.class);
        DemoResult result = (DemoResult) simpleJob.execute(domain);
        System.out.println("job执行状态status:" + result.getStatus());


        SimpleJob simpleJob1 = ctx.getBean("simpleJob1", SimpleJob.class);
        domain.setName("job1 test domain");
        DemoResult result1 = (DemoResult) simpleJob1.execute(domain);

        System.out.println("job1执行状态status:" + result1.getStatus());
    }

}

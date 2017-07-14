package com.framework.process.support;

import com.framework.process.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description SimpleJobFactory——根据SimpleJobFactory生成对应的job实例
 */
public class SimpleJobFactory implements FactoryBean<Job>, ApplicationContextAware {
    /**
     * job任务
     */
    private Job job;
    /**
     * Node节点持有器，以便spring解析完成后构造流程节点
     */
    private NodeNameHolder nodeNameHolder;
    /**
     * jobContext构造器
     */
    private JobContextBuilder jobContextBuilder;
    /**
     * applicationContext
     */
    protected ApplicationContext applicationContext;

    public Job getObject() throws Exception {
        if(null == job) {
            throw new RuntimeException("初始化[Job]实例失败...");
        }
//        JobContext jobContext = job.getJobContext();
//        if(null == jobContextBuilder) {
//            jobContextBuilder = new JobContextBuilder();
//            jobContextBuilder.setApplicationContext(applicationContext);
//            jobContextBuilder.setNodeNameHolder(nodeNameHolder);
//            jobContext = jobContextBuilder.build(jobContext);
//            jobContext.setJob(job);
//        }
//        job.setJobContext(jobContext);
        job.setJobFactory(this);
        return job;
    }

    public JobContext buildContext() {
        JobContext jobContext = null;
        if(null == jobContextBuilder) {
            jobContextBuilder = new JobContextBuilder();
        }
        jobContextBuilder.setApplicationContext(applicationContext);
        jobContextBuilder.setNodeNameHolder(nodeNameHolder);
        jobContext = jobContextBuilder.build(jobContext);
        jobContext.setJob(job);
        job.setJobContext(jobContext);
        return jobContext;
    }

    public Class<?> getObjectType() {
        return Job.class;
    }

    public boolean isSingleton() {
        return false;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public NodeNameHolder getNodeNameHolder() {
        return nodeNameHolder;
    }

    public void setNodeNameHolder(NodeNameHolder nodeNameHolder) {
        this.nodeNameHolder = nodeNameHolder;
    }
}

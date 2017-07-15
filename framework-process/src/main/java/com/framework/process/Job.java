package com.framework.process;

import com.framework.process.domain.Domain;
import com.framework.process.result.Result;
import com.framework.process.support.SimpleJobFactory;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description Job——job抽象
 */
public abstract class Job {
    /**
     * job id
     */
    private String id;
    /**
     * job name
     */
    private String name;
    /**
     * job 容器
     */
    private JobContext jobContext;

    /**
     * jobFactory
     */
    private SimpleJobFactory jobFactory;

    /**
     * start execute
     */
    public abstract Result execute(Domain domain);

    protected JobContext getJobContext() {
        return jobFactory.buildContext();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJobContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    public SimpleJobFactory getJobFactory() {
        return jobFactory;
    }

    public void setJobFactory(SimpleJobFactory jobFactory) {
        this.jobFactory = jobFactory;
    }
}

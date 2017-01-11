package com.framework.process;

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
     * start execute
     */
    public abstract void execute();

    public JobContext getJobContext() {
        return this.jobContext;
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
}

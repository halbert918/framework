package com.framework.process;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/6
 * @Description 请求任务
 */
public abstract class Job {

    /**
     * job名称
     */
    private String name;
    /**
     * 上下文
     */
    private Context context;



    public Job(){
        this(null);
    }

    public Job(String name) {
        this.name = name;
    }

    /**
     * 获取当前job的名称
     * @return
     */
    public abstract String getName();

}

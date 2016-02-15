package com.framework.process;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/6
 * @Description 流程上下文
 */
public interface  Context {

    /**
     * 获取容器ID
     * @return
     */
    public String getId();

    /**
     * 获取容器名称
     * @return
     */
    public String getName();

}

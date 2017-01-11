package com.framework.process;

import com.framework.process.exception.JobException;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description SimpleJob——简单job实现
 */
public class SimpleJob extends Job {

    public void execute() {
        JobContext context = getJobContext();
        try {
            while (!context.isDone()) {
                context.invoke();
            }
        } catch (Exception e) {
            throw new JobException("执行任务[" + this.getId() + "]发生异常...", e);
        }
    }
}

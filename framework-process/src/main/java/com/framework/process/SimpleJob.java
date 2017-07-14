package com.framework.process;

import com.framework.process.domain.Domain;
import com.framework.process.exception.JobException;
import com.framework.process.result.Result;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description SimpleJob——简单job实现
 */
public class SimpleJob extends Job {

    public Result execute(Domain domain) {
        DefaultJobContext context = (DefaultJobContext) getJobContext();
        context.setDomain(domain);
        try {
            while (!context.isDone()) {
                context.invoke();
            }
        } catch (Exception e) {
            throw new JobException("执行任务[" + this.getId() + "]发生异常...", e);
        }
        return context.getResult();
    }
}

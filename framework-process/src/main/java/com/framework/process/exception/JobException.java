package com.framework.process.exception;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/6
 * @Description JobException
 */
public class JobException extends RuntimeException {

    public JobException() {
        super();
    }

    public JobException(String msg) {
        super(msg);
    }

    public JobException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JobException(Throwable cause) {
        super(cause);
    }

}

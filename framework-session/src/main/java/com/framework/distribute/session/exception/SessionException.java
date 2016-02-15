package com.framework.distribute.session.exception;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/9/7
 * @Description
 */
public class SessionException extends RuntimeException {

    public SessionException(String msg) {
        super(msg);
    }

    public SessionException(String msg, Throwable throwable) {
        super(msg, throwable);
    }


}

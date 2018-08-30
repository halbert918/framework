package com.framework.auth.exception;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 16:53
 * @Description: 自定义异常类
 */
public class AuthException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer status;
    /**
     * 异常信息
     */
    private String message;

    public AuthException(Integer status, String message) {
        super(message);
        this.status = status;
    }

}

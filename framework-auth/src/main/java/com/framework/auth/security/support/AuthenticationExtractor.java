package com.framework.auth.security.support;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/13 11:11
 * @Description: 构建Authentication接口
 */
public interface AuthenticationExtractor {

    /**
     * 根据request请求构建Authentication
     * @param request
     * @return
     * @throws AuthenticationException
     */
    Authentication extractAuthentication(HttpServletRequest request) throws AuthenticationException;

    /**
     * 根据request请求参数判断是否支持认证模式
     * @return
     */
    boolean supports(HttpServletRequest request);

}

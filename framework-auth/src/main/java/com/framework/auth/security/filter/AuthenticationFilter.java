package com.framework.auth.security.filter;

import com.framework.auth.security.support.AuthenticationExtractorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/13 11:07
 * @Description: Authentication认证filter
 */
public class AuthenticationFilter extends OncePerRequestFilter implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    private AuthenticationExtractorManager extractorManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationExtractorManager extractorManager) {
        this.authenticationManager = authenticationManager;
        this.extractorManager = extractorManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        logger.info(">>The request authentication beginning. request url: {}", request.getRequestURI());
        Authentication authentication = extractorManager.extractAuthentication(request);
        if (authentication == null) {
//            logger.info(">>The request authentication failure.");
            response.addHeader("WWW-Authenticate", "Bearer error=\"invalid_token\" description=\"not support the authentication method\"");
            // TODO 默认不做任何处理，兼容之前未传入accessToken的接口
            // throw new AuthenticationServiceException("不支持的认证模式.");
        }

        //authentication 认证
        if (authentication != null) {
            try {
                Authentication fullyAuthentication = authenticationManager.authenticate(authentication);
                // 保障线程安全，Authentication提供给其他地方是用，
                // 比如@AuthenticationPrincipal可直接获取Authentication.getDetails()方法返回的值
                SecurityContextHolder.getContext().setAuthentication(fullyAuthentication);

            } catch (Exception e) {
//                logger.error(">>The request authentication error.", e);
                response.addHeader("WWW-Authenticate", "Bearer error=\"invalid_token\" description=\"" + e.getMessage() + "\"");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        Assert.notNull(authenticationManager, "AuthenticationManager must be set");
        Assert.notNull(extractorManager, "SenseAuthenticationExtractorManager must be set");
    }

}

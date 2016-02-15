package com.framework.distribute.session.servlet;

import com.framework.distribute.session.manager.DistributedSessionManager;
import com.framework.distribute.session.manager.SessionManager;
import com.framework.distribute.session.store.SessionStoreBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 分布式请求filter
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/9/7
 * @Description
 */
public class DistributedSessionFilter implements Filter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private SessionStoreBase sessionStore;

    private SessionManager sessionManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //配置存储类型,默认为redis
        String storeClass = filterConfig.getInitParameter("storeClass");
        //初始化DistributedSessionManager
        sessionManager = new DistributedSessionManager(storeClass);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        DistrributedRequstWrapper requstWrapper = new DistrributedRequstWrapper((HttpServletRequest) request, (HttpServletResponse)response, sessionManager);
        chain.doFilter(requstWrapper, response);
    }

    @Override
    public void destroy() {
        sessionManager.destroy();
    }
}

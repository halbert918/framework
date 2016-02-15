package com.framework.distribute.session.servlet;

import com.framework.distribute.session.manager.SessionManager;

import javax.servlet.SessionCookieConfig;
import javax.servlet.http.*;

/**
 * 分布式request包装器
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/9/7
 * @Description
 */
public class DistrributedRequstWrapper extends HttpServletRequestWrapper {

    private static final String DEFAULT_SESSION_COOKIE_NAME = "JSESSIONID";

    private SessionManager sessionManager;

    private HttpServletResponse response;

    private String sessionId;

    public DistrributedRequstWrapper(HttpServletRequest request, HttpServletResponse response, SessionManager sessionManager) {
        super(request);
        this.response = response;
        this.sessionManager = sessionManager;
    }

    /**
     * The default behavior of this method is to return
     * isRequestedSessionIdValid() on the wrapped request object.
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        String sessionId = getSessionId();
        return sessionManager.isSessionIdValid(sessionId);
    }

    /**
     *
     * @param create true：session为空时，创建一个session，false：session为空时，直接返回
     * @return
     */
    @Override
    public HttpSession getSession(boolean create) {
        if(null == sessionId) {
            sessionId = getSessionId();
        }
        HttpSession session = sessionManager.findSession(sessionId);
        //createSession
        if(!create && null == session) {
            session = sessionManager.createSession(null);
            sessionId = session.getId();
            response.addCookie(new Cookie(DEFAULT_SESSION_COOKIE_NAME, sessionId));
        }

        //设置session过期时间
        if(null != session) {
            sessionManager.setExpire(sessionId);
        }
        return session;
    }

    /**
     * The default behavior of this method is to return getSession() on the
     * wrapped request object.
     */
    @Override
    public HttpSession getSession() {

        return getSession(false);
    }

    /**
     * 获取sessionId
     * @return
     */
    private String getSessionId() {
        HttpServletRequest request = (HttpServletRequest) getRequest();
        if (null == request) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        //获取sessionId存储在cookie中的名称
        String sessionCookieName = request.getServletContext().getSessionCookieConfig().getName();
        return getSessionIdFromCookie(null == sessionCookieName ? DEFAULT_SESSION_COOKIE_NAME : sessionCookieName, request.getCookies());
    }

    /**
     * 获取sessionID
     * @param sessionCookieName
     * @param cookies
     * @return
     */
    private String getSessionIdFromCookie(String sessionCookieName, Cookie[] cookies) {
        if(null != cookies && null != sessionCookieName) {
            for (Cookie cookie : cookies) {
                if (sessionCookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}

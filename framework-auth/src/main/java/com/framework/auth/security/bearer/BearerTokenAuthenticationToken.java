package com.framework.auth.security.bearer;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/13 10:37
 * @Description: Bearer Token请求方式1.header  2.body   3.url
 * 其中请求头中格式应为：
 * GET /resource HTTP/1.1
 * Host: www.example.com
 * Authorization: Bearer 3bdd7a52acb6434b9dff45c79ff4ba3d
 */
public class BearerTokenAuthenticationToken extends AbstractAuthenticationToken {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * mac
     */
    private String accessToken;

    public BearerTokenAuthenticationToken(Long userId, String accessToken) {
        super(null);
        this.userId = userId;
        this.accessToken = accessToken;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        if (subject == null)
            return false;
        return subject.getPrincipals().contains(this);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

package com.framework.auth.security.support;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/13 10:53
 * @Description: 认证成功返回SenseAuthenticationToken，
 * 主要用于保存认证后用户的相关信息，以便后续使用
 */
public class AuthenticationToken extends AbstractAuthenticationToken {
    /**
     * accessToken
     */
    private Object token;

    public AuthenticationToken(Object token, UserDetailInfo userDetail) {
        super(userDetail.getAuthorities());
        this.token = token;
        setDetails(userDetail);
        setAuthenticated(true);
    }

    /**
     * 凭证
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }

    /**
     * 获取用户ID
     * @AuthenticationPrincipal 注解可获取返回值
     * @return
     */
    @Override
    public Object getPrincipal() {
        UserDetailInfo detail = (UserDetailInfo) super.getDetails();
        return detail.getUser();
    }

    @Override
    public boolean implies(Subject subject) {
        if (subject == null)
            return false;
        return subject.getPrincipals().contains(this);
    }

}

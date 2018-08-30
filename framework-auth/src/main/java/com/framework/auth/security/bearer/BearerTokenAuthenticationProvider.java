package com.framework.auth.security.bearer;

import com.framework.auth.security.support.AuthenticationToken;
import com.framework.auth.security.support.UserDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/12 20:06
 * @Description: access token 认证提供者
 * 验证token是否有效
 */
@Component
public class BearerTokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "authentication must not be null.");

        BearerTokenAuthenticationToken authenticationToken = (BearerTokenAuthenticationToken) authentication;
        UserDetailInfo userDetails = (UserDetailInfo) userDetailsService.loadUserByUsername(authenticationToken.getAccessToken());

        Assert.notNull(userDetails, "userDetials must not be null.");

        userDetails.setAuthType("Bearer");

        return new AuthenticationToken(authenticationToken.getAccessToken(), userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == BearerTokenAuthenticationToken.class;
    }
}

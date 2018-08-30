package com.framework.auth.security.bearer;

import com.framework.auth.security.support.AuthenticationExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/13 11:13
 * @Description: AccessAuthenticationToken 构造
 */
@Component
public class BearerTokenAuthenticationExtractor implements AuthenticationExtractor {

    @Override
    public Authentication extractAuthentication(HttpServletRequest request) throws AuthenticationException {
        String accessToken = request.getHeader("accessToken");

        if (accessToken == null) {
            return null;
        }

        return new BearerTokenAuthenticationToken(null, accessToken);
    }

    @Override
    public boolean supports(HttpServletRequest request) {
        return request.getHeader("accessToken") != null;
    }
}

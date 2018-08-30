package com.framework.auth.client;

import com.framework.auth.client.support.BearerTokenAuthenticationProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 10:35
 * @Description: AuthRestTemplate拦截器
 * 通过拦截器对获取当前请求所需的认证token
 */
public class AuthHttpRequestInterceptor implements ClientHttpRequestInterceptor, InitializingBean {

    private BearerTokenAuthenticationProvider bearerTokenAuthenticationProvider;

    public AuthHttpRequestInterceptor(BearerTokenAuthenticationProvider bearerTokenAuthenticationProvider) {
        this.bearerTokenAuthenticationProvider = bearerTokenAuthenticationProvider;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        String authentication = bearerTokenAuthenticationProvider.getAuthorization();

        Assert.notNull(authentication, "authentication must be not null.");

        request.getHeaders().add("Authentication", authentication);

        return execution.execute(request, body);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(bearerTokenAuthenticationProvider, "bearerTokenAuthenticationProvider must be not null.");
    }

}

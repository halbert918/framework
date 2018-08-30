package com.framework.auth.client.support;

import com.framework.auth.client.domain.BearerToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 11:36
 * @Description:
 */
public class BearerTokenAuthenticationProvider {

    private BearerTokenProvider provider;

    @Autowired
    public void setProvider(BearerTokenProvider provider) {
        this.provider = provider;
    }

    /**
     * 获取authorization验证信息
     * @return
     */
    public String getAuthorization() {

        Assert.notNull(provider, "bearerTokenProvider token must be not null.");

        BearerToken bearerToken = provider.getBearerToken();

        Assert.notNull(bearerToken, "bearer token must be not null.");

        return "Bearer \"" + bearerToken.getAccessToken() + "\"";
    }

}

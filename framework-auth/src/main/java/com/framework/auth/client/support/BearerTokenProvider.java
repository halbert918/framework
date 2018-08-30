package com.framework.auth.client.support;

import com.framework.auth.client.domain.BearerToken;
import com.framework.auth.client.config.AppConfig;
import com.framework.auth.exception.AuthException;
import com.framework.auth.client.service.BearerTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 11:11
 * @Description:
 */
public class BearerTokenProvider {

    /**
     * 系统参数配置,启动装载
     */
    private AppConfig config;
    /**
     * bearer token
     */
    private BearerToken bearerToken;

    private BearerTokenService bearerTokenService;

    @Autowired
    public void setBearerTokenService(BearerTokenService bearerTokenService, AppConfig config) {
        this.bearerTokenService = bearerTokenService;
        this.config = config;
    }

    public BearerToken getBearerToken() {
        //获取token
        if (bearerToken == null) {
            synchronized (this) {
                if (bearerToken == null) {
                    bearerToken = bearerTokenService.getBearerToken(config.getUrl(), config.getAppid(), config.getAppsecret());
                    return bearerToken;
                }
            }
        }

        //判断是否过期
        if (isExpire()) {
            synchronized (this) {
                String url = String.format(config.getRefreshUrl(), bearerToken.getRefreshToken());
                bearerToken = bearerTokenService.refreshBearerToken(url, config.getAppid(), config.getAppsecret());
            }
        }

        return bearerToken;
    }

    /**
     * 判断是否过期
     * @return
     */
    public boolean isExpire() {
        if (bearerToken == null) {
            throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "bearer token must be not null.");
        }

        Date currentDate = new Date();
        return bearerToken.getExpiresAt().getTime() > currentDate.getTime();
    }

}

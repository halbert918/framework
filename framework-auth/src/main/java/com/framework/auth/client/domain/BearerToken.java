package com.framework.auth.client.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 10:39
 * @Description:
 */
public class BearerToken implements Serializable {
    /**
     * 应用appid
     */
    private String appId;
    /**
     * bearer token
     */
    private String accessToken;
    /**
     * token类型
     */
    private String tokenType = "Bearer";
    /**
     * 过期时间
     */
    private Date expiresAt;
    /**
     * 刷新token
     */
    private String refreshToken;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "BearerToken{" +
                "appId='" + appId + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresAt=" + expiresAt +
                '}';
    }

}

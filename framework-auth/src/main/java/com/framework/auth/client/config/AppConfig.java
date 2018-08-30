package com.framework.auth.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 15:07
 * @Description: 启动配置项
 */
@Configuration
public class AppConfig {

    @Value("${app.auth.appid:sf}")
    private String appid;

    @Value("${app.auth.appsecret:hdkefusmxuskapqldicxksqlsoxuthsxn}")
    private String appsecret;

    @Value("${app.auth.url:http://127.0.0.1:10219/token}")
    private String url;

    @Value("${app.auth.url:http://127.0.0.1:10219/token/%s/refresh}")
    private String refreshUrl;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    public void setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "appid='" + appid + '\'' +
                ", url='" + url + '\'' +
                ", refreshUrl='" + refreshUrl + '\'' +
                '}';
    }
}

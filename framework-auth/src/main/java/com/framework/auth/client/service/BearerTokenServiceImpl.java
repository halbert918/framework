package com.framework.auth.client.service;

import com.framework.auth.client.domain.BearerToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 11:05
 * @Description: 请求认证服务器获取相应的token
 */
public class BearerTokenServiceImpl implements BearerTokenService {

    protected static final Logger logger = LoggerFactory.getLogger(BearerTokenServiceImpl.class);

    private RestTemplate normalRestTemplate;

    @Autowired
    public void setNormalRestTemplate(RestTemplate normalRestTemplate) {
        this.normalRestTemplate = normalRestTemplate;
    }

    @Override
    public BearerToken getBearerToken(String url, String appid, String appsecret) {

        logger.info(">> 获取bearer token, 请求地址:{}", url);

        BearerToken token = normalRestTemplate.postForObject(url, new RequestBody(appid, appsecret), BearerToken.class);

        logger.info(">> 获取bearer token, 请求结果:{}", token);

        return token;
    }

    @Override
    public BearerToken refreshBearerToken(String url, String appid, String appsecret) {

        logger.info(">> 刷新bearer token, 请求地址:{}", url);

        BearerToken token = normalRestTemplate.postForObject(url, new RequestBody(appid, appsecret), BearerToken.class);

        logger.info(">> 刷新bearer token, 请求结果:{}", token);

        return token;
    }

    static class RequestBody {

        private String appid;

        private String appsecret;

        public RequestBody(String appid, String appsecret) {
            this.appid = appid;
            this.appsecret = appsecret;
        }

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

    }

}

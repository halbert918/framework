package com.framework.auth.client.service;

import com.framework.auth.client.domain.BearerToken;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 10:59
 * @Description:
 */
public interface BearerTokenService {

    /**
     * 获取bearer token
     * @param url
     * @param appid
     * @param appsecret
     * @return
     */
    BearerToken getBearerToken(String url, String appid, String appsecret);

    /**
     * 刷新token
     * @param url
     * @param appid
     * @param appsecret
     * @return
     */
    BearerToken refreshBearerToken(String url, String appid, String appsecret);

}

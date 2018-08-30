package com.framework.auth.client.support;

import org.springframework.web.client.RestTemplate;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 14:41
 * @Description: 重构RestTemplate
 */
public interface AuthRestTemplateCustomizer {
    /**
     * 自定义restTemplate
     * @param restTemplate
     */
    void customize(RestTemplate restTemplate);

}

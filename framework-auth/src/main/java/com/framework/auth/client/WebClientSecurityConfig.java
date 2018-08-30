package com.framework.auth.client;

import com.framework.auth.client.service.BearerTokenService;
import com.framework.auth.client.service.BearerTokenServiceImpl;
import com.framework.auth.client.support.AuthRestTemplateCustomizer;
import com.framework.auth.client.support.BearerTokenAuthenticationProvider;
import com.framework.auth.client.support.BearerTokenProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 11:58
 * @Description:
 */
@ConditionalOnClass(AuthRestTemplate.class)
@Configuration
public class WebClientSecurityConfig {

    private List<RestTemplate> restTemplates;

    @Autowired
    public void setList(List<RestTemplate> list) {
        this.restTemplates = list;
    }

    /**
     * restTemplate添加Interceptor
     * @param authRestTemplateCustomizer
     * @return
     */
    @Bean
    public SmartInitializingSingleton restTemplateInitializer(
            final AuthRestTemplateCustomizer authRestTemplateCustomizer) {
        return () -> {
            for (RestTemplate restTemplate : restTemplates) {
                if (restTemplate instanceof AuthRestTemplate) {
                    authRestTemplateCustomizer.customize(restTemplate);
                }
            }
        };
    }

    /**
     * restTemplate添加Interceptor
     * @param authHttpRequestInterceptor
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthRestTemplateCustomizer authRestTemplateCustomizer(
            final AuthHttpRequestInterceptor authHttpRequestInterceptor) {
        return restTemplate -> {
            List<ClientHttpRequestInterceptor> list = new ArrayList<>(restTemplate.getInterceptors());
            list.add(authHttpRequestInterceptor);
            restTemplate.setInterceptors(list);
        };
    }

    @Bean
    public BearerTokenService bearerTokenService() {
        return new BearerTokenServiceImpl();
    }

    @Bean
    public BearerTokenProvider bearerTokenProvider() {
        return new BearerTokenProvider();
    }

    @Bean
    public BearerTokenAuthenticationProvider bearerTokenAuthenticationProvider() {
        return new BearerTokenAuthenticationProvider();
    }

    @Bean
    public AuthHttpRequestInterceptor authHttpRequestInterceptor() {
        return new AuthHttpRequestInterceptor(bearerTokenAuthenticationProvider());
    }

    /**
     * 未加认证功能，只用于特定场景（比如请求认证服务请求获取token）
     * @return
     */
    @LoadBalanced
    @Bean("normalRestTemplate")
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30 * 1000);
        requestFactory.setConnectionRequestTimeout(30 * 1000);
        requestFactory.setReadTimeout(60 * 1000);
        return new RestTemplate(requestFactory);
    }

}

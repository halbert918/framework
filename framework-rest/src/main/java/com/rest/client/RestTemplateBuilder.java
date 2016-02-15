package com.rest.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/3/12
 * @Description
 */
public class RestTemplateBuilder {

    private HttpComponentsClientHttpRequestFactory requestFactory;
    private CustomRestTemplate restTemplate;

    public static RestTemplateBuilder create() {
        return new RestTemplateBuilder();
    }

    /**
     * 构造CustomRestTemplate
     * @param connectTimeout
     * @param readTimeout
     * @return
     */
    public CustomRestTemplate build(int connectTimeout, int readTimeout) {
        HttpClientBuilder httpClientBuilder = RestHttpClientBuilder.create();
        return build(httpClientBuilder.build(), connectTimeout, readTimeout);
    }

    public CustomRestTemplate build(HttpClient httpClient, int connectTimeout, int readTimeout) {
        //创建clientRequestFactory,设置相关属性
        requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        //设置连接超时时间
        requestFactory.setConnectTimeout(connectTimeout);
        //设置socket读取超时时间, 若0则表示无穷
        requestFactory.setReadTimeout(readTimeout);
        restTemplate = new CustomRestTemplate(requestFactory);
        return restTemplate;
    }

    public CustomRestTemplate getRestTemplate() {
        return restTemplate;
    }
}

package com.framework.auth.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.*;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/30 10:09
 * @Description: RestTemplate扩展
 * 可用于扩展自定义header，安全认证
 * AuthRestTemplate会通过Interceptor做安全认证
 */
public class AuthRestTemplate extends RestTemplate {

    /**
     * GET:对泛型支持
     * @param url
     * @param responseType
     * @param urlVariables
     * @param <T>
     * @return
     */
    public <T> T getForObject(String url, ParameterizedTypeReference<T> responseType, Object... urlVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        ResponseEntity<T> responseEntity = exchange(url, HttpMethod.GET, httpEntity, responseType, urlVariables);
        return responseEntity.getBody();
    }

    /**
     * POST:对泛型支持
     * @param url
     * @param request
     * @param responseType
     * @param urlVariables
     * @param <T>
     * @return
     */
    public <T> T postForObject(String url, Object request, ParameterizedTypeReference<T> responseType, Object... urlVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        ResponseEntity<T> responseEntity = exchange(url, HttpMethod.POST, httpEntity, responseType, urlVariables);
        return responseEntity.getBody();
    }

    /**
     * PATCH:对泛型支持
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T patchForObject(String url, Object request, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        ResponseEntity<T> responseEntity = exchange(url, HttpMethod.PATCH, httpEntity, responseType, uriVariables);
        return responseEntity.getBody();
    }

    /**
     * 构造HttpEntity
     * @param request
     * @return
     */
    private HttpEntity<?> getHttpEntity(Object request) {
        HttpEntity<?> httpEntity = new HttpEntity<>(request);
        return mergeEntity(httpEntity);
    }

    /**
     * 合并自定义扩展header
     * @param httpEntity
     * @return
     */
    private HttpEntity<?> mergeEntity(HttpEntity<?> httpEntity) {
        HttpHeaders headers = getCustomizerHeader();
        if (headers != null) {
            httpEntity.getHeaders().putAll(headers);
        }
        return httpEntity;
    }

    /**
     * 扩展自定义header
     * @return
     */
    public HttpHeaders getCustomizerHeader() {
        return null;
    }

}

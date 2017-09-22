package com.rest.client;

import com.rest.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/3/12
 * @Description
 */
public class RestHttpClient {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CustomRestTemplate restTemplate;
    /**
     * rest配置
     */
    private IConfig config;

    /**
     * 初始化client
       */
    public RestHttpClient() {
        this(RestConstant.DEFALUT_CONNECT_TIMEOUT_VALUE, RestConstant.DEFALUT_SOCKET_READ_TIMEOUT_VALUE);
    }

    public RestHttpClient(int connectTimeout, int readTimeout) {
        if (null == config) {
            config = getDefaultConfig();
        }
        RestTemplateBuilder restTemplateBuilder = RestTemplateBuilder.create();
        restTemplate = restTemplateBuilder.build(config, connectTimeout, readTimeout);
    }

    /**
     * 获取默认的配置
     * @return
     * @throws URISyntaxException
     */
    private IConfig getDefaultConfig() {
        IResourceLoader filesystemLoader = new ClasspathResourceLoader();
        final IConfig config = new RestConfig(filesystemLoader);
        return config;
    }

    /////////////////////////////////////////////////////GET////////////////////////////////////////////////////////////
    /**
     * GET请求，返回responseType类型对象
     * @param url
     * @param responseType
     * @param urlVariables
     * @param <T>
     * @return
     */
    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        return executeForObject(url, HttpMethod.GET, httpEntity, responseType, urlVariables);
    }

    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        return executeForObject(url, HttpMethod.GET, httpEntity, responseType, urlVariables);
    }

    public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        return executeForObject(url, HttpMethod.GET, httpEntity, responseType);
    }

    /**
     * GET请求，返回ResponseEntity对象
     * @param url
     * @param responseType
     * @param urlVariables
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... urlVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        return executeForEntity(url, HttpMethod.GET, httpEntity, responseType, urlVariables);
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> urlVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        return executeForEntity(url, HttpMethod.GET, httpEntity, responseType, urlVariables);
    }

    public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        return executeForEntity(url, HttpMethod.GET, httpEntity, responseType);
    }

    /////////////////////////////////////////////////////HEAD////////////////////////////////////////////////////////////
    /**
     * HEAD请求
     * @param url
     * @param urlVariables
     * @return
     * @throws RestClientException
     */
    public HttpHeaders headForHeaders(String url, Object... urlVariables) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        return executeForHeader(url, HttpMethod.HEAD, httpEntity, urlVariables);
    }

    public HttpHeaders headForHeaders(String url, Map<String, ?> urlVariables) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        return executeForHeader(url, HttpMethod.HEAD, httpEntity, urlVariables);
    }

    public HttpHeaders headForHeaders(URI url) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        return executeForHeader(url, HttpMethod.HEAD, httpEntity);
    }

    ////////////////////////////////////////////////////POST////////////////////////////////////////////////////////////
    /**
     *
     * @param url
     * @param request
     * @param urlVariables
     * @return
     * @throws RestClientException
     */
    public URI postForLocation(String url, Object request, Object... urlVariables) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        HttpHeaders headers = executeForHeader(url, HttpMethod.POST, httpEntity, urlVariables);
        return headers.getLocation();
    }

    public URI postForLocation(String url, Object request, Map<String, ?> urlVariables) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        HttpHeaders headers = executeForHeader(url, HttpMethod.POST, httpEntity, urlVariables);
        return headers.getLocation();
    }

    public URI postForLocation(URI url, Object request) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        HttpHeaders headers = executeForHeader(url, HttpMethod.POST, httpEntity);
        return headers.getLocation();
    }
    /**
     * post请求，返回responseType类型对象
     * @param url 请求地址
     * @param request 请求参数
     * @param responseType 返回类型
     * @param uriVariables uri参数
     * @param <T>
     * @return
     */
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        return executeForObject(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        return executeForObject(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    public <T> T postForObject(URI url, Object request, Class<T> responseType) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        return executeForObject(url, HttpMethod.POST, httpEntity, responseType);
    }

    /**
     * post请求,返回ResponseEntity
     * @param url
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        return executeForEntity(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        return executeForEntity(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> postForEntity(URI url, Object request, Class<T> responseType) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        return executeForEntity(url, HttpMethod.POST, httpEntity, responseType);
    }

    /////////////////////////////////////////////////////PUT////////////////////////////////////////////////////////////

    /**
     * PUT请求
     * @param url
     * @param request
     * @param urlVariables
     * @throws RestClientException
     */
    public void put(String url, Object request, Object... urlVariables) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        execute(url, HttpMethod.PUT, httpEntity, urlVariables);
    }

    public void put(String url, Object request, Map<String, ?> urlVariables) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        execute(url, HttpMethod.PUT, httpEntity, urlVariables);
    }

    public void put(URI url, Object request) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(request);
        execute(url, HttpMethod.PUT, httpEntity);
    }

    /////////////////////////////////////////////////////DELETE////////////////////////////////////////////////////////////
    /**
     * DETELE请求
     * @param url
     * @param urlVariables
     * @throws RestClientException
     */
    public void delete(String url, Object... urlVariables) throws RestClientException {
        execute(url, HttpMethod.PUT, null, urlVariables);
    }

    public void delete(String url, Map<String, ?> urlVariables) throws RestClientException {
        execute(url, HttpMethod.PUT, null, urlVariables);
    }

    public void delete(URI url) throws RestClientException {
        execute(url, HttpMethod.PUT, null);
    }

    /////////////////////////////////////////////////////OPTIONS////////////////////////////////////////////////////////
    /**
     * OPTIONS请求
     * @param url
     * @param urlVariables
     * @return
     * @throws RestClientException
     */
    public Set<HttpMethod> optionsForAllow(String url, Object... urlVariables) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        HttpHeaders headers = executeForHeader(url, HttpMethod.OPTIONS, httpEntity, urlVariables);
        return headers.getAllow();
    }

    public Set<HttpMethod> optionsForAllow(String url, Map<String, ?> urlVariables) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        HttpHeaders headers = executeForHeader(url, HttpMethod.OPTIONS, httpEntity, urlVariables);
        return headers.getAllow();
    }

    public Set<HttpMethod> optionsForAllow(URI url) throws RestClientException {
        HttpEntity<?> httpEntity = getHttpEntity(null);
        HttpHeaders headers = executeForHeader(url, HttpMethod.OPTIONS, httpEntity);
        return headers.getAllow();
    }

    /////////////////////////////////////////////////////EXECUTE////////////////////////////////////////////////////////////
    /**
     * 不返回数据
     * @param url
     * @param method
     * @param requestEntity
     * @param urlVariables
     */
    public void execute(String url, HttpMethod method, HttpEntity<?> requestEntity, Object... urlVariables) {
        URI uri = new UriTemplate(url).expand(urlVariables);
        HttpEntity<?> mergeRequestEntity = mergeEntity(requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity);
        doExecute(uri, method, requestCallback, null);
    }

    public void execute(String url, HttpMethod method, HttpEntity<?> requestEntity, Map<String, ?> urlVariables) {
        URI uri = new UriTemplate(url).expand(urlVariables);
        HttpEntity<?> mergeRequestEntity = mergeEntity(requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity);
        doExecute(uri, method, requestCallback, null);
    }

    public void execute(URI uri, HttpMethod method, HttpEntity<?> requestEntity) {
        HttpEntity<?> mergeRequestEntity = mergeEntity(requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity);
        doExecute(uri, method, requestCallback, null);
    }

    /**
     * 返回header
     * @param url
     * @param method
     * @param requestEntity
     * @param urlVariables
     * @return
     * @throws RestClientException
     */
    public HttpHeaders executeForHeader(String url, HttpMethod method, HttpEntity<?> requestEntity, Object... urlVariables) throws RestClientException {
        UriTemplateHandler uriTemplateHandler = restTemplate.getUriTemplateHandler();
        URI expanded = uriTemplateHandler.expand(url, urlVariables);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(requestEntity);
        ResponseExtractor<HttpHeaders> responseExtractor = restTemplate.headersExtractor();
        return doExecute(expanded, method, requestCallback, responseExtractor);
    }

    public HttpHeaders executeForHeader(URI uri, HttpMethod method, HttpEntity<?> requestEntity) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(requestEntity);
        ResponseExtractor<HttpHeaders> responseExtractor = restTemplate.headersExtractor();
        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    /**
     * 执行并返回responseType类型的对象
     * @param url
     * @param method
     * @param requestEntity
     * @param responseType
     * @param urlVariables
     * @param <T>
     * @return
     */
    public <T> T executeForObject(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... urlVariables) {
        URI uri = new UriTemplate(url).expand(urlVariables);
        HttpEntity<?> mergeRequestEntity = mergeEntity(requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<T> responseExtractor = restTemplate.httpMessageConverterExtractor(responseType);
        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    public <T> T executeForObject(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> urlVariables) {
        URI uri = new UriTemplate(url).expand(urlVariables);
        HttpEntity<?> mergeRequestEntity = mergeEntity(requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<T> responseExtractor = restTemplate.httpMessageConverterExtractor(responseType);
        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    public <T> T executeForObject(URI uri, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        HttpEntity<?> mergeRequestEntity = mergeEntity(requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<T> responseExtractor = restTemplate.httpMessageConverterExtractor(responseType);
        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    /**
     * 执行并返回ResponseEntity对象,包括header对象、状态以及responseType对应的类型对象
     * @param url
     * @param method
     * @param requestEntity
     * @param responseType
     * @param urlVariables
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> executeForEntity(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... urlVariables) {
        URI uri = new UriTemplate(url).expand(urlVariables);
        HttpEntity<?> mergeRequestEntity = mergeEntity(requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    public <T> ResponseEntity<T> executeForEntity(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> urlVariables) {
        URI uri = new UriTemplate(url).expand(urlVariables);
        HttpEntity<?> mergeRequestEntity = mergeEntity(requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    public <T> ResponseEntity<T> executeForEntity(URI uri, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        HttpEntity<?> mergeRequestEntity = mergeEntity(requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    /**
     * rest请求
     * @param url 请求uri
     * @param method 请求方式
     * @param requestCallback request回调封装request请求
     * @param responseExtractor 转换返回结果response为对应的类型
     * @param <T>
     * @return
     */
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) {
        logger.info("Rest请求开始:请求方式{},请求地址{}", method, url);
        long startTime = System.currentTimeMillis();
        T result = restTemplate.execute(url, method, requestCallback, responseExtractor);
        long endTime = System.currentTimeMillis();
        logger.info("Rest请求{}:{}完成,耗时{}", method, url, (startTime - endTime) / 1000);
        return result;
    }

    /**
     * 构造HttpEntity
     * @param request
     * @return
     */
    protected HttpEntity<?> getHttpEntity(Object request) {
        return new HttpEntity<>(request);
    }

    /**
     * 合并entity
     * @param httpEntity
     * @return
     */
    private HttpEntity<?> mergeEntity(HttpEntity<?> httpEntity) {
        return new HttpEntity<>(httpEntity.getBody(), httpEntity.getHeaders());
    }
}

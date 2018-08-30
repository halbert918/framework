package com.framework.auth.util;

import com.framework.auth.exception.AuthException;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/29 17:08
 * @Description: 网上拷贝修改
 */
public class HttpClientUtil<T, R> {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final String CHARSET_UTF8 = "UTF-8";

    private static HttpClientContext context = HttpClientContext.create();

    /**
     *
     * @param url
     * @param param
     * @param responseClass
     * @param <T>
     * @return
     */
    public static <T, R> R doGet(String url, Map<String, String> param, Class<R> responseClass) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString;
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 使用HttpClient认证机制
            // response = httpClient.execute(httpGet, context);

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultString = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
                JsonUtil.getEntity(resultString, responseClass);
            }
        } catch (Exception e) {
            logger.error(">> 请求失败,请求地址:{}, 原因:{}", url, e);
            throw new AuthException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "http get request failed.");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                logger.error(">> close I/O exception:{}", e);
            }
        }
        return null;
    }

    /**
     *
     * @param url
     * @param responseClass
     * @param <T>
     * @return
     */
    public static <T, R> R doGet(String url, Class<R> responseClass) {
        return doGet(url, null, responseClass);
    }

    /**
     * 表单请求
     * @param url
     * @param param
     * @param responseClass
     * @param <T>
     * @return
     */
    public static <T, R> R doPostForm(String url, Map<String, String> param, Class<R> responseClass) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, CHARSET_UTF8);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
        } catch (Exception e) {
            logger.error(">> 请求失败,请求地址:{}, 原因:{}", url, e);
            throw new AuthException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "http post request failed.");
        } finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (IOException e) {
                logger.error(">> close I/O exception:{}", e);
            }
        }

        return JsonUtil.getEntity(resultString, responseClass);
    }

    /**
     *
     * @param url
     * @param responseClass
     * @return
     */
    public static <T, R> R doPost(String url, Class<R> responseClass) {
        return doPost(url, null, responseClass);
    }

    /**
     *
     * @param url
     * @param t
     * @param responseClass
     * @param <T>
     * @return
     */
    public static <T, R> R doPost(String url, T t, Class<R> responseClass) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            String json = JsonUtil.writeEntity(t);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            // 使用HttpClient认证机制
            // response = httpClient.execute(httpPost, context);

            resultString = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
        } catch (Exception e) {
            logger.error(">> 请求失败,请求地址:{}, 原因:{}", url, e);
            throw new AuthException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "http post request failed.");
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                logger.error(">> close I/O exception:{}", e);
            }
        }

        return JsonUtil.getEntity(resultString, responseClass);
    }

    /**
     *
     * @param username
     * @param password
     */
    public void addUserOAuth(String username, String password) {

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        org.apache.http.auth.Credentials credentials = new org.apache.http.auth.UsernamePasswordCredentials(username,
                password);
        credsProvider.setCredentials(org.apache.http.auth.AuthScope.ANY, credentials);
        context.setCredentialsProvider(credsProvider);
    }

}

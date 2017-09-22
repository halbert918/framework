package com.rest.client;

import com.rest.common.IConfig;
import com.rest.common.RestConfig;
import com.rest.common.RestConstant;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/3/12
 * @Description
 */
public class RestTemplateBuilder {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private HttpComponentsClientHttpRequestFactory requestFactory;

    private CustomRestTemplate restTemplate;

    public static RestTemplateBuilder create() {
        return new RestTemplateBuilder();
    }

    /**
     * 构造CustomRestTemplate
     * @param connectTimeout
     * @param readTimeout
     * @param config
     * @return
     */
    public CustomRestTemplate build(IConfig config, int connectTimeout, int readTimeout) {
        HttpClientBuilder builder = RestHttpClientBuilder.create();
        String useSsl = config.getProperty(RestConstant.USE_SSL);
        //判断是否使用SSL加密
        if (StringUtils.hasLength(useSsl)) {
            if (Boolean.valueOf(useSsl)) {
                String certpath = config.getProperty(RestConstant.CERT_PATH);
                String password = config.getProperty(RestConstant.CERT_PASSWORD);
                logger.info(">>构造SSLRestTemplate证书:{}", certpath);
                FileInputStream instream = null;
                try {
                    KeyStore keyStore  = KeyStore.getInstance("PKCS12");
                    instream = new FileInputStream(new File(certpath));//P12文件目录
                    keyStore.load(instream, password.toCharArray());
                    // Trust own CA and all self-signed certs
                    SSLContext sslcontext = SSLContexts.custom()
                            .loadKeyMaterial(keyStore, password.toCharArray())
                            .build();
                    // Allow TLSv1 protocol only
                    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                            sslcontext,
                            new String[] { "TLSv1" },
                            null,
                            SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

                    builder.setSSLSocketFactory(sslsf);
                } catch (Exception e) {
                    logger.error("获取证书失败,原因:", e);
                    throw new IllegalArgumentException(e);
                } finally {
                    if (null != instream) {
                        try {
                            instream.close();
                        } catch (Exception e) {
                            logger.error("关闭流失败,原因:", e);
                        }
                    }
                }
            }
        }
        if (connectTimeout <= 0) {
            connectTimeout = Integer.parseInt(config.getProperty(RestConstant.REST_CONNECT_TIMEOUT_KEY));
        }
        if (readTimeout <= 0) {
            readTimeout = Integer.parseInt(config.getProperty(RestConstant.REST_SOCKET_READ_TIMEOUT_KEY));
        }
        return build(builder.build(), connectTimeout, readTimeout);
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

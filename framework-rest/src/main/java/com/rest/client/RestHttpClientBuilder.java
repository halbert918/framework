package com.rest.client;

import com.rest.common.RestConstant;
import com.rest.util.RestProperties;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

import java.util.concurrent.TimeUnit;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/3/12
 * @Description
 */
public class RestHttpClientBuilder extends HttpClientBuilder {

    public static RestHttpClientBuilder create() {
        return new RestHttpClientBuilder();
    }

    public CloseableHttpClient build() {
        super.setConnectionManager(buildConnectionManager())
                .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
                //重试次数
//                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                ;
        return super.build();
    }

    protected HttpClientConnectionManager buildConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        configConnectionManager(connectionManager);
        startMonitorThread(connectionManager);
        return connectionManager;
    }

    protected void configConnectionManager(HttpClientConnectionManager connectionManager) {
        if (connectionManager instanceof PoolingHttpClientConnectionManager) {
            PoolingHttpClientConnectionManager poolingConnectionManager = (PoolingHttpClientConnectionManager) connectionManager;
            poolingConnectionManager.setMaxTotal(RestProperties.getPropertyForInteger(RestConstant.REST_CLIENT_POOL_MAX_TOTAL_KEY));
//            poolingConnectionManager.setMaxPerRoute(RestProperties.getPropertyForInteger(RestConstant.REST_CLIENT_MAX_PER_ROUTE_KEY));
            poolingConnectionManager.setDefaultMaxPerRoute(RestProperties.getPropertyForInteger(RestConstant.REST_CLIENT_DEFAULT_MAX_PER_ROUTE_KEY));
        }
    }

    protected void startMonitorThread(HttpClientConnectionManager connectionManager) {
        IdleConnectionMonitorThread monitorThread = new IdleConnectionMonitorThread(connectionManager);
        monitorThread.start();
    }

    /**
     * Interface for deciding how long a connection can remain
     * idle before being reused.
     * 自定义连接存活策略，即当前连接最大的空闲时长，
     * 超过该时长，则该连接必须可以被其他请求重新使用
     */
    @Immutable
    private class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {
        // 连接空闲时间
        private final int KEEP_ALIVE_TIME_OUT = 6000;

        public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
            Args.notNull(response, "HTTP response");
            //获取服务端最后响应数据
            final HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                final HeaderElement he = it.nextElement();
                final String param = he.getName();
                final String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch(final NumberFormatException ignore) {
                    }
                }
            }
            return KEEP_ALIVE_TIME_OUT;
        }
    }

    /**
     * 监控关闭过期的连接
     * 监控关闭超过指定空闲时间的连接
     */
    public static class IdleConnectionMonitorThread extends Thread {

        private HttpClientConnectionManager connMgr;

        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException e) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }

}

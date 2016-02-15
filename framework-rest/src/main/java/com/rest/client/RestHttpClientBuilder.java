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
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

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
                .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy());
        return super.build();
    }

    protected HttpClientConnectionManager buildConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        configConnectionManager(connectionManager);
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

    @Immutable
    private class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {
        public final DefaultConnectionKeepAliveStrategy INSTANCE = new DefaultConnectionKeepAliveStrategy();

        private final int KEEP_ALIVE_TIME_OUT = 60000;

        public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
            Args.notNull(response, "HTTP response");
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
}

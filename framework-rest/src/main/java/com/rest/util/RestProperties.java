package com.rest.util;

import com.rest.common.RestConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/3/13
 * @Description
 */
public class RestProperties {

    private static final Logger logger = LoggerFactory.getLogger(RestProperties.class);

    private static Properties properties;

    static {
        properties = new Properties();
        properties.put(RestConstant.REST_CONNECT_TIMEOUT_KEY, RestConstant.DEFALUT_CONNECT_TIMEOUT_VALUE);
        properties.put(RestConstant.REST_SOCKET_READ_TIMEOUT_KEY, RestConstant.DEFALUT_SOCKET_READ_TIMEOUT_VALUE);
        properties.put(RestConstant.REST_CLIENT_POOL_MAX_TOTAL_KEY, RestConstant.DEFALUT_CLIENT_POOL_MAX_TOTAL_VALUE);
        properties.put(RestConstant.REST_CLIENT_MAX_PER_ROUTE_KEY, RestConstant.CLIENT_MAX_PER_ROUTE_VALUE);
        properties.put(RestConstant.REST_CLIENT_DEFAULT_MAX_PER_ROUTE_KEY, RestConstant.CLIENT_DEFALUT_MAX_PER_ROUTE_VALUE);
        try {
            InputStream stream = RestProperties.class.getClassLoader().getResourceAsStream("rest.properties");
            if (stream != null) {
                properties.load(stream);
            }
        } catch (Exception ex) {
            logger.error("Read Properties File Failed.", ex);
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static int getPropertyForInteger(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Convert " + key + " failed by value:" + value);
        }
    }
}

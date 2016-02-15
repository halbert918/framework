package com.rest.common;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/3/13
 * @Description
 */
public abstract class RestConstant {

    /**
     * 远程连接时间
     */
    public static final String REST_CONNECT_TIMEOUT_KEY = "rest.connect.timeout";
    /**
     * socket读写时间
     */
    public static final String REST_SOCKET_READ_TIMEOUT_KEY = "rest.socket.timeout";
    /**
     * 设置连接池的最大连接数，
     */
    public static final String REST_CLIENT_POOL_MAX_TOTAL_KEY = "client.pool.maxTotal";

    /**
     * 设置某个站点最大连接个数
     */
    public static final String REST_CLIENT_MAX_PER_ROUTE_KEY = "client.maxPerRoute";

    /**
     * 设置最大路由连接数
     */
    public static final String REST_CLIENT_DEFAULT_MAX_PER_ROUTE_KEY = "client.default.maxPerRoute";

    /**
     * 默认连接超时时间
     */
    public static final int DEFALUT_CONNECT_TIMEOUT_VALUE = 5000;
    /**
     * 默认SOCKET读取超时时间
     */
    public static final int DEFALUT_SOCKET_READ_TIMEOUT_VALUE = 10000;
    /**
     * 默认连接池大小
     */
    public static final String DEFALUT_CLIENT_POOL_MAX_TOTAL_VALUE = "200";
    /**
     * 单个站点最大连接数
     */
    public static final String CLIENT_MAX_PER_ROUTE_VALUE = "50";
    /**
     * 最大路由连接数
     */
    public static final String CLIENT_DEFALUT_MAX_PER_ROUTE_VALUE = "20";

}

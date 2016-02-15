package com.framework.distribute.session.store;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date  2015/9/17
 * @Description
 */
public abstract class SessionStoreConstants {

    public static final String HOST_KEY= "host";

    public static final String PORT_KEY = "port";

    /**
     * 设置最大连接数
     */
    public static final String MAX_TOTAL_KEY = "maxTotal";

    /**
     * 连接耗尽时是否阻塞
     */
    public static final String BLOCK_WHEN_EXHAUSTED_KEY = "blockWhenExhausted";
    /**
     * 设置最大空闲连接
     */
    public static final String MAX_IDLE_KEY = "maxIdle";
    /**
     * 设置最小空闲连接
     */
    public static final String MIN_IDLE_KEY = "minIdle";
    /**
     * 设置最大阻塞时间 单位毫秒数milliseconds
     */
    public static final String MAX_WAIT_MILLIS_KEY = "maxWaitMillis";

    /**
     * 创建session时，默认的域-SESSION创建时间
     * redis hset(key, field, value)
     */
    public static final String CREATE_SESSION_DEFAULT_FIELD_TIME = "CREATE-SESSION-DEFAULT-FIELD-TIME";
}

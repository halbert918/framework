package com.framework.distribute.session.config;

import com.esotericsoftware.kryo.NotNull;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/9/3
 * @Description
 */
public class CacheConfig {
    /**
     * 端口
     */
    @NotNull
    private int port;
    /**
     * 主机
     */
    @NotNull
    private String host;
    /**
     * 设置最大连接数
     */
    private int maxTotal = 100;
    /**
     * 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
     */
    private boolean blockWhenExhausted = true;
    /**
     * 设置最大空闲连接
     */
    private int maxIdle = 10;
    /**
     * 设置最小空闲连接
     */
    private int minIdle = 0;
    /**
     * 设置最大阻塞时间 单位毫秒数milliseconds
     */
    private long maxWaitMillis = 1000;
    /**
     * 在获取连接的时候检查有效性, 默认false
     */
    private boolean testOnBorrow = false;
    /**
     * 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
     */
    private boolean testWhileIdle = false;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public boolean isBlockWhenExhausted() {
        return blockWhenExhausted;
    }

    public void setBlockWhenExhausted(boolean blockWhenExhausted) {
        this.blockWhenExhausted = blockWhenExhausted;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }
}

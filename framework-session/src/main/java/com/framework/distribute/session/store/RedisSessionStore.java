package com.framework.distribute.session.store;

import com.framework.distribute.session.exception.SessionException;
import com.framework.distribute.session.util.ResourceUtil;
import com.framework.distribute.session.util.SerializerUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/27
 * @Description
 */
public class RedisSessionStore extends SessionStoreBase {

    private static final String PROPERTIES_FILE_NAME = "redis";

    private JedisPool jedisPool;

    public Jedis getJedis() {
        if(null == jedisPool) {
            initPool();
        }
        return jedisPool.getResource();
    }

    /**
     * 初始化连接池
     */
    public synchronized void initPool() {
        if(null == jedisPool) {
            createPool();
        }
    }

//    public void createPool1() {
//        jedisPool = new JedisPool("192.168.63.173", 6379);
//    }

//    public void createPool() {
//        JedisPoolConfig config = new JedisPoolConfig();
//
//
//        config.setMaxTotal(200);
//        jedisPool = new JedisPool(config, "192.168.63.173", 6379);
//    }

    /**
     * 创建连接池
     */
    public void createPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        if(!ResourceUtil.containsKey(PROPERTIES_FILE_NAME, SessionStoreConstants.HOST_KEY)) {
            throw new SessionException("redis配置文件缺少配置主机host");
        }
        String host = ResourceUtil.getValue(PROPERTIES_FILE_NAME, SessionStoreConstants.HOST_KEY);
        if(!ResourceUtil.containsKey(PROPERTIES_FILE_NAME, SessionStoreConstants.PORT_KEY)) {
            throw new SessionException("redis配置文件缺少配置端口port");
        }
        int port = Integer.parseInt(ResourceUtil.getValue(PROPERTIES_FILE_NAME, SessionStoreConstants.PORT_KEY));
        if(ResourceUtil.containsKey(PROPERTIES_FILE_NAME, SessionStoreConstants.MAX_IDLE_KEY)) {
            config.setMaxIdle(Integer.parseInt(ResourceUtil.getValue(PROPERTIES_FILE_NAME, SessionStoreConstants.MAX_IDLE_KEY)));
        }
        if(ResourceUtil.containsKey(PROPERTIES_FILE_NAME, SessionStoreConstants.MAX_TOTAL_KEY)) {
            config.setMaxTotal(Integer.parseInt(ResourceUtil.getValue(PROPERTIES_FILE_NAME, SessionStoreConstants.MAX_TOTAL_KEY)));
        }
        if(ResourceUtil.containsKey(PROPERTIES_FILE_NAME, SessionStoreConstants.MIN_IDLE_KEY)) {
            config.setMinIdle(Integer.parseInt(ResourceUtil.getValue(PROPERTIES_FILE_NAME, SessionStoreConstants.MIN_IDLE_KEY)));
        }
        jedisPool = new JedisPool(config, host, port);
    }

    /**
     * 释放Jedis
     * @param jedis
     */
    public void returnResource(Jedis jedis) {
        if(null != jedis) {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    public long getSize() {
        Jedis jedis = getJedis();
        try {
            return jedis.keys("*").size();
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public long getSize(String sessionId) {
        Set<String> set = get(sessionId);
        if(null != set) {
            return set.size();
        }
        return 0;
    }


    @Override
    public long set(String sessionId, String key, Object value) {
        Jedis jedis = getJedis();
        try {
            return jedis.hset(sessionId.getBytes(), key.getBytes(), SerializerUtil.kryoSerialize(value));
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Object get(String sessionId, String key) {
        Jedis jedis = getJedis();
        try {
            byte[] data = jedis.hget(sessionId.getBytes(), key.getBytes());
            return SerializerUtil.kryoDeserialize(data);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Set<String> get(String sessionId) {
        Jedis jedis = getJedis();
        try {
            return jedis.hkeys(sessionId);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean containsKey(String sessionId, String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.hexists(sessionId.getBytes(), key.getBytes());
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void remove(String sessionId, String key) {
        Jedis jedis = getJedis();
        try {
            jedis.hdel(sessionId.getBytes(), key.getBytes());
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void remove(String sessionId) {
        Jedis jedis = getJedis();
        try {
            jedis.del(sessionId.getBytes());
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void setExpire(String sessionId, int maxInactiveInterval) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(sessionId.getBytes(Protocol.CHARSET), maxInactiveInterval);
        } catch (Exception e) {
            throw new SessionException(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean isValid(String sessionId, String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.hexists(sessionId.getBytes(), key.getBytes());
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean isValid(String sessionId) {
        Jedis jedis = getJedis();
        try {
            return jedis.exists(sessionId.getBytes());
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void destroy() {
        if(null != jedisPool) {
            jedisPool.destroy();
        }
    }

}

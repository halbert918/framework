package com.framework.distribute.session.store;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/27
 * @Description
 */
public abstract class SessionStoreBase {

    protected static final String info = "StoreBase/1.0";

    protected static String storeName = "StoreBase";

    /**
     * 统计session数量
     * @return
     */
    public abstract long getSize();

    /**
     *
     * @return
     * @throws IOException
     */
    public abstract long getSize(String sessionId);

    /**
     *
     * @param sessionId
     * @param key
     * @return
     */
    public abstract boolean isValid(String sessionId, String key);

    /**
     *
     * @param sessionId
     * @return
     */
    public abstract boolean isValid(String sessionId);

    /**
     *
     * @param sessionId
     * @param key
     * @return
     */
    public abstract Object get(String sessionId, String key);

    /**
     *
     * @param sessionId
     * @return
     */
    public abstract Set<String> get(String sessionId);

    /**
     *
     * @param sessionId
     * @param key
     * @return
     */
    public abstract boolean containsKey(String sessionId, String key);

    /**
     *
     * @param sessionId
     * @param key
     */
    public abstract void remove(String sessionId, String key);

    /**
     *
     * @param sessionId
     */
    public abstract void remove(String sessionId);

    /**
     * 设置过期时间
     * @param id
     */
    public abstract void setExpire(String id, int maxInactiveInterval);

    /**
     * 销毁
     */
    public abstract void destroy();

    /**
     *
     * @param sessionId
     * @param key
     * @param value
     * @return
     */
    public abstract long set(String sessionId, String key, Object value);

    public static String getInfo() {
        return info;
    }

    public static String getStoreName() {
        return storeName;
    }

    public static void setStoreName(String storeName) {
        SessionStoreBase.storeName = storeName;
    }
}

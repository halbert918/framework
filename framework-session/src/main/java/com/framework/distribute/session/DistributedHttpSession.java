package com.framework.distribute.session;

import com.framework.distribute.session.exception.SessionException;
import com.framework.distribute.session.store.SessionStoreBase;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Set;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/27
 * @Description
 */
public class DistributedHttpSession implements HttpSession, Serializable {

    /**
     * sessionId
     */
    private String id;
    /**
     * 创建session时间
     */
    private long creationTime;
    /**
     * 最后使用session时间
     */
    private long lastAccessedTime;
    /**
     *
     */
    protected int maxInactiveInterval = -1;
    /**
     * 是否有效
     */
    protected volatile boolean isValid = true;

    private SessionStoreBase sessionStore;

    public DistributedHttpSession() {
    }

    public DistributedHttpSession(String id, SessionStoreBase sessionStore, int maxInactiveInterval) {
        this(id, sessionStore, maxInactiveInterval, System.currentTimeMillis());
    }

    public DistributedHttpSession(String id, SessionStoreBase sessionStore, int maxInactiveInterval, long creationTime) {
        this.id = id;
        this.maxInactiveInterval = maxInactiveInterval;
        this.sessionStore = sessionStore;
        setCreationTime(creationTime);
    }


    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
        this.lastAccessedTime = creationTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    @Deprecated
    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Deprecated
    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getAttribute(String key) {
        if (!isValidInternal()) {
            throw new SessionException("无效Session");
        }

        if (null == key) {
            return null;
        }
        return sessionStore.get(id, key);
    }

    @Override
    public Object getValue(String key) {
        return getAttribute(key);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        if (!isValidInternal()) {
            throw new SessionException("无效Session");
        }
        return new Enumerator<String>(sessionStore.get(id), true);
    }

    @Override
    public String[] getValueNames() {
        Set<String> names = sessionStore.get(id);
        return null == names ? null : (String[]) names.toArray();
    }

    @Override
    public void setAttribute(String key, Object o) {
        if (!isValidInternal()) {
            throw new SessionException("无效Session");
        }

        if (key == null) {
            throw new SessionException("key不能为空");
        }

        if (null == o) {
            removeAttribute(key);
        }

        sessionStore.set(id, key, o);
    }

    @Override
    public void putValue(String key, Object o) {
        setAttribute(key, o);
    }

    @Override
    public void removeAttribute(String key) {
        if (null == key)
            return;
        sessionStore.remove(id, key);
    }

    @Override
    public void removeValue(String key) {
        removeAttribute(key);
    }

    @Override
    public void invalidate() {
        sessionStore.remove(id);
        isValid = false;
    }

    /**
     * 判断session是否可用
     *
     * @return
     */
    protected boolean isValidInternal() {
        if (!isValid) {
            return false;
        }
        return sessionStore.isValid(id);
    }

    @Override
    public boolean isNew() {
        return lastAccessedTime == creationTime;
    }


}

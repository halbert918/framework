package com.framework.distribute.session.manager;

import com.framework.distribute.session.DistributedHttpSession;
import com.framework.distribute.session.exception.SessionException;
import com.framework.distribute.session.id.SessionIdGenerator;
import com.framework.distribute.session.store.RedisSessionStore;
import com.framework.distribute.session.store.SessionStoreBase;
import com.framework.distribute.session.store.SessionStoreConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/8/27
 * @Description
 */
public class DistributedSessionManager implements SessionManager {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String info = "DistributedSessionManager/1.0";

    protected final static int DEFAULT_MAX_TIME_OUT = 30 * 60;

    /**
     * 过期时间
     */
    protected  int maxInactiveInterval = DEFAULT_MAX_TIME_OUT;
    /**
     * ID长度
     */
    protected int sessionIdLength = 16;
    /**
     * sessionCounter
     */
    protected long sessionCounter = 0;
    /**
     * name
     */
    protected static String name = "DistributedSessionManager";

    protected SessionIdGenerator sessionIdGenerator = null;

    protected SessionStoreBase sessionStore = null;

    public DistributedSessionManager(String storeType) {
        this(storeType, DEFAULT_MAX_TIME_OUT);
    }

    public DistributedSessionManager(String storeType, int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
        initStore(storeType);
    }

    /**
     * 初始化存储session对象
     * @param storeType
     */
    private void initStore(String storeType) {
        if(null == storeType) {
            sessionStore = new RedisSessionStore();
            return ;
        }
        try {
            Class clazz = Class.forName(storeType);
            sessionStore = (SessionStoreBase) clazz.newInstance();
        } catch (Exception e) {
            logger.error("初始化session分布式存储对象失败{}", e);
            throw new SessionException("初始化session分布式存储对象失败");
        }
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    @Override
    public int getSessionIdLength() {
        return sessionIdLength;
    }

    @Override
    public void setSessionIdLength(int idLength) {
        this.sessionIdLength = idLength;
    }

    @Override
    public long getSessionCounter() {
        sessionCounter = sessionStore.getSize();
        return sessionCounter;
    }

    @Override
    public HttpSession createEmptySession() {

        return new DistributedHttpSession();
    }

    @Override
    public HttpSession createSession(String sessionId) {
        if(null == sessionId) {
            sessionId = generateSessionId();
        }
        long createTime =  System.currentTimeMillis();
        HttpSession session = new DistributedHttpSession(sessionId, sessionStore, maxInactiveInterval, createTime);
        //redis中创建session标示（可直接保存session到redis中，但每次都需序列化与反序列化，所有就只存储session中的attr）
        sessionStore.set(sessionId, SessionStoreConstants.CREATE_SESSION_DEFAULT_FIELD_TIME, createTime);
        return session;
    }

    @Override
    public HttpSession findSession(String id) {
        if(null != id && sessionStore.isValid(id)) {
            //获取创建session的时间
            long createTime = (long)sessionStore.get(id, SessionStoreConstants.CREATE_SESSION_DEFAULT_FIELD_TIME);
            HttpSession session = new DistributedHttpSession(id, sessionStore, maxInactiveInterval, createTime);
            ((DistributedHttpSession)session).setLastAccessedTime(System.currentTimeMillis());
            return session;
        }
        return null;
    }

    @Override
    public void remove(HttpSession session) {
        sessionStore.remove(session.getId());
    }

    @Override
    public void setExpire(String id) {
        if(null != id) {
            sessionStore.setExpire(id, maxInactiveInterval);
        }
    }

    @Override
    public boolean isSessionIdValid(String id) {
        if(null != id) {
            return sessionStore.isValid(id);
        }
        return false;
    }

    @Override
    public void destroy() {
        sessionStore.destroy();
    }

    /**
     * 生成sessionId
     * @return
     */
    protected String generateSessionId() {
        String sessionId = SessionIdGenerator.generateSessionId();
        setSessionIdLength(sessionId.length());
        return sessionId;
    }

}

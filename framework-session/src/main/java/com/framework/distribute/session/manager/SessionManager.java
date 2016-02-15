/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.framework.distribute.session.manager;


import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * A <b>SessionManager</b> manages the pool of Sessions that are associated with a
 * particular Container.  Different SessionManager implementations may support
 * value-added features such as the persistent storage of session data,
 * as well as migrating sessions for distributable web applications.
 * <p>
 * In order for a <code>SessionManager</code> implementation to successfully operate
 * with a <code>Context</code> implementation that implements reloading, it
 * must obey the following constraints:
 * <ul>
 * <li>Must implement <code>Lifecycle</code> so that the Context can indicate
 *     that a restart is required.
 * <li>Must allow a call to <code>stop()</code> to be followed by a call to
 *     <code>start()</code> on the same <code>SessionManager</code> instance.
 * </ul>
 *
 * @author Craig R. McClanahan
 * @version $Id: SessionManager.java 1036595 2010-11-18 19:59:11Z markt $
 */

public interface SessionManager {



    /**
     * Return descriptive information about this SessionManager implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo();


    /**
     * Return the default maximum inactive interval (in seconds)
     * for Sessions created by this SessionManager.
     */
    public int getMaxInactiveInterval();


    /**
     * Set the default maximum inactive interval (in seconds)
     * for Sessions created by this SessionManager.
     *
     * @param interval The new default value
     */
    public void setMaxInactiveInterval(int interval);


    /**
     * Gets the session id length (in bytes) of Sessions created by
     * this SessionManager.
     *
     * @return The session id length
     */
    public int getSessionIdLength();


    /**
     * Sets the session id length (in bytes) for Sessions created by this
     * SessionManager.
     *
     * @param idLength The session id length
     */
    public void setSessionIdLength(int idLength);


    /**
     * Returns the total number of sessions created by this manager.
     *
     * @return Total number of sessions created by this manager.
     */
    public long getSessionCounter();


    /**
     * Get a session from the recycled ones or create a new empty one.
     * The PersistentManager manager does not need to create session data
     * because it reads it from the Store.
     */
    public HttpSession createEmptySession();


    /**
     * Construct and return a new session object, based on the default
     * settings specified by this SessionManager's properties.  The session
     * id specified will be used as the session id.
     * If a new session cannot be created for any reason, return 
     * <code>null</code>.
     *
     * @param sessionId The session id which should be used to create the
     *  new session; if <code>null</code>, the session
     *  id will be assigned by this method, and available via the getId()
     *  method of the returned session.
     * @exception IllegalStateException if a new session cannot be
     *  instantiated for any reason
     */
    public HttpSession createSession(String sessionId);


    /**
     * Return the active Session, associated with this SessionManager, with the
     * specified session id (if any); otherwise return <code>null</code>.
     *
     * @param id The session id for the session to be returned
     *
     * @exception IllegalStateException if a new session cannot be
     *  instantiated for any reason
     * @exception IOException if an input/output error occurs while
     *  processing this request
     */
    public HttpSession findSession(String id);


    /**
     * Remove this Session from the active Sessions for this SessionManager.
     *
     * @param session Session to be removed
     */
    public void remove(HttpSession session);

    /**
     * set Time Out
     * @param id
     */
    public void setExpire(String id);

    /**
     *
     * @param id
     * @return
     */
    public boolean isSessionIdValid(String id);

    public void destroy();

}

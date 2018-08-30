package com.framework.auth.security.service;

import com.framework.auth.security.domain.User;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/12 17:58
 * @Description: 需实现
 */
public interface UserService {

    /**
     *
     * @param token
     * @return
     */
    User getUserByToken(String token);

}

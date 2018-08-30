package com.framework.auth.security.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/30 17:56
 * @Description:
 */
public class User implements Serializable {

    private String userId;

    private String userName;

    private String password;

    private List<Role> roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}

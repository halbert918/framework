package com.framework.auth.security.domain;

/**
 * @Auther: heyinbo
 * @Date: 2018/8/30 18:05
 * @Description:
 */
public class Role {
    /**
     *
     */
    private Long roleId;
    /**
     *
     */
    private String roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}

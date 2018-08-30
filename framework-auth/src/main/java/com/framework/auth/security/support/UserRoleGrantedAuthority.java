package com.framework.auth.security.support;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/13 10:09
 * @Description: 用户角色授权
 */
public class UserRoleGrantedAuthority implements GrantedAuthority {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;

    public UserRoleGrantedAuthority(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

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

    @Override
    public String getAuthority() {
        return roleName == null ? String.valueOf(roleId) : roleName;
    }

}

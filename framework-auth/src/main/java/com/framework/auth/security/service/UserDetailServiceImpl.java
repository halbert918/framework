package com.framework.auth.security.service;

import com.framework.auth.security.domain.Role;
import com.framework.auth.security.domain.User;
import com.framework.auth.security.support.UserDetailInfo;
import com.framework.auth.security.support.UserRoleGrantedAuthority;
import com.framework.auth.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/12 19:22
 * @Description: 获取用户信息Service
 */
@Component("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Resource(name = "redisTemplate")
    private ValueOperations valueOperations;

    @Value("${senseface.api.common.auth.useCache:false}")
    private boolean useCache;

    public static final String TARGET_LIBRARY_KEY = "SenseTime:SenseFace:AccessToken:%s";

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        try {
            User user = null;
            String cacheKey = String.format(TARGET_LIBRARY_KEY, token);
            if (useCache) {
                String userJson = (String) valueOperations.get(cacheKey);

                if (!StringUtils.isEmpty(userJson)) {
                    user = JsonUtil.getEntity(userJson, User.class);
                } else {
                    user = userService.getUserByToken(token);
                    valueOperations.set(cacheKey, JsonUtil.writeEntity(user), 30, TimeUnit.MINUTES);
                }

            }

            if (user == null) {
                user = userService.getUserByToken(token);
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (!CollectionUtils.isEmpty(user.getRoles())) {
                for (Role role : user.getRoles()) {
                    authorities.add(new UserRoleGrantedAuthority(role.getRoleId(), role.getRoleName()));
                }
            }
            UserDetailInfo userDetail = new UserDetailInfo();
            userDetail.setAuthorities(authorities);
            userDetail.setUser(user);
            return userDetail;
        } catch (Exception e) {
            throw new UsernameNotFoundException("load user info failed", e);
        }
    }

}

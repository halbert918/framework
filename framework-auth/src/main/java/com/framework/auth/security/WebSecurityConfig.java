package com.framework.auth.security;

import com.framework.auth.security.filter.AuthenticationFilter;
import com.framework.auth.security.support.AuthenticationExtractorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/12 19:41
 * @Description: spring security config
 */
@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private List<AuthenticationProvider> providers;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        if (providers != null) {
            for (AuthenticationProvider provider : providers) {
                auth.authenticationProvider(provider);
            }
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        if (providers != null) {
//            for (AuthenticationProvider provider : providers) {
//                http.authenticationProvider(provider);
//            }
//        }
        http.authorizeRequests()
                .anyRequest().permitAll()
//                .antMatchers("/senseface/**").permitAll()                  //允许所有用户访问senseface下的资源
//                .anyRequest().authenticated()                             //允许认证通过的用户访问
                .and()
                .addFilterAfter(senseAuthenticationFilter(),
                        SecurityContextPersistenceFilter.class)           //保障SecurityContext内的对象被卸载
                .csrf().disable()
                .cors();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
//                .antMatchers("/settings/**")
//                .antMatchers("/inner/settings/**")
                .antMatchers("/logs")
                .antMatchers("/health");
    }

    /**
     * 构建filter
     * @return
     * @throws Exception
     */
    private AuthenticationFilter senseAuthenticationFilter() throws Exception {
        return new AuthenticationFilter(super.authenticationManager(), senseAuthenticationExtractorManager());
    }

    @Bean
    public AuthenticationExtractorManager senseAuthenticationExtractorManager() {
        return new AuthenticationExtractorManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // 允许所有跨域请求 — "*"
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
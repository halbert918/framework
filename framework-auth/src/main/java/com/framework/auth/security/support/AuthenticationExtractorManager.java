package com.framework.auth.security.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: heyinbo
 * @Date: 2018/6/13 11:09
 * @Description: Authentication转换管理类
 */
public class AuthenticationExtractorManager {
    /**
     * 支持不同类型的转换类列表
     */
    private List<AuthenticationExtractor> extractors = new ArrayList<>();

    @Autowired
    public void addExtractors(List<AuthenticationExtractor> extractors) {
        this.extractors.addAll(extractors);
    }

    /**
     * 根据参数判断使用不同的转换器
     * @param request
     * @return
     * @throws AuthenticationException
     */
    public Authentication extractAuthentication(HttpServletRequest request) throws AuthenticationException {

        for (AuthenticationExtractor extractor : extractors) {
            if (extractor.supports(request)) {
                return extractor.extractAuthentication(request);
            }
        }
        return null;
    }

}

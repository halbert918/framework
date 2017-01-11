package com.framework.process.parser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description 自定义Job解析器Handler
 */
public class JobNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("job", new JobBeanDefinitionParser());
    }
}
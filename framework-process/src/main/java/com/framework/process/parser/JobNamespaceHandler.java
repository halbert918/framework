package com.framework.process.parser;


import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author heyinbo
 *
 * 自定义Job解析器Handler
 */
public class JobNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("job", new JobBeanDefinitionParser());
    }
}
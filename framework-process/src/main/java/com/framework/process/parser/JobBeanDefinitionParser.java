package com.framework.process.parser;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author heyinbo
 *
 * 自定义Job解析器
 */
public class JobBeanDefinitionParser extends AbstractBeanDefinitionParser {


    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        // TODO 解析schemas
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        String className = element.getAttribute("class");
        if(StringUtils.hasLength(className)) {
            beanDefinition.getPropertyValues().addPropertyValue("className", className);
        }
        String initMethod = element.getAttribute("init-method");
        if(StringUtils.hasLength(initMethod)) {
            beanDefinition.setInitMethodName(initMethod);
        }
        String isAbstract = StringUtils.trimWhitespace(element.getAttribute("abstract"));
        if(StringUtils.hasLength(isAbstract)) {
            beanDefinition.setAbstract(Boolean.parseBoolean(isAbstract));
        }
        return beanDefinition;
    }
}

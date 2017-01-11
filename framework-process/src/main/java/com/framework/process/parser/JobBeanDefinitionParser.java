package com.framework.process.parser;

import com.framework.process.DefaultJobContext;
import com.framework.process.SimpleJob;
import com.framework.process.support.DeciderHolder;
import com.framework.process.support.NodeNameHolder;
import com.framework.process.support.SimpleJobFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Description 自定义Job解析器，job-context-node
 */
public class JobBeanDefinitionParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder jobFactory = BeanDefinitionBuilder.rootBeanDefinition(SimpleJobFactory.class);
        //job bean definition
        GenericBeanDefinition jobDefinition = new GenericBeanDefinition();
        String id = StringUtils.trimWhitespace(element.getAttribute("id"));
        if(StringUtils.hasLength(id)) {
            jobDefinition.getPropertyValues().addPropertyValue("id", id);
        }
        String name = StringUtils.trimWhitespace(element.getAttribute("name"));
        if(StringUtils.hasLength(name)) {
            jobDefinition.getPropertyValues().addPropertyValue("name", name);
        }
        String className = element.getAttribute("class");
        if(StringUtils.hasLength(className)) {
            jobDefinition.setBeanClassName(className);
        }
        String initMethod = element.getAttribute("init-method");
        if(StringUtils.hasLength(initMethod)) {
            jobDefinition.setInitMethodName(initMethod);
        }
        String isAbstract = StringUtils.trimWhitespace(element.getAttribute("abstract"));
        if(StringUtils.hasLength(isAbstract)) {
            jobDefinition.setAbstract(Boolean.parseBoolean(isAbstract));
        }
        //contextDefinition
        RootBeanDefinition contextDefinition = new RootBeanDefinition();
        Element contextElement = DomUtils.getChildElementByTagName(element, "context");
        String contextName = StringUtils.trimWhitespace(contextElement.getAttribute("name"));
        if(StringUtils.hasLength(contextName)) {
            contextDefinition.getPropertyValues().addPropertyValue("name", contextName);
        }
        String contextClassName = contextElement.getAttribute("class");
        if(StringUtils.hasLength(contextClassName)) {
            contextDefinition.setBeanClassName(contextClassName);
        }
        jobDefinition.getPropertyValues().addPropertyValue("jobContext", contextDefinition);
        //add job beanDefinition property
        jobFactory.addPropertyValue("job", jobDefinition);
        //parse node
        Element nodeElement = DomUtils.getChildElementByTagName(contextElement, "node");
        jobFactory.addPropertyValue("nodeNameHolder", parseNodeElement(nodeElement, null, parserContext));
        return jobFactory.getBeanDefinition();
    }

    /**
     * 解析node子节点
     * @param nodeElement
     * @param rootHolder
     * @param parserContext
     */
    private static NodeNameHolder parseNodeElement(Element nodeElement, NodeNameHolder rootHolder, ParserContext parserContext) {
        String name = StringUtils.trimWhitespace(nodeElement.getAttribute("name"));
        String className = StringUtils.trimWhitespace(nodeElement.getAttribute("class"));
        String scope = StringUtils.trimWhitespace(nodeElement.getAttribute("scope"));

        BeanDefinitionBuilder nodeBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(className);
        if(!StringUtils.hasLength(scope)) {
            scope = BeanDefinition.SCOPE_SINGLETON;
        }

        nodeBeanDefinitionBuilder.setScope(scope);
        nodeBeanDefinitionBuilder.addPropertyValue("name", name);
        AbstractBeanDefinition beanDefinition = nodeBeanDefinitionBuilder.getBeanDefinition();

        if(validate(name, beanDefinition, parserContext)) {
            throw new RuntimeException("配置错误，同一beanName不能对应多个beanClass");
        }

        BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(
                beanDefinition, name), parserContext.getRegistry());
        NodeNameHolder nodeHolder;
        if(null == rootHolder) {
            rootHolder = nodeHolder = new NodeNameHolder(name, true);
        } else {
            nodeHolder = new NodeNameHolder(name);
            rootHolder.addChild(nodeHolder);
        }
        //decision节点,根据配置信息选择不同的node节点执行
        Element decisionElement = DomUtils.getChildElementByTagName(nodeElement, "decision");
        if(null != decisionElement) {
            nodeHolder.setDeciderHolder(parseDeciderElement(decisionElement, parserContext));
        }
        //node子节点
        List<Element> nodeElements = DomUtils.getChildElementsByTagName(nodeElement, "node");
        if(null != nodeElements) {
            for(Element element : nodeElements) {
                parseNodeElement(element, nodeHolder, parserContext);
            }
        }
        return rootHolder;
    }

    /**
     * 解析decision节点
     * @param element
     * @param parserContext
     * @return
     */
    private static DeciderHolder parseDeciderElement(Element element, ParserContext parserContext) {
        String name = StringUtils.trimWhitespace(element.getAttribute("name"));
        String decide = StringUtils.trimWhitespace(element.getAttribute("decide"));
        if(!StringUtils.hasLength(decide)) {
            throw new RuntimeException("配置错误，节点[decision]必须配置[decide]属性");
        }
        return new DeciderHolder(name, decide);
    }

    /**
     * 解析job节点
     * @param element
     * @param parserContext
     * @return
     */
    private static AbstractBeanDefinition parseJobElement(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder job = BeanDefinitionBuilder.rootBeanDefinition(SimpleJob.class);
        String id = StringUtils.trimWhitespace(element.getAttribute("id"));
        if(StringUtils.hasLength(id)) {
            job.addPropertyValue("id", id);
        }
        String name = StringUtils.trimWhitespace(element.getAttribute("name"));
        if(StringUtils.hasLength(name)) {
            job.addPropertyValue("name", name);
        }

        Element contextElement = DomUtils.getChildElementByTagName(element, "job");
        job.addPropertyValue("jobContext", parseContextElement(contextElement, parserContext));
        return job.getBeanDefinition();
    }


    /**
     * 验证生成的beanDefinition是否含有相同的beanName且class不一样
     * @param beanName
     * @param nodeBeanDefinition
     * @param parserContext
     * @return
     */
    private static boolean validate(String beanName, BeanDefinition nodeBeanDefinition, ParserContext parserContext) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        if(registry.containsBeanDefinition(beanName)) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            if(!nodeBeanDefinition.equals(beanDefinition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解析context节点
     * @param element
     * @param parserContext
     * @return
     */
    private static AbstractBeanDefinition parseContextElement(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder jobContext = BeanDefinitionBuilder.rootBeanDefinition(DefaultJobContext.class);
//        Element nodeElement = DomUtils.getChildElementByTagName(element, "context");
//        jobContext.addPropertyValue("nodeHolder", parseChildNode(nodeElement, null, parserContext));
        return jobContext.getBeanDefinition();
    }

}

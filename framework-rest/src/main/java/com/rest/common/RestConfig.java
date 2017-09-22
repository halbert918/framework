package com.rest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.text.ParseException;
import java.util.Properties;

/**
 * Rest相关参数配置Config
 * Created by admin on 2017/9/22.
 */
public class RestConfig extends IConfig {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Properties properties;

    private final IResourceLoader resourceLoader;

    public RestConfig(IResourceLoader resourceLoader) {
        this(resourceLoader, null);
    }

    public RestConfig(IResourceLoader resourceLoader, String configName) {
        this.resourceLoader = resourceLoader;
        Reader configReader = configName != null ? resourceLoader
                .loadResource(configName) : resourceLoader
                .loadDefaultResource();
        if (configReader == null) {
            throw new IllegalArgumentException("Can't load resource " + resourceLoader.getName() + " \"" + configName + "\"");
        }
        ConfigurationParser confParser = new ConfigurationParser();
        properties = confParser.getProperties();
        assignDefaults();
        try {
            confParser.parse(configReader);
        } catch (ParseException pex) {
            logger.error("配置文件解析出错，原因:", pex);
//            throw new IllegalArgumentException("解析配置文件出错");
        }
    }

    @Override
    public void setProperty(String name, String value) {
        properties.put(name, value);
    }

    @Override
    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    @Override
    public IResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}

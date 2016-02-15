package com.rest.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/3/12
 * @Description
 */
public class CustomRestTemplate extends RestTemplate {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    //判断第三方Converter对应的mapper是否存在
    private static boolean romePresent =
            ClassUtils.isPresent("com.sun.syndication.feed.WireFeed", CustomRestTemplate.class.getClassLoader());

    private static final boolean jaxb2Present =
            ClassUtils.isPresent("javax.xml.bind.Binder", CustomRestTemplate.class.getClassLoader());

    private static final boolean jackson2Present =
            ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", CustomRestTemplate.class.getClassLoader()) &&
                    ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", CustomRestTemplate.class.getClassLoader());

    private static final boolean jackson2XmlPresent =
            ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", RestTemplate.class.getClassLoader());

    private static final boolean gsonPresent =
            ClassUtils.isPresent("com.google.gson.Gson", RestTemplate.class.getClassLoader());

    public CustomRestTemplate() {
        configureMessageConverters();
    }

    public CustomRestTemplate(ClientHttpRequestFactory requestFactory) {
        this();
        setRequestFactory(requestFactory);
    }

    public List<HttpMessageConverter<?>> configureMessageConverters(){
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<Source>());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        converters.add(new MarshallingHttpMessageConverter());
        if (romePresent) {
            converters.add(new AtomFeedHttpMessageConverter());
            converters.add(new RssChannelHttpMessageConverter());
        }

        if (jackson2XmlPresent) {
            converters.add(new MappingJackson2XmlHttpMessageConverter());
        }

        if (jaxb2Present) {
            converters.add(new Jaxb2RootElementHttpMessageConverter());
        }

        if (jackson2Present) {
            MappingJackson2HttpMessageConverter convert = new MappingJackson2HttpMessageConverter();
            convert.setObjectMapper(CustomObjectMapper.getMapper());
            converters.add(convert);
        } else if (gsonPresent) {
            converters.add(new GsonHttpMessageConverter());
        }
        return converters;
    }

    /**
     * 添加Converter
     * @param messageConverter
     */
    public void addMessageConverter(HttpMessageConverter<?> messageConverter) {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(messageConverter);
        setMessageConverters(converters);
    }

    /**
     * 构造mapper参数
     */
    public static class CustomObjectMapper{
        private static com.fasterxml.jackson.databind.ObjectMapper objectMapper;

        public static com.fasterxml.jackson.databind.ObjectMapper getMapper() {
            if(null == objectMapper) {
                objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                setConfig();
            }
            return objectMapper;
        }

        //配置mapper序列化方式
        private static void setConfig() {
            //不序列化null的map
            objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            //序列化ENUM时，使用Enum.toString()读取枚举
            objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
            //反序列化ENUM时，使用Enum.toString()读取枚举
            objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
            // 忽略不存在的属性
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        public static void setMapper(com.fasterxml.jackson.databind.ObjectMapper objectMapper) {
            CustomObjectMapper.objectMapper = objectMapper;
        }
    }

    @Override
    protected <T> RequestCallback acceptHeaderRequestCallback(Class<T> responseType) {
        return super.acceptHeaderRequestCallback(responseType);
    }

    @Override
    protected <T> RequestCallback httpEntityCallback(Object requestBody) {
        return super.httpEntityCallback(requestBody);
    }

    @Override
    protected <T> RequestCallback httpEntityCallback(Object requestBody, Type responseType) {
        return super.httpEntityCallback(requestBody, responseType);
    }

    @Override
    protected <T> ResponseExtractor<ResponseEntity<T>> responseEntityExtractor(Type responseType) {
        return super.responseEntityExtractor(responseType);
    }

    protected <T> ResponseExtractor<T> httpMessageConverterExtractor(Type responseType) {
        return new HttpMessageConverterExtractor(responseType, getMessageConverters());
    }

    /**
     * Returns a response extractor for {@link HttpHeaders}.
     */
    @Override
    protected ResponseExtractor<HttpHeaders> headersExtractor() {
        return super.headersExtractor();
    }

}

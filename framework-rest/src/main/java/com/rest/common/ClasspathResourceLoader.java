package com.rest.common;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 读取classpath下的文件
 * Created by admin on 2017/9/22.
 */
public class ClasspathResourceLoader implements IResourceLoader {

	private final String defaultResource;

	private final ClassLoader classLoader;

	public ClasspathResourceLoader() {
		this(IConfig.DEFAULT_CONFIG);
	}

	public ClasspathResourceLoader(String defaultResource) {
		this(defaultResource, Thread.currentThread().getContextClassLoader());
	}

	public ClasspathResourceLoader(String defaultResource,
								   ClassLoader classLoader) {
		this.defaultResource = defaultResource;
		this.classLoader = classLoader;
	}

	@Override
	public Reader loadDefaultResource() {
		return loadResource(defaultResource);
	}

	@Override
	public Reader loadResource(String relativePath) {
		InputStream is = this.classLoader.getResourceAsStream(relativePath);
		return is != null ? new InputStreamReader(is) : null;
	}

	@Override
	public String getName() {
		return "classpath resource";
	}

}

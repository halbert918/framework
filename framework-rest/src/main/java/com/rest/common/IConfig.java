package com.rest.common;

/**
 * Created by admin on 2017/9/22.
 */
public abstract class IConfig {

	public static final String DEFAULT_CONFIG = "config/rest.conf";

	public abstract void setProperty(String name, String value);

	public abstract String getProperty(String name);

	public abstract String getProperty(String name, String defaultValue);

	/**
	 * 设置默认值
	 */
	public void assignDefaults() {
		setProperty(RestConstant.REST_CONNECT_TIMEOUT_KEY,
				Integer.toString(RestConstant.DEFALUT_CONNECT_TIMEOUT_VALUE));
		setProperty(RestConstant.REST_SOCKET_READ_TIMEOUT_KEY,
				Integer.toString(RestConstant.DEFALUT_SOCKET_READ_TIMEOUT_VALUE));
		setProperty(RestConstant.REST_CLIENT_POOL_MAX_TOTAL_KEY,
				Integer.toString(RestConstant.DEFALUT_CLIENT_POOL_MAX_TOTAL_VALUE));
		setProperty(RestConstant.REST_CLIENT_MAX_PER_ROUTE_KEY,
				Integer.toString(RestConstant.CLIENT_MAX_PER_ROUTE_VALUE));
		setProperty(RestConstant.REST_CLIENT_DEFAULT_MAX_PER_ROUTE_KEY,
				Integer.toString(RestConstant.CLIENT_DEFALUT_MAX_PER_ROUTE_VALUE));
	}

	public abstract IResourceLoader getResourceLoader();

}

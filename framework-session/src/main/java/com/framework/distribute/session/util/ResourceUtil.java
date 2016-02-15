package com.framework.distribute.session.util;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.*;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date  2015/9/17
 * @Description
 */
public class ResourceUtil {

	private static ResourceBundle readProperties(String propertiesFile) {
		return ResourceBundle.getBundle(propertiesFile, LocaleContextHolder.getLocale());
	}

	/**
	 * 获取properties文件中key对应的value，没找到key时返回null
	 * @param key
	 * @return
	 */
	public static String getValue(String propertiesFile, String key) {
		ResourceBundle res = readProperties(propertiesFile);
		return res.getString(key);
	}

	/**
	 * 是否含有key数据
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String propertiesFile, String key) {
		ResourceBundle res = readProperties(propertiesFile);
		return res.containsKey(key);
	}

}

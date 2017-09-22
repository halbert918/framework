package com.rest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.util.Properties;

/**
 * Created by admin on 2017/9/22.
 */
public class ConfigurationParser {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Properties properties = new Properties();

	/**
	 * 解析conf文件
	 * @param file
	 * @throws ParseException
     */
	public void parse(File file) throws ParseException {
		if (file == null) {
			logger.info("解析配置文件对象file为空...");
			return;
		}
		if (!file.exists()) {
			logger.info("解析配置文件不存在,文件地址:{}", file.getAbsolutePath());
			return;
		}
		try {
			FileReader reader = new FileReader(file);
			parse(reader);
		} catch (FileNotFoundException e) {
			logger.error("解析配置文件失败:{}",e);
		}
	}

	/**
	 *
	 * @param reader
	 * @throws ParseException
     */
	public void parse(Reader reader) throws ParseException {
		if (reader == null) {
			logger.info("解析配置文件对象为空");
			return;
		}

		BufferedReader br = new BufferedReader(reader);
		String line;
		try {
			while ((line = br.readLine()) != null) {
				// # 为配置文件中的注释
				int commentMarker = line.indexOf('#');
				if (commentMarker != -1) {
					if (commentMarker == 0) {
						continue;
					} else {
						throw new ParseException(line, commentMarker);
					}
				} else {
					if (line.isEmpty() || line.matches("^\\s*$")) {
						continue;
					}
					//配置内容  timeout 1000
					int delimiterIdx = line.indexOf(' ');
					String key = line.substring(0, delimiterIdx).trim();
					String value = line.substring(delimiterIdx).trim();

					properties.put(key, value);
				}
			}
		} catch (IOException ex) {
			throw new ParseException("Failed to read", 1);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	Properties getProperties() {
		return properties;
	}
}

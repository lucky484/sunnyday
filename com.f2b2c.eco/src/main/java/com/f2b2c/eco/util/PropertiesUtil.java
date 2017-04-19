package com.f2b2c.eco.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取properties配置文件
 * 
 * @author jacob.shen
 *
 */
public class PropertiesUtil {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static final Properties properties;

	private static final String CONFIG_FILE = "param/file.properties";

	static {
		try {
			ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException ex) {
			throw new IllegalStateException("Could not load '[" + CONFIG_FILE + "]': " + ex.getMessage());
		}
	}

	public static String getValue(String key) {
		if (StringUtils.isEmpty(key)) {
			return "";
		}
		logger.debug("start loading property,name : [" + key + "]");
		return properties.getProperty(key);
	}
}

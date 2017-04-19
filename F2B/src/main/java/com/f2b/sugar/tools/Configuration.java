package com.f2b.sugar.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Author: 居泽平
 */
@SuppressWarnings("unused")
public class Configuration {

	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

	private static final Properties properties;

	private static final String CONFIG_FILE = "config.properties";

	static {
		try {
			ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException ex) {
			throw new IllegalStateException("Could not load '[" + CONFIG_FILE + "]': " + ex.getMessage());
		}
	}

	public static String getConfigurationByName(String keyName) {
		if (StringUtils.isEmpty(keyName)) {
			return "";
		}
		logger.debug("start loading property,name : [" + keyName + "]");
		return properties.getProperty(keyName);
	}

	public static String getConfigBaseUrl() {
		String baseUrl = Configuration.getConfigurationByName("BaseUrl");
		baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
		return baseUrl;
	}
}

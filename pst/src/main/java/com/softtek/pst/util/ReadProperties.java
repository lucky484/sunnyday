package com.softtek.pst.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class ReadProperties {

	private static InputStream input;
	private static Properties properties;

	public ReadProperties() {
		input = ReadProperties.class.getResourceAsStream("conf/config.properties");
		properties = new Properties();
	}

	public String readProperties(String name) {
		try {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty(name);
	}

}

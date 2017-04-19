package com.f2b2c.eco.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.f2b2c.eco.param.Config;

public class ConfigUtil {

	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	
	private static ConfigUtil configUtil;
	private static Properties properties;
	
	private ConfigUtil() throws IOException{
		
		if(properties == null){
			properties = new Properties();
			properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("param/config.properties"));
		}
	}
	
	public static ConfigUtil getInstance(){
		
		try {
			if(configUtil == null){
				configUtil = new ConfigUtil();
			}
			return configUtil;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public Config read(){
		
		Config config = new Config();
		config.setProducer(properties.getProperty("producer"));
		return config;
		
	}
	
	public Config write(Config config){
		
		FileOutputStream fileOutputStream = null;
		try{
			properties.setProperty("producer",config.getProducer());
			String path = ConfigUtil.class.getClassLoader().getResource("param/config.properties").getPath();
			fileOutputStream = new FileOutputStream(path);
			properties.store(fileOutputStream, null);
			return config;
		}catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			if(fileOutputStream!=null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return null;
	}
	
	
	
	
}

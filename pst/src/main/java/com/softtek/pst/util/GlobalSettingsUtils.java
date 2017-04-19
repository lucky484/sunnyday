package com.softtek.pst.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: GlobalSettingsUtils
 * @Description: TODO
 * @author kaishen
 * @date Nov 18, 2015 12:10:47 PM
 * 
 */

@Component
public class GlobalSettingsUtils {
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	@Value("${filePath}")
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}

package com.softtek.mdm.bean;

import java.util.List;
import com.softtek.mdm.model.BlackWhiteListUrl;

/**
 * 判断是否上网bean
 * @author jane.hui
 *
 */
public class IsNetworkAvailableBean {
	
	/**
	 * url列表
	 */
	private List<BlackWhiteListUrl> urlList;

	/**
	 * 是否启用网页黑名单(1.启用白名单 0.启用黑名单)
	 */
	private String enableBlacklist;
	
	/**
	 * 上网时间段
	 */
	private String visitTimeStr;

	public List<BlackWhiteListUrl> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<BlackWhiteListUrl> urlList) {
		this.urlList = urlList;
	}

	public String getEnableBlacklist() {
		return enableBlacklist;
	}

	public void setEnableBlacklist(String enableBlacklist) {
		this.enableBlacklist = enableBlacklist;
	}

	public String getVisitTimeStr() {
		return visitTimeStr;
	}

	public void setVisitTimeStr(String visitTimeStr) {
		this.visitTimeStr = visitTimeStr;
	}
}
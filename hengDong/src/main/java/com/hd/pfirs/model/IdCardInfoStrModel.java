package com.hd.pfirs.model;

public class IdCardInfoStrModel extends IdCardInfoModel {

	private String collectTimeStr;

	/**
	 * @return the collectTimeStr
	 */
	public String getCollectTimeStr() {
		collectTimeStr = getCollectTime().toString().substring(0, 19);
		return collectTimeStr;
	}

	/**
	 * @param collectTimeStr
	 *            the collectTimeStr to set
	 */
	public void setCollectTimeStr(String collectTimeStr) {
		this.collectTimeStr = collectTimeStr;
	}

}

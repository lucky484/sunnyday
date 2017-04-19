package com.hd.pfirs.model;

public class IdCardComPoliceModel {

	/**
	 * 布控id
	 */
	private String ctlId;
	/**
	 * 表
	 */
	private String tableName;

	/**
	 * 案件
	 */
	private String content;
	
	private String xm;
	private String xb;
	private String idCardNo;

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the ctlId
	 */
	public String getCtlId() {
		return ctlId;
	}

	/**
	 * @param ctlId
	 *            the ctlId to set
	 */
	public void setCtlId(String ctlId) {
		this.ctlId = ctlId;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

}

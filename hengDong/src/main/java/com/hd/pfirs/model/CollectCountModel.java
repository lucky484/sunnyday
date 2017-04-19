package com.hd.pfirs.model;

import java.util.Arrays;
import java.util.Date;

public class CollectCountModel {
	/**
	 * 编号
	 */
	private String cardCode;
	/**
	 * 采集时间
	 */
	private Date collectTime;
	/**
	 * 采集地点
	 */
	private String collectsite;
	/**
	 * 身份证图片
	 */
	private byte[] idCardPic;
	/**
	 * 姓名
	 */
	private String idCardName;
	/**
	 * 身份证号码
	 */
	private String idCardNo;
	/**
	 * 性别
	 */
	private String idCardSex;
	/**
	 * 民族
	 */
	private String idCardNation;
	/**
	 * 生日
	 */
	private String idCardBirth;
	/**
	 * 住址
	 */
	private String idCardAddress;
	/**
	 * 签发单位
	 */
	private String cardUnit;
	/**
	 * 发证日
	 */
	private String cardIssueDate;
	/**
	 * 截止日
	 */
	private String cardExpiryDate;
	/**
	 * 身份证照片base64
	 */
	private String idCardPicStr;
	/**
	 * 字符型时间yyyy-MM-dd HH:mm:ss
	 */
	private String collectTimeStr;
	/**
	 * 采集次数
	 */
	private int collectCount;
	
	/**
	 * 比对结果，>1为逃犯，0为正常
	 */
	private int idCardInfoCompareResult;	
	/**
	 * 采集开始时间
	 */
	private String startDate;	
	/**
	 * 采集结束时间
	 */
	private String endDate;
	/**
	 * 在逃案件类型(不用)
	 */
	private String ZaiTaoAJLX;
	/**
	 * 案件类型
	 */
	private String content;
	/**
	 * 库id
	 */
	private String compareBaseID;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 逃犯id
	 */
	private String ctrlBaseID;
	
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getCollectsite() {
		return collectsite;
	}
	public void setCollectsite(String collectsite) {
		this.collectsite = collectsite;
	}
	public byte[] getIdCardPic() {
		return idCardPic;
	}
	public void setIdCardPic(byte[] idCardPic) {
		this.idCardPic = idCardPic;
	}
	public String getIdCardName() {
		return idCardName;
	}
	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getIdCardSex() {
		return idCardSex;
	}
	public void setIdCardSex(String idCardSex) {
		this.idCardSex = idCardSex;
	}
	public String getCardUnit() {
		return cardUnit;
	}
	public void setCardUnit(String cardUnit) {
		this.cardUnit = cardUnit;
	}
	public String getCardIssueDate() {
		return cardIssueDate;
	}
	public void setCardIssueDate(String cardIssueDate) {
		this.cardIssueDate = cardIssueDate;
	}
	public String getCardExpiryDate() {
		return cardExpiryDate;
	}
	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}
	public String getIdCardPicStr() {
		return idCardPicStr;
	}
	public void setIdCardPicStr(String idCardPicStr) {
		this.idCardPicStr = idCardPicStr;
	}
	public String getCollectTimeStr() {
		return collectTimeStr;
	}
	public void setCollectTimeStr(String collectTimeStr) {
		this.collectTimeStr = collectTimeStr;
	}
	public int getCollectCount() {
		return collectCount;
	}
	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
	}
	public int getIdCardInfoCompareResult() {
		return idCardInfoCompareResult;
	}
	public void setIdCardInfoCompareResult(int idCardInfoCompareResult) {
		this.idCardInfoCompareResult = idCardInfoCompareResult;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getZaiTaoAJLX() {
		return ZaiTaoAJLX;
	}
	public void setZaiTaoAJLX(String ZaiTaoAJLX) {
		this.ZaiTaoAJLX = ZaiTaoAJLX;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	@Override
	public String toString() {
		return "CollectCountModel [cardCode=" + cardCode + ", collectTime=" + collectTime + ", collectsite="
				+ collectsite + ", idCardPic=" + Arrays.toString(idCardPic) + ", idCardName=" + idCardName
				+ ", idCardNo=" + idCardNo + ", idCardSex=" + idCardSex + ", cardUnit=" + cardUnit + ", cardIssueDate="
				+ cardIssueDate + ", cardExpiryDate=" + cardExpiryDate + ", idCardPicStr=" + idCardPicStr
				+ ", collectTimeStr=" + collectTimeStr + ", collectCount=" + collectCount + ", idCardInfoCompareResult="
				+ idCardInfoCompareResult + ", startDate=" + startDate + ", endDate=" + endDate + ", ZaiTaoAJLX="
				+ ZaiTaoAJLX + "]";
	}
	public String getIdCardNation() {
		return idCardNation;
	}
	public void setIdCardNation(String idCardNation) {
		this.idCardNation = idCardNation;
	}
	public String getIdCardBirth() {
		return idCardBirth;
	}
	public void setIdCardBirth(String idCardBirth) {
		this.idCardBirth = idCardBirth;
	}
	public String getIdCardAddress() {
		return idCardAddress;
	}
	public void setIdCardAddress(String idCardAddress) {
		this.idCardAddress = idCardAddress;
	}
	public String getCompareBaseID() {
		return compareBaseID;
	}
	public void setCompareBaseID(String compareBaseID) {
		this.compareBaseID = compareBaseID;
	}
	public String getCtrlBaseID() {
		return ctrlBaseID;
	}
	public void setCtrlBaseID(String ctrlBaseID) {
		this.ctrlBaseID = ctrlBaseID;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}

package com.hd.pfirs.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class IntegratedQueryMessageModel {
	/**
	 * 图片类型：1-全身 0-头部
	 */
	private String picType;
	/**
	 * 人像图片
	 */
	private byte[] collectFacePic;
	/**
	 * base64图片
	 */
	private String collectFacePicStr;
	/**
	 * 身份证图片
	 */
	private byte[] collectIdCardPic;
	/**
	 * base64图片
	 */
	private String collectIdCardPicStr;
	/**
	 * 是否在逃人员，0-否，1-在逃
	 */
	private int idCardInfoCompareResult;
	/**
	 * 在逃人员案件类型(不用)
	 */
	private String ZaiTaoAJLX;
	/**
	 * 在逃人员案件类型
	 */
	private String content;
	/**
	 * 在逃人员案件类型集合
	 */
	private List<String> ZaiTaoAJLXList;
	/**
	 * 人像编号
	 */
	private String faceCode;
	/**
	 * 身份证编号
	 */
	private String cardCode;
	/**
	 * 身份证号码
	 */
	private String idCardNo;
	/**
	 * 性别
	 */
	private String idCardSex;
	/**
	 * 身份证姓名
	 */
	private String idCardName;
	
	/**
	 * 身份证采集时间
	 */
	private Date idCardCollectTime;
	/**
	 * 身份证采集时间字符串
	 */
	private String idCardCollectTimeStr;
	/**
	 * 人像采集时间
	 */
	private Date faceCollectTime;
	/**
	 * 人像采集时间字符串
	 */
	private String faceCollectTimeStr;
	/**
	 * 人像采集地点
	 */
	private String faceCollectSite;
	/**
	 * 身份证采集地点
	 */
	private String idCardCollectSite;
	/**
	 * 相似度
	 */
	private float similarity;
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
	 * 逃犯id
	 */
	private String ctrlBaseId;
	
	/**
	 * 人证是否匹配标志1--匹配，0--不匹配
	 */
	private String faceAndCodeCompareFlag;
	/**
	 * T_QB_RY_ZTRYJBXX--在逃,T_QB_LK_LKBK -- 临控,changzhong -- 常空
	 */
	private String tableName;
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
	public byte[] getCollectFacePic() {
		return collectFacePic;
	}
	public void setCollectFacePic(byte[] collectFacePic) {
		this.collectFacePic = collectFacePic;
	}
	public String getCollectFacePicStr() {
		return collectFacePicStr;
	}
	public void setCollectFacePicStr(String collectFacePicStr) {
		this.collectFacePicStr = collectFacePicStr;
	}
	public byte[] getCollectIdCardPic() {
		return collectIdCardPic;
	}
	public void setCollectIdCardPic(byte[] collectIdCardPic) {
		this.collectIdCardPic = collectIdCardPic;
	}
	public String getCollectIdCardPicStr() {
		return collectIdCardPicStr;
	}
	public void setCollectIdCardPicStr(String collectIdCardPicStr) {
		this.collectIdCardPicStr = collectIdCardPicStr;
	}
	public int getIdCardInfoCompareResult() {
		return idCardInfoCompareResult;
	}
	public void setIdCardInfoCompareResult(int idCardInfoCompareResult) {
		this.idCardInfoCompareResult = idCardInfoCompareResult;
	}
	public String getZaiTaoAJLX() {
		return ZaiTaoAJLX;
	}
	public void setZaiTaoAJLX(String ZaiTaoAJLX) {
		this.ZaiTaoAJLX = ZaiTaoAJLX;
	}
	public String getFaceCode() {
		return faceCode;
	}
	public void setFaceCode(String faceCode) {
		this.faceCode = faceCode;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
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
	public String getIdCardName() {
		return idCardName;
	}
	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}
	public Date getIdCardCollectTime() {
		return idCardCollectTime;
	}
	public void setIdCardCollectTime(Date idCardCollectTime) {
		this.idCardCollectTime = idCardCollectTime;
	}
	public String getIdCardCollectTimeStr() {
		return idCardCollectTimeStr;
	}
	public void setIdCardCollectTimeStr(String idCardCollectTimeStr) {
		this.idCardCollectTimeStr = idCardCollectTimeStr;
	}
	public Date getFaceCollectTime() {
		return faceCollectTime;
	}
	public void setFaceCollectTime(Date faceCollectTime) {
		this.faceCollectTime = faceCollectTime;
	}
	public String getFaceCollectSite() {
		return faceCollectSite;
	}
	public void setFaceCollectSite(String faceCollectSite) {
		this.faceCollectSite = faceCollectSite;
	}
	public String getIdCardCollectSite() {
		return idCardCollectSite;
	}
	public void setIdCardCollectSite(String idCardCollectSite) {
		this.idCardCollectSite = idCardCollectSite;
	}
	public float getSimilarity() {
		return similarity;
	}
	public void setSimilarity(float similarity) {
		this.similarity = similarity;
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
	@Override
	public String toString() {
		return "IntegratedQueryMessageModel [picType=" + picType + ", collectFacePic=" + Arrays.toString(collectFacePic)
				+ ", collectFacePicStr=" + collectFacePicStr + ", collectIdCardPic=" + Arrays.toString(collectIdCardPic)
				+ ", collectIdCardPicStr=" + collectIdCardPicStr + ", idCardInfoCompareResult="
				+ idCardInfoCompareResult + ", ZaiTaoAJLX=" + ZaiTaoAJLX + ", faceCode=" + faceCode + ", cardCode="
				+ cardCode + ", idCardNo=" + idCardNo + ", idCardSex=" + idCardSex + ", idCardName=" + idCardName
				+ ", idCardCollectTime=" + idCardCollectTime + ", idCardCollectTimeStr=" + idCardCollectTimeStr
				+ ", faceCollectTime=" + faceCollectTime + ", faceCollectTimeStr=" + faceCollectTimeStr
				+ ", faceCollectSite=" + faceCollectSite + ", idCardCollectSite=" + idCardCollectSite + ", similarity="
				+ similarity + ", cardUnit=" + cardUnit + ", cardIssueDate=" + cardIssueDate + ", cardExpiryDate="
				+ cardExpiryDate + "]";
	}
	public String getFaceCollectTimeStr() {
		return faceCollectTimeStr;
	}
	public void setFaceCollectTimeStr(String faceCollectTimeStr) {
		this.faceCollectTimeStr = faceCollectTimeStr;
	}
	public String getCtrlBaseId() {
		return ctrlBaseId;
	}
	public void setCtrlBaseId(String ctrlBaseId) {
		this.ctrlBaseId = ctrlBaseId;
	}
	public List<String> getZaiTaoAJLXList() {
		return ZaiTaoAJLXList;
	}
	public void setZaiTaoAJLXList(List<String> ZaiTaoAJLXList) {
		this.ZaiTaoAJLXList = ZaiTaoAJLXList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFaceAndCodeCompareFlag() {
		return faceAndCodeCompareFlag;
	}
	public void setFaceAndCodeCompareFlag(String faceAndCodeCompareFlag) {
		this.faceAndCodeCompareFlag = faceAndCodeCompareFlag;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
}

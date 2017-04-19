package com.hd.pfirs.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import sun.misc.BASE64Encoder;

/**
 * 身份证信息查询
 * 
 * @author cliff.fan
 *
 */
public class InquiryIdCardInfoModel {
	/**
	 * 身份证ID
	 */
	private Long cardId;
	/**
	 * 采集时间
	 */
	private Date collectTime;
	/**
	 * 采集时间戳
	 */
	private String collectTimeStamp;
	/**
	 * 采集照片
	 */
	private byte[] collectPic;
	/**
	 * 采集地点
	 */
	private String collectSite;
	/**
	 * 身份证照片
	 */
	private byte[] idCardPic;
	/**
	 * 姓名
	 */
	private String idCardName;
	/**
	 * 性别
	 */
	private String idCardSex;
	/**
	 * 身份证号码
	 */
	private String idCardNo;
	/**
	 * 相似度
	 */
	private Float similarity;
	/**
	 * 是否布控人员/是否比中(0-否；1-是)
	 */
	private String isControlled;
	/**
	 * 对比库的ID
	 */
	private String compareBaseID;
	/**
	 *身份证采集数据编号
	 */
	private String cardCode;
	/**
	 * 字符型时间yyyy-MM-dd HH:mm:ss
	 */
	private String collectTimeStr;
	
	private String idCardBirth;
	
    private String collectPicStr;
    
    private String idCardPicStr;
    
    private byte[] photo;
    
    private String photoStr;
	/**
	 * @return the collectTime
	 */
	public Date getCollectTime() {
		return collectTime;
	}
	/**
	 * @return the cardId
	 */
	public Long getCardId() {
		return cardId;
	}
	/**
	 * @param cardId the cardId to set
	 */
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	/**
	 * @param collectTime the collectTime to set
	 */
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	/**
	 * @return the similarity
	 */
	public Float getSimilarity() {
		return similarity;
	}
	/**
	 * @param similarity the similarity to set
	 */
	public void setSimilarity(Float similarity) {
		this.similarity = similarity;
	}
	/**
	 * @return the collectPic
	 */
	public byte[] getCollectPic() {
		return collectPic;
	}
	/**
	 * @param collectPic the collectPic to set
	 */
	public void setCollectPic(byte[] collectPic) {
		this.collectPic = collectPic;
	}
	/**
	 * @return the collectSite
	 */
	public String getCollectSite() {
		return collectSite;
	}
	/**
	 * @param collectSite the collectSite to set
	 */
	public void setCollectSite(String collectSite) {
		this.collectSite = collectSite;
	}
	/**
	 * @return the idCardPic
	 */
	public byte[] getIdCardPic() {
		return idCardPic;
	}
	/**
	 * @param idCardPic the idCardPic to set
	 */
	public void setIdCardPic(byte[] idCardPic) {
		this.idCardPic = idCardPic;
	}
	/**
	 * @return the idCardName
	 */
	public String getIdCardName() {
		return idCardName;
	}
	/**
	 * @param idCardName the idCardName to set
	 */
	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}
	/**
	 * @return the idCardSex
	 */
	public String getIdCardSex() {
		return idCardSex;
	}
	/**
	 * @param idCardSex the idCardSex to set
	 */
	public void setIdCardSex(String idCardSex) {
		this.idCardSex = idCardSex;
	}
	/**
	 * @return the idCardNo
	 */
	public String getIdCardNo() {
		return idCardNo;
	}
	/**
	 * @param idCardNo the idCardNo to set
	 */
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}


	/**
	 * @return the isControlled
	 */
	public String getIsControlled() {
		return isControlled;
	}
	/**
	 * @param isControlled the isControlled to set
	 */
	public void setIsControlled(String isControlled) {
		this.isControlled = isControlled;
	}
	/**
	 * @return the compareBaseID
	 */
	public String getCompareBaseID() {
		return compareBaseID;
	}
	/**
	 * @param compareBaseID the compareBaseID to set
	 */
	public void setCompareBaseID(String compareBaseID) {
		this.compareBaseID = compareBaseID;
	}
	/**
	 * @return the collectTimeStr
	 */
	public String getCollectTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String collectTimeStr = sdf.format(getCollectTime());
		return collectTimeStr;
	}
	/**
	 * @param collectTimeStr the collectTimeStr to set
	 */
	public void setCollectTimeStr(String collectTimeStr) {
		this.collectTimeStr = collectTimeStr;
	}

	/**
	 * @return the collectTimeStamp
	 */
	public String getCollectTimeStamp() {
		return collectTimeStamp;
	}
	/**
	 * @param collectTimeStamp the collectTimeStamp to set
	 */
	public void setCollectTimeStamp(String collectTimeStamp) {
		this.collectTimeStamp = collectTimeStamp;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getIdCardBirth() {
		return idCardBirth;
	}
	public void setIdCardBirth(String idCardBirth) {
		this.idCardBirth = idCardBirth;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getCollectPicStr() {
		if(getCollectPic() != null){
			return new BASE64Encoder().encode(getCollectPic());
		}
		return "";
	}
	public void setCollectPicStr(String collectPicStr) {
		this.collectPicStr = collectPicStr;
	}
	public String getIdCardPicStr() {
		if(getIdCardPic() != null){
			return new BASE64Encoder().encode(getIdCardPic());
		}
		return "";
	}
	public void setIdCardPicStr(String idCardPicStr) {
		this.idCardPicStr = idCardPicStr;
	}
	public String getPhotoStr() {
		if(getPhoto() != null){
			return new BASE64Encoder().encode(getPhoto());
		}
		return "";
	}
	public void setPhotoStr(String photoStr) {
		this.photoStr = photoStr;
	}
}

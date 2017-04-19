package com.hd.pfirs.model;

/**
 * @author ligang.yang
 *
 */
public class FCResultModel {
	/**
	 * 人脸code
	 */
	private String faceCode;
	/**
	 * 相似度
	 */
	private Float similarity;
	/**
	 * 备注
	 */
	private String remarkDesc;
	/**
	 * 身份证id
	 */
	private String idCardNo;
	/**
	 * 身份证采集时间字符串
	 */
	private String cardDateStr;

	/**
	 * 身份证图片
	 */
	private byte[] idCardPic;

	/**
	 * base64的人脸图片
	 */
	private String Base64IdCardPic;
	/**
	 * 格式化的采集时间
	 */
	private String fCardDate;

	/**
	 * 身份证姓名
	 */
	private String idCardName;

	/**
	 * 身份证性别
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
	 * 人脸采集时间字符串
	 */
	private String faceDateStr;

	/**
	 * 格式化的采集时间
	 */
	private String fFaceDate;

	/**
	 * 人脸图片
	 */
	private byte[] facePic;

	/**
	 * base64的人脸图片
	 */
	private String Base64FacePic;

	/**
	 * 临时的时间戳
	 */
	private String FCtimeStamp;

	/**
	 * 接收时间
	 */
	private String receiveTimeStr;
	
	/**
	 * 设备号
	 */
	private String deviceCode;
	/**
	 * @return the faceCode
	 */
	public String getFaceCode() {
		return faceCode;
	}

	/**
	 * @param faceCode
	 *            the faceCode to set
	 */
	public void setFaceCode(String faceCode) {
		this.faceCode = faceCode;
	}

	/**
	 * @return the similarity
	 */
	public Float getSimilarity() {
		return similarity;
	}

	/**
	 * @param similarity
	 *            the similarity to set
	 */
	public void setSimilarity(Float similarity) {
		this.similarity = similarity;
	}

	/**
	 * @return the remarkDesc
	 */
	public String getRemarkDesc() {
		return remarkDesc;
	}

	/**
	 * @param remarkDesc
	 *            the remarkDesc to set
	 */
	public void setRemarkDesc(String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}

	/**
	 * @return the idCardNo
	 */
	public String getIdCardNo() {
		return idCardNo;
	}

	/**
	 * @param idCardNo
	 *            the idCardNo to set
	 */
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	/**
	 * @return the cardDateStr
	 */
	public String getCardDateStr() {
		return cardDateStr;
	}

	/**
	 * @param cardDateStr
	 *            the cardDateStr to set
	 */
	public void setCardDateStr(String cardDateStr) {
		this.cardDateStr = cardDateStr;
	}

	/**
	 * @return the idCardPic
	 */
	public byte[] getIdCardPic() {
		return idCardPic;
	}

	/**
	 * @param idCardPic
	 *            the idCardPic to set
	 */
	public void setIdCardPic(byte[] idCardPic) {
		this.idCardPic = idCardPic;
	}

	/**
	 * @return the base64IdCardPic
	 */
	public String getBase64IdCardPic() {
		return Base64IdCardPic;
	}

	/**
	 * @param base64IdCardPic
	 *            the base64IdCardPic to set
	 */
	public void setBase64IdCardPic(String base64IdCardPic) {
		Base64IdCardPic = base64IdCardPic;
	}

	/**
	 * @return the fCardDate
	 */
	public String getfCardDate() {
		return fCardDate;
	}

	/**
	 * @param fCardDate
	 *            the fCardDate to set
	 */
	public void setfCardDate(String fCardDate) {
		this.fCardDate = fCardDate;
	}

	/**
	 * @return the idCardName
	 */
	public String getIdCardName() {
		return idCardName;
	}

	/**
	 * @param idCardName
	 *            the idCardName to set
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
	 * @param idCardSex
	 *            the idCardSex to set
	 */
	public void setIdCardSex(String idCardSex) {
		this.idCardSex = idCardSex;
	}

	/**
	 * @return the idCardNation
	 */
	public String getIdCardNation() {
		return idCardNation;
	}

	/**
	 * @param idCardNation
	 *            the idCardNation to set
	 */
	public void setIdCardNation(String idCardNation) {
		this.idCardNation = idCardNation;
	}

	/**
	 * @return the idCardBirth
	 */
	public String getIdCardBirth() {
		return idCardBirth;
	}

	/**
	 * @param idCardBirth
	 *            the idCardBirth to set
	 */
	public void setIdCardBirth(String idCardBirth) {
		this.idCardBirth = idCardBirth;
	}

	/**
	 * @return the faceDateStr
	 */
	public String getFaceDateStr() {
		return faceDateStr;
	}

	/**
	 * @param faceDateStr
	 *            the faceDateStr to set
	 */
	public void setFaceDateStr(String faceDateStr) {
		this.faceDateStr = faceDateStr;
	}

	/**
	 * @return the fFaceDate
	 */
	public String getfFaceDate() {
		return fFaceDate;
	}

	/**
	 * @param fFaceDate
	 *            the fFaceDate to set
	 */
	public void setfFaceDate(String fFaceDate) {
		this.fFaceDate = fFaceDate;
	}

	/**
	 * @return the facePic
	 */
	public byte[] getFacePic() {
		return facePic;
	}

	/**
	 * @param facePic
	 *            the facePic to set
	 */
	public void setFacePic(byte[] facePic) {
		this.facePic = facePic;
	}

	/**
	 * @return the base64FacePic
	 */
	public String getBase64FacePic() {
		return Base64FacePic;
	}

	/**
	 * @param base64FacePic
	 *            the base64FacePic to set
	 */
	public void setBase64FacePic(String base64FacePic) {
		Base64FacePic = base64FacePic;
	}

	/**
	 * @return the fCtimeStamp
	 */
	public String getFCtimeStamp() {
		return FCtimeStamp;
	}

	/**
	 * @param fCtimeStamp
	 *            the fCtimeStamp to set
	 */
	public void setFCtimeStamp(String fCtimeStamp) {
		FCtimeStamp = fCtimeStamp;
	}

	public String getReceiveTimeStr() {
		return receiveTimeStr;
	}

	public void setReceiveTimeStr(String receiveTimeStr) {
		this.receiveTimeStr = receiveTimeStr;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

}

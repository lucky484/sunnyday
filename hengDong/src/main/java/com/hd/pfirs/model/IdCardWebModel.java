package com.hd.pfirs.model;

import java.util.Arrays;
import java.util.Date;

/**
 * 
 * @author curry.su
 *
 */
//a.collecttime,a.collectsite,a.IDCardPic,a.IDCardName,a.IDCardNo,a.IDCardSex,a.IDCardNation,b.IsControlled
public class IdCardWebModel {
	private long cardID;
	private String cardCode;
	private Date collectTime;
	private String collectsite;
	private byte[] idCardPic;
	private String idCardName;
	private String idCardNo;
	private String idCardSex;
	private String idCardNation;
	private String isControlled;
	private String idCardPicStr;
	private String collectTimeStr;

	
	public long getCardID() {
		return cardID;
	}
	public void setCardID(long cardID) {
		this.cardID = cardID;
	}
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
	public String getIdCardNation() {
		return idCardNation;
	}
	public void setIdCardNation(String idCardNation) {
		this.idCardNation = idCardNation;
	}
	public String getIsControlled() {
		return isControlled;
	}
	public void setIsControlled(String isControlled) {
		this.isControlled = isControlled;
	}
	
	public String getIdCardPicStr() {
		return idCardPicStr;
	}
	public void setIdCardPicStr(String idCardPicStr) {
		this.idCardPicStr = idCardPicStr;
	}
	@Override
	public String toString() {
		return "IdCardWebModel [cardID=" + cardID + ", collectTime=" + collectTime + ", collectsite=" + collectsite
				+ ", idCardPic=" + Arrays.toString(idCardPic) + ", idCardName=" + idCardName + ", idCardNo=" + idCardNo
				+ ", idCardSex=" + idCardSex + ", idCardNation=" + idCardNation + ", isControlled=" + isControlled
				+ ", idCardPicStr=" + idCardPicStr + "]";
	}
	public String getCollectTimeStr() {
		return collectTimeStr;
	}
	public void setCollectTimeStr(String collectTimeStr) {
		this.collectTimeStr = collectTimeStr;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	
	
	
	
}

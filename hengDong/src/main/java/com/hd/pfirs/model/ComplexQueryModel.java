package com.hd.pfirs.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import sun.misc.BASE64Encoder;

public class ComplexQueryModel {
     
	 private Long faceId;
	 private Date collectTime;
	 private String collectSite;
	 private byte[] collectPic;
	 private Long cardId;
	 private byte[] idCardPic;
	 private String idCardName;
	 private String idCardSex;
	 private String idCardNo;
	 private String isControlled;
	 private Float similarity;
	 private String remarkDesc;
	 private String compareBaseID;
	 private String ctrlBaseID;
	 private byte[] photo;
	 private String collectTimeStr;
	 private Float faceSimilarity;
	 private String bbkrxm;
	 private String bbkrxb;
	 private String T_QB_LK_LKBKxb;
	 private String bbkrzjhm;
	 private String lkzllx;
	 private String T_QB_RY_CKRYJBXXxm;
	 private String T_QB_RY_CKRYJBXXxb;
	 private String T_QB_RY_CKRYJBXXxbStr;
	 private String T_QB_RY_CKRYJBXXsfzh;
	 private String zdrylbbj;
	 private String T_QB_RY_CKRYJBXXContext;
	 private String xm;
	 private String xb;
	 private String T_QB_RY_ZTRYJBXXxb;
	 private String ysfzh;
	 private String context;
	 private String idCardNation;
	 private String idCardBirth;
	 private String idCardAddress;
	 private String cardUnit;
	 private String cardIssueDate;
	 private String cardExpiryDate;
	 private String cardCompareBaseId;
	 private String photoStr;
	 private String collectPicStr;
	 private String idCardPicStr;
	 private String tableName;
	public Long getFaceId() {
		return faceId;
	}
	public void setFaceId(Long faceId) {
		this.faceId = faceId;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getCollectSite() {
		return collectSite;
	}
	public void setCollectSite(String collectSite) {
		this.collectSite = collectSite;
	}
	public byte[] getCollectPic() {
		return collectPic;
	}
	public void setCollectPic(byte[] collectPic) {
		this.collectPic = collectPic;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
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
	public String getIdCardSex() {
		return idCardSex;
	}
	public void setIdCardSex(String idCardSex) {
		this.idCardSex = idCardSex;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getIsControlled() {
		return isControlled;
	}
	public void setIsControlled(String isControlled) {
		this.isControlled = isControlled;
	}
	public Float getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Float similarity) {
		this.similarity = similarity;
	}
	public String getRemarkDesc() {
		return remarkDesc;
	}
	public void setRemarkDesc(String remarkDesc) {
		this.remarkDesc = remarkDesc;
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
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getCollectTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String collectTimeStr = sdf.format(getCollectTime());
		return collectTimeStr;
	}
	public void setCollectTimeStr(String collectTimeStr) {
		this.collectTimeStr = collectTimeStr;
	}
	public Float getFaceSimilarity() {
		return faceSimilarity;
	}
	public void setFaceSimilarity(Float faceSimilarity) {
		this.faceSimilarity = faceSimilarity;
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
	public String getYsfzh() {
		return ysfzh;
	}
	public void setYsfzh(String ysfzh) {
		this.ysfzh = ysfzh;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
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
	public String getPhotoStr() {
		if(getPhoto() != null){
			return new BASE64Encoder().encode(getPhoto());
		}
		return "";
	}
	public void setPhotoStr(String photoStr) {
		this.photoStr = photoStr;
	}
	public String getCollectPicStr() {
		if(getCollectPic() != null){
			String collectPicStr = new BASE64Encoder().encode(getCollectPic());
			return collectPicStr;
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
	public String getBbkrxm() {
		return bbkrxm;
	}
	public void setBbkrxm(String bbkrxm) {
		this.bbkrxm = bbkrxm;
	}
	public String getBbkrxb() {
		return bbkrxb;
	}
	public void setBbkrxb(String bbkrxb) {
		this.bbkrxb = bbkrxb;
	}
	public String getBbkrzjhm() {
		return bbkrzjhm;
	}
	public void setBbkrzjhm(String bbkrzjhm) {
		this.bbkrzjhm = bbkrzjhm;
	}
	public String getLkzllx() {
		return lkzllx;
	}
	public void setLkzllx(String lkzllx) {
		this.lkzllx = lkzllx;
	}
	public String getT_QB_RY_CKRYJBXXxm() {
		return T_QB_RY_CKRYJBXXxm;
	}
	public void setT_QB_RY_CKRYJBXXxm(String T_QB_RY_CKRYJBXXxm) {
		this.T_QB_RY_CKRYJBXXxm = T_QB_RY_CKRYJBXXxm;
	}
	public String getT_QB_RY_CKRYJBXXxb() {
		return T_QB_RY_CKRYJBXXxb;
	}
	public void setT_QB_RY_CKRYJBXXxb(String T_QB_RY_CKRYJBXXxb) {
		this.T_QB_RY_CKRYJBXXxb = T_QB_RY_CKRYJBXXxb;
	}
	public String getT_QB_RY_CKRYJBXXsfzh() {
		return T_QB_RY_CKRYJBXXsfzh;
	}
	public void setT_QB_RY_CKRYJBXXsfzh(String T_QB_RY_CKRYJBXXsfzh) {
		this.T_QB_RY_CKRYJBXXsfzh = T_QB_RY_CKRYJBXXsfzh;
	}
	public String getZdrylbbj() {
		return zdrylbbj;
	}
	public void setZdrylbbj(String zdrylbbj) {
		this.zdrylbbj = zdrylbbj;
	}
	public String getCardCompareBaseId() {
		return cardCompareBaseId;
	}
	public void setCardCompareBaseId(String cardCompareBaseId) {
		this.cardCompareBaseId = cardCompareBaseId;
	}
	
	public String getT_QB_RY_CKRYJBXXContext() {
		String T_QB_RY_CKRYJBXXContext = "";
		if(getZdrylbbj() != null && getZdrylbbj() != ""){
			int index = getZdrylbbj().indexOf("1");
			switch(index){
			case 0:T_QB_RY_CKRYJBXXContext="涉恐人员";break;
			case 1:T_QB_RY_CKRYJBXXContext="涉稳人员";break;
			case 2:T_QB_RY_CKRYJBXXContext="在逃人员";break;
			case 3:T_QB_RY_CKRYJBXXContext="涉毒人员";break;
			case 4:T_QB_RY_CKRYJBXXContext="刑事犯罪";break;
			case 5:T_QB_RY_CKRYJBXXContext="肇事肇案精神病人";break;
			case 6:T_QB_RY_CKRYJBXXContext="重点上访人员";break;
			default:T_QB_RY_CKRYJBXXContext = "";
			}
		}
		return T_QB_RY_CKRYJBXXContext;
	}
	public void setT_QB_RY_CKRYJBXXContext(String T_QB_RY_CKRYJBXXContext) {
		this.T_QB_RY_CKRYJBXXContext = T_QB_RY_CKRYJBXXContext;
	}
	public String getT_QB_LK_LKBKxb() {
		if(getBbkrxb() != null){
			if(getBbkrxb().equals("1")){
				return "男";
			}else{
				return "女";
			}
		}
		return T_QB_LK_LKBKxb;
	}
	public void setT_QB_LK_LKBKxb(String T_QB_LK_LKBKxb) {
		this.T_QB_LK_LKBKxb = T_QB_LK_LKBKxb;
	}
	public String getT_QB_RY_CKRYJBXXxbStr() {
		if(getT_QB_RY_CKRYJBXXxb() != null){
			if(getT_QB_RY_CKRYJBXXxb().equals("1")){
				return "男";
			}else{
				return "女";
			}
		}
		return T_QB_RY_CKRYJBXXxbStr;
	}
	public void setT_QB_RY_CKRYJBXXxbStr(String T_QB_RY_CKRYJBXXxbStr) {
		this.T_QB_RY_CKRYJBXXxbStr = T_QB_RY_CKRYJBXXxbStr;
	}
	public String getT_QB_RY_ZTRYJBXXxb() {
		if(getXb() != null){
			if(getXb().equals("1")){
				return "男";
			}else{
				return "女";
			}
		}
		return T_QB_RY_ZTRYJBXXxb;
	}
	public void setT_QB_RY_ZTRYJBXXxb(String T_QB_RY_ZTRYJBXXxb) {
		this.T_QB_RY_ZTRYJBXXxb = T_QB_RY_ZTRYJBXXxb;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}

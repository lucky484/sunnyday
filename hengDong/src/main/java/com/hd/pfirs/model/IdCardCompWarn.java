package com.hd.pfirs.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import sun.misc.BASE64Encoder;


public class IdCardCompWarn {
	private String cardComprltid;
	private String cardCode;
	private String ctrlBaseID;
    private String collectIdCardNo;
    private String compareBaseId;
    private byte[] photo;
    private String photoStr;
    private byte[] collectPic;
    private String collectPicStr;
    private byte[] idcardpic;
    private String idcardpicStr;
    private float similarity;
    private Date collectTime;
    private String collectTimeStr;
    private String xm;
    private String xb;
    private String ysfzh;
    private String context;
    private String bbkrxm;
    private String bbkrxb;
    private String bbkrzjhm;
    private String lkzllx;
    private String T_QB_RY_CKRYJBXXxm;
    private String T_QB_RY_CKRYJBXXxb;
    private String T_QB_RY_CKRYJBXXsfzh;
    private String zdrylbbj;
	private String T_QB_RY_CKRYJBXXContext;
	private String idCardNo;
	private String ajlx;
	private String tableName;
	private String idCollectTimeStr;
	private String receiveTimeStr;
	private String deviceCode;
	public String getCardComprltid() {
		return cardComprltid;
	}
	public void setCardComprltid(String cardComprltid) {
		this.cardComprltid = cardComprltid;
	}
	public String getCollectIdCardNo() {
		return collectIdCardNo;
	}
	public void setCollectIdCardNo(String collectIdCardNo) {
		this.collectIdCardNo = collectIdCardNo;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	/**
	 * @return the ctrlBaseID
	 */
	public String getCtrlBaseID() {
		return ctrlBaseID;
	}
	/**
	 * @param ctrlBaseID the ctrlBaseID to set
	 */
	public void setCtrlBaseID(String ctrlBaseID) {
		this.ctrlBaseID = ctrlBaseID;
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
	 * @return the collectPicStr
	 */
	public String getCollectPicStr() {
		if(getCollectPic() != null){
			String collectPicStr = new BASE64Encoder().encode(getCollectPic());
			return collectPicStr;
		}
		return "";
	}
	/**
	 * @param collectPicStr the collectPicStr to set
	 */
	public void setCollectPicStr(String collectPicStr) {
		this.collectPicStr = collectPicStr;
	}
	/**
	 * @return the collectTime
	 */
	public Date getCollectTime() {
		return collectTime;
	}
	/**
	 * @param collectTime the collectTime to set
	 */
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	/**
	 * @return the collectTimeStr
	 */
	public String getCollectTimeStr() {
		if(getCollectTime() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String collectTimeStr = sdf.format(getCollectTime());
			return collectTimeStr;
		}
		return collectTimeStr;
	}
	/**
	 * @param collectTimeStr the collectTimeStr to set
	 */
	public void setCollectTimeStr(String collectTimeStr) {
		this.collectTimeStr = collectTimeStr;
	}
	/**
	 * @return the xm
	 */
	public String getXm() {
		return xm;
	}
	/**
	 * @param xm the xm to set
	 */
	public void setXm(String xm) {
		this.xm = xm;
	}
	/**
	 * @return the xb
	 */
	public String getXb() {
		return xb;
	}
	/**
	 * @param xb the xb to set
	 */
	public void setXb(String xb) {
		this.xb = xb;
	}
	/**
	 * @return the ysfzh
	 */
	public String getYsfzh() {
		return ysfzh;
	}
	/**
	 * @param ysfzh the ysfzh to set
	 */
	public void setYsfzh(String ysfzh) {
		this.ysfzh = ysfzh;
	}
	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	/**
	 * @return the bbkrxm
	 */
	public String getBbkrxm() {
		return bbkrxm;
	}
	/**
	 * @param bbkrxm the bbkrxm to set
	 */
	public void setBbkrxm(String bbkrxm) {
		this.bbkrxm = bbkrxm;
	}
	/**
	 * @return the bbkrxb
	 */
	public String getBbkrxb() {
		return bbkrxb;
	}
	/**
	 * @param bbkrxb the bbkrxb to set
	 */
	public void setBbkrxb(String bbkrxb) {
		this.bbkrxb = bbkrxb;
	}
	/**
	 * @return the bbkrzjhm
	 */
	public String getBbkrzjhm() {
		return bbkrzjhm;
	}
	/**
	 * @param bbkrzjhm the bbkrzjhm to set
	 */
	public void setBbkrzjhm(String bbkrzjhm) {
		this.bbkrzjhm = bbkrzjhm;
	}
	/**
	 * @return the lkzllx
	 */
	public String getLkzllx() {
		return lkzllx;
	}
	/**
	 * @param lkzllx the lkzllx to set
	 */
	public void setLkzllx(String lkzllx) {
		this.lkzllx = lkzllx;
	}
	/**
	 * @return the T_QB_RY_CKRYJBXXxm
	 */
	public String getT_QB_RY_CKRYJBXXxm() {
		return T_QB_RY_CKRYJBXXxm;
	}
	/**
	 * @param T_QB_RY_CKRYJBXXxm the T_QB_RY_CKRYJBXXxm to set
	 */
	public void setT_QB_RY_CKRYJBXXxm(String T_QB_RY_CKRYJBXXxm) {
		this.T_QB_RY_CKRYJBXXxm = T_QB_RY_CKRYJBXXxm;
	}
	/**
	 * @return the T_QB_RY_CKRYJBXXxb
	 */
	public String getT_QB_RY_CKRYJBXXxb() {
		return T_QB_RY_CKRYJBXXxb;
	}
	/**
	 * @param T_QB_RY_CKRYJBXXxb the T_QB_RY_CKRYJBXXxb to set
	 */
	public void setT_QB_RY_CKRYJBXXxb(String T_QB_RY_CKRYJBXXxb) {
		this.T_QB_RY_CKRYJBXXxb = T_QB_RY_CKRYJBXXxb;
	}
	/**
	 * @return the T_QB_RY_CKRYJBXXsfzh
	 */
	public String getT_QB_RY_CKRYJBXXsfzh() {
		return T_QB_RY_CKRYJBXXsfzh;
	}
	/**
	 * @param T_QB_RY_CKRYJBXXsfzh the T_QB_RY_CKRYJBXXsfzh to set
	 */
	public void setT_QB_RY_CKRYJBXXsfzh(String T_QB_RY_CKRYJBXXsfzh) {
		this.T_QB_RY_CKRYJBXXsfzh = T_QB_RY_CKRYJBXXsfzh;
	}
	/**
	 * @return the zdrylbbj
	 */
	public String getZdrylbbj() {
		return zdrylbbj;
	}
	/**
	 * @param zdrylbbj the zdrylbbj to set
	 */
	public void setZdrylbbj(String zdrylbbj) {
		this.zdrylbbj = zdrylbbj;
	}
	/**
	 * @return the photoStr
	 */
	public String getPhotoStr() {
		if(getPhoto() != null){
			return new BASE64Encoder().encode(getPhoto());
		}
		return "";
	}
	/**
	 * @param photoStr the photoStr to set
	 */
	public void setPhotoStr(String photoStr) {
		this.photoStr = photoStr;
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
	/**
	 * @return the compareBaseId
	 */
	public String getCompareBaseId() {
		return compareBaseId;
	}
	/**
	 * @param compareBaseId the compareBaseId to set
	 */
	public void setCompareBaseId(String compareBaseId) {
		this.compareBaseId = compareBaseId;
	}
	/**
	 * @param T_QB_RY_CKRYJBXXContext the T_QB_RY_CKRYJBXXContext to set
	 */
	public void setT_QB_RY_CKRYJBXXContext(String T_QB_RY_CKRYJBXXContext) {
		this.T_QB_RY_CKRYJBXXContext = T_QB_RY_CKRYJBXXContext;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public float getSimilarity() {
		return similarity;
	}
	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getAjlx() {
		return ajlx;
	}
	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getIdCollectTimeStr() {
		return idCollectTimeStr;
	}
	public void setIdCollectTimeStr(String idCollectTimeStr) {
		this.idCollectTimeStr = idCollectTimeStr;
	}
	public byte[] getIdcardpic() {
		return idcardpic;
	}
	public void setIdcardpic(byte[] idcardpic) {
		this.idcardpic = idcardpic;
	}
	public String getIdcardpicStr() {
		if(getIdcardpic() != null){
			String idcardpicStr = new BASE64Encoder().encode(getIdcardpic());
			return idcardpicStr;
		}
		return "";
	}
	public void setIdcardpicStr(String idcardpicStr) {
		this.idcardpicStr = idcardpicStr;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getReceiveTimeStr() {
		return receiveTimeStr;
	}
	public void setReceiveTimeStr(String receiveTimeStr) {
		this.receiveTimeStr = receiveTimeStr;
	}
	
	
}

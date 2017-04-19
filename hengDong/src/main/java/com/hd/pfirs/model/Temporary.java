/**
 * 
 */
package com.hd.pfirs.model;

/**
 * @ClassName: Temporary
 * @Description: 临控人员
 * @author light.chen
 * @date Dec 24, 2015 2:52:48 PM
 */
public class Temporary {
	/**
	 * 记录唯一标识
	 */
	private String id;
	/**
	 * 联动信息编号
	 */
	private String ldxxbh;
	/**
	 * 临控指令编号
	 */
	private String lkzlbh;
	/**
	 * 临控指令类型
	 */
	private String lkzllx;
	/**
	 * 临控指令级别
	 */
	private String lkzljb;
	/**
	 * 布控天数
	 */
	private long bkts;
	/**
	 * 布控起始时间
	 */
	private String bkqssj;
	/**
	 * 布控截至时间
	 */
	private String bkjzsj;
	/**
	 * 被布控人证件类型
	 */
	private String bbkrzjlx;
	/**
	 * 被布控人证件号码
	 */
	private String bbkrzjhm;
	/**
	 * 被布控人姓名
	 */
	private String bbkrxm;
	/**
	 * 被布控人性别
	 */
	private String bbkrxb;
	/**
	 * 被布控人出生日期
	 */
	private String bbkrcsrq;
	
	private byte[] photo;
	
	private String photoStr;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the ldxxbh
	 */
	public String getLdxxbh() {
		return ldxxbh;
	}

	/**
	 * @param ldxxbh the ldxxbh to set
	 */
	public void setLdxxbh(String ldxxbh) {
		this.ldxxbh = ldxxbh;
	}

	/**
	 * @return the lkzlbh
	 */
	public String getLkzlbh() {
		return lkzlbh;
	}

	/**
	 * @param lkzlbh the lkzlbh to set
	 */
	public void setLkzlbh(String lkzlbh) {
		this.lkzlbh = lkzlbh;
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
	 * @return the lkzljb
	 */
	public String getLkzljb() {
		return lkzljb;
	}

	/**
	 * @param lkzljb the lkzljb to set
	 */
	public void setLkzljb(String lkzljb) {
		this.lkzljb = lkzljb;
	}

	/**
	 * @return the bkts
	 */
	public long getBkts() {
		return bkts;
	}

	/**
	 * @param bkts the bkts to set
	 */
	public void setBkts(long bkts) {
		this.bkts = bkts;
	}

	/**
	 * @return the bkqssj
	 */
	public String getBkqssj() {
		return bkqssj;
	}

	/**
	 * @param bkqssj the bkqssj to set
	 */
	public void setBkqssj(String bkqssj) {
		this.bkqssj = bkqssj;
	}

	/**
	 * @return the bkjzsj
	 */
	public String getBkjzsj() {
		return bkjzsj;
	}

	/**
	 * @param bkjzsj the bkjzsj to set
	 */
	public void setBkjzsj(String bkjzsj) {
		this.bkjzsj = bkjzsj;
	}

	/**
	 * @return the bbkrzjlx
	 */
	public String getBbkrzjlx() {
		return bbkrzjlx;
	}

	/**
	 * @param bbkrzjlx the bbkrzjlx to set
	 */
	public void setBbkrzjlx(String bbkrzjlx) {
		this.bbkrzjlx = bbkrzjlx;
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
	 * @return the bbkrcsrq
	 */
	public String getBbkrcsrq() {
		return bbkrcsrq;
	}

	/**
	 * @param bbkrcsrq the bbkrcsrq to set
	 */
	public void setBbkrcsrq(String bbkrcsrq) {
		this.bbkrcsrq = bbkrcsrq;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPhotoStr() {
		return photoStr;
	}

	public void setPhotoStr(String photoStr) {
		this.photoStr = photoStr;
	}

}

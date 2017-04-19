/**
 * 
 */
package com.hd.pfirs.model;

/**
 * @ClassName: Constant
 * @Description: 常控人员
 * @author light.chen
 * @date Dec 25, 2015 10:02:03 AM
 */
public class Constant {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 姓名
	 */
	private String xm;
	/**
	 * 性别
	 */
	private String xb;
	/**
	 * 出生日期
	 */
	private String csrq;
	/**
	 * 身份证号
	 */
	private String sfzh;
	/**
	 * 重点人员类别标记
	 */
	private String zdrylbbj;
	
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
	 * @return the csrq
	 */
	public String getCsrq() {
		return csrq;
	}

	/**
	 * @param csrq the csrq to set
	 */
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	/**
	 * @return the sfzh
	 */
	public String getSfzh() {
		return sfzh;
	}

	/**
	 * @param sfzh the sfzh to set
	 */
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
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

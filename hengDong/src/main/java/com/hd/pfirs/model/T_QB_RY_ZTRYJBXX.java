/**
 * 
 */
package com.hd.pfirs.model;

/**
 * @ClassName: T_QB_RY_ZTRYJBXX
 * @Description: 在逃人员
 * @author light.chen
 * @date Dec 19, 2015 3:56:37 PM
 */
public class T_QB_RY_ZTRYJBXX {
	/**
	 * ID
	 */
	private String id;
	/**
	 * 人员编号
	 */
	private String rybh;
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
	private byte csrq;
	/**
	 * 原身份证号
	 */
	private String ysfzh;
	/**
	 * 民族
	 */
	private byte mz;
	/**
	 * 身高
	 */
	private long sg;
	/**
	 * 案件编号
	 */
	private String ajbh;
	/**
	 * 案件类型代码
	 */
	private String ajlbdm;
	/**
	 * 有效性(1-有效,0-无效,2-在逃手动撤销)
	 */
	private String yxx;
	/**
	 * 相似度
	 */
	private String similarity;
	
	/**
	 * @return the id
	 */
	private String context;
	
	private byte[] photo;
	
	private String photoStr;
	
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
	 * @return the rybh
	 */
	public String getRybh() {
		return rybh;
	}
	/**
	 * @param rybh the rybh to set
	 */
	public void setRybh(String rybh) {
		this.rybh = rybh;
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
	public byte getCsrq() {
		return csrq;
	}
	/**
	 * @param csrq the csrq to set
	 */
	public void setCsrq(byte csrq) {
		this.csrq = csrq;
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
	 * @return the mz
	 */
	public byte getMz() {
		return mz;
	}
	/**
	 * @param mz the mz to set
	 */
	public void setMz(byte mz) {
		this.mz = mz;
	}
	/**
	 * @return the sg
	 */
	public long getSg() {
		return sg;
	}
	/**
	 * @param sg the sg to set
	 */
	public void setSg(long sg) {
		this.sg = sg;
	}
	/**
	 * @return the ajbh
	 */
	public String getAjbh() {
		return ajbh;
	}
	/**
	 * @param ajbh the ajbh to set
	 */
	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}
	/**
	 * @return the ajlbdm
	 */
	public String getAjlbdm() {
		return ajlbdm;
	}
	/**
	 * @param ajlbdm the ajlbdm to set
	 */
	public void setAjlbdm(String ajlbdm) {
		this.ajlbdm = ajlbdm;
	}
	/**
	 * @return the yxx
	 */
	public String getYxx() {
		return yxx;
	}
	/**
	 * @param yxx the yxx to set
	 */
	public void setYxx(String yxx) {
		this.yxx = yxx;
	}
	/**
	 * @return the similarity
	 */
	public String getSimilarity() {
		return similarity;
	}
	/**
	 * @param similarity the similarity to set
	 */
	public void setSimilarity(String similarity) {
		this.similarity = similarity;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "T_QB_RY_ZTRYJBXX [id=" + id + ", rybh=" + rybh + ", xm=" + xm + ", xb="
				+ xb + ", csrq=" + csrq + ", ysfzh=" + ysfzh + ", mz=" + mz
				+ ", sg=" + sg + ", ajbh=" + ajbh + ", ajlbdm=" + ajlbdm
				+ ", yxx=" + yxx + "]";
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

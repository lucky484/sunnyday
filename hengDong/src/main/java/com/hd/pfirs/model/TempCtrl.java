package com.hd.pfirs.model;

import java.util.Arrays;
import java.util.Date;

/**
 * 
 * @author curry.su
 *
 */
public class TempCtrl {
	/**
	 * 主键
	 */
	private long tempCompID;
	/**
	 * 身份证号码
	 */
	private String idCardNo;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 *人员描述
	 */
	private String description;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 辑控图片
	 */
	private byte[] pic;
	/**
	 * 辑控图片base64
	 */
	private String picStr;
	/**
	 * 备注
	 */
	private String remarkDesc;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建时间字符串
	 */
	private String createTimeStr;
	/**
	 * 创建人
	 */
	private String createName;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新时间字符串
	 */
	private String updateTimeStr;
	/**
	 * 更新人
	 */
	private String updateUser;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 状态字符串
	 */
	private String statusStr;
	
	private String featureID;

	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	public String getPicStr() {
		return picStr;
	}
	public void setPicStr(String picStr) {
		this.picStr = picStr;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateTimeStr() {
		return updateTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TempCtrl [tempCompCode=" + tempCompID + ", idCardNo=" + idCardNo + ", idCardName=" + name
				+ ", sex=" + sex + ", pic=" + Arrays.toString(pic) + ", picStr=" + picStr + ", remark=" + remarkDesc
				+ ", createTime=" + createTime + ", createTimeStr=" + createTimeStr + ", createUser=" + createName
				+ ", updateTime=" + updateTime + ", updateTimeStr=" + updateTimeStr + ", updateUser=" + updateUser
				+ ", status=" + status + "]";
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public long getTempCompID() {
		return tempCompID;
	}
	public void setTempCompID(long tempCompID) {
		this.tempCompID = tempCompID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFeatureID() {
		return featureID;
	}
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}
	public String getRemarkDesc() {
		return remarkDesc;
	}
	public void setRemarkDesc(String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	
}


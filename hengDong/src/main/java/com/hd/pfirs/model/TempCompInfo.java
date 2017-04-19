package com.hd.pfirs.model;

import java.util.Date;

public class TempCompInfo {
    
	/*
	 * 主键
	 */
	private Long tempCompID;
	/*
	 * 临时比对人员编号
	 */
	private String personCode;
	/*
	 * 人员照片
	 */
	private byte[] pic;
	/*
	 * 人员姓名
	 */
	private String name;
	/*
	 * 人员性别
	 */
	private String sex;
	/*
	 * 人员身份证
	 */
    private String idCardNo;
    /*
	 * 人员描述
	 */
    private String description;
    /*
	 * 特征值ID
	 */
    private String featureID;
    /*
	 * 备注
	 */
    private String remarkDesc;
    /*
	 * 删除状态
	 */
    private String deleteStatus;
    /*
	 * 创建人
	 */
    private String createName;
    /*
	 * 创建时间
	 */
    private Date createTime;
    /*
	 * 修改人
	 */
    private String updateName;
    /*
	 * 修改时间
	 */
    private Date updateTime;
	public Long getTempCompID() {
		return tempCompID;
	}
	public void setTempCompID(Long tempCompID) {
		this.tempCompID = tempCompID;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
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
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
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
	public String getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    
    
    
}

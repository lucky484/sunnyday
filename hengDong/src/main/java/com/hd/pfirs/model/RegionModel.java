package com.hd.pfirs.model;



/**
 * 辖区管理
 * 
 * @author cliff.fan
 *
 */
public class RegionModel {
	/**
	 * 辖区id
	 */
	private Long regionId;
	/**
	 * 辖区编号
	 */
	private String regionCode;
	/**
	 * 辖区名称
	 */
	private String regionName;
	/**
	 * 级别
	 */
	private String regionLevel;
	/**
	 * 上级辖区编号
	 */
	private String preRegionCode;
	/**
	 * 辖区描述
	 */
	private String description;
	/**
	 * 备注clob
	 */
	private String remarkDesc;
	/**
	 * 转发标记（0-未发送；1-发送中；2-发送成功；-1-发送失败）
	 */
	private String deleteStatus;
	/**
	 * 创建人
	 */
	private String createName;
	/**
	 * 创建时间datetime
	 */
	private String createTime;
	/**
	 * 更新人
	 */
	private String updateName;

	/**
	 * 更新时间datetime
	 */
	private String updateTime;
	
	/**
	 * 设备数量
	 */
	private int deviceCount;
	
	/**
	 * @return the regionId
	 */
	public Long getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return the regionLevel
	 */
	public String getRegionLevel() {
		return regionLevel;
	}

	/**
	 * @param regionLevel the regionLevel to set
	 */
	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel;
	}

	/**
	 * @return the preRegionCode
	 */
	public String getPreRegionCode() {
		return preRegionCode;
	}

	/**
	 * @param preRegionCode the preRegionCode to set
	 */
	public void setPreRegionCode(String preRegionCode) {
		this.preRegionCode = preRegionCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the remarkDesc
	 */
	public String getRemarkDesc() {
		return remarkDesc;
	}

	/**
	 * @param remarkDesc the remarkDesc to set
	 */
	public void setRemarkDesc(String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}

	/**
	 * @return the deleteStatus
	 */
	public String getDeleteStatus() {
		return deleteStatus;
	}

	/**
	 * @param deleteStatus the deleteStatus to set
	 */
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}

	/**
	 * @param createName the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateName
	 */
	public String getUpdateName() {
		return updateName;
	}

	/**
	 * @param updateName the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getDeviceCount()
	{
		return deviceCount;
	}

	public void setDeviceCount(int deviceCount)
	{
		this.deviceCount = deviceCount;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RegionModel [regionId=");
		builder.append(regionId);
		builder.append(", regionCode=");
		builder.append(regionCode);
		builder.append(", regionName=");
		builder.append(regionName);
		builder.append(", regionLevel=");
		builder.append(regionLevel);
		builder.append(", preRegionCode=");
		builder.append(preRegionCode);
		builder.append(", description=");
		builder.append(description);
		builder.append(", remarkDesc=");
		builder.append(remarkDesc);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append(", createName=");
		builder.append(createName);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", updateName=");
		builder.append(updateName);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", deviceCount=");
		builder.append(deviceCount);
		builder.append("]");
		return builder.toString();
	}
}
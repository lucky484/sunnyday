package com.softtek.mdm.model;

/**
 * 设备策略Entity
 * @author jane.hui
 *
 */
public class PolicyEntity extends BaseEntity{
	
	/**
	 * 编号
	 */
	private Integer id;
	
	/**
	 * 策略名称
	 */
	private String name;
	
	/**
	 * 0:android  1:ios
	 */
	private Integer type;
	
	/**
	 * 策略描述
	 */
	private String description;
	
	/**
	 * 设备类型
	 */
	private Integer deviceType;
	
	/**
	 * 是否启用
	 */
	private String isEnable;

	/**
	 * 已分配台数
	 */
	private Integer assignedCount;
	
	/**
	 * 已安装台数
	 */
	private Integer installedCount;
	
	/**
	 * 创建时间
	 */
    private String createDate;

    /**
     * 创建者
     */
    private Integer createUser;

    /**
     * 最后更新时间
     */
    private String updateDate;

    /**
     * 最后更新者
     */
    private Integer updateUser;
	
    
    
	public Integer getAssignedCount() {
		return assignedCount;
	}

	public void setAssignedCount(Integer assignedCount) {
		this.assignedCount = assignedCount;
	}

	public Integer getInstalledCount() {
		return installedCount;
	}

	public void setInstalledCount(Integer installedCount) {
		this.installedCount = installedCount;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
}

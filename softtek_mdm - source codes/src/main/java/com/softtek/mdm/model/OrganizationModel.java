package com.softtek.mdm.model;

import java.util.List;

/**
 * 组织机构对应数据表organization
 * @author color.wu
 *
 */
public class OrganizationModel extends BaseEntity {

	private Integer id;
	
	/**
	 * 机构编码
	 */
	private String orgType;
	
	/**
	 * 机构名称
	 */
	private String name;
	
	/**
	 * 分配License个数,-1不限制
	 */
	private Integer licenseCount;
	
	/**
	 * 备注
	 */
	private String mark;

	private String createName;
	/**
	 * 机构启用：1  禁用：0
	 */
	private String status;
	
	private List<OrganizationModel> lists;
	
	private Integer totalDevices;
	
	private Integer totalUsers;
	
	private Integer useUsers;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLicenseCount() {
		return licenseCount;
	}

	public void setLicenseCount(Integer licenseCount) {
		this.licenseCount = licenseCount;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrganizationModel> getLists() {
		return lists;
	}

	public void setLists(List<OrganizationModel> lists) {
		this.lists = lists;
	}

	public Integer getTotalDevices() {
		return totalDevices;
	}

	public void setTotalDevices(Integer totalDevices) {
		this.totalDevices = totalDevices;
	}

	public Integer getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Integer getUseUsers() {
		return useUsers;
	}

	public void setUseUsers(Integer useUsers) {
		this.useUsers = useUsers;
	}

}

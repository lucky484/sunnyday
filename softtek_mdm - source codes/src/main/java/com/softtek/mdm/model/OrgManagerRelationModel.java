package com.softtek.mdm.model;

/**
 * 机构与机构管理员关联表
 * @author color.wu
 *
 */
public class OrgManagerRelationModel extends BaseEntity{

	private Integer id;
	
	private OrganizationModel organization;
	
	private ManagerModel manager;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public ManagerModel getManager() {
		return manager;
	}

	public void setManager(ManagerModel manager) {
		this.manager = manager;
	}
	
	
}

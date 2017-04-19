package com.softtek.mdm.model;

public class UserDepartmentModel extends BaseEntity {

	private Integer id;
	
	private OrganizationModel organization;
	
	private UserRoleDepartmentModel userRoleDepartment;
	
	private StructureModel structure;

	
	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserRoleDepartmentModel getUserRoleDepartment() {
		return userRoleDepartment;
	}

	public void setUserRoleDepartment(UserRoleDepartmentModel userRoleDepartment) {
		this.userRoleDepartment = userRoleDepartment;
	}

	public StructureModel getStructure() {
		return structure;
	}

	public void setStructure(StructureModel structure) {
		this.structure = structure;
	}
	
	
	
	
}

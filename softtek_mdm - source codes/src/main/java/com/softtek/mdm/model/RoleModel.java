package com.softtek.mdm.model;

/**
 * 用户角色表对应数据库user_role
 * @author color.wu
 *
 */
public class RoleModel extends BaseEntity{

	private Integer id;
	/**
	 * 所属机构对象
	 */
	private OrganizationModel organization;
	
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 备注
	 */
	private String mark;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
}

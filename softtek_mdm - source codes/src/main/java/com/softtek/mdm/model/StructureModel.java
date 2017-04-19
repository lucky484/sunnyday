package com.softtek.mdm.model;


/**
 * 对应数据库表org_structure
 * @author color.wu
 *
 */
public class StructureModel extends BaseEntity{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -4817401313801619386L;
	
	private Integer id;
	/**
	 * 所属部门
	 */
	private OrganizationModel organization;
	/**
	 * 所属父 集团/机构/部门
	 */
	private StructureModel parent;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 创建类型
	 */
	private String createType;
	/**
	 * 权重
	 */
	private Integer weight;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 用户策略，表明对该集团/机构/部门以及子集团/子机构/子部门里面的用户使用此策略
	 * 如果下面的有具体的用户策略，则覆盖父级菜单
	 */
	private PolicyModel policy;

	/**
	 * 是否授权(1.已授权 0.未授权)
	 */
	private String isAuth;
	
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


	public StructureModel getParent() {
		return parent;
	}

	public void setParent(StructureModel parent) {
		this.parent = parent;
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public PolicyModel getPolicy() {
		return policy;
	}

	public void setPolicy(PolicyModel policy) {
		this.policy = policy;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}
}

package com.softtek.mdm.model;

/**
 * 对应数据表virtual_collection
 * @author color.wu
 *
 */
public class VirtualCollectionModel extends BaseEntity {

	private Integer id;
	/**
	 * 所属机构对象
	 */
	private OrganizationModel organization;
	/**
	 * 虚拟组集合的名称
	 */
	private String name;
	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 是否允许多选，表示用户是否可以同时加入该集合下的多个虚拟组
	 * 1：允许 0：不允许
	 */
	private Integer multiple;
	
	/**
	 * 虚拟集合人数统计
	 */
	private Integer quantity;
	
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
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}

package com.softtek.mdm.model;
/**
 * 对应数据表virtual_group
 * @author color.wu
 *
 */
public class VirtualGroupModel extends BaseEntity{

	private Integer id;
	/**
	 * 所属的机构
	 */
	private OrganizationModel organization;
	/**
	 * 虚拟组的名称
	 */
	private String name;
	/**
	 * 所属的虚拟组集合对象
	 */
	private VirtualCollectionModel virtualCollection;
	/**
	 * 所属的虚拟组对象
	 */
	private UserVirtualGroupModel   virtualGroup;
	/**
	 * 用户对象
	 */
	private UserModel user;
	/**
	 * 排序使用的权重
	 */
	private Integer weight;
	
	/**
	 * 虚拟组人数统计
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
	public VirtualCollectionModel getVirtualCollection() {
		return virtualCollection;
	}
	public void setVirtualCollection(VirtualCollectionModel virtualCollection) {
		this.virtualCollection = virtualCollection;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public UserVirtualGroupModel getVirtualGroup() {
		return virtualGroup;
	}
	public void setVirtualGroup(UserVirtualGroupModel virtualGroup) {
		this.virtualGroup = virtualGroup;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	
}

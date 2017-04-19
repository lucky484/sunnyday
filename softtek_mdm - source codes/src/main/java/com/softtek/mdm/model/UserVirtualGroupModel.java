package com.softtek.mdm.model;
/**
 * 对应数据表users_virtual_group
 * @author color.wu
 *
 */
public class UserVirtualGroupModel extends BaseEntity{

	private Integer id;
	/**
	 * 所属的机构
	 */
	private OrganizationModel organization;
	/**
	 * 所属的虚拟组集合
	 */
	private VirtualCollectionModel  virtualCollection;
	/**
	 * 所属的虚拟组
	 */
	private VirtualGroupModel virtualGroup;
	/**
	 * 用户对象
	 */
	private UserModel user;
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
	public VirtualGroupModel getVirtualGroup() {
		return virtualGroup;
	}
	public void setVirtualGroup(VirtualGroupModel virtualGroup) {
		this.virtualGroup = virtualGroup;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public VirtualCollectionModel getVirtualCollection() {
		return virtualCollection;
	}
	public void setVirtualCollection(VirtualCollectionModel virtualCollection) {
		this.virtualCollection = virtualCollection;
	}
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("UserVirtualGroupModel [id=");
		builder.append(id);
		builder.append(", organization=");
		builder.append(organization);
		builder.append(", virtualCollection=");
		builder.append(virtualCollection);
		builder.append(", virtualGroup=");
		builder.append(virtualGroup);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}
	

}

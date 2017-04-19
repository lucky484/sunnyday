package com.hd.pfirs.model;

import java.util.List;

/**
 * 角色全信息对象
 * 
 * @author brave.chen
 * @since 2016-01-27
 */
public class RoleFullInfoModel extends RoleModel
{
	/**
	 * 角色对象
	 */
	private RoleModel roleModel;

	/**
	 * 角色权限列表
	 */
	private List<RoleAuthModel> roleAuthModels;

	public RoleModel getRoleModel()
	{
		return roleModel;
	}

	public void setRoleModel(RoleModel roleModel)
	{
		this.roleModel = roleModel;
	}

	public List<RoleAuthModel> getRoleAuthModels()
	{
		return roleAuthModels;
	}

	public void setRoleAuthModels(List<RoleAuthModel> roleAuthModels)
	{
		this.roleAuthModels = roleAuthModels;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RoleFullInfoModel [roleModel=");
		builder.append(roleModel);
		builder.append(", roleAuthModels=");
		builder.append(roleAuthModels);
		builder.append("]");
		return builder.toString();
	}

}

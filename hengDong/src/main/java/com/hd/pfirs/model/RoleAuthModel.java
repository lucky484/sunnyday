package com.hd.pfirs.model;

import java.util.Date;

/**
 * 角色权限模型
 * 
 * @author brave.chen
 * @since 2016-01-26
 *
 */
public class RoleAuthModel
{
	/**
	 * 角色权限ID
	 */
	private Long rolePermissionID;

	/**
	 * 角色ID
	 */
	private Long roleID;

	/**
	 * 菜单ID
	 */
	private Long menuID;

	/**
	 * 页面功能ID
	 */
	private Long functionID;

	/**
	 * 备注
	 */
	private String remarkDesc;

	/**
	 * 删除状态(0-未删除；1-已删除)
	 */
	private String deleteStatus;

	/**
	 * 创建人
	 */
	private String createName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新人
	 */
	private String updateName;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	public RoleAuthModel(Long roleID, String menuID)
	{
		this.roleID =roleID;
		this.menuID = Long.valueOf(menuID);
		this.deleteStatus = "0";
		this.updateTime = new Date();
	}

	public Long getRolePermissionID()
	{
		return rolePermissionID;
	}

	public void setRolePermissionID(Long rolePermissionID)
	{
		this.rolePermissionID = rolePermissionID;
	}

	public Long getRoleID()
	{
		return roleID;
	}

	public void setRoleID(Long roleID)
	{
		this.roleID = roleID;
	}

	public Long getMenuID()
	{
		return menuID;
	}

	public void setMenuID(Long menuID)
	{
		this.menuID = menuID;
	}

	public Long getFunctionID()
	{
		return functionID;
	}

	public void setFunctionID(Long functionID)
	{
		this.functionID = functionID;
	}

	public String getRemarkDesc()
	{
		return remarkDesc;
	}

	public void setRemarkDesc(String remarkDesc)
	{
		this.remarkDesc = remarkDesc;
	}

	public String getDeleteStatus()
	{
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus)
	{
		this.deleteStatus = deleteStatus;
	}

	public String getCreateName()
	{
		return createName;
	}

	public void setCreateName(String createName)
	{
		this.createName = createName;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getUpdateName()
	{
		return updateName;
	}

	public void setUpdateName(String updateName)
	{
		this.updateName = updateName;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RoleAuthModel [rolePermissionID=");
		builder.append(rolePermissionID);
		builder.append(", roleID=");
		builder.append(roleID);
		builder.append(", menuID=");
		builder.append(menuID);
		builder.append(", functionID=");
		builder.append(functionID);
		builder.append(", remarkDesc=");
		builder.append(remarkDesc);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append(", createName=");
		builder.append(createName);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", updateName=");
		builder.append(updateName);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append("]");
		return builder.toString();
	}
}

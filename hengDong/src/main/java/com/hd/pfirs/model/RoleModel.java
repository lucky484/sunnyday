package com.hd.pfirs.model;

import java.util.Date;

/**
 * 角色模型
 * 
 * @author brave.chen
 * @since 2016-01-26
 *
 */
public class RoleModel
{
	/**
	 * 角色ID
	 */
	private Long roleID;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String description;

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
	 * 创时间
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

	public RoleModel(String roleName, String description, String remarkDesc)
	{
		this.roleName = roleName;
		this.description = description;
		this.remarkDesc = remarkDesc;
		this.updateTime = new Date();
		// 默认未删除
		this.deleteStatus = "0";
	}
	
	public RoleModel()
	{
		
	}

	public Long getRoleID()
	{
		return roleID;
	}

	public void setRoleID(Long roleID)
	{
		this.roleID = roleID;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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
		builder.append("RoleModel [roleID=");
		builder.append(roleID);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append(", description=");
		builder.append(description);
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

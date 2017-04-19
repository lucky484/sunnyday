/**
 * 
 */
package com.hd.pfirs.model;

import java.util.Date;

/**
 * @ClassName: User
 * @Description: 用户信息表
 * @author light.chen
 * @date Jan 6, 2016 2:48:19 PM
 */
public class User
{
	/**
	 * 用户id
	 */
	private Long diId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 身份证号
	 */
	private String idCardNm;

	/**
	 * 警员编号
	 */
	private String policeNm;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 部门ID
	 */
	private String institutionCode;

	/**
	 * 部门名称
	 */
	private String institutionName;

	/**
	 * 所属角色id
	 */
	private long roleId;

	/**
	 * 所属角色名称
	 */
	private String roleName;

	/**
	 * 状态(0-启用；1-禁用)
	 */
	private String status;

	/**
	 * 最后登录时间
	 */
	private Date lastloginDate;

	/**
	 * 注销时间
	 */
	private Date logoutDate;

	/**
	 * 备注
	 */
	private String remarkDesc;

	/**
	 * 删除状态(0-未删除;1-已删除)
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

	public Long getDiId()
	{
		return diId;
	}

	public void setDiId(Long diId)
	{
		this.diId = diId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getIdCardNm()
	{
		return idCardNm;
	}

	public void setIdCardNm(String idCardNm)
	{
		this.idCardNm = idCardNm;
	}

	public String getPoliceNm()
	{
		return policeNm;
	}

	public void setPoliceNm(String policeNm)
	{
		this.policeNm = policeNm;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getInstitutionCode()
	{
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode)
	{
		this.institutionCode = institutionCode;
	}

	public String getInstitutionName()
	{
		return institutionName;
	}

	public void setInstitutionName(String institutionName)
	{
		this.institutionName = institutionName;
	}

	public long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(long roleId)
	{
		this.roleId = roleId;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Date getLastloginDate()
	{
		return lastloginDate;
	}

	public void setLastloginDate(Date lastloginDate)
	{
		this.lastloginDate = lastloginDate;
	}

	public Date getLogoutDate()
	{
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate)
	{
		this.logoutDate = logoutDate;
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
		builder.append("User [diId=");
		builder.append(diId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", idCardNm=");
		builder.append(idCardNm);
		builder.append(", policeNm=");
		builder.append(policeNm);
		builder.append(", password=");
		builder.append(password);
		builder.append(", name=");
		builder.append(name);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", institutionCode=");
		builder.append(institutionCode);
		builder.append(", institutionName=");
		builder.append(institutionName);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append(", status=");
		builder.append(status);
		builder.append(", lastloginDate=");
		builder.append(lastloginDate);
		builder.append(", logoutDate=");
		builder.append(logoutDate);
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

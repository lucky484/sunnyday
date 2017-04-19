package com.hd.pfirs.model;

/**
 * 用户操作日志信息对象
 * 
 * @author brave.chen
 * @since 2016-02-02
 */
public class OperationLogInfo
{
	/**
	 * 记录标识
	 */
	private Long numID;

	/**
	 * 应用系统/资源库标识
	 */
	private String regID;

	/**
	 * 用户标识
	 */
	private String userID;

	/**
	 * 单位名称
	 */
	private String organization;

	/**
	 * 单位机构代码
	 */
	private String organizationID;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 操作时间YYYYMMDDhhmmss，24小时制格式
	 */
	private String operateTime;

	/**
	 * 终端标识(硬件序列号）
	 */
	private String terminalID;

	/**
	 * 操作类型0：登录；1：查询；2：新增；3：修改；4：删除
	 */
	private int operateType;

	/**
	 * 操作结果
	 */
	private String operateResult;

	/**
	 * 失败原因代码
	 */
	private String errorCode;

	/**
	 * 功能模块名称
	 */
	private String operateName;

	/**
	 * 操作条件
	 */
	private String operateCondition;

	/**
	 * 操作描述
	 */
	private String operateDesc;

	public Long getNumID()
	{
		return numID;
	}

	public void setNumID(Long numID)
	{
		this.numID = numID;
	}

	public String getRegID()
	{
		return regID;
	}

	public void setRegID(String regID)
	{
		this.regID = regID;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public String getOrganization()
	{
		return organization;
	}

	public void setOrganization(String organization)
	{
		this.organization = organization;
	}

	public String getOrganizationID()
	{
		return organizationID;
	}

	public void setOrganizationID(String organizationID)
	{
		this.organizationID = organizationID;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getOperateTime()
	{
		return operateTime;
	}

	public void setOperateTime(String operateTime)
	{
		this.operateTime = operateTime;
	}

	public String getTerminalID()
	{
		return terminalID;
	}

	public void setTerminalID(String terminalID)
	{
		this.terminalID = terminalID;
	}

	public int getOperateType()
	{
		return operateType;
	}

	public void setOperateType(int operateType)
	{
		this.operateType = operateType;
	}

	public String getOperateResult()
	{
		return operateResult;
	}

	public void setOperateResult(String operateResult)
	{
		this.operateResult = operateResult;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getOperateName()
	{
		return operateName;
	}

	public void setOperateName(String operateName)
	{
		this.operateName = operateName;
	}

	public String getOperateCondition()
	{
		return operateCondition;
	}

	public void setOperateCondition(String operateCondition)
	{
		this.operateCondition = operateCondition;
	}

	public String getOperateDesc()
	{
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc)
	{
		this.operateDesc = operateDesc;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("OperationInfo [numID=");
		builder.append(numID);
		builder.append(", regID=");
		builder.append(regID);
		builder.append(", userID=");
		builder.append(userID);
		builder.append(", organization=");
		builder.append(organization);
		builder.append(", organizationID=");
		builder.append(organizationID);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", operateTime=");
		builder.append(operateTime);
		builder.append(", terminalID=");
		builder.append(terminalID);
		builder.append(", operateType=");
		builder.append(operateType);
		builder.append(", operateResult=");
		builder.append(operateResult);
		builder.append(", errorCode=");
		builder.append(errorCode);
		builder.append(", operateName=");
		builder.append(operateName);
		builder.append(", operateCondition=");
		builder.append(operateCondition);
		builder.append(", operateDesc=");
		builder.append(operateDesc);
		builder.append("]");
		return builder.toString();
	}

}

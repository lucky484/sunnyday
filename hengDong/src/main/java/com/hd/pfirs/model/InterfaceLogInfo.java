package com.hd.pfirs.model;

/**
 * 用户接口日志信息对象
 * 
 * @author brave.chen
 * @since 2016-02-02
 */
public class InterfaceLogInfo
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
	 * 接口名称
	 */
	private String interfaceName;

	/**
	 * 请求方名称
	 */
	private String requester;

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
	private String interfaceTime;

	/**
	 * 终端标识(硬件序列号）
	 */
	private String terminalID;

	/**
	 * 操作结果
	 */
	private String interfaceResult;

	/**
	 * 失败原因代码
	 */
	private String errorCode;

	/**
	 * 操作条件
	 */
	private String interfaceCondition;

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

	public String getInterfaceName()
	{
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName)
	{
		this.interfaceName = interfaceName;
	}

	public String getRequester()
	{
		return requester;
	}

	public void setRequester(String requester)
	{
		this.requester = requester;
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

	public String getInterfaceTime()
	{
		return interfaceTime;
	}

	public void setInterfaceTime(String interfaceTime)
	{
		this.interfaceTime = interfaceTime;
	}

	public String getTerminalID()
	{
		return terminalID;
	}

	public void setTerminalID(String terminalID)
	{
		this.terminalID = terminalID;
	}

	public String getInterfaceResult()
	{
		return interfaceResult;
	}

	public void setInterfaceResult(String interfaceResult)
	{
		this.interfaceResult = interfaceResult;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getInterfaceCondition()
	{
		return interfaceCondition;
	}

	public void setInterfaceCondition(String interfaceCondition)
	{
		this.interfaceCondition = interfaceCondition;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("InterfaceLogInfo [numID=");
		builder.append(numID);
		builder.append(", regID=");
		builder.append(regID);
		builder.append(", interfaceName=");
		builder.append(interfaceName);
		builder.append(", requester=");
		builder.append(requester);
		builder.append(", userID=");
		builder.append(userID);
		builder.append(", organization=");
		builder.append(organization);
		builder.append(", organizationID=");
		builder.append(organizationID);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", interfaceTime=");
		builder.append(interfaceTime);
		builder.append(", terminalID=");
		builder.append(terminalID);
		builder.append(", interfaceResult=");
		builder.append(interfaceResult);
		builder.append(", errorCode=");
		builder.append(errorCode);
		builder.append(", interfaceCondition=");
		builder.append(interfaceCondition);
		builder.append("]");
		return builder.toString();
	}

}

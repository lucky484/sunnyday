package com.softtek.mdm.model;

/**
 * 许可证文件对象 date: May 27, 2016 10:35:52 AM
 * 
 * @author brave.chen
 */
public class LicenseFileObject
{
	/**
	 * 版本类型："0"试用版，"1"正式版
	 */
	private String versionType;

	/**
	 * 开始时间(格式:yyyy-MM-dd【2016-01-01】)
	 */
	private String startDate;

	/**
	 * 到期时间(格式:yyyy-MM-dd【2016-01-01】)
	 */
	private String endDate;

	/**
	 * 激活码
	 */
	private String activeCode;

	/**
	 * CPU编号
	 */
	private String cpuId;

	/**
	 * CPU编号
	 */
	private String mac;

	/**
	 * 授权总人数
	 */
	private Integer generateMembers;

	public String getVersionType()
	{
		return versionType;
	}

	public void setVersionType(String versionType)
	{
		this.versionType = versionType;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getActiveCode()
	{
		return activeCode;
	}

	public void setActiveCode(String activeCode)
	{
		this.activeCode = activeCode;
	}

	public String getCpuId()
	{
		return cpuId;
	}

	public void setCpuId(String cpuId)
	{
		this.cpuId = cpuId;
	}

	public String getMac()
	{
		return mac;
	}

	public void setMac(String mac)
	{
		this.mac = mac;
	}

	public Integer getGenerateMembers()
	{
		return generateMembers;
	}

	public void setGenerateMembers(Integer generateMembers)
	{
		this.generateMembers = generateMembers;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("LicenseFileObject [versionType=");
		builder.append(versionType);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", activeCode=");
		builder.append(activeCode);
		builder.append(", cpuId=");
		builder.append(cpuId);
		builder.append(", mac=");
		builder.append(mac);
		builder.append(", generateMembers=");
		builder.append(generateMembers);
		builder.append("]");
		return builder.toString();
	}

}

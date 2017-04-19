package com.softtek.mdm.model;

/**
 * 许可证对象
 * 
 * date: May 24, 2016 8:55:54 AM
 *
 * @author brave.chen
 */
public class LicenseModel
{
	/**
	 * 版本信息
	 */
	private String version;

	/**
	 * 产品类型
	 */
	private String producteType;

	/**
	 * 授权总天数
	 */
	private Integer authorizedDays;

	/**
	 * 剩余天数
	 */
	private Integer remainDays;

	/**
	 * 授权总数
	 */
	private Integer authorizedAllNum;

	/**
	 * 使用授权数
	 */
	private Integer authorizedUsedNum;

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getProducteType()
	{
		return producteType;
	}

	public void setProducteType(String producteType)
	{
		this.producteType = producteType;
	}

	public Integer getAuthorizedDays()
	{
		return authorizedDays;
	}

	public void setAuthorizedDays(Integer authorizedDays)
	{
		this.authorizedDays = authorizedDays;
	}

	public Integer getRemainDays()
	{
		return remainDays;
	}

	public void setRemainDays(Integer remainDays)
	{
		this.remainDays = remainDays;
	}

	public Integer getAuthorizedAllNum()
	{
		return authorizedAllNum;
	}

	public void setAuthorizedAllNum(Integer authorizedAllNum)
	{
		this.authorizedAllNum = authorizedAllNum;
	}

	public Integer getAuthorizedUsedNum()
	{
		return authorizedUsedNum;
	}

	public void setAuthorizedUsedNum(Integer authorizedUsedNum)
	{
		this.authorizedUsedNum = authorizedUsedNum;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("LicenseModel [version=");
		builder.append(version);
		builder.append(", producteType=");
		builder.append(producteType);
		builder.append(", authorizedDays=");
		builder.append(authorizedDays);
		builder.append(", remainDays=");
		builder.append(remainDays);
		builder.append(", authorizedAllNum=");
		builder.append(authorizedAllNum);
		builder.append(", authorizedUsedNum=");
		builder.append("]");
		return builder.toString();
	}
}

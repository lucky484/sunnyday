package com.hd.pfirs.model;

/**
 * 系统工作人员信息model
 * 
 * @author brave.chen
 * @since 2016-01-26
 *
 */
public class InstitutionInfoModel
{
	/**
	 * 组织编码
	 */
	private String institutionCode;

	/**
	 * 组织名称
	 */
	private String institutionName;

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

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("InstitutionInfoModel [institutionCode=");
		builder.append(institutionCode);
		builder.append(", institutionName=");
		builder.append(institutionName);
		builder.append("]");
		return builder.toString();
	}

}

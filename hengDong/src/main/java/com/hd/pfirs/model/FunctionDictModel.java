package com.hd.pfirs.model;

/**
 * 功能模型
 * 
 * @author brave.chen
 * @since 2016-01-27
 *
 */
public class FunctionDictModel
{
	/**
	 * 页面功能ID
	 */
	private Long functionID;

	/**
	 * 页面名
	 */
	private String pageName;

	/**
	 * 页面名中文
	 */
	private String pageNameZH;

	/**
	 * 功能名
	 */
	private String functionName;

	/**
	 * 功能名中文
	 */
	private String functionNameZH;

	/**
	 * 备注
	 */
	private String remarkDesc;

	/**
	 * 删除状态(0-未删除；1-已删除)
	 */
	private String deleteStatus;

	public Long getFunctionID()
	{
		return functionID;
	}

	public void setFunctionID(Long functionID)
	{
		this.functionID = functionID;
	}

	public String getPageName()
	{
		return pageName;
	}

	public void setPageName(String pageName)
	{
		this.pageName = pageName;
	}

	public String getPageNameZH()
	{
		return pageNameZH;
	}

	public void setPageNameZH(String pageNameZH)
	{
		this.pageNameZH = pageNameZH;
	}

	public String getFunctionName()
	{
		return functionName;
	}

	public void setFunctionName(String functionName)
	{
		this.functionName = functionName;
	}

	public String getFunctionNameZH()
	{
		return functionNameZH;
	}

	public void setFunctionNameZH(String functionNameZH)
	{
		this.functionNameZH = functionNameZH;
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

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FunctionDictModel [functionID=");
		builder.append(functionID);
		builder.append(", pageName=");
		builder.append(pageName);
		builder.append(", pageNameZH=");
		builder.append(pageNameZH);
		builder.append(", functionName=");
		builder.append(functionName);
		builder.append(", functionNameZH=");
		builder.append(functionNameZH);
		builder.append(", remarkDesc=");
		builder.append(remarkDesc);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append("]");
		return builder.toString();
	}

}

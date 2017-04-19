package com.softtek.mdm.model;

/**
 *
 * 上网行为黑名单部门关联关系对象 date: Jun 23, 2016 1:51:06 PM
 *
 * @author brave.chen
 */
public class DeptNetBwListRelation
{
	/**
	 * 关系id
	 */
	private Integer id;

	/**
	 * 部门id或者机构id
	 */
	private Integer orgManagerId;

	/**
	 * 网页黑白名单id
	 */
	private Integer netbehavioirBwListId;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getOrgManagerId()
	{
		return orgManagerId;
	}

	public void setOrgManagerId(Integer orgManagerId)
	{
		this.orgManagerId = orgManagerId;
	}

	public Integer getNetbehavioirBwListId()
	{
		return netbehavioirBwListId;
	}

	public void setNetbehavioirBwListId(Integer netbehavioirBwListId)
	{
		this.netbehavioirBwListId = netbehavioirBwListId;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("DeptNetBwListRelation [id=");
		builder.append(id);
		builder.append(", orgManagerId=");
		builder.append(orgManagerId);
		builder.append(", netbehavioirBwListId=");
		builder.append(netbehavioirBwListId);
		builder.append("]");
		return builder.toString();
	}

}

package com.softtek.mdm.model;

/**
 * 图文消息推送关联关系 date: Jun 23, 2016 1:54:55 PM
 *
 * @author brave.chen
 */
public class DeptPicMsgRelation
{
	/**
	 * 图文消息部分关联关系id
	 */
	private Integer id;

	/**
	 * 部门ID或者机构id
	 */
	private Integer orgManagerId;

	/**
	 * 图文消息ID
	 */
	private Integer picMsgId;

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

	public Integer getPicMsgId()
	{
		return picMsgId;
	}

	public void setPicMsgId(Integer picMsgId)
	{
		this.picMsgId = picMsgId;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("DeptPicMsgRelation [id=");
		builder.append(id);
		builder.append(", orgManagerId=");
		builder.append(orgManagerId);
		builder.append(", picMsgId=");
		builder.append(picMsgId);
		builder.append("]");
		return builder.toString();
	}

}

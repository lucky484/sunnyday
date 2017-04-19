package com.softtek.mdm.model;

/**
 * 邮件参数对象
 * date: Jun 7, 2016 3:53:34 PM
 *
 * @author brave.chen
 */
public class EmailParamModel extends EmailModel
{
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 邮件发送者
	 */
	private String sender;
	
	private Integer isSSL;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getSender()
	{
		return sender;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}

	public Integer getIsSSL()
	{
		return isSSL;
	}

	public void setIsSSL(Integer isSSL)
	{
		this.isSSL = isSSL;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("EmailParamModel [id=");
		builder.append(id);
		builder.append(", sender=");
		builder.append(sender);
		builder.append(", isSSL=");
		builder.append(isSSL);
		builder.append("]");
		return builder.toString();
	}

	
}


package com.softtek.mdm.model;

/**
 * 消息通知配置对象
 * date: Jun 2, 2016 3:07:07 PM
 *
 * @author brave.chen
 */
public class AdivceMessageParamSetting
{
	/**
	 * 消息类型(1:邮件服务器配置,2:短信参数配置)
	 */
	private int messageType;
	
	/**
	 * 其他信息(messageType为1格式:SMTP服务器地址\SMTP服务器端口\登录用户名\登录密码发送者邮件地址
	 * messageType为2格式：启用短信发送能力\短信服务地址\企业标识\应用标识\测试短信内容)
	 */
	private String other;

	public int getMessageType()
	{
		return messageType;
	}

	public void setMessageType(int messageType)
	{
		this.messageType = messageType;
	}

	public String getOther()
	{
		return other;
	}

	public void setOther(String other)
	{
		this.other = other;
	}
}


package com.softtek.mdm.model;

public class PushMsgSendModel extends MessageSendModel
{
	private String msgId;
	
	private String imgUrl;

	public String getMsgId()
	{
		return msgId;
	}

	public void setMsgId(String msgId)
	{
		this.msgId = msgId;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}
}


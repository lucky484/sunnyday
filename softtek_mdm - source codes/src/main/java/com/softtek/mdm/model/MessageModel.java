package com.softtek.mdm.model;

public class MessageModel {
    
	/**
	 * 消息标题
	 */
	private String messageTitle;
	
	/**
	 * 消息内容
	 */
	private String messageContent;
	
	/**
	 * 消息发送人
	 */
	private String messageSender;
	
	/**
	 * 消息发送时间
	 */
	private String messageSenderTime;

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(String messageSender) {
		this.messageSender = messageSender;
	}

	public String getMessageSenderTime() {
		return messageSenderTime;
	}

	public void setMessageSenderTime(String messageSenderTime) {
		this.messageSenderTime = messageSenderTime;
	}
	
	
}

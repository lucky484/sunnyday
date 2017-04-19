package com.softtek.mdm.web.http;



public class JsonResult {
	
	/** 结果等级. success, warning, danger */
	public MessageType type;
	
	/** 消息内容. */
	public String message;

	/** 内容. */
	public Object content;
	
	/**
	 * 
	 * @param type 结果等级
	 * @param message 消息内容
	 */
	public JsonResult() {
	}
	
	/**
	 * 
	 * @param type 结果等级
	 * @param message 消息内容
	 */
	public JsonResult(MessageType type, String message) {
		this.type = type;
		this.message = message;
	}
	
	/**
	 * 
	 * @param type 结果等级
	 * @param message 消息内容
	 * @param content 内容
	 */
	public JsonResult(MessageType type, String message, Object content) {
		this.type = type;
		this.message = message;
		this.content = content;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	
}

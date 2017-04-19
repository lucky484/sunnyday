package com.f2b2c.eco.share.dto;

import com.f2b2c.eco.status.MessageType;
import com.f2b2c.eco.status.ResultStatus;

/**
 * DataToObject�����
 * @author color.wu
 *
 */
public class DTOResult {

	/** ����ȼ�. success, warning, danger */
	public MessageType type;
	
	/** ��Ϣ����. */
	public String message;

	/** ����. */
	public Object content;
	
	public ResultStatus resultStatus;
	
	/**
	 * ��ʾ���캯��
	 */
	public DTOResult(){
		
	}
	
	/**
	 * 
	 * @param type ����ȼ�
	 * @param message ��Ϣ����
	 */
	public DTOResult(MessageType type, String message) {
		this.type = type;
		this.message = message;
	}

	/**
	 * 
	 * @param type ����ȼ�
	 * @param message ��Ϣ����
	 * @param content ����
	 */
	public DTOResult(MessageType type, String message, Object content) {
		this.type = type;
		this.message = message;
		this.content = content;
	}

	//==========================getter & setter start===================
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

	public ResultStatus getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}
	//==========================getter & setter end===================
}


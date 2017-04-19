package com.softtek.mdm.model;

/**
 * 只用于处理对客户端返回响应的数据使用
 * @author color.wu
 *
 * @param <T>
 */
public class DeviceResultModel<T> {

	/**
	 * 消息状态码
	 */
	private Integer status;
	/**
	 * 消息提示
	 */
	private String msg;
	
	/**
	 * 具体的响应数据
	 */
	private T data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public DeviceResultModel(){}
	
	public DeviceResultModel(Integer status,String msg,T data){
		this.status=status;
		this.msg=msg;
		this.data=data;
	}

	
	
	
} 

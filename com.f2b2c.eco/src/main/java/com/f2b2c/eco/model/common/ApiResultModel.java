package com.f2b2c.eco.model.common;

/**
 * 只用于处理对客户端返回响应的数据使用
 * @author jane.hui
 *
 * @param <T>
 */
public class ApiResultModel<T> {

	/**
	 * 消息状�?�码
	 */
	private Integer status;
	/**
	 * 消息提示
	 */
	private String msg;
	
	/**
	 * 具体的响应数?
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
} 
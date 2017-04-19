package com.f2b2c.eco.status;

/**
 * 返回状态
 * 
 * @author brave.chen
 *
 */
public enum ResultStatus {
	/**
	 * 标志错误
	 */
	FAILED(-200),
	
	/**
	 * 成功
	 */
	SUCESS(200),
	
	/**
	 * 未授权
	 */
	NO_AUTHORIZED(401),
	
	/**
	 * 服务器内部错误
	 */
	SERVER_ERROR(503);
	
	/**
	 * 状态值
	 */
	private int status;

	private ResultStatus(final int value) {
		this.status = value;
	}

	public int getStatus() {
		return this.status;
	}
}


package com.softtek.mdm.exception;

/**
 * 
 * Description: 业务层抛出异常类
 * date: May 6, 2016 1:46:23 PM
 *
 * @author brave.chen
 */
public class BusinessException extends Exception
{
	private String exceptionCode;
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8477013897014990687L;

	public BusinessException(String msg)
	{
		super(msg);
	}
	
	public BusinessException(String msg, String exceptionCode)
	{
		super(msg);
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionCode()
	{
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode)
	{
		this.exceptionCode = exceptionCode;
	}
	
	
}


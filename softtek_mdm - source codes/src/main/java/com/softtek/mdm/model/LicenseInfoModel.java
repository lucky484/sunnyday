package com.softtek.mdm.model;

import java.util.Arrays;

/**
 * 许可证对象类
 * date: May 24, 2016 4:32:13 PM
 *
 * @author brave.chen
 */
public class LicenseInfoModel
{
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 到期提醒天数
	 */
	private Integer adviceDays;
	
	/**
	 * 激活码
	 */
	private String activeCode;
	
	/**
	 * 本地私钥
	 */
	private String privateKey;
	
	/**
	 * 机器码文件路径
	 */
	private String machineFilePath;
	
	/**
	 * 许可证文件字节流数组
	 */
	private byte[] licenceFileBytes;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getAdviceDays()
	{
		return adviceDays;
	}

	public void setAdviceDays(Integer adviceDays)
	{
		this.adviceDays = adviceDays;
	}

	public String getActiveCode()
	{
		return activeCode;
	}

	public void setActiveCode(String activeCode)
	{
		this.activeCode = activeCode;
	}

	public String getPrivateKey()
	{
		return privateKey;
	}

	public void setPrivateKey(String privateKey)
	{
		this.privateKey = privateKey;
	}

	public String getMachineFilePath()
	{
		return machineFilePath;
	}

	public void setMachineFilePath(String machineFilePath)
	{
		this.machineFilePath = machineFilePath;
	}

	public byte[] getLicenceFileBytes()
	{
		return licenceFileBytes;
	}

	public void setLicenceFileBytes(byte[] licenceFileBytes)
	{
		this.licenceFileBytes = licenceFileBytes;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("LicenseInfoModel [id=");
		builder.append(id);
		builder.append(", adviceDays=");
		builder.append(adviceDays);
		builder.append(", activeCode=");
		builder.append(activeCode);
		builder.append(", privateKey=");
		builder.append(privateKey);
		builder.append(", machineFilePath=");
		builder.append(machineFilePath);
		builder.append(", licenceFileBytes=");
		builder.append(Arrays.toString(licenceFileBytes));
		builder.append("]");
		return builder.toString();
	}
}


package com.softtek.mdm.model;

/**
 * 系统参数设置对象 date: Jun 2, 2016 2:04:03 PM
 *
 * @author brave.chen
 */
public class SystemParamSetModel
{
	/**
	 * 系统参数id
	 */
	private Integer id;

	/**
	 * MDM管理平台地址
	 */
	private String mdmAddress;

	/**
	 * 设备脱管时间限制(分)
	 */
	private Integer outManagerTime;

	/**
	 * 终端设备信息采集周期(分)
	 */
	private Integer deviceInfoCollectTime;

	/**
	 * 终端违规采集周期(分)
	 */
	private Integer illegalInfoCollectTime;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getMdmAddress()
	{
		return mdmAddress;
	}

	public void setMdmAddress(String mdmAddress)
	{
		this.mdmAddress = mdmAddress;
	}

	public Integer getOutManagerTime()
	{
		return outManagerTime;
	}

	public void setOutManagerTime(Integer outManagerTime)
	{
		this.outManagerTime = outManagerTime;
	}

	public Integer getDeviceInfoCollectTime()
	{
		return deviceInfoCollectTime;
	}

	public void setDeviceInfoCollectTime(Integer deviceInfoCollectTime)
	{
		this.deviceInfoCollectTime = deviceInfoCollectTime;
	}

	public Integer getIllegalInfoCollectTime()
	{
		return illegalInfoCollectTime;
	}

	public void setIllegalInfoCollectTime(Integer illegalInfoCollectTime)
	{
		this.illegalInfoCollectTime = illegalInfoCollectTime;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SystemParamSetModel [id=");
		builder.append(id);
		builder.append(", mdmAddress=");
		builder.append(mdmAddress);
		builder.append(", outManagerTime=");
		builder.append(outManagerTime);
		builder.append(", deviceInfoCollectTime=");
		builder.append(deviceInfoCollectTime);
		builder.append(", illegalInfoCollectTime=");
		builder.append(illegalInfoCollectTime);
		builder.append("]");
		return builder.toString();
	}
}

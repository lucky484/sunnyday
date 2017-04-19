package com.softtek.mdm.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 推送命令表
 * @author jane.hui
 *
 */
public class Command implements Serializable
{
	/**
	 * 主键
	 * 
	 * @mbggenerated
	 */
	private String id;

	/**
	 * 设备ID
	 */
	private String deviceId;

	/**
	 * 命令
	 * 
	 * @mbggenerated
	 */
	private String command;

	/**
	 * 命令外键
	 */
	private Integer commandId;
	
	/**
	 * 类别(devicePolicy.设备策略)
	 */
	private String type;
	
	/**
	 * 
	 * @mbggenerated
	 */
	private String doit;

	/**
	 * 返回结果
	 */
	private String result;

	/**
	 * 创建时间
	 * 
	 * @mbggenerated
	 */
	private Date createDate;

	/**
	 * 创建者
	 * 
	 * @mbggenerated
	 */
	private Integer createUser;

	/**
	 * 更新时间
	 * 
	 * @mbggenerated
	 */
	private Date updateDate;

	/**
	 * 更新者
	 * 
	 * @mbggenerated
	 */
	private Integer updateUser;

	/**
	 * 删除时间
	 * 
	 * @mbggenerated
	 */
	private Date deleteTime;

	/**
	 * 处理类名称
	 */
	private String className;

	/**
	 * 序列号
	 * 
	 * @mbggenerated
	 */
	private static final long serialVersionUID = 1L;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getCommand()
	{
		return command;
	}

	public void setCommand(String command)
	{
		this.command = command == null ? null : command.trim();
	}
	
	public Integer getCommandId() {
		return commandId;
	}

	public void setCommandId(Integer commandId) {
		this.commandId = commandId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public String getDoit()
	{
		return doit;
	}

	public void setDoit(String doit)
	{
		this.doit = doit == null ? null : doit.trim();
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Integer getCreateUser()
	{
		return createUser;
	}

	public void setCreateUser(Integer createUser)
	{
		this.createUser = createUser;
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	public Integer getUpdateUser()
	{
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser)
	{
		this.updateUser = updateUser;
	}

	public Date getDeleteTime()
	{
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime)
	{
		this.deleteTime = deleteTime;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	@Override
	public boolean equals(Object that)
	{
		if (this == that)
		{
			return true;
		}
		if (that == null)
		{
			return false;
		}
		if (getClass() != that.getClass())
		{
			return false;
		}
		Command other = (Command) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
				&& (this.getCommand() == null ? other.getCommand() == null
						: this.getCommand().equals(other.getCommand()))
				&& (this.getDoit() == null ? other.getDoit() == null : this.getDoit().equals(other.getDoit()))
				&& (this.getCreateDate() == null ? other.getCreateDate() == null
						: this.getCreateDate().equals(other.getCreateDate()))
				&& (this.getCreateUser() == null ? other.getCreateUser() == null
						: this.getCreateUser().equals(other.getCreateUser()))
				&& (this.getUpdateDate() == null ? other.getUpdateDate() == null
						: this.getUpdateDate().equals(other.getUpdateDate()))
				&& (this.getUpdateUser() == null ? other.getUpdateUser() == null
						: this.getUpdateUser().equals(other.getUpdateUser()))
				&& (this.getDeleteTime() == null ? other.getDeleteTime() == null
						: this.getDeleteTime().equals(other.getDeleteTime()));
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCommand() == null) ? 0 : getCommand().hashCode());
		result = prime * result + ((getDoit() == null) ? 0 : getDoit().hashCode());
		result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
		result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
		result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
		result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
		result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
		return result;
	}

	@Override
	public String toString()
	{
		return "Command [id=" + id + ", deviceId=" + deviceId + ", command=" + command + ", doit=" + doit + ", result="
				+ result + ", createDate=" + createDate + ", createUser=" + createUser + ", updateDate=" + updateDate
				+ ", updateUser=" + updateUser + ", deleteTime=" + deleteTime + ", className=" + className + "]";
	}

}
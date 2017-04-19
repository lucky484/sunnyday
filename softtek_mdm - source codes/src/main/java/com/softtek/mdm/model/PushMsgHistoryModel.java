package com.softtek.mdm.model;

import java.util.Date;

/**
 * 
 * 推送消息历史记录对象 date: Apr 26, 2016 12:40:31 PM
 *
 * @author brave.chen
 */
public class PushMsgHistoryModel
{
	/**
	 * 消息历史记录id
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 设备唯一编号
	 */
	private String udId;

	/**
	 * 消息id
	 */
	private PushMobileMsgModel pushMobileMsgModel;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人
	 */
	private String updateUser;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 组织机构id
	 */
	private Integer orgId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public String getUdId()
	{
		return udId;
	}

	public void setUdId(String udId)
	{
		this.udId = udId;
	}
	

	public PushMobileMsgModel getPushMobileMsgModel()
	{
		if (pushMobileMsgModel == null)
		{
			pushMobileMsgModel = new PushMobileMsgModel();
		}
		
		return pushMobileMsgModel;
	}

	public void setPushMobileMsgModel(PushMobileMsgModel pushMobileMsgModel)
	{
		this.pushMobileMsgModel = pushMobileMsgModel;
	}

	public String getCreateUser()
	{
		return createUser;
	}

	public void setCreateUser(String createUser)
	{
		this.createUser = createUser;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getUpdateUser()
	{
		return updateUser;
	}

	public void setUpdateUser(String updateUser)
	{
		this.updateUser = updateUser;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Integer getOrgId()
	{
		return orgId;
	}

	public void setOrgId(Integer orgId)
	{
		this.orgId = orgId;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("PushMsgHistoryModel [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", udId=");
		builder.append(udId);
		builder.append(", pushMobileMsgModel=");
		builder.append(pushMobileMsgModel);
		builder.append(", createUser=");
		builder.append(createUser);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", updateUser=");
		builder.append(updateUser);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append("]");
		return builder.toString();
	}
}

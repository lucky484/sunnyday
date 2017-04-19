
package com.softtek.mdm.model;

import java.util.Date;
import java.util.Set;

/**
 * 推送客户端消息对象类 date: Apr 22, 2016 7:07:57 PM
 *
 * @author brave.chen
 */
public class PushMobileMsgModel
{
	/**
     * 消息ID
     */
	private Integer id;

	/**
     * 图片url
     */
	private String picUrl;

	/**
     * 消息主题
     */
	private String msgTheme;

	/**
     * 创建人ID
     */
	private Integer createUserId;

	/**
     * 创建人姓名
     */
	private String createUserName;

	/**
     * 创建时间
     */
	private Date createTime;

	/**
     * 修改人ID
     */
	private Integer updateUserId;

	/**
     * 修改人姓名
     */
	private String updateUserName;

	/**
     * 修改时间
     */
	private Date updateTime;

    /**
     * 推送时间
     */
    private Date pushTime;

	/**
     * 组织机构id
     */
	private Integer orgId;

	/**
     * 消息内容
     */
	private String content;

	/**
     * 消息推送部门ids
     */
	private String departIds;

	/**
     * 消息推送虚拟组ids
     */
	private String virtualIds;

	/**
     * 消息推送用户ids
     */
	private String userIds;

    /**
     * @Description授权人数
     * @author josen.yang
     * @return
     */

    private Integer userCount;

    /**
     * @Description授权人数
     * @author josen.yang
     * @return
     */

    private Set<Integer> userIdList;

    /**
     * @return userCount
     */

    public Integer getUserCount() {
        return userCount;
    }

    /**
     * @param userCount
     *            要设置的 userCount
     */

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    /**
     * @return userIdList
     */

    public Set<Integer> getUserIdList() {
        return userIdList;
    }

    /**
     * @param testreusername
     *            要设置的 userIdList
     */

    public void setUserIdList(Set<Integer> userIdList) {
        this.userIdList = userIdList;
    }

    public Integer getId()
	{
		return id;
	}


    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public void setId(Integer id)
	{
		this.id = id;
	}

	public String getPicUrl()
	{
		return picUrl;
	}

	public void setPicUrl(String picUrl)
	{
		this.picUrl = picUrl;
	}

	public String getMsgTheme()
	{
		return msgTheme;
	}

	public void setMsgTheme(String msgTheme)
	{
		this.msgTheme = msgTheme;
	}

	public Integer getCreateUserId()
	{
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId)
	{
		this.createUserId = createUserId;
	}

	public String getCreateUserName()
	{
		return createUserName;
	}

	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Integer getUpdateUserId()
	{
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId)
	{
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName()
	{
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName)
	{
		this.updateUserName = updateUserName;
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

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getDepartIds()
	{
		return departIds;
	}

	public void setDepartIds(String departIds)
	{
		this.departIds = departIds;
	}

	public String getVirtualIds()
	{
		return virtualIds;
	}

	public void setVirtualIds(String virtualIds)
	{
		this.virtualIds = virtualIds;
	}

	public String getUserIds()
	{
		return userIds;
	}

	public void setUserIds(String userIds)
	{
		this.userIds = userIds;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("PushMobileMsgModel [id=");
		builder.append(id);
		builder.append(", picUrl=");
		builder.append(picUrl);
		builder.append(", msgTheme=");
		builder.append(msgTheme);
		builder.append(", createUserId=");
		builder.append(createUserId);
		builder.append(", createUserName=");
		builder.append(createUserName);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", updateUserId=");
		builder.append(updateUserId);
		builder.append(", updateUserName=");
		builder.append(updateUserName);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", content=");
		builder.append(content);
		builder.append(", departIds=");
		builder.append(departIds);
		builder.append(", virtualIds=");
		builder.append(virtualIds);
		builder.append(", userIds=");
		builder.append(userIds);
		builder.append("]");
		return builder.toString();
	}
}

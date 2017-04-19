package com.softtek.mdm.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.softtek.mdm.util.Constant;

/**
 * Description: 上网行为黑白名单对象类 date: Apr 12, 2016 3:56:11 PM
 *
 * @author brave.chen
 */
public class NetBehaviorBlackWhiteList
{
	/**
	 * 黑白名单ID
	 */
	private Integer id;

	/**
	 * 黑白名单名称
	 */
	private String blackWhiteName;

	/**
	 * 黑白名单类型（0：黑名单；1：白名单）
	 */
	private Integer type;

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
	private Date createDate;

	/**
	 * 更新人ID
	 */
	private Integer updateUserId;

	/**
	 * 修改人姓名
	 */
	private String updateUserName;

	/**
	 * 更新时间
	 */
	private Date updateDate;

	/**
	 * 删除时间
	 */
	private Date delDate;

	/**
	 * 组织ID
	 */
	private Integer organizationId;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 网页数
	 */
	private Integer size;

	/**
	 * 更新者id
	 */
	private Integer updateUserfId;
	
	/**
	 * 网页数
	 */
	private String netCount;
	
	/**
	 * 黑白名单监测url
	 */
	private List<BlackWhiteListUrl> bWUrlList;

	/**
	 * 
	 * Creates a new instance of NetBehaviorBlackWhiteList.
	 *
	 */
	public NetBehaviorBlackWhiteList()
	{

	}

	/**
	 * Creates a new instance of NetBehaviorBlackWhiteList.用于新增黑白名单对象
	 *
	 * @param paramsMap
	 *            属性map对象
	 */
	public NetBehaviorBlackWhiteList(Map<String, Object> paramsMap)
	{
		if (MapUtils.isNotEmpty(paramsMap))
		{
			this.type = (Integer) paramsMap.get(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_TYPE);
			this.blackWhiteName = (String) paramsMap.get(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_NAME);
			this.remark = (String) paramsMap.get(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_REMARK);
			this.organizationId = (Integer) paramsMap.get(Constant.FullRegionUseConstant.ORGANIZATION_ID);
			this.createUserId = (Integer) paramsMap.get("createUserId");
			this.createUserName = (String) paramsMap.get("createUserName");
			this.createDate = new Date();
			this.updateUserId = this.createUserId;
			this.updateUserName = this.createUserName;
			this.updateDate = this.createDate;
		}
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getBlackWhiteName()
	{
		return blackWhiteName;
	}

	public void setBlackWhiteName(String blackWhiteName)
	{
		this.blackWhiteName = blackWhiteName;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
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

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
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

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	public Date getDelDate()
	{
		return delDate;
	}

	public void setDelDate(Date delDate)
	{
		this.delDate = delDate;
	}

	public Integer getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId)
	{
		this.organizationId = organizationId;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Integer getSize()
	{
		return size;
	}

	public void setSize(Integer size)
	{
		this.size = size;
	}

	public List<BlackWhiteListUrl> getbWUrlList()
	{
		return bWUrlList;
	}

	public void setbWUrlList(List<BlackWhiteListUrl> bWUrlList)
	{
		this.bWUrlList = bWUrlList;
	}
	
	public Integer getUpdateUserfId() {
		return updateUserfId;
	}

	public void setUpdateUserfId(Integer updateUserfId) {
		this.updateUserfId = updateUserfId;
	}
	
	public String getNetCount() {
		return netCount;
	}

	public void setNetCount(String netCount) {
		this.netCount = netCount;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("NetBehaviorBlackWhiteList [id=");
		builder.append(id);
		builder.append(", blackWhiteName=");
		builder.append(blackWhiteName);
		builder.append(", type=");
		builder.append(type);
		builder.append(", createUserId=");
		builder.append(createUserId);
		builder.append(", createUserName=");
		builder.append(createUserName);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateUserId=");
		builder.append(updateUserId);
		builder.append(", updateUserName=");
		builder.append(updateUserName);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", delDate=");
		builder.append(delDate);
		builder.append(", organizationId=");
		builder.append(organizationId);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", size=");
		builder.append(size);
		builder.append(", bWUrlList=");
		builder.append(bWUrlList);
		builder.append("]");
		return builder.toString();
	}
}

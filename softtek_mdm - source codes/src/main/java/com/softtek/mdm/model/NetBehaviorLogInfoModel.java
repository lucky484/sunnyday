package com.softtek.mdm.model;

import java.util.Date;

/**
 * 上网行为日志类 date: Apr 13, 2016 9:08:15 AM
 *
 * @author brave.chen
 */
public class NetBehaviorLogInfoModel
{
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 登录帐号
	 */
	private String loginAccount;
	
	/**
	 * 设备型号
	 */
	private String deviceName;
	
	/**
	 * 序列号
	 */
	private String sn;
	
	/**
	 * 设备信息主键id
	 */
	private Integer deviceId;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 上网人姓名
	 */
	private String userName;
	
	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 用户类型
	 */
	private Integer userType;

	/**
	 * 用户部门类型
	 */
	private String departmentName;

	/**
	 * 上网时间
	 */
	private Date surfTime;

	/**
	 * 上网内容
	 */
	private String conetent;

	/**
	 * 机构名称
	 */
	private String organizationName;

	/**
	 * 部门ID
	 */
	private Integer departmentId;

	/**
	 * 组织机构ID
	 */
	private Integer organizationId;

	/**
	 * 上网网址
	 */
	private String surfUrl;

	/**
	 * 上网时间字符串格式
	 */
	private String surfTimeStr;

	/**
	 * 标题
	 */
	private String title;

    /**
     * 网站类型
     */
    private Integer websiteType;
	
    /**
     * 关键字
     */
    private String headKeywords;
    
    /**
     * 描述
     */
    private String headDescription;
    
	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

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

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public Integer getUserType()
	{
		return userType;
	}

	public void setUserType(Integer userType)
	{
		this.userType = userType;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	public Integer getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId)
	{
		this.departmentId = departmentId;
	}

	public Date getSurfTime()
	{
		return surfTime;
	}

	public void setSurfTime(Date surfTime)
	{
		this.surfTime = surfTime;
	}

	public String getConetent()
	{
		return conetent;
	}

	public void setConetent(String conetent)
	{
		this.conetent = conetent;
	}

	public String getOrganizationName()
	{
		return organizationName;
	}

	public void setOrganizationName(String organizationName)
	{
		this.organizationName = organizationName;
	}

	public Integer getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId)
	{
		this.organizationId = organizationId;
	}

	public String getSurfUrl()
	{
		return surfUrl;
	}

	public void setSurfUrl(String surfUrl)
	{
		this.surfUrl = surfUrl;
	}

	public String getSurfTimeStr()
	{
		return surfTimeStr;
	}

	public void setSurfTimeStr(String surfTimeStr)
	{
		this.surfTimeStr = surfTimeStr;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("NetBehaviorLogInfoModel [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", departmentName=");
		builder.append(departmentName);
		builder.append(", surfTime=");
		builder.append(surfTime);
		builder.append(", conetent=");
		builder.append(conetent);
		builder.append(", organizationName=");
		builder.append(organizationName);
		builder.append(", departmentId=");
		builder.append(departmentId);
		builder.append(", organizationId=");
		builder.append(organizationId);
		builder.append(", surfUrl=");
		builder.append(surfUrl);
		builder.append(", surfTimeStr=");
		builder.append(surfTimeStr);
		builder.append(", title=");
		builder.append(title);
		builder.append(", headKeywords=");
		builder.append(headKeywords);
		builder.append(", headDescription=");
		builder.append(headDescription);
		builder.append(", websiteType=");
		builder.append(websiteType);
		builder.append("]");
		return builder.toString();
	}

	public Integer getWebsiteType() {
		return websiteType;
	}

	public void setWebsiteType(Integer websiteType) {
		this.websiteType = websiteType;
	}

	public String getHeadKeywords() {
		return headKeywords;
	}

	public void setHeadKeywords(String headKeywords) {
		this.headKeywords = headKeywords;
	}

	public String getHeadDescription() {
		return headKeywords;
	}

	public void setHeadDescription(String headDescription) {
		this.headDescription = headDescription;
	}
}

package com.softtek.mdm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户表，对应数据库users
 * @author color.wu
 *
 */
public class UserModel extends BaseEntity{
	
	private Integer id;
	
	/**
	 * 用户名，登录帐号
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 所属机构对象
	 */
	private OrganizationModel organization;
	
	/**
	 * 所属部门对象
	 */
	private StructureModel structure;
	/**
	 * 所属的用户策略
	 */
	private PolicyModel policy;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 用户类型
	 */
	private Integer type;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 公司电话
	 */
	private String office_phone;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 权限属性
	 */
	private Integer authorition_properties;
	/**
	 * 允许登录的ip地址段
	 */
	private String ip_range;
	/**
	 * 登录时间限制
	 */
	private String login_time_range;
	/**
	 * 登录时间
	 */
	private Date last_time;
	/**
	 * 登录ip
	 */
	private String last_ip;
	/**
	 * 是否激活
	 * 1：激活；0：未激活
	 */
	private Integer is_active;
	/**
	 * 是否锁定
	 * 1：激活；0：未激活
	 */
	private Integer is_lock;
	/**
	 * 是否创建邮件账户
	 * 1：是；0：否
	 */
	private Integer is_create_email_account;
	/**
	 * 发送通知邮件
	 * 1：是；0：否
	 */
	private Integer is_create_email_inform;
	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 权重
	 */
	private Integer weight;
	
	private Integer login_count;
	
	private String token;
	
	private String lastTimeStr;
	
	private Date createDate; //运维统计部分 创建时间
	
	private Integer totalUser;//运维统计部分用户总数
	
	private Integer activeUser;//运维统计部分激活总数
	
	private Integer inActiveUser;//运维统计部分未激活总数
	
	private Integer deleteUser;//运维统计部分删除用户数目
	
	private String departName;
	
	private Integer deviceLoginCount;
	
	private String deviceName;
	
	private String iosUuid;
	
	/**
	 * ios用户保存未签名描述文件的路径
	 */
	private String configFile;
	
	/**
	 * ios用户保存已签名描述文件的路径
	 */
	private String signConfigFile;
	
	/**
	 * Device Token
	 */
	private String deviceToken;
	
	/**
	 * 是否安装描述文件   0-未安装   1-已安装
	 */
	private Integer isProfile;
	
	private DeviceManagerModel deviceManager;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public OrganizationModel getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}
	public StructureModel getStructure() {
		return structure;
	}
	public void setStructure(StructureModel structure) {
		this.structure = structure;
	}
	public PolicyModel getPolicy() {
		return policy;
	}
	public void setPolicy(PolicyModel policy) {
		this.policy = policy;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOffice_phone() {
		return office_phone;
	}
	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getAuthorition_properties() {
		return authorition_properties;
	}
	public void setAuthorition_properties(Integer authorition_properties) {
		this.authorition_properties = authorition_properties;
	}
	public String getIp_range() {
		return ip_range;
	}
	public void setIp_range(String ip_range) {
		this.ip_range = ip_range;
	}
	public String getLogin_time_range() {
		return login_time_range;
	}
	public void setLogin_time_range(String login_time_range) {
		this.login_time_range = login_time_range;
	}
	public Date getLast_time() {
		return last_time;
	}
	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}
	public String getLast_ip() {
		return last_ip;
	}
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}
	public Integer getIs_active() {
		return is_active;
	}
	public void setIs_active(Integer is_active) {
		this.is_active = is_active;
	}
	public Integer getIs_lock() {
		return is_lock;
	}
	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
	}
	public Integer getIs_create_email_account() {
		return is_create_email_account;
	}
	public void setIs_create_email_account(Integer is_create_email_account) {
		this.is_create_email_account = is_create_email_account;
	}
	public Integer getIs_create_email_inform() {
		return is_create_email_inform;
	}
	public void setIs_create_email_inform(Integer is_create_email_inform) {
		this.is_create_email_inform = is_create_email_inform;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getLogin_count() {
		return login_count==null?0:login_count;
	}
	public void setLogin_count(Integer login_count) {
		this.login_count = login_count;
	}
	public String getLastTimeStr() {
		if(getLast_time() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(getLast_time());
		}
		return "";
	}
	public void setLastTimeStr(String lastTimeStr) {
		this.lastTimeStr = lastTimeStr;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(Integer totalUser) {
		this.totalUser = totalUser;
	}
	public Integer getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(Integer activeUser) {
		this.activeUser = activeUser;
	}
	public Integer getInActiveUser() {
		return inActiveUser;
	}
	public void setInActiveUser(Integer inActiveUser) {
		this.inActiveUser = inActiveUser;
	}
	public Integer getDeleteUser() {
		return deleteUser;
	}
	public void setDeleteUser(Integer deleteUser) {
		this.deleteUser = deleteUser;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Integer getDeviceLoginCount() {
		return deviceLoginCount==null?0:deviceLoginCount;
	}
	public void setDeviceLoginCount(Integer deviceLoginCount) {
		this.deviceLoginCount = deviceLoginCount;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public DeviceManagerModel getDeviceManager() {
		return deviceManager;
	}
	public void setDeviceManager(DeviceManagerModel deviceManager) {
		this.deviceManager = deviceManager;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getIosUuid() {
		return iosUuid;
	}
	public void setIosUuid(String iosUuid) {
		this.iosUuid = iosUuid;
	}
	public String getConfigFile() {
		return configFile;
	}
	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}
	public String getSignConfigFile() {
		return signConfigFile;
	}
	public void setSignConfigFile(String signConfigFile) {
		this.signConfigFile = signConfigFile;
	}
	
	public Integer getIsProfile() {
		return isProfile;
	}
	public void setIsProfile(Integer isProfile) {
		this.isProfile = isProfile;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserModel [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", organization=");
		builder.append(organization);
		builder.append(", structure=");
		builder.append(structure);
		builder.append(", policy=");
		builder.append(policy);
		builder.append(", realname=");
		builder.append(realname);
		builder.append(", type=");
		builder.append(type);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", sign=");
		builder.append(sign);
		builder.append(", address=");
		builder.append(address);
		builder.append(", office_phone=");
		builder.append(office_phone);
		builder.append(", position=");
		builder.append(position);
		builder.append(", authorition_properties=");
		builder.append(authorition_properties);
		builder.append(", ip_range=");
		builder.append(ip_range);
		builder.append(", login_time_range=");
		builder.append(login_time_range);
		builder.append(", last_time=");
		builder.append(last_time);
		builder.append(", last_ip=");
		builder.append(last_ip);
		builder.append(", is_active=");
		builder.append(is_active);
		builder.append(", is_lock=");
		builder.append(is_lock);
		builder.append(", is_create_email_account=");
		builder.append(is_create_email_account);
		builder.append(", is_create_email_inform=");
		builder.append(is_create_email_inform);
		builder.append(", mark=");
		builder.append(mark);
		builder.append(", weight=");
		builder.append(weight);
		builder.append(", login_count=");
		builder.append(login_count);
		builder.append(", token=");
		builder.append(token);
		builder.append(", lastTimeStr=");
		builder.append(lastTimeStr);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", totalUser=");
		builder.append(totalUser);
		builder.append(", activeUser=");
		builder.append(activeUser);
		builder.append(", inActiveUser=");
		builder.append(inActiveUser);
		builder.append(", deleteUser=");
		builder.append(deleteUser);
		builder.append(", departName=");
		builder.append(departName);
		builder.append(", deviceLoginCount=");
		builder.append(deviceLoginCount);
		builder.append(", deviceName=");
		builder.append(deviceName);
		builder.append(", iosUuid=");
		builder.append(iosUuid);
		builder.append(", deviceToken=");
		builder.append(deviceToken);
		builder.append(", deviceManager=");
		builder.append(deviceManager);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

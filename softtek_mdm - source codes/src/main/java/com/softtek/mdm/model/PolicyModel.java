package com.softtek.mdm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户策略对应数据表user_policy
 * @author color.wu
 * @version 1.0
 *
 */
public class PolicyModel extends BaseEntity{

	private Integer id;
	/**
	 * 所属机构对象
	 */
	private OrganizationModel organization;
	
	/**
	 * 决策名称
	 */
	private String name;
	/**
	 * 部门名称
	 */
	private String department;
	
	/**
	 * 是否是默认策略   
	 * 1:是；0：否
	 */
	private Integer isDefault;
	/**
	 * 只能在工作时间访问   
	 * 1:是；0：否
	 */
	private Integer visit_on_worktime;
	/**
	 * 如果设定只能在工作时间访问，则需要指定
	 * 开始工作时间
	 */
	private String visit_time_start;
	/**
	 * 如果设定只能在工作时间访问，则需要指定
	 * 结束工作时间
	 */
	private String visit_time_end;
	/**
	 * 是否通过wifi
	 * 1:是；0：否
	 */
	private Integer allow_wifi;
	/**
	 * 是否仅公司wifi
	 * 1:是；0：否
	 */
	private Integer only_comp_wifi;
	/**
	 * 是否禁止登录
	 * 1:是；0：否
	 */
	private Integer login_limit;
	/**
	 * 是否禁止自动登录
	 * 1:是；0：否
	 */
	private Integer auto_login_limit;
	/**
	 * 允许用户登录错误次数
	 */
	private Integer login_error_limit;
	/**
	 * 是否允许用户连续输错
	 * 1:是；0：否
	 */
	private Integer login_error_limit_times;
	/**
	 * 是否连续输入错误时锁定
	 * 1:是；0：否
	 */
	private Integer login_error_limit_lock;
	/**
	 * 是否允许用户可以登记多个设备
	 * 1:是；0：否
	 */
	private Integer device_limit;
	/**
	 * 允许用户可以登记的设备的个数
	 */
	private Integer device_limit_count;
	/**
	 * 是否禁止非监控的设备访问应用资源
	 * 1:是；0：否
	 */
	private Integer access_resource_limit;
	/**
	 * 手势密码后台生效间隔时间(单位：秒)
	 */
	private String device_password_interval;
	/**
	 * 个人文档分配空间(单位:GB)
	 */
	private String doc_space;
	/**
	 * 是否强制设置手势开关
	 * 1:是；0：否
	 */
	private Integer force_password;
	/**
	 * 是否开启周期定位开关每日的启止时间
	 * 1:是；0：否
	 */
	private Integer switch_gps;
	/**
	 * 如果开启周期定位开关每日的启止时间
	 * 周期定位开始时间
	 */
	private Date gps_start;
	/**
	 * 如果开启周期定位开关每日的启止时间
	 * 周期定位结束时间
	 */
	private Date gps_end;
	/**
	 * 周期（只星期几，一个或多个，通过分隔符分割）
	 */
	private String gps_week_time;
	
	private String deparmentName;
	
	private String deparmentId;
	
	private String parentId;
	
	private String flag;
	
	private Integer count;//统计策略使用的人数
	
	private UserModel user;
	
	private DeviceManagerModel deviceManager;
	
	@SuppressWarnings("unused")
	private String createTimeStr;//用string类型的代替createTime
	
	private String nodeIds;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public OrganizationModel getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getVisit_on_worktime() {
		return visit_on_worktime;
	}
	public void setVisit_on_worktime(Integer visit_on_worktime) {
		this.visit_on_worktime = visit_on_worktime;
	}
	public String getVisit_time_start() {
		return visit_time_start;
	}
	public void setVisit_time_start(String visit_time_start) {
		this.visit_time_start = visit_time_start;
	}
	public String getVisit_time_end() {
		return visit_time_end;
	}
	public void setVisit_time_end(String visit_time_end) {
		this.visit_time_end = visit_time_end;
	}
	public Integer getAllow_wifi() {
		return allow_wifi;
	}
	public void setAllow_wifi(Integer allow_wifi) {
		this.allow_wifi = allow_wifi;
	}
	public Integer getOnly_comp_wifi() {
		return only_comp_wifi;
	}
	public void setOnly_comp_wifi(Integer only_comp_wifi) {
		this.only_comp_wifi = only_comp_wifi;
	}
	public Integer getLogin_limit() {
		return login_limit;
	}
	public void setLogin_limit(Integer login_limit) {
		this.login_limit = login_limit;
	}
	public Integer getAuto_login_limit() {
		return auto_login_limit;
	}
	public void setAuto_login_limit(Integer auto_login_limit) {
		this.auto_login_limit = auto_login_limit;
	}
	public Integer getLogin_error_limit() {
		return login_error_limit;
	}
	public void setLogin_error_limit(Integer login_error_limit) {
		this.login_error_limit = login_error_limit;
	}
	public Integer getLogin_error_limit_times() {
		return login_error_limit_times==null?3:login_error_limit_times;
	}
	public void setLogin_error_limit_times(Integer login_error_limit_times) {
		this.login_error_limit_times = login_error_limit_times;
	}
	public Integer getLogin_error_limit_lock() {
		return login_error_limit_lock;
	}
	public void setLogin_error_limit_lock(Integer login_error_limit_lock) {
		this.login_error_limit_lock = login_error_limit_lock;
	}
	public Integer getDevice_limit() {
		return device_limit;
	}
	public void setDevice_limit(Integer device_limit) {
		this.device_limit = device_limit;
	}
	public Integer getDevice_limit_count() {
		return device_limit_count;
	}
	public void setDevice_limit_count(Integer device_limit_count) {
		this.device_limit_count = device_limit_count;
	}
	public Integer getAccess_resource_limit() {
		return access_resource_limit;
	}
	public void setAccess_resource_limit(Integer access_resource_limit) {
		this.access_resource_limit = access_resource_limit;
	}
	public String getDevice_password_interval() {
		return device_password_interval;
	}
	public void setDevice_password_interval(String device_password_interval) {
		this.device_password_interval = device_password_interval;
	}
	public String getDoc_space() {
		return doc_space;
	}
	public void setDoc_space(String doc_space) {
		this.doc_space = doc_space;
	}
	public Integer getForce_password() {
		return force_password;
	}
	public void setForce_password(Integer force_password) {
		this.force_password = force_password;
	}
	public Integer getSwitch_gps() {
		return switch_gps;
	}
	public void setSwitch_gps(Integer switch_gps) {
		this.switch_gps = switch_gps;
	}
	public Date getGps_start() {
		return gps_start;
	}
	public void setGps_start(Date gps_start) {
		this.gps_start = gps_start;
	}
	public Date getGps_end() {
		return gps_end;
	}
	public void setGps_end(Date gps_end) {
		this.gps_end = gps_end;
	}
	public String getGps_week_time() {
		return gps_week_time;
	}
	public void setGps_week_time(String gps_week_time) {
		this.gps_week_time = gps_week_time;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getDeparmentName() {
		return deparmentName;
	}
	public void setDeparmentName(String deparmentName) {
		this.deparmentName = deparmentName;
	}
	public String getDeparmentId() {
		return deparmentId;
	}
	public void setDeparmentId(String deparmentId) {
		this.deparmentId = deparmentId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCreateTimeStr() {
		if(getCreateTime() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(getCreateTime());
		}
		return "";
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getNodeIds() {
		return nodeIds;
	}
	public void setNodeIds(String nodeIds) {
		this.nodeIds = nodeIds;
	}
	public DeviceManagerModel getDeviceManager() {
		return deviceManager;
	}
	public void setDeviceManager(DeviceManagerModel deviceManager) {
		this.deviceManager = deviceManager;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
}

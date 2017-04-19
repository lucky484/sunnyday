package com.softtek.mdm.model;

import java.util.Date;
import java.util.List;

/**
 * 对应数据库表org_managers
 * @author color.wu
 *
 */
public class ManagerModel extends BaseEntity{

	private Integer id;
	/**
	 * 用户对象
	 */
	private UserModel user;
	
	private String username;
	/**
	 * 所属机构
	 */
	private OrganizationModel organization;
	/**
	 * 用户类型 
	 * 部门管理员/机构管理员
	 */
	private Integer user_type;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 登录之后session有效时间
	 */
	private Integer expiry;
	/**
	 * 登录名称
	 */
	private String name;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 备注
	 */
	private String mark;
	
	private Integer login_count;
	
	/**
	 * 机构管理员启用：1  禁用：0
	 */
	private String status;
	
	private Integer total;
	
	private List<OrganizationModel> lists;
	
	private Date last_login_time;
	
	private Integer auth;
	
	public Integer getAuth() {
		return auth;
	}

	public void setAuth(Integer auth) {
		this.auth = auth;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserModel getUserModel() {
		return user;
	}

	public void setUserModel(UserModel user) {
		this.user = user;
	}
	
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getExpiry() {
		return expiry;
	}

	public void setExpiry(Integer expiry) {
		this.expiry = expiry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getLogin_count() {
		return login_count;
	}

	public void setLogin_count(Integer login_count) {
		this.login_count = login_count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrganizationModel> getLists() {
		return lists;
	}

	public void setLists(List<OrganizationModel> lists) {
		this.lists = lists;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Date getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}
	
}

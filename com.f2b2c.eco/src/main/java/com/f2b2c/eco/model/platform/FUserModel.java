package com.f2b2c.eco.model.platform;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class FUserModel implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2529073059925335594L;

	/**
	 * 平台用户ID
	 */
	private Integer id;

	/**
	 * 用户名
	 */
	@NotEmpty(message = "帐户名不能为空！")
	private String accountName;

	/**
	 * 密码
	 */
	@NotEmpty(message = "密码不能为空！")
	@Length(min = 6, max = 20, message = "密码在6到20个字符之间！")
	private String password;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 邮箱帐号
	 */
	private String email;

	/**
	 * 性别，0：男1：女
	 *
	 * @mbggenerated
	 */
	private Integer sex;

	/**
	 * 身份证号码
	 */
	private String identityId;

	/**
	 * 是否激活0激活1锁定
	 */
	private Integer isActive;

	/**
	 * 创建人
	 */
	private FUserModel createdUser;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 更新人
	 */
	private FUserModel updatedUser;

	/**
	 * 更新时间
	 */
	private Date updatedTime;

	/**
	 * 删除时间
	 */
	private Date deletedTime;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 分润比例
	 */
	private BigDecimal shareProfitPer;

	/**
	 * 余额
	 */
	private BigDecimal balance;

	/**
	 * 角色对象
	 */
	private FRoleModel fRoleModel;

	/**
	 * 区域ID（城市ID）
	 */
	private Integer areaId;

	/**
	 * 省份id
	 */
	private Integer provinceid;

	/**
	 * 城市id
	 */
	private Integer cityid;

	private Integer roleId;
	
	private String roleName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public FUserModel getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(FUserModel createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public FUserModel getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(FUserModel updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getShareProfitPer() {
		return shareProfitPer;
	}

	public void setShareProfitPer(BigDecimal shareProfitPer) {
		this.shareProfitPer = shareProfitPer;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public FRoleModel getfRoleModel() {
		return fRoleModel;
	}

	public void setfRoleModel(FRoleModel fRoleModel) {
		this.fRoleModel = fRoleModel;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
}
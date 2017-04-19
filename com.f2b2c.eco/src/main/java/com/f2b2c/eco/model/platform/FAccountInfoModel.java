package com.f2b2c.eco.model.platform;

import java.io.Serializable;
import java.util.Date;

public class FAccountInfoModel implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户
     */
    private FUserModel user;

    /**
     * 用户类型
     */
    private Integer userType;
    
    /**
     * 佣金账户余额
     */
    private Long commission_remain;
    
    /**
     * 平台账户余额
     */
    private Long platform_remain;

    /**
     * 更新时间
     */
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FUserModel getUser() {
		return user;
	}

	public void setUser(FUserModel user) {
		this.user = user;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getCommission_remain() {
		return commission_remain;
	}

	public void setCommission_remain(Long commission_remain) {
		this.commission_remain = commission_remain;
	}

	public Long getPlatform_remain() {
		return platform_remain;
	}

	public void setPlatform_remain(Long platform_remain) {
		this.platform_remain = platform_remain;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FAccountInfoModel [id=");
		builder.append(id);
		builder.append(", user=");
		builder.append(user);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", commission_remain=");
		builder.append(commission_remain);
		builder.append(", platform_remain=");
		builder.append(platform_remain);
		builder.append(", updatedTime=");
		builder.append(updatedTime);
		builder.append("]");
		return builder.toString();
	}

}
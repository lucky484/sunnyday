package com.f2b.security.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 其他分享平台
 * 
 * @author jacob.shen
 *
 */
@Entity
@Table(name = "share_company")
@GenericGenerator(name = "SHARE_COMPANY_GEN", strategy = "enhanced-table", parameters = {
		@org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
		@org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"),
		@org.hibernate.annotations.Parameter(name = "segment_value", value = "share_company_id"),
		@org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"),
		@org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
		@org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
		@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
public class ShareCompany {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 来源的唯一标识，公众号，平台
	 */
	private String openid;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 提成比例，单位：箱
	 */
	private String profit;
	private Date createDate;
	private Date updateDate;

	@Id
	@GeneratedValue(generator = "SHARE_COMPANY_GEN")
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

}

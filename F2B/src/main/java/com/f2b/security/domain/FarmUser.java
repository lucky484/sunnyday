package com.f2b.security.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 关注过的用户
 * @author mozzie.chu
 * @version 16/12/13, 10:21
 */
@Entity
@Table(name = "farm_user")
@GenericGenerator(name = "FARM_USER_GEN", strategy = "enhanced-table", parameters = {
		@org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
		@org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"),
		@org.hibernate.annotations.Parameter(name = "segment_value", value = "id"),
		@org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"),
		@org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
		@org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
		@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
public class FarmUser {

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 加密后的微信号
	 */
	private String openId;
	
	/**
	 * 是否使用过抵用卷 0：没有抵用券| 1：有抵用券
	 */
	private String status;

	/**
	 * 关注后第一次访问促销时间
	 */
	private Date createDate;
	
	/**
	 * 收货时间 finshDate
	 */
	private Date updateDate;
	
	@Id
	@GeneratedValue(generator = "FARM_USER_GEN")
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "open_id")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
}

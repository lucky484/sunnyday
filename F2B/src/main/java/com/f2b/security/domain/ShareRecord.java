package com.f2b.security.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Created by Administrator on 2016/3/24.
 */
@Entity
@Table(name = "share_record")
@GenericGenerator(name = "SHARE_RECORD_GEN", strategy = "enhanced-table", parameters = {
		@org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
		@org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"),
		@org.hibernate.annotations.Parameter(name = "segment_value", value = "share_record_id"),
		@org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"),
		@org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
		@org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
		@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
public class ShareRecord {
	/**
	 * 主键
	 */
	private Long shareRecordId;

	/**
	 * 分享人的openid
	 */
	private String shareOpenId;

	/**
	 * 分享人的昵称，以base64加密
	 */
	private String shareNickName;

	/**
	 * 购买者的openid
	 */
	private String openId;
	/**
	 * 购买者的昵称，以base64加密
	 */
	private String nickname;

	/**
	 * 购买者一共买了多少箱
	 */
	private Integer number;

	private Date createDate;

	private Date updateDate;
	private String sku;
	private String comments;

	/**
	 * 是否已经发送红包给分享人（是否分销）1:是；0：否
	 */
	private Integer sendRedPack;

	@Id
	@GeneratedValue(generator = "SHARE_RECORD_GEN")
	@Column(name = "share_record_id")
	public Long getShareRecordId() {
		return shareRecordId;
	}

	public void setShareRecordId(Long shareRecordId) {
		this.shareRecordId = shareRecordId;
	}

	@Column(name = "share_open_id")
	public String getShareOpenId() {
		return shareOpenId;
	}

	public void setShareOpenId(String shareOpenId) {
		this.shareOpenId = shareOpenId;
	}

	@Column(name = "open_id")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getSendRedPack() {
		return sendRedPack;
	}

	public void setSendRedPack(Integer sendRedPack) {
		this.sendRedPack = sendRedPack;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getShareNickName() {
		return shareNickName;
	}

	public void setShareNickName(String shareNickName) {
		this.shareNickName = shareNickName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}

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
@Table(name = "reward_index")
@GenericGenerator(name = "REWARD_INDEX_GEN", strategy = "enhanced-table", parameters = { @org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
		@org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"), @org.hibernate.annotations.Parameter(name = "segment_value", value = "id"),
		@org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"), @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
		@org.hibernate.annotations.Parameter(name = "increment_size", value = "1"), @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
public class RewardIndex {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 当前抽奖的索引位置,25个奖项，轮回
	 * {5,10,5,20,5,30,5,30,10,10,5,40,5,20,5,20,5,10,5,60,5,80,10,60,10};
	 */
	private Integer currentIndex;
	/**
	 * 总的抽奖索引位置
	 */
	private Integer totalIndex;
	
	private Date updateTime;

	@Id
	@GeneratedValue(generator = "REWARD_INDEX_GEN")
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(Integer currentIndex) {
		this.currentIndex = currentIndex;
	}

	public Integer getTotalIndex() {
		return totalIndex;
	}

	public void setTotalIndex(Integer totalIndex) {
		this.totalIndex = totalIndex;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}

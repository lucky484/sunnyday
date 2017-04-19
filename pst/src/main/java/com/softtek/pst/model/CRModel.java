/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: projectCodeModel.java
 * @Prject: pst
 * @Package: com.softtek.pst.model
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 6, 2016 9:33:32 AM
 * @version: V1.0  
 */
package com.softtek.pst.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * @ClassName: projectCodeModel
 * @Description: 新增CR
 * @author: light.chen
 * @date: Apr 6, 2016 9:33:32 AM
 */
@Alias("CRModel")
public class CRModel {
	/**
	 * CRid
	 */
	private long crId;
	/**
	 * 项目编号
	 */
	private long projectId;
	/**
	 * 需求描述
	 */
	private String crDescribe;
	/**
	 * 预估工作量(小时)
	 */
	private int estimatedWorkload;
	/**
	 * 提交人
	 */
	private String presenter;
	/**
	 * 确认人
	 */
	private String confirmor;
	/**
	 * 预估报价
	 */
	private int estimateQuotation;
	/**
	 * 确认报价
	 */
	private int confirmQuotation;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	
	private long creatorId;

	public CRModel() {
	}

	public CRModel(long crId, long projectId, String crDescribe, int estimatedWorkload, String presenter,
			String confirmor, int estimateQuotation, int confirmQuotation, String remark, Date createTime,
			Date updateTime,long creatorId) {
		this.crId = crId;
		this.projectId = projectId;
		this.crDescribe = crDescribe;
		this.estimatedWorkload = estimatedWorkload;
		this.presenter = presenter;
		this.confirmor = confirmor;
		this.estimateQuotation = estimateQuotation;
		this.confirmQuotation = confirmQuotation;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.creatorId = creatorId;
	}

	/**
	 * @return the crId
	 */
	public long getCrId() {
		return crId;
	}

	/**
	 * @param crId
	 *            the crId to set
	 */
	public void setCrId(long crId) {
		this.crId = crId;
	}

	/**
	 * @return the projectCode
	 */
	public long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectCode
	 *            the projectCode to set
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the crDescribe
	 */
	public String getCrDescribe() {
		return crDescribe;
	}

	/**
	 * @param crDescribe
	 *            the crDescribe to set
	 */
	public void setCrDescribe(String crDescribe) {
		this.crDescribe = crDescribe;
	}

	/**
	 * @return the estimatedWorkload
	 */
	public int getEstimatedWorkload() {
		return estimatedWorkload;
	}

	/**
	 * @param estimatedWorkload
	 *            the estimatedWorkload to set
	 */
	public void setEstimatedWorkload(int estimatedWorkload) {
		this.estimatedWorkload = estimatedWorkload;
	}

	/**
	 * @return the presenter
	 */
	public String getPresenter() {
		return presenter;
	}

	/**
	 * @param presenter
	 *            the presenter to set
	 */
	public void setPresenter(String presenter) {
		this.presenter = presenter;
	}

	/**
	 * @return the confirmor
	 */
	public String getConfirmor() {
		return confirmor;
	}

	/**
	 * @param confirmor
	 *            the confirmor to set
	 */
	public void setConfirmor(String confirmor) {
		this.confirmor = confirmor;
	}

	/**
	 * @return the estimateQuotation
	 */
	public int getEstimateQuotation() {
		return estimateQuotation;
	}

	/**
	 * @param estimateQuotation
	 *            the estimateQuotation to set
	 */
	public void setEstimateQuotation(int estimateQuotation) {
		this.estimateQuotation = estimateQuotation;
	}

	/**
	 * @return the confirmQuotation
	 */
	public int getConfirmQuotation() {
		return confirmQuotation;
	}

	/**
	 * @param confirmQuotation
	 *            the confirmQuotation to set
	 */
	public void setConfirmQuotation(int confirmQuotation) {
		this.confirmQuotation = confirmQuotation;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the creatorId
	 */
	public long getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

}

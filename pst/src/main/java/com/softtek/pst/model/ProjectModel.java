package com.softtek.pst.model;

import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: projectModel
 * @Description: TODO
 * @author: cliff.fan
 * @date: Apr 6, 2016 9:33:14 AM
 */
public class ProjectModel {
	/**
	 * 
	 */
	/**
	 * 项目ID
	 */
	private long projectId;
	/**
	 * 项目编号
	 */
	private String projectCode;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目经理
	 */
	private String projectManager;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 内侧时间
	 */
	private Date medialTime;
	/**
	 * 交付时间
	 */
	private Date leadTime;
	/**
	 * 项目报价
	 */
	private int projectQuotation;
	/**
	 * 客户名称
	 */
	private String customerName;
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
	/**
	 * 创建者
	 */
	private long creatorId;
	/**
	 * 客户ID
	 */
	private long customerId;

	private String projectStatus;

	private String projectType;

	private int projectStatusNumber;

	private int projectTypeNumber;

	/**
	 * 成本支出
	 */
	private Double cost;
	/**
	 * 回款状态
	 */
	private String returnedStatus;
	
	private String implementManager;
	
	private int projectNature;
	
	private int outsourcingQuotation;
	
	private int crTotal;

	private List<CRModel> crList;
	
	private int projectQuotationTotal;
	
	private int receive;
	
	private int payment;
	
	private int unreceive;
	
	private int unpayment;

	public ProjectModel() {
	}


	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getMedialTime() {
		return medialTime;
	}

	public void setMedialTime(Date medialTime) {
		this.medialTime = medialTime;
	}

	public Date getLeadTime() {
		return leadTime;
	}

	public void setLeadTime(Date leadTime) {
		this.leadTime = leadTime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public int getProjectStatusNumber() {
		return projectStatusNumber;
	}

	public void setProjectStatusNumber(int projectStatusNumber) {
		this.projectStatusNumber = projectStatusNumber;
	}

	public int getProjectTypeNumber() {
		return projectTypeNumber;
	}

	public void setProjectTypeNumber(int projectTypeNumber) {
		this.projectTypeNumber = projectTypeNumber;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getReturnedStatus() {
		return returnedStatus;
	}

	public void setReturnedStatus(String returnedStatus) {
		this.returnedStatus = returnedStatus;
	}

	public List<CRModel> getCrList() {
		return crList;
	}

	public void setCrList(List<CRModel> crList) {
		this.crList = crList;
	}

	public String getImplementManager() {
		return implementManager;
	}

	public void setImplementManager(String implementManager) {
		this.implementManager = implementManager;
	}

	public int getProjectNature() {
		return projectNature;
	}

	public void setProjectNature(int projectNature) {
		this.projectNature = projectNature;
	}

	public int getCrTotal() {
		return crTotal;
	}

	public void setCrTotal(int crTotal) {
		this.crTotal = crTotal;
	}


	public int getProjectQuotation() {
		return projectQuotation;
	}


	public void setProjectQuotation(int projectQuotation) {
		this.projectQuotation = projectQuotation;
	}


	public int getOutsourcingQuotation() {
		return outsourcingQuotation;
	}


	public void setOutsourcingQuotation(int outsourcingQuotation) {
		this.outsourcingQuotation = outsourcingQuotation;
	}


	public int getProjectQuotationTotal() {
		return projectQuotationTotal;
	}


	public void setProjectQuotationTotal(int projectQuotationTotal) {
		this.projectQuotationTotal = projectQuotationTotal;
	}


	public int getReceive() {
		return receive;
	}


	public void setReceive(int receive) {
		this.receive = receive;
	}


	public int getPayment() {
		return payment;
	}


	public void setPayment(int payment) {
		this.payment = payment;
	}


	public int getUnreceive() {
		return unreceive;
	}


	public void setUnreceive(int unreceive) {
		this.unreceive = unreceive;
	}


	public int getUnpayment() {
		return unpayment;
	}


	public void setUnpayment(int unpayment) {
		this.unpayment = unpayment;
	}


}

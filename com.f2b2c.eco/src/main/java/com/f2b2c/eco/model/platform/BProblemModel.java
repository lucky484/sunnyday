package com.f2b2c.eco.model.platform;

import java.util.Date;

public class BProblemModel {
	
	private Integer id;
	private String problem;
	private String response;
	private Integer createBy;
	private Date createTime;
	private Integer updateBy;
	private Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Integer getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Problem [id=");
		builder.append(id);
		builder.append(", problem=");
		builder.append(problem);
		builder.append(", response=");
		builder.append(response);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append("]");
		return builder.toString();
	}

}

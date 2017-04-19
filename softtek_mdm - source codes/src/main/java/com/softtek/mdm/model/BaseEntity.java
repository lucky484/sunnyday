package com.softtek.mdm.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseEntity implements Serializable{
	private static final long serialVersionUID = -4473948289885586309L;

	private Date createTime;    //创建时间
	
	private Integer createBy;       //创建人
	
	private Date updateTime;    //修改时间
	
	private Integer updateBy;       //修改人
	
	private Date deleteTime;    //删除时间

	// 创建时间
    private String createDateStr;
    // 更新时间按
    private String updateDateStr;
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseEntity [createTime=");
		builder.append(createTime);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", deleteTime=");
		builder.append(deleteTime);
		builder.append(", createDateStr=");
		builder.append(createDateStr);
		builder.append(", updateDateStr=");
		builder.append(updateDateStr);
		builder.append("]");
		return builder.toString();
	}

	public String getCreateDateStr() {
		if(getCreateTime() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(getCreateTime());
		}
		return "";
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getUpdateDateStr() {
		if(getUpdateTime() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(getUpdateTime());
		}
		return "";
	}

	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	
}

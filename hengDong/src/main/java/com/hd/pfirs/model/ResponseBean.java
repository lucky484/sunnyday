package com.hd.pfirs.model;

import java.util.Arrays;
import java.util.List;

public class ResponseBean {
	private String state;
	private String remark;
	private List<String> compareResult;
	private List<FaceWebModel> list;
	private String compareBaseID;
	private String featureID;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public  List<String> getCompareResult() {
		return compareResult;
	}
	public void setCompareResult( List<String> compareResult) {
		this.compareResult = compareResult;
	}
	
	public List<FaceWebModel> getList() {
		return list;
	}
	public void setList(List<FaceWebModel> list) {
		this.list = list;
	}
	public String getCompareBaseID() {
		return compareBaseID;
	}
	public void setCompareBaseID(String compareBaseID) {
		this.compareBaseID = compareBaseID;
	}
	public String getFeatureID() {
		return featureID;
	}
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}
	@Override
	public String toString() {
		return "ResponseBean [state=" + state + ", remark=" + remark + ", compareResult=" + compareResult + ", list="
				+ list + ", compareBaseID=" + compareBaseID + ", featureID=" + featureID + "]";
	}
	
	
}

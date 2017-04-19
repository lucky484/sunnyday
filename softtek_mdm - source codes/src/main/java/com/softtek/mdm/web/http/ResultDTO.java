package com.softtek.mdm.web.http;

import java.util.List;
import java.util.Set;

import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.AppDepartmentAuthorization;
import com.softtek.mdm.model.AppVirtualGroupAuthorization;

/**
 * 结果返回
 * */
public class ResultDTO extends BaseDTO{
	
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	public String type = SUCCESS;

    public Object result;
    
    public String token;

    public String message;
    
    public List<AndroidDevicePolicy> policyList;
    
    public Set<Integer> userSet;
    
    /**
     * 部门架构
     */
    public String jsonStr;
    
    /**
     * 策略
     */
    public AndroidDevicePolicy currentPolicy;
    
    /**
     * 授权部门List
     */
    public List<AppDepartmentAuthorization> departList;
    
    /**
     * 授权虚拟组List
     */
    public List<AppVirtualGroupAuthorization> virtualList;
    
    /**
     * 已授权虚拟组List
     */
    public List<AppVirtualGroupAuthorization> firstVirtualList;
    
    public ResultDTO() {	
    }

    public List<AppDepartmentAuthorization> getDepartList() {
		return departList;
	}



	public void setDepartList(List<AppDepartmentAuthorization> departList) {
		this.departList = departList;
	}



	public List<AppVirtualGroupAuthorization> getVirtualList() {
		return virtualList;
	}



	public void setVirtualList(List<AppVirtualGroupAuthorization> virtualList) {
		this.virtualList = virtualList;
	}



	public ResultDTO(String type, Object result, String message,String token) {
        this.type = type;
        this.result = result;
        this.message = message;
        this.token = token;
    }

    public ResultDTO(Object result) {
        this.result = result;
    }

    public ResultDTO(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public ResultDTO(String type, Object result) {
        this.type = type;
        this.result = result;
    }
}

package com.softtek.mdm.util;

import java.util.Date;

@SuppressWarnings("rawtypes")
public class UserPolicyComparable implements Comparable{

    private Integer id;

    private Integer usersId;
    
    private Integer fatherId;
    
    private Integer androidDevicePolicyId;
    
    private long updateDate;
    
    private Date createDate;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUsersId() {
		return usersId;
	}
	
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	public Integer getFatherId() {
		return fatherId;
	}



	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}



	public Integer getAndroidDevicePolicyId() {
		return androidDevicePolicyId;
	}



	public void setAndroidDevicePolicyId(Integer androidDevicePolicyId) {
		this.androidDevicePolicyId = androidDevicePolicyId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(long updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public int compareTo(Object o) {
		UserPolicyComparable tmp = (UserPolicyComparable)o;
		int result = tmp.updateDate>updateDate?1:(tmp.updateDate==updateDate?0:-1);
		if(result==0){
			result = tmp.getId().toString().indexOf(0)>getId().toString().indexOf(0)?1:-1;
		}
		return result;
	}
}

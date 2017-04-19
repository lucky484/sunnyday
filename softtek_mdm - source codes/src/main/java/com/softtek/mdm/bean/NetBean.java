package com.softtek.mdm.bean;

/**
 * 上网统计bean
 * @author jane.hui
 *
 */
public class NetBean {

    /**
     * 主键id
     */
    private Integer id;
    
    /**
     * 用户
     */
    private String surfUsername;
    
    /**
     * 用户类型
     */
    private String surfUsertype;
    
    /**
     * 网站类型
     */
    private Integer websiteType;
    
    /**
     * 网站类型名称
     */
    private String websiteName;
    
    /**
     * 部门名称
     */
    private String departmentName;
    
    /**
     * 上网内容
     */
    private String conetent;
    
    /**
     * 网址
     */
    private String surfUrl;
    
    /**
     * 时间
     */
    private String surftime;
    
    /**
     * 浏览网页数量
     */
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurfUsername() {
        return surfUsername;
    }

    public void setSurfUsername(String surfUsername) {
        this.surfUsername = surfUsername;
    }

    public String getSurfUsertype() {
        return surfUsertype;
    }

    public void setSurfUsertype(String surfUsertype) {
        this.surfUsertype = surfUsertype;
    }
    
	public Integer getWebsiteType() {
		return websiteType;
	}

	public void setWebsiteType(Integer websiteType) {
		this.websiteType = websiteType;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getDepartmentName() {
        return departmentName;
    }
 
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

	public String getSurfUrl() {
		return surfUrl;
	}

	public void setSurfUrl(String surfUrl) {
		this.surfUrl = surfUrl;
	}

	public String getSurftime() {
		return surftime;
	}

	public void setSurftime(String surftime) {
		this.surftime = surftime;
	}

	public String getConetent() {
		return conetent;
	}

	public void setConetent(String conetent) {
		this.conetent = conetent;
	}
}
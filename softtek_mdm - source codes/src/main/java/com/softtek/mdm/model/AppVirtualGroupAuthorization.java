package com.softtek.mdm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 应用虚拟组授权表
 * @author jane.hui
 *
 */
public class AppVirtualGroupAuthorization implements Serializable {
	
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 虚拟组外键
     * @mbggenerated
     */
    private Integer virtualGroupId;

    /**
     * 应用id
     * @mbggenerated
     */
    private Integer appId;
    
    /**
     * 虚拟组名称
     */
    private String name;

    /**
     * 父虚拟集合
     */
    private Integer fatherId;
    
    /**
     * 父虚拟集合名称
     */
    private String fatherName;
    
    /**
     * 虚拟组
     */
    private List<AppVirtualGroupAuthorization> list;
    
    /**
     * 是否有允许安装权限
     * @mbggenerated
     */
    private String isInstall;

    /**
     * 创建时间
     * @mbggenerated
     */
    private Date createDate;

    /**
     * 创建者
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 最后更新时间
     * @mbggenerated
     */
    private Date updateDate;

    /**
     * 最后更新者
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 是否删除
     * @mbggenerated
     */
    private Date deleteTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVirtualGroupId() {
        return virtualGroupId;
    }

    public void setVirtualGroupId(Integer virtualGroupId) {
        this.virtualGroupId = virtualGroupId;
    }

    public Integer getAppId() {
        return appId;
    }
    
    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getIsInstall() {
        return isInstall;
    }

    public void setIsInstall(String isInstall) {
        this.isInstall = isInstall == null ? null : isInstall.trim();
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	
	public List<AppVirtualGroupAuthorization> getList() {
		return list;
	}

	public void setList(List<AppVirtualGroupAuthorization> list) {
		this.list = list;
	}

	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AppVirtualGroupAuthorization other = (AppVirtualGroupAuthorization) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVirtualGroupId() == null ? other.getVirtualGroupId() == null : this.getVirtualGroupId().equals(other.getVirtualGroupId()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getIsInstall() == null ? other.getIsInstall() == null : this.getIsInstall().equals(other.getIsInstall()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVirtualGroupId() == null) ? 0 : getVirtualGroupId().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getIsInstall() == null) ? 0 : getIsInstall().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", virtualGroupId=").append(virtualGroupId);
        sb.append(", appId=").append(appId);
        sb.append(", isInstall=").append(isInstall);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append("]");
        return sb.toString();
    }
}
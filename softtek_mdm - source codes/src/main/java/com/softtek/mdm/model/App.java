package com.softtek.mdm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 应用表
 * @author jane.hui
 *
 */
public class App implements Serializable {
	
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 机构外键
     * @mbggenerated
     */
    private Integer orgId;

    /**
     * 发布类型(0.android应用   1.iOS应用)
     * @mbggenerated
     */
    private String releaseType;

    /**
     * APK文件地址
     * @mbggenerated
     */
    private String apkFile;

    /**
     * 应用ID
     * @mbggenerated
     */
    private String appId;

    /**
     * 图标地址
     * @mbggenerated
     */
    private String iconPath;
    
    /**
     * 图片名称
     */
    private String icon;

    /**
     * 应用名称
     * @mbggenerated
     */
    private String appName;

    /**
     * 版本
     * @mbggenerated
     */
    private String appVersion;

    /**
     * 签名证书
     * @mbggenerated
     */
    private String signatureCertificate;

    /**
     * 作者
     * @mbggenerated
     */
    private String author;

    /**
     * 应用说明
     * @mbggenerated
     */
    private String appDescription;

    /**
     * 最低版本要求
     * @mbggenerated
     */
    private String minimumVersion;

    /**
     * 上下架状态
     * @mbggenerated
     */
    private String state;
    
    /**
     * 装机数
     */
    private Integer appCount;

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
     * 更新时间
     * @mbggenerated
     */
    private Date updateDate;

    /**
     * 更新者
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 删除时间
     * @mbggenerated
     */
    private Date deleteTime;
    
    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 未授权设备数
     */
    private Integer notAuthCount;
    
    /**
     * 文件大小
     */
    private String fileSize;
    
    /**
     * iTunesStoreID
     */
    private String trackId;
    
    /**
     * 格式化后价格
     */
    private String formattedPrice;
    
    /**
     * 用户列表
     */
    private List<UserModel> userList;
    
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType == null ? null : releaseType.trim();
    }

    public String getApkFile() {
        return apkFile;
    }

    public void setApkFile(String apkFile) {
        this.apkFile = apkFile == null ? null : apkFile.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath == null ? null : iconPath.trim();
    }

    public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }

    public String getSignatureCertificate() {
        return signatureCertificate;
    }

    public void setSignatureCertificate(String signatureCertificate) {
        this.signatureCertificate = signatureCertificate == null ? null : signatureCertificate.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription == null ? null : appDescription.trim();
    }

    public String getMinimumVersion() {
        return minimumVersion;
    }

    public void setMinimumVersion(String minimumVersion) {
        this.minimumVersion = minimumVersion == null ? null : minimumVersion.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getAppCount() {
		return appCount;
	}

	public void setAppCount(Integer appCount) {
	    this.appCount = appCount == null ? 0 : appCount;
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
    
    public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getNotAuthCount() {
		return notAuthCount;
	}

	public void setNotAuthCount(Integer notAuthCount) {
		this.notAuthCount = notAuthCount;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public List<UserModel> getUserList() {
		return userList;
	}

	public void setUserList(List<UserModel> userList) {
		this.userList = userList;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
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
        App other = (App) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getReleaseType() == null ? other.getReleaseType() == null : this.getReleaseType().equals(other.getReleaseType()))
            && (this.getApkFile() == null ? other.getApkFile() == null : this.getApkFile().equals(other.getApkFile()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getIconPath() == null ? other.getIconPath() == null : this.getIconPath().equals(other.getIconPath()))
            && (this.getAppName() == null ? other.getAppName() == null : this.getAppName().equals(other.getAppName()))
            && (this.getAppVersion() == null ? other.getAppVersion() == null : this.getAppVersion().equals(other.getAppVersion()))
            && (this.getSignatureCertificate() == null ? other.getSignatureCertificate() == null : this.getSignatureCertificate().equals(other.getSignatureCertificate()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getAppDescription() == null ? other.getAppDescription() == null : this.getAppDescription().equals(other.getAppDescription()))
            && (this.getMinimumVersion() == null ? other.getMinimumVersion() == null : this.getMinimumVersion().equals(other.getMinimumVersion()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState())) 
            && (this.getAppCount() == null ? other.getAppCount() == null : this.getAppCount().equals(other.getAppCount()))             
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize())) 
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
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getReleaseType() == null) ? 0 : getReleaseType().hashCode());
        result = prime * result + ((getApkFile() == null) ? 0 : getApkFile().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getIconPath() == null) ? 0 : getIconPath().hashCode());
        result = prime * result + ((getAppName() == null) ? 0 : getAppName().hashCode());
        result = prime * result + ((getAppVersion() == null) ? 0 : getAppVersion().hashCode());
        result = prime * result + ((getSignatureCertificate() == null) ? 0 : getSignatureCertificate().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getAppDescription() == null) ? 0 : getAppDescription().hashCode());
        result = prime * result + ((getMinimumVersion() == null) ? 0 : getMinimumVersion().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getAppCount() == null) ? 0 : getAppCount().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
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
        sb.append(", orgId=").append(orgId);
        sb.append(", releaseType=").append(releaseType);
        sb.append(", apkFile=").append(apkFile);
        sb.append(", appId=").append(appId);
        sb.append(", iconPath=").append(iconPath);
        sb.append(", appName=").append(appName);
        sb.append(", appVersion=").append(appVersion);
        sb.append(", signatureCertificate=").append(signatureCertificate);
        sb.append(", author=").append(author);
        sb.append(", appDescription=").append(appDescription);
        sb.append(", minimumVersion=").append(minimumVersion);
        sb.append(", state=").append(state);
        sb.append(", appCount=").append(appCount);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append("]");
        return sb.toString();
    }
}
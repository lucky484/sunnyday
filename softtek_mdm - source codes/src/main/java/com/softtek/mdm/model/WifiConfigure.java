package com.softtek.mdm.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * WIFI配置
 * @author jane.hui
 *
 */
public class WifiConfigure implements Serializable {
	
    /**
     * 主键
     */
    private Integer id;

    /**
     * 服务级标识符(SSID)
     */
    private String ssid;

    /**
     * 是否自动加入网络
     */
    private String isAutojoin;

    /**
     * 是否隐藏网络
     */
    private String isHiddennetwork;

    /**
     * 安全类型
     */
    private String securityType;

    /**
     * 密码
     */
    private String password;

    /**
     * eap
     */
    private String eapMethod;

    /**
     * 阶段2身份验证
     */
    private String stageAuthentication;

    /**
     * CA证书
     */
    private String caCertificate;

    /**
     * 用户证书
     */
    private String userCertificate;

    /**
     * 身份
     */
    private String identity;

    /**
     * 匿名身份
     */
    private String anonymousIdentity;

    /**
     * 对应AndroidDevicePolicy表的主键id(策略表外键)
     */
    private Integer androidDevicePolicyId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建者
     */
    private Integer createUser;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 更新者
     */
    private Integer updateUser;

    /**
     * 删除时间
     */
    private Date deleteTime;
    
	// 创建时间
    @SuppressWarnings("unused")
	private String createDateStr;
    
    // 更新时间按
    @SuppressWarnings("unused")
	private String updateDateStr;
    
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid == null ? null : ssid.trim();
    }

    public String getIsAutojoin() {
        return isAutojoin;
    }

    public void setIsAutojoin(String isAutojoin) {
        this.isAutojoin = isAutojoin == null ? null : isAutojoin.trim();
    }

    public String getIsHiddennetwork() {
        return isHiddennetwork;
    }

    public void setIsHiddennetwork(String isHiddennetwork) {
        this.isHiddennetwork = isHiddennetwork == null ? null : isHiddennetwork.trim();
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType == null ? null : securityType.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEapMethod() {
        return eapMethod;
    }

    public void setEapMethod(String eapMethod) {
        this.eapMethod = eapMethod == null ? null : eapMethod.trim();
    }

    public String getStageAuthentication() {
        return stageAuthentication;
    }

    public void setStageAuthentication(String stageAuthentication) {
        this.stageAuthentication = stageAuthentication == null ? null : stageAuthentication.trim();
    }

    public String getCaCertificate() {
        return caCertificate;
    }

    public void setCaCertificate(String caCertificate) {
        this.caCertificate = caCertificate == null ? null : caCertificate.trim();
    }

    public String getUserCertificate() {
        return userCertificate;
    }

    public void setUserCertificate(String userCertificate) {
        this.userCertificate = userCertificate == null ? null : userCertificate.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getAnonymousIdentity() {
        return anonymousIdentity;
    }

    public void setAnonymousIdentity(String anonymousIdentity) {
        this.anonymousIdentity = anonymousIdentity == null ? null : anonymousIdentity.trim();
    }

    public Integer getAndroidDevicePolicyId() {
        return androidDevicePolicyId;
    }

    public void setAndroidDevicePolicyId(Integer androidDevicePolicyId) {
        this.androidDevicePolicyId = androidDevicePolicyId;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
    
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}	

	public String getCreateDateStr() {
		if(getCreateDate() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(getCreateDate());
		}
		return "";
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getUpdateDateStr() {
		if(getUpdateDate() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(getUpdateDate());
		}
		return "";
	}

	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}
}
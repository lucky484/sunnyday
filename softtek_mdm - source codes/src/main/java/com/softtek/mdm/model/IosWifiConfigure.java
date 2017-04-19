package com.softtek.mdm.model;

import java.io.Serializable;
import java.util.Date;

/**
 * IosWIFI配置表
 * @author jane.hui
 *
 */
public class IosWifiConfigure implements Serializable {
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 服务级标识符(SSID)
     * @mbggenerated
     */
    private String ssid;

    /**
     * 是否自动加入(1.是  0.否)
     * @mbggenerated
     */
    private String isAutojoin;

    /**
     * 是否隐藏网络(1.是  0.否)
     * @mbggenerated
     */
    private String isHiddennetwork;

    /**
     * 安全类型()
     * @mbggenerated
     */
    private String securityType;

    /**
     * 密码
     * @mbggenerated
     */
    private String password;

    /**
     * EAP方法
     * @mbggenerated
     */
    private String eapMethod;

    /**
     * 阶段2身份验证
     * @mbggenerated
     */
    private String stageAuthentication;

    /**
     * CA证书
     * @mbggenerated
     */
    private String caCertificate;

    /**
     * 用户证书
     * @mbggenerated
     */
    private String userCertificate;

    /**
     * 身份
     * @mbggenerated
     */
    private String identity;

    /**
     * 匿名身份
     * @mbggenerated
     */
    private String anonymousIdentity;

    /**
     * 代理
     * @mbggenerated
     */
    private String agent;
    
    private String agentServer;
    
    private String agentPort;
    
    private String agentAppraisal;
    
    private String agentPassword;
    
    private String agentUrl;
    
    /**
     * ios策略
     */
    private Integer iosDevicePolicyId;

    public String getAgentServer() {
		return agentServer;
	}

	public void setAgentServer(String agentServer) {
		this.agentServer = agentServer;
	}

	public String getAgentPort() {
		return agentPort;
	}

	public void setAgentPort(String agentPort) {
		this.agentPort = agentPort;
	}

	public String getAgentAppraisal() {
		return agentAppraisal;
	}

	public void setAgentAppraisal(String agentAppraisal) {
		this.agentAppraisal = agentAppraisal;
	}

	public String getAgentPassword() {
		return agentPassword;
	}

	public void setAgentPassword(String agentPassword) {
		this.agentPassword = agentPassword;
	}

	public String getAgentUrl() {
		return agentUrl;
	}

	public void setAgentUrl(String agentUrl) {
		this.agentUrl = agentUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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
    
    public Integer getIosDevicePolicyId() {
		return iosDevicePolicyId;
	}

	public void setIosDevicePolicyId(Integer iosDevicePolicyId) {
		this.iosDevicePolicyId = iosDevicePolicyId;
	}

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

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
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
        IosWifiConfigure other = (IosWifiConfigure) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSsid() == null ? other.getSsid() == null : this.getSsid().equals(other.getSsid()))
            && (this.getIsAutojoin() == null ? other.getIsAutojoin() == null : this.getIsAutojoin().equals(other.getIsAutojoin()))
            && (this.getIsHiddennetwork() == null ? other.getIsHiddennetwork() == null : this.getIsHiddennetwork().equals(other.getIsHiddennetwork()))
            && (this.getSecurityType() == null ? other.getSecurityType() == null : this.getSecurityType().equals(other.getSecurityType()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getEapMethod() == null ? other.getEapMethod() == null : this.getEapMethod().equals(other.getEapMethod()))
            && (this.getStageAuthentication() == null ? other.getStageAuthentication() == null : this.getStageAuthentication().equals(other.getStageAuthentication()))
            && (this.getCaCertificate() == null ? other.getCaCertificate() == null : this.getCaCertificate().equals(other.getCaCertificate()))
            && (this.getUserCertificate() == null ? other.getUserCertificate() == null : this.getUserCertificate().equals(other.getUserCertificate()))
            && (this.getIdentity() == null ? other.getIdentity() == null : this.getIdentity().equals(other.getIdentity()))
            && (this.getAnonymousIdentity() == null ? other.getAnonymousIdentity() == null : this.getAnonymousIdentity().equals(other.getAnonymousIdentity()))
            && (this.getAgent() == null ? other.getAgent() == null : this.getAgent().equals(other.getAgent()))
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
        result = prime * result + ((getSsid() == null) ? 0 : getSsid().hashCode());
        result = prime * result + ((getIsAutojoin() == null) ? 0 : getIsAutojoin().hashCode());
        result = prime * result + ((getIsHiddennetwork() == null) ? 0 : getIsHiddennetwork().hashCode());
        result = prime * result + ((getSecurityType() == null) ? 0 : getSecurityType().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getEapMethod() == null) ? 0 : getEapMethod().hashCode());
        result = prime * result + ((getStageAuthentication() == null) ? 0 : getStageAuthentication().hashCode());
        result = prime * result + ((getCaCertificate() == null) ? 0 : getCaCertificate().hashCode());
        result = prime * result + ((getUserCertificate() == null) ? 0 : getUserCertificate().hashCode());
        result = prime * result + ((getIdentity() == null) ? 0 : getIdentity().hashCode());
        result = prime * result + ((getAnonymousIdentity() == null) ? 0 : getAnonymousIdentity().hashCode());
        result = prime * result + ((getAgent() == null) ? 0 : getAgent().hashCode());
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
        sb.append(", ssid=").append(ssid);
        sb.append(", isAutojoin=").append(isAutojoin);
        sb.append(", isHiddennetwork=").append(isHiddennetwork);
        sb.append(", securityType=").append(securityType);
        sb.append(", password=").append(password);
        sb.append(", eapMethod=").append(eapMethod);
        sb.append(", stageAuthentication=").append(stageAuthentication);
        sb.append(", caCertificate=").append(caCertificate);
        sb.append(", userCertificate=").append(userCertificate);
        sb.append(", identity=").append(identity);
        sb.append(", anonymousIdentity=").append(anonymousIdentity);
        sb.append(", agent=").append(agent);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append("]");
        return sb.toString();
    }
}
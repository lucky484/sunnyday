package com.softtek.mdm.model;

public class ClientManagerModel extends BaseEntity{
   
	private Integer id;
	
	private Integer orgId;
	
	private String clientIdName;
	
	private String clientAppName;
	
	private String belongOrg;
	
	private String clientVersion;
	
	private String clientByte;
	
	private Integer isUpgrade;
	
	private String applyPlatform;
	
	private String supportDevice;
	
	private String signatureCertificate;
	
	private String remark;
	
	private String downloadUrl;
	
	private String imageUrl;
	
	private String fileName;

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

	public String getClientIdName() {
		return clientIdName;
	}

	public void setClientIdName(String clientIdName) {
		this.clientIdName = clientIdName;
	}

	public String getClientAppName() {
		return clientAppName;
	}

	public void setClientAppName(String clientAppName) {
		this.clientAppName = clientAppName;
	}

	public String getBelongOrg() {
		return belongOrg;
	}

	public void setBelongOrg(String belongOrg) {
		this.belongOrg = belongOrg;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getClientByte() {
		return clientByte;
	}

	public void setClientByte(String clientByte) {
		this.clientByte = clientByte;
	}

	public Integer getIsUpgrade() {
		return isUpgrade;
	}

	public void setIsUpgrade(Integer isUpgrade) {
		this.isUpgrade = isUpgrade;
	}

	public String getApplyPlatform() {
		return applyPlatform;
	}

	public void setApplyPlatform(String applyPlatform) {
		this.applyPlatform = applyPlatform;
	}

	public String getSupportDevice() {
		return supportDevice;
	}

	public void setSupportDevice(String supportDevice) {
		this.supportDevice = supportDevice;
	}

	public String getSignatureCertificate() {
		return signatureCertificate;
	}

	public void setSignatureCertificate(String signatureCertificate) {
		this.signatureCertificate = signatureCertificate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}

package com.softtek.mdm.model;

/**
 * 流量统计Model
 * 
 * @author jzhu
 *
 */
public class FluxModel extends BaseEntity{

	private Integer id;
	private OrganizationModel organization;
	private String device_name;
	private String device_type;
	private String sn;
	private String esnorimei;
	private String device_status;
    private String flux;
	private String departName;
	private String totalFlux;
	private String real_name;
	private UserModel user;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public OrganizationModel getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getEsnorimei() {
		return esnorimei;
	}
	public void setEsnorimei(String esnorimei) {
		this.esnorimei = esnorimei;
	}
	public String getDevice_status() {
		return device_status;
	}
	public void setDevice_status(String device_status) {
		this.device_status = device_status;
	}

    public String getFlux() {
        return flux;
    }

    public void setFlux(String flux) {
        this.flux = flux;
    }

    public String getTotalFlux() {
		return totalFlux;
	}
	public void setTotalFlux(String totalFlux) {
		this.totalFlux = totalFlux;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	
}

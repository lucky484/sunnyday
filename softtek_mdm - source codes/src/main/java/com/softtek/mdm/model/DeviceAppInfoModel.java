package com.softtek.mdm.model;
/**
 * device_app_info
 * @author color.wu
 *
 */
public class DeviceAppInfoModel {

	/**
     * 自动编号
     */
	private Integer id;
	
    /**
     * 应用名称
     */
	private String name;
	
    /**
     * 应用id
     */
	private String appid;
	
    /**
     * 应用版本
     */
	private String app_version;
	
    /**
     * 应用状态
     */
	private String app_status;
	
    /**
     * @注意：此字段忽略即可
     */
	private String app_belong;
		
    /**
     * 应用流量
     */
    private String app_flux;
	
    /**
     * 所属的设备
     */
	private DeviceBasicInfoModel belongDevice;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}

    public String getApp_flux() {
        return app_flux;
    }

    public void setApp_flux(String app_flux) {
        this.app_flux = app_flux;
    }

    public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public String getApp_status() {
		return app_status;
	}
	public void setApp_status(String app_status) {
		this.app_status = app_status;
	}
	public String getApp_belong() {
		return app_belong;
	}
	public void setApp_belong(String app_belong) {
		this.app_belong = app_belong;
	}
	public DeviceBasicInfoModel getBelongDevice() {
		return belongDevice;
	}
	public void setBelongDevice(DeviceBasicInfoModel belongDevice) {
		this.belongDevice = belongDevice;
	}
	
}

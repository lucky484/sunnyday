package com.softtek.mdm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 设备和用户的基本信息表 对应于数据库表device_basic_info
 * 
 * @author color.wu
 *
 */
@SuppressWarnings("serial")
public class DeviceBasicInfoModel extends BaseEntity{

	/**
	 * 真实姓名
	 */
	private String real_name;
	
	private Integer id;
	
	/**
     * 机构id
     */
	private Integer orgId;
	
    /**
     * 设备名称
     */
	private String device_name;
	
    /**
     * 设备类型android,ios
     */
	private String device_type;
	
    /**
     * 序列号
     */
	private String sn;
	
    /**
     * esn/imei号
     */
	private String esnorimei;
	
    /**
     * 归属
     */
	private String belong;
	
    /**
     * 设备状态
     */
    private String device_status; // 2-监控中 3-待监控 1-注销中 4-托管中
	
    /**
     * 所属用户
     */
	private UserModel user;
	
    /**
     * 系统版本
     */
	private String os_version;
	
    /**
     * 手机号
     */
	private String phone_number;
	
    /**
     * 客户端版本
     */
	private String app_versoin;
	
    /**
     * 最后登录ip
     */
	private String ip;
	
    /**
     * 电源状态
     */
	private String power;
	
    /**
     * 分辨率
     */
	private String resolution;
	/**
	 * UDID
	 */
	private String udid;
	
    /**
     * 存储容量
     */
	private String capacity;
	
    /**
     * 最后登录时间
     */
	private Date last_login_time;
	
    /**
     * 设备唯一标识
     */
	private String device_unique_id;
	
	
	private String longitude;
	
	private String latitude;
	
	private String deviceToken;
	
	private String iosUuid;
	
	/**
	 * 是否已经安装过描述文件，0-未安装 1-已安装
	 */
	private String isProfile;
	
	
	
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getAdroid_id() {
		return device_unique_id;
	}
	public void setAdroid_id(String adroid_id) {
		this.device_unique_id = adroid_id;
	}
	private String last_login_time_str;
	
	private String last_collection_time_str;
	
    private String sum_flux;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceBasicInfoModel [id=");
		builder.append(id);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", device_name=");
		builder.append(device_name);
		builder.append(", device_type=");
		builder.append(device_type);
		builder.append(", sn=");
		builder.append(sn);
		builder.append(", esnorimei=");
		builder.append(esnorimei);
		builder.append(", belong=");
		builder.append(belong);
		builder.append(", device_status=");
		builder.append(device_status);
		builder.append(", user=");
		builder.append(user);
		builder.append(", os_version=");
		builder.append(os_version);
		builder.append(", phone_number=");
		builder.append(phone_number);
		builder.append(", app_versoin=");
		builder.append(app_versoin);
		builder.append(", ip=");
		builder.append(ip);
		builder.append(", power=");
		builder.append(power);
		builder.append(", resolution=");
		builder.append(resolution);
		builder.append(", udid=");
		builder.append(udid);
		builder.append(", capacity=");
		builder.append(capacity);
		builder.append(", last_login_time=");
		builder.append(last_login_time);
		builder.append(", device_unique_id=");
		builder.append(device_unique_id);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", deviceToken=");
		builder.append(deviceToken);
		builder.append(", iosUuid=");
		builder.append(iosUuid);
		builder.append(", last_login_time_str=");
		builder.append(last_login_time_str);
		builder.append(", last_collection_time_str=");
		builder.append(last_collection_time_str);
		builder.append(", flux=");
		builder.append(flux);
		builder.append(", imsi=");
		builder.append(imsi);
		builder.append(", deviceNetworkStatus=");
		builder.append(deviceNetworkStatus);
		builder.append(", deviceLegalListModel=");
		builder.append(deviceLegalListModel);
		builder.append(", last_collection_time=");
		builder.append(last_collection_time);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", irr_status=");
		builder.append(irr_status);
		builder.append(", lost_status=");
		builder.append(lost_status);
		builder.append(", break_status=");
		builder.append(break_status);
		builder.append(", visit_status=");
		builder.append(visit_status);
		builder.append(", time=");
		builder.append(time);
		builder.append(", sdCard=");
		builder.append(sdCard);
		builder.append(", update_time=");
		builder.append(update_time);
		builder.append("]");
		return builder.toString();
	}
	private String flux;
	
	private String imsi;
	public String getLast_login_time_str() {
		if(this.last_login_time != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(this.last_login_time);
		}
		return "";
	}
	public String getLast_collection_time_str() {
		if(this.last_collection_time != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(this.last_collection_time);
		}
		return "";
	}
	

    /**
     * @return sum_flux
     */

    public String getSum_flux() {
        return sum_flux;
    }

    /**
     * @param sum_flux
     *            要设置的 sum_flux
     */

    public void setSum_flux(String sum_flux) {
        this.sum_flux = sum_flux;
    }

    /**
     * 最后采集时间
     */
    // 设备基本信息中的网络基本信息
	private DeviceNetworkStatusModel deviceNetworkStatus;
	
	private DeviceLegalListModel deviceLegalListModel;
	
	private Date last_collection_time;
	
    private Integer isActive;// 0-未激活 1-激活
	
    private Integer irr_status; // 0-设备未违规 1-违规
	
    private Integer lost_status; // 1-设备已丢失 0-未丢失
	
    private Integer break_status; // 1-已破解 0-未破解
	
    private Integer visit_status; // 0-访问锁定用户不能访问应用 1-引爆锁定，用户下次 访问时，触发炸毁客户端数据
                                  // 其他是为锁定
	
    private Float time; // 当前时间减去采集时间获得的小时数
    
    private String sdCard;
    
    /**
     * 更新时间
     */
    private Date update_time;

	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Date getLast_collection_time() {
		return last_collection_time;
	}
	public void setLast_collection_time(Date last_collection_time) {
		this.last_collection_time = last_collection_time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getDevice_status() {
		return device_status;
	}
	public void setDevice_status(String device_status) {
		this.device_status = device_status;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public String getOs_version() {
		return os_version;
	}
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getApp_versoin() {
		return app_versoin;
	}
	public void setApp_versoin(String app_versoin) {
		this.app_versoin = app_versoin;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public Date getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public Integer getIrr_status() {
		return irr_status;
	}
	public void setIrr_status(Integer irr_status) {
		this.irr_status = irr_status;
	}
	public Integer getLost_status() {
		return lost_status;
	}
	public void setLost_status(Integer lost_status) {
		this.lost_status = lost_status;
	}
	public Integer getBreak_status() {
		return break_status;
	}
	public void setBreak_status(Integer break_status) {
		this.break_status = break_status;
	}
	public Integer getVisit_status() {
		return visit_status;
	}
	public void setVisit_status(Integer visit_status) {
		this.visit_status = visit_status;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Float getTime() {
		if(getLast_collection_time() != null){
			Float time = (float)(new Date().getTime() - getLast_collection_time().getTime())/60000;
			return time;
		}
		return null;
	}
	public void setTime(Float time) {
		this.time = time;
	}
	public DeviceNetworkStatusModel getDeviceNetworkStatus() {
		return deviceNetworkStatus;
	}
	public void setDeviceNetworkStatus(DeviceNetworkStatusModel deviceNetworkStatus) {
		this.deviceNetworkStatus = deviceNetworkStatus;
	}
	public String getSdCard() {
		return sdCard;
	}
	public void setSdCard(String sdCard) {
		this.sdCard = sdCard;
	}
	public DeviceLegalListModel getDeviceLegalListModel() {
		return deviceLegalListModel;
	}
	public void setDeviceLegalListModel(DeviceLegalListModel deviceLegalListModel) {
		this.deviceLegalListModel = deviceLegalListModel;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getFlux() {
		return flux;
	}
	public void setFlux(String flux) {
		this.flux = flux;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getIosUuid() {
		return iosUuid;
	}
	public void setIosUuid(String iosUuid) {
		this.iosUuid = iosUuid;
	}
	public String getIsProfile() {
		return isProfile;
	}
	public void setIsProfile(String isProfile) {
		this.isProfile = isProfile;
	}
	
}

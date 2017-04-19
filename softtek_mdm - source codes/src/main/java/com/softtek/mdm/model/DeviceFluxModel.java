package com.softtek.mdm.model;

import java.util.Date;

/**
 * @Description 设备流量上传
 * @author josen.yang
 * @date Jan 6, 2017 11:09:45 AM
 */

public class DeviceFluxModel {
    /**
     * 关系id
     */
    private Integer id;

    /**
     * 机构ID
     */
    private Integer orgId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * sn号
     */
    private String sn;

    /**
     * imei号
     */
    private String esnorimei;

    /**
     * 设备状态
     */
    private String deviceStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 设备流量
     */
    private String flux;

    /**
     * 用户ID 对应Users表
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户名称
     */
    private String realName;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 设备信息表主键索引
     */
    private Integer deviceBasicInfoId;

    /**
     * 采集时间
     */
    private Date statisticalTime;

    /**
     * 是否异常
     */
    private Integer isAbnormal;

    /**
     * 是否上报过一场详情
     */
    private Integer isReport;


    /**
     * @return id
     */

    public Integer getId() {
        return id;
    }

    /**
     * @return realName
     */

    public String getRealName() {
        return realName;
    }

    /**
     * @param realName
     *            要设置的 realName
     */

    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @param id
     *            要设置的 id
     */

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return orgId
     */

    public Integer getOrgId() {
        return orgId;
    }

    /**
     * @return userName
     */

    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            要设置的 userName
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return phone
     */

    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            要设置的 phone
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @param orgId
     *            要设置的 orgId
     */

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    /**
     * @return deviceName
     */

    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName
     *            要设置的 deviceName
     */

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return deviceType
     */

    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType
     *            要设置的 deviceType
     */

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return sn
     */

    public String getSn() {
        return sn;
    }

    /**
     * @param sn
     *            要设置的 sn
     */

    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * @return esnorimei
     */

    public String getEsnorimei() {
        return esnorimei;
    }

    /**
     * @param esnorimei
     *            要设置的 esnorimei
     */

    public void setEsnorimei(String esnorimei) {
        this.esnorimei = esnorimei;
    }

    /**
     * @return deviceStatus
     */

    public String getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * @param deviceStatus
     *            要设置的 deviceStatus
     */

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    /**
     * @return createTime
     */

    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            要设置的 createTime
     */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    /**
     * @return flux
     */

    public String getFlux() {
        return flux;
    }

    /**
     * @param flux
     *            要设置的 flux
     */

    public void setFlux(String flux) {
        this.flux = flux;
    }

    /**
     * @return userId
     */

    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            要设置的 userId
     */

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return deviceBasicInfoId
     */

    public Integer getDeviceBasicInfoId() {
        return deviceBasicInfoId;
    }

    /**
     * @param deviceBasicInfoId
     *            要设置的 deviceBasicInfoId
     */

    public void setDeviceBasicInfoId(Integer deviceBasicInfoId) {
        this.deviceBasicInfoId = deviceBasicInfoId;
    }

    /**
     * @return statisticalTime
     */

    public Date getStatisticalTime() {
        return statisticalTime;
    }

    /**
     * @param statisticalTime
     *            要设置的 statisticalTime
     */

    public void setStatisticalTime(Date statisticalTime) {
        this.statisticalTime = statisticalTime;
    }

    /**
     * @return isAbnormal
     */

    public Integer getIsAbnormal() {
        return isAbnormal;
    }

    /**
     * @param isAbnormal
     *            要设置的 isAbnormal
     */

    public void setIsAbnormal(Integer isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    /**
     * @return isReport
     */

    public Integer getIsReport() {
        return isReport;
    }

    /**
     * @param isReport
     *            要设置的 isReport
     */

    public void setIsReport(Integer isReport) {
        this.isReport = isReport;
    }

    /**
     * Description
     * 
     * @return
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "DeviceFluxModel [id=" + id + ", orgId=" + orgId + ", deviceName=" + deviceName + ", deviceType="
                + deviceType + ", sn=" + sn + ", esnorimei=" + esnorimei + ", deviceStatus=" + deviceStatus
                + ", createTime=" + createTime + ", flux=" + flux + ", userId=" + userId + ", deviceBasicInfoId="
                + deviceBasicInfoId + ", statisticalTime=" + statisticalTime + ", isAbnormal=" + isAbnormal
                + ", isReport=" + isReport + ", getId()=" + getId() + ", getOrgId()=" + getOrgId()
                + ", getDeviceName()=" + getDeviceName() + ", getDeviceType()=" + getDeviceType() + ", getSn()="
                + getSn() + ", getEsnorimei()=" + getEsnorimei() + ", getDeviceStatus()=" + getDeviceStatus()
                + ", getCreateTime()=" + getCreateTime() + ", getFlux()=" + getFlux() + ", getUserId()=" + getUserId()
                + ", getDeviceBasicInfoId()=" + getDeviceBasicInfoId() + ", getStatisticalTime()="
                + getStatisticalTime() + ", getIsAbnormal()=" + getIsAbnormal() + ", getIsReport()=" + getIsReport()
                + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
                + "]";
    }

}

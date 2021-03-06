package com.softtek.mdm.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Webclip
 * @author jane.hui
 *
 */
public class Webclip implements Serializable {
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 标签
     * @mbggenerated
     */
    private String lable;

    /**
     * url地址
     * @mbggenerated
     */
    private String url;

    /**
     * 图标路径
     * @mbggenerated
     */
    private String iconPath;

    /**
     * 是否可删除
     * @mbggenerated
     */
    private String isRemove;

    /**
     * 是否预作图标
     * @mbggenerated
     */
    private String precomposeIcon;

    /**
     * 全屏幕
     * @mbggenerated
     */
    private String fullScreen;

    /**
     * ios设备策略外键
     * @mbggenerated
     */
    private Integer iosDevicePolicyId;

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
     * 序列号
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable == null ? null : lable.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath == null ? null : iconPath.trim();
    }

    public String getIsRemove() {
        return isRemove;
    }

    public void setIsRemove(String isRemove) {
        this.isRemove = isRemove == null ? null : isRemove.trim();
    }

    public String getPrecomposeIcon() {
        return precomposeIcon;
    }

    public void setPrecomposeIcon(String precomposeIcon) {
        this.precomposeIcon = precomposeIcon == null ? null : precomposeIcon.trim();
    }

    public String getFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(String fullScreen) {
        this.fullScreen = fullScreen == null ? null : fullScreen.trim();
    }

    public Integer getIosDevicePolicyId() {
        return iosDevicePolicyId;
    }

    public void setIosDevicePolicyId(Integer iosDevicePolicyId) {
        this.iosDevicePolicyId = iosDevicePolicyId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column webclip.create_date
     *
     * @param createDate the value for webclip.create_date
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column webclip.create_user
     *
     * @return the value of webclip.create_user
     *
     * @mbggenerated
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column webclip.create_user
     *
     * @param createUser the value for webclip.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column webclip.update_date
     *
     * @return the value of webclip.update_date
     *
     * @mbggenerated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column webclip.update_date
     *
     * @param updateDate the value for webclip.update_date
     *
     * @mbggenerated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column webclip.update_user
     *
     * @return the value of webclip.update_user
     *
     * @mbggenerated
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column webclip.update_user
     *
     * @param updateUser the value for webclip.update_user
     *
     * @mbggenerated
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column webclip.delete_time
     *
     * @return the value of webclip.delete_time
     *
     * @mbggenerated
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column webclip.delete_time
     *
     * @param deleteTime the value for webclip.delete_time
     *
     * @mbggenerated
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table webclip
     *
     * @mbggenerated
     */
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
        Webclip other = (Webclip) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLable() == null ? other.getLable() == null : this.getLable().equals(other.getLable()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getIconPath() == null ? other.getIconPath() == null : this.getIconPath().equals(other.getIconPath()))
            && (this.getIsRemove() == null ? other.getIsRemove() == null : this.getIsRemove().equals(other.getIsRemove()))
            && (this.getPrecomposeIcon() == null ? other.getPrecomposeIcon() == null : this.getPrecomposeIcon().equals(other.getPrecomposeIcon()))
            && (this.getFullScreen() == null ? other.getFullScreen() == null : this.getFullScreen().equals(other.getFullScreen()))
            && (this.getIosDevicePolicyId() == null ? other.getIosDevicePolicyId() == null : this.getIosDevicePolicyId().equals(other.getIosDevicePolicyId()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table webclip
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLable() == null) ? 0 : getLable().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getIconPath() == null) ? 0 : getIconPath().hashCode());
        result = prime * result + ((getIsRemove() == null) ? 0 : getIsRemove().hashCode());
        result = prime * result + ((getPrecomposeIcon() == null) ? 0 : getPrecomposeIcon().hashCode());
        result = prime * result + ((getFullScreen() == null) ? 0 : getFullScreen().hashCode());
        result = prime * result + ((getIosDevicePolicyId() == null) ? 0 : getIosDevicePolicyId().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table webclip
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", lable=").append(lable);
        sb.append(", url=").append(url);
        sb.append(", iconPath=").append(iconPath);
        sb.append(", isRemove=").append(isRemove);
        sb.append(", precomposeIcon=").append(precomposeIcon);
        sb.append(", fullScreen=").append(fullScreen);
        sb.append(", iosDevicePolicyId=").append(iosDevicePolicyId);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append("]");
        return sb.toString();
    }
}
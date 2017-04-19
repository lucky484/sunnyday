package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 运费模板
 * @author jane.hui
 *
 */
public class BFreightTemplate implements Serializable {
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 模板名称
     * @mbggenerated
     */
    private String name;

    /**
     * B端用户id
     * @mbggenerated
     */
    private Integer bUserId;

    /**
     * 宝贝地址
     * @mbggenerated
     */
    private Integer babyAddress;

    /**
     * 发货时间
     * @mbggenerated
     */
    private String deliveryTime;

    /**
     * 是否免费包邮
     * @mbggenerated
     */
    private String isFreeDelivery;

    /**
     * 
     * @mbggenerated
     */
    private String valuationMethod;

    /**
     * 指定条件方法包邮
     * @mbggenerated
     */
    private String isConditionsFreeDelivery;

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
     * 序列化
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * 运费模板list
     */
    private List<BCityFreightModel> cityFreightList;
    
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
        this.name = name == null ? null : name.trim();
    }

    public Integer getbUserId() {
        return bUserId;
    }

    public void setbUserId(Integer bUserId) {
        this.bUserId = bUserId;
    }

    public Integer getBabyAddress() {
        return babyAddress;
    }

    public void setBabyAddress(Integer babyAddress) {
        this.babyAddress = babyAddress;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime == null ? null : deliveryTime.trim();
    }

    public String getIsFreeDelivery() {
        return isFreeDelivery;
    }

    public void setIsFreeDelivery(String isFreeDelivery) {
        this.isFreeDelivery = isFreeDelivery == null ? null : isFreeDelivery.trim();
    }

    public String getValuationMethod() {
        return valuationMethod;
    }

    public void setValuationMethod(String valuationMethod) {
        this.valuationMethod = valuationMethod == null ? null : valuationMethod.trim();
    }

    public String getIsConditionsFreeDelivery() {
        return isConditionsFreeDelivery;
    }

    public void setIsConditionsFreeDelivery(String isConditionsFreeDelivery) {
        this.isConditionsFreeDelivery = isConditionsFreeDelivery == null ? null : isConditionsFreeDelivery.trim();
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
    
    public List<BCityFreightModel> getCityFreightList() {
        return cityFreightList;
    }

    public void setCityFreightList(List<BCityFreightModel> cityFreightList) {
        this.cityFreightList = cityFreightList;
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
        BFreightTemplate other = (BFreightTemplate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getbUserId() == null ? other.getbUserId() == null : this.getbUserId().equals(other.getbUserId()))
            && (this.getBabyAddress() == null ? other.getBabyAddress() == null : this.getBabyAddress().equals(other.getBabyAddress()))
            && (this.getDeliveryTime() == null ? other.getDeliveryTime() == null : this.getDeliveryTime().equals(other.getDeliveryTime()))
            && (this.getIsFreeDelivery() == null ? other.getIsFreeDelivery() == null : this.getIsFreeDelivery().equals(other.getIsFreeDelivery()))
            && (this.getValuationMethod() == null ? other.getValuationMethod() == null : this.getValuationMethod().equals(other.getValuationMethod()))
            && (this.getIsConditionsFreeDelivery() == null ? other.getIsConditionsFreeDelivery() == null : this.getIsConditionsFreeDelivery().equals(other.getIsConditionsFreeDelivery()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getbUserId() == null) ? 0 : getbUserId().hashCode());
        result = prime * result + ((getBabyAddress() == null) ? 0 : getBabyAddress().hashCode());
        result = prime * result + ((getDeliveryTime() == null) ? 0 : getDeliveryTime().hashCode());
        result = prime * result + ((getIsFreeDelivery() == null) ? 0 : getIsFreeDelivery().hashCode());
        result = prime * result + ((getValuationMethod() == null) ? 0 : getValuationMethod().hashCode());
        result = prime * result + ((getIsConditionsFreeDelivery() == null) ? 0 : getIsConditionsFreeDelivery().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", bUserId=").append(bUserId);
        sb.append(", babyAddress=").append(babyAddress);
        sb.append(", deliveryTime=").append(deliveryTime);
        sb.append(", isFreeDelivery=").append(isFreeDelivery);
        sb.append(", valuationMethod=").append(valuationMethod);
        sb.append(", isConditionsFreeDelivery=").append(isConditionsFreeDelivery);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateUser=").append(updateUser);
        sb.append("]");
        return sb.toString();
    }
}
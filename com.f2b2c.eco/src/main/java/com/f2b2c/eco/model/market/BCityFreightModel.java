package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

/**
 * 每个城市对应的运费
 * @author jane.hui
 *
 */
public class BCityFreightModel implements Serializable {
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 发货城市
     * @mbggenerated
     */
    private String deliveryCity;

    /**
     * 首件
     * @mbggenerated
     */
    private Integer first;

    /**
     * 首件运费
     * @mbggenerated
     */
    private Integer firstFreight;

    /**
     * 续件
     * @mbggenerated
     */
    private Integer continueQuantity;

    /**
     * 续件运费
     * @mbggenerated
     */
    private Integer continueFreight;

    /**
     * 是否默认
     * @mbggenerated
     */
    private String isDefault;

    /**
     * 运费模板外键
     * @mbggenerated
     */
    private Integer freightTemplateId;

    /**
     * 发货模式
     * @mbggenerated
     */
    private String deliveryMode;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity == null ? null : deliveryCity.trim();
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getFirstFreight() {
        return firstFreight;
    }

    public void setFirstFreight(Integer firstFreight) {
        this.firstFreight = firstFreight;
    }

    public Integer getContinueQuantity() {
        return continueQuantity;
    }

    public void setContinueQuantity(Integer continueQuantity) {
        this.continueQuantity = continueQuantity;
    }

    public Integer getContinueFreight() {
        return continueFreight;
    }

    public void setContinueFreight(Integer continueFreight) {
        this.continueFreight = continueFreight;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    public Integer getFreightTemplateId() {
        return freightTemplateId;
    }

    public void setFreightTemplateId(Integer freightTemplateId) {
        this.freightTemplateId = freightTemplateId;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode == null ? null : deliveryMode.trim();
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
        BCityFreightModel other = (BCityFreightModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDeliveryCity() == null ? other.getDeliveryCity() == null : this.getDeliveryCity().equals(other.getDeliveryCity()))
            && (this.getFirst() == null ? other.getFirst() == null : this.getFirst().equals(other.getFirst()))
            && (this.getFirstFreight() == null ? other.getFirstFreight() == null : this.getFirstFreight().equals(other.getFirstFreight()))
            && (this.getContinueQuantity() == null ? other.getContinueQuantity() == null : this.getContinueQuantity().equals(other.getContinueQuantity()))
            && (this.getContinueFreight() == null ? other.getContinueFreight() == null : this.getContinueFreight().equals(other.getContinueFreight()))
            && (this.getIsDefault() == null ? other.getIsDefault() == null : this.getIsDefault().equals(other.getIsDefault()))
            && (this.getFreightTemplateId() == null ? other.getFreightTemplateId() == null : this.getFreightTemplateId().equals(other.getFreightTemplateId()))
            && (this.getDeliveryMode() == null ? other.getDeliveryMode() == null : this.getDeliveryMode().equals(other.getDeliveryMode()))
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
        result = prime * result + ((getDeliveryCity() == null) ? 0 : getDeliveryCity().hashCode());
        result = prime * result + ((getFirst() == null) ? 0 : getFirst().hashCode());
        result = prime * result + ((getFirstFreight() == null) ? 0 : getFirstFreight().hashCode());
        result = prime * result + ((getContinueQuantity() == null) ? 0 : getContinueQuantity().hashCode());
        result = prime * result + ((getContinueFreight() == null) ? 0 : getContinueFreight().hashCode());
        result = prime * result + ((getIsDefault() == null) ? 0 : getIsDefault().hashCode());
        result = prime * result + ((getFreightTemplateId() == null) ? 0 : getFreightTemplateId().hashCode());
        result = prime * result + ((getDeliveryMode() == null) ? 0 : getDeliveryMode().hashCode());
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
        sb.append(", deliveryCity=").append(deliveryCity);
        sb.append(", first=").append(first);
        sb.append(", firstFreight=").append(firstFreight);
        sb.append(", continueQuantity=").append(continueQuantity);
        sb.append(", continueFreight=").append(continueFreight);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", freightTemplateId=").append(freightTemplateId);
        sb.append(", deliveryMode=").append(deliveryMode);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateUser=").append(updateUser);
        sb.append("]");
        return sb.toString();
    }
}
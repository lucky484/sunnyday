package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

/**
 * 指定条件发货
 * @author jane.hui
 *
 */
public class BConditionsFreeDeliveryModel implements Serializable {
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
     * 发货模式
     * @mbggenerated
     */
    private String deliveryMode;

    /**
     * 条件类型
     * @mbggenerated
     */
    private String conditionType;

    /**
     * 数量
     * @mbggenerated
     */
    private Integer quantity;

    /**
     * 金额
     * @mbggenerated
     */
    private Integer money;

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

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity == null ? null : deliveryCity.trim();
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode == null ? null : deliveryMode.trim();
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType == null ? null : conditionType.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
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
        BConditionsFreeDeliveryModel other = (BConditionsFreeDeliveryModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDeliveryCity() == null ? other.getDeliveryCity() == null : this.getDeliveryCity().equals(other.getDeliveryCity()))
            && (this.getDeliveryMode() == null ? other.getDeliveryMode() == null : this.getDeliveryMode().equals(other.getDeliveryMode()))
            && (this.getConditionType() == null ? other.getConditionType() == null : this.getConditionType().equals(other.getConditionType()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
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
        result = prime * result + ((getDeliveryMode() == null) ? 0 : getDeliveryMode().hashCode());
        result = prime * result + ((getConditionType() == null) ? 0 : getConditionType().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
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
        sb.append(", deliveryMode=").append(deliveryMode);
        sb.append(", conditionType=").append(conditionType);
        sb.append(", quantity=").append(quantity);
        sb.append(", money=").append(money);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateUser=").append(updateUser);
        sb.append("]");
        return sb.toString();
    }
}
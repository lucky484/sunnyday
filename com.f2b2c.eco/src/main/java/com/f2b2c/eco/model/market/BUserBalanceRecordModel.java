package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

/**
 * B端用户帐户操作记录表
 * @author jane.hui
 *
 */
public class BUserBalanceRecordModel implements Serializable {
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 操作类型(1.充值 2.支付购买 3.退款 4.提现 5.分佣)
     * @mbggenerated
     */
    private String operateType;

    /**
     * 操作内容
     * @mbggenerated
     */
    private String operateContent;

    /**
     * B段用户帐户id
     * @mbggenerated
     */
    private Integer bUserBalanceId;

    /**
     * 标识[1.增加(+)   2.减去(-)]
     * @mbggenerated
     */
    private String tag;

    /**
     * 金额
     * @mbggenerated
     */
    private Integer money;

    /**
     * 创建时间
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private String strCreateDate;
    
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent == null ? null : operateContent.trim();
    }
    
    public Integer getbUserBalanceId() {
        return bUserBalanceId;
    }

    public void setbUserBalanceId(Integer bUserBalanceId) {
        this.bUserBalanceId = bUserBalanceId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getStrCreateDate() {
        return strCreateDate;
    }

    public void setStrCreateDate(String strCreateDate) {
        this.strCreateDate = strCreateDate;
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
        BUserBalanceRecordModel other = (BUserBalanceRecordModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOperateType() == null ? other.getOperateType() == null : this.getOperateType().equals(other.getOperateType()))
            && (this.getOperateContent() == null ? other.getOperateContent() == null : this.getOperateContent().equals(other.getOperateContent()))
            && (this.getbUserBalanceId() == null ? other.getbUserBalanceId() == null : this.getbUserBalanceId().equals(other.getbUserBalanceId()))
            && (this.getTag() == null ? other.getTag() == null : this.getTag().equals(other.getTag()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOperateType() == null) ? 0 : getOperateType().hashCode());
        result = prime * result + ((getOperateContent() == null) ? 0 : getOperateContent().hashCode());
        result = prime * result + ((getbUserBalanceId() == null) ? 0 : getbUserBalanceId().hashCode());
        result = prime * result + ((getTag() == null) ? 0 : getTag().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", operateType=").append(operateType);
        sb.append(", operateContent=").append(operateContent);
        sb.append(", bUserBalanceId=").append(bUserBalanceId);
        sb.append(", tag=").append(tag);
        sb.append(", money=").append(money);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}
package com.f2b2c.eco.model.market;

import java.io.Serializable;

/**
 * B端用户帐户余额表
 * @author jane.hui
 *
 */
public class BUserBalanceModel implements Serializable {
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * B端用户id
     * @mbggenerated
     */
    private Integer bUserId;

    /**
     * 账户余额
     * @mbggenerated
     */
    private Integer accountBalance;

    /**
     * 可用佣金
     */
    private Integer commission;
    
    /**
     * 冻结佣金
     */
    private Integer totalCommission;
    
    /**
     * 版本
     * @mbggenerated
     */
    private Integer version;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getbUserId() {
        return bUserId;
    }

    public void setbUserId(Integer bUserId) {
        this.bUserId = bUserId;
    }

    public Integer getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Integer accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }
    
    public Integer getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Integer totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
        BUserBalanceModel other = (BUserBalanceModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getbUserId() == null ? other.getbUserId() == null : this.getbUserId().equals(other.getbUserId()))
            && (this.getAccountBalance() == null ? other.getAccountBalance() == null : this.getAccountBalance().equals(other.getAccountBalance()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getbUserId() == null) ? 0 : getbUserId().hashCode());
        result = prime * result + ((getAccountBalance() == null) ? 0 : getAccountBalance().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bUserId=").append(bUserId);
        sb.append(", accountBalance=").append(accountBalance);
        sb.append(", version=").append(version);
        sb.append("]");
        return sb.toString();
    }
}
package com.f2b2c.eco.model.market;

import java.io.Serializable;

/**
 * C端用户帐户表
 * @author jane.hui
 *
 */
public class CUserBalanceModel implements Serializable {
    
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * C端用户id
     * @mbggenerated
     */
    private Integer cUserId;

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
     * 版本号
     * @mbggenerated
     */
    private Integer version;

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

    public Integer getcUserId() {
        return cUserId;
    }

    public void setcUserId(Integer cUserId) {
        this.cUserId = cUserId;
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
        CUserBalanceModel other = (CUserBalanceModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getcUserId() == null ? other.getcUserId() == null : this.getcUserId().equals(other.getcUserId()))
            && (this.getAccountBalance() == null ? other.getAccountBalance() == null : this.getAccountBalance().equals(other.getAccountBalance()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getcUserId() == null) ? 0 : getcUserId().hashCode());
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
        sb.append(", cUserId=").append(cUserId);
        sb.append(", accountBalance=").append(accountBalance);
        sb.append(", version=").append(version);
        sb.append("]");
        return sb.toString();
    }
}
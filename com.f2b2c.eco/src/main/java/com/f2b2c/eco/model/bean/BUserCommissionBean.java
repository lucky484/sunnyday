package com.f2b2c.eco.model.bean;

/**
 * 用户佣金Bean
 * @author jane.hui
 *
 */
public class BUserCommissionBean {

    /**
     * 主键id
     */
    private Integer id;
    
    /**
     * 用户外键
     */
    private Integer userId;
    
    /**
     * 合计金额
     */
    private Integer money;

    /**
     * 可用余额
     */
    private Integer commission;
    
    /**
     * 版本号
     */
    private Integer version;

    /**
     * 标识(1.已计算佣金  0.未计算佣金)
     */
    private String isCalculated;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getIsCalculated() {
        return isCalculated;
    }

    public void setIsCalculated(String isCalculated) {
        this.isCalculated = isCalculated;
    }
}
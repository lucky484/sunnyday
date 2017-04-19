package com.f2b2c.eco.status;

/**
 * 钱包结果操作记录表
 * @author jane.hui
 *
 */
public enum CRechardOrderStatusEnum {

    /**
     * 待付款
     */
    WAIT_FOR_PAY("0"),
    
    /**
     * 已付款
     */
    ACCOUNT_PAID("1");
    
    private String displayName;
    
    private CRechardOrderStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
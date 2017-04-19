package com.f2b2c.eco.status;

/**
 * 钱包结果操作记录表
 * @author jane.hui
 *
 */
public enum WalletResultEnum {

    /**
     * 标志错误
     */
    FAILED("failure"),
    
    /**
     * 成功
     */
    SUCCESS("success");
    
    private String displayName;
    
    private WalletResultEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
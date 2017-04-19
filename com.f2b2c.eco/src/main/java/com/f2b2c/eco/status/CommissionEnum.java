package com.f2b2c.eco.status;

/**
 * 是否参与佣金计算 0:不参与 1：参与
 * @author jane.hui
 *
 */
public enum CommissionEnum {
    
    /** 参与佣金计算 */
    YES("1"),

    /** 不参与佣金计算*/
    NO("0");
    
    private String displayName;
    
    private CommissionEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}

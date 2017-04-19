package com.f2b2c.eco.status;

/**
 * 是否已计算过佣金(1.计算过 0.未计算)
 * @author jane.hui
 *
 */
public enum CalculateEnum {
    
    /** 啟用 */
    YES("1"),

    /** 禁用 */
    NO("0");
    
    private String displayName;
    
    private CalculateEnum(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return this.displayName;
    }
}
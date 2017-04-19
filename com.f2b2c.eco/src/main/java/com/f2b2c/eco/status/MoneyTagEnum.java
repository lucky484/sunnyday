package com.f2b2c.eco.status;

/**
 * 金额操作标识符
 * @author jane.hui
 *
 */
public enum MoneyTagEnum {
    
    /** 加 */
    ADD("1"),

    /** 减 */
    SUBTRACTION("2");
    
    private String displayName;
    
    private MoneyTagEnum (String displayName){
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return this.displayName;
    }
}

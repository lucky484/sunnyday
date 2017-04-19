package com.f2b2c.eco.status;

/**
 * 金额操作枚举类
 * @author jane.hui
 *
 */
public enum MoneyOperateEnum {
    
    /** 充值 */
    RECHARD("1"),

    /** 支付购买 */
    PURCHASE("2"),
    
    /** 退款 */
    BACKDRAW("3"),
    
    /** 提现 */
    WITHDRAWALS("4"),
    
    /** 分佣 */
    BROKERAGE("5"),
	
	/**佣金转出到余额 */
    TRANSFERTOBALANCE("6"),
    
	/**每个月1号计算佣金扣除税*/
    MONTHLY_COMMISSION_CALCULATION("7"),;
    
    private String displayName;
    
    private MoneyOperateEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
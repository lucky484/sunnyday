package com.f2b2c.eco.status;

/**
 * 生成订单号的前缀类别
 * @author jane.hui
 *
 */
public enum SequenceType {
	/** F端 */
	F,

	/** B端 */
	B,

	/** C端 */
	C,
	/**合并订单号*/
	M,
	/**充值*/
	R;
}

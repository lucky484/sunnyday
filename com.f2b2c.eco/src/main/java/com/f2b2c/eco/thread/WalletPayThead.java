package com.f2b2c.eco.thread;

import java.util.Date;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.f2b2c.eco.dao.market.BSalesOrderDao;
import com.f2b2c.eco.dao.market.CUserBalanceDao;
import com.f2b2c.eco.dao.market.CUserBalanceRecordDao;
import com.f2b2c.eco.dao.market.CWalletPaymentRecordDao;
import com.f2b2c.eco.model.market.CUserBalanceModel;
import com.f2b2c.eco.model.market.CUserBalanceRecordModel;
import com.f2b2c.eco.model.market.CWalletPaymentRecordModel;
import com.f2b2c.eco.status.MoneyOperateEnum;
import com.f2b2c.eco.status.MoneyTagEnum;
import com.f2b2c.eco.status.WalletResultEnum;
import com.f2b2c.eco.util.BalanceOperateUtil;

public class WalletPayThead implements Callable<Boolean>{
    
	 @Autowired
     private CUserBalanceDao cUserBalanceDao;
	 
	 @Autowired
     private BSalesOrderDao bSalesOrderDao;
	 
     @Autowired
     private CUserBalanceRecordDao cUserBalanceRecordDao;
	    
     @Autowired
     private CWalletPaymentRecordDao walletPaymentRecordDao;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 交易金额
	 */
	private Integer totalFee;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	
	public WalletPayThead(Integer userId, Integer totalFee, String orderNo) {
		this.userId = userId;
		this.totalFee = totalFee;
		this.orderNo = orderNo;
	}


	@Override
	public Boolean call() throws Exception {
		boolean result = false;
		synchronized (this) {
			CUserBalanceModel balance = cUserBalanceDao.selectByCUserId(userId);
	        CUserBalanceModel userBalance = new CUserBalanceModel();
	        if(balance != null){
	        	if(balance.getAccountBalance() > totalFee){
	        		 System.out.println(balance.getAccountBalance());
	        		 userBalance.setcUserId(userId);
	                 userBalance.setAccountBalance(BalanceOperateUtil.minus(balance.getAccountBalance(), totalFee));
	                 userBalance.setId(balance.getId());
	                 int size = cUserBalanceDao.updateByPrimaryKeyAndVersion(userBalance);
	                 if(size > 0){
	                	 result = true;
	                 }
	        	}
	        }
	        if(result){
	        	bSalesOrderDao.updateOrderStatusByOrderNo(orderNo); //修改订单状态
	        	// 插入用户帐户余额支付记录表
	        	CUserBalanceRecordModel cUserBalanceRecord = new CUserBalanceRecordModel();
	        	cUserBalanceRecord.setcUserBalanceId(userBalance.getId());
	        	cUserBalanceRecord.setMoney(totalFee);
	        	cUserBalanceRecord.setOperateContent("钱包支付操作成功");
	        	cUserBalanceRecord.setOperateType(MoneyOperateEnum.PURCHASE.toString());
	        	cUserBalanceRecord.setTag(MoneyTagEnum.SUBTRACTION.toString());
	        	cUserBalanceRecord.setCreateTime(new Date());
	        	cUserBalanceRecordDao.insertSelective(cUserBalanceRecord);
	        	
	        	// 插入钱包支付操作记录表
	        	CWalletPaymentRecordModel walletPaymentRecord = new CWalletPaymentRecordModel();
	        	walletPaymentRecord.setOutTradeNo(orderNo);
	        	walletPaymentRecord.setTotalFee(String.valueOf(totalFee));
	        	walletPaymentRecord.setResult(WalletResultEnum.SUCCESS.toString());
	        	walletPaymentRecord.setCreateTime(new Date());
	        	walletPaymentRecordDao.insertSelective(walletPaymentRecord);
	        }
		}
		return result;
	}


}

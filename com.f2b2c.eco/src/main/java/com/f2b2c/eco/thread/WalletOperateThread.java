package com.f2b2c.eco.thread;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.f2b2c.eco.controller.platform.f2b.FKindController;
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

/**
 * 钱包操作线程类
 * @author jane.hui
 */
public class WalletOperateThread extends Thread
{
    /**
     * 日志工具类
     */
    private static final Logger logger = LoggerFactory.getLogger(WalletOperateThread.class);
    
    @Autowired
    private CUserBalanceDao cUserBalanceDao;
    
    @Autowired
    private CUserBalanceRecordDao cUserBalanceRecordDao;
    
    @Autowired
    private CWalletPaymentRecordDao walletPaymentRecordDao;
    
    @Autowired
    private BSalesOrderDao bSalesOrderDao;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 交易金额
     */
    private Integer totalFee;
    
    /**
     * 商户订单号
     */
    private String  outTradeNo;
    
    /**
     * 钱包操作类线程
     * @param userId：用户id
     * @param totalFee:交易金额
     * @param outTradeNo：商品订单号
     */
    public WalletOperateThread(Integer userId,Integer totalFee,String outTradeNo)
    {
        this.userId = userId;
        this.totalFee = totalFee;
        this.outTradeNo = outTradeNo;
    }
    
    @Override
    public synchronized void run()
    {
        CUserBalanceModel balance = cUserBalanceDao.selectByCUserId(userId);
        CUserBalanceModel userBalance = new CUserBalanceModel();
        if(balance!=null&&balance.getId()!=null){
            userBalance.setVersion(balance.getVersion());
            if(balance.getAccountBalance()<totalFee){
                logger.info("订单号"+outTradeNo+":钱包余额不足");
            } else {
                userBalance.setcUserId(userId);
                userBalance.setAccountBalance(BalanceOperateUtil.minus(balance.getAccountBalance(), totalFee));
                userBalance.setId(balance.getId());
                int size = cUserBalanceDao.updateByPrimaryKeyAndVersion(userBalance);
                if(size==0){
                    logger.info("订单号"+outTradeNo+":钱包支付失败");
                }
            }
        } else {
            userBalance.setVersion(balance.getVersion()+1);
            userBalance.setAccountBalance(totalFee);
            userBalance.setcUserId(userId);
            int size = cUserBalanceDao.insertSelective(userBalance);
            if(size==0){
                logger.info("订单号"+outTradeNo+":钱包支付失败");
            }
        }
        
        /**
         * 更新订单状态
         */
        bSalesOrderDao.updateOrderStatusByMergeNo(outTradeNo);
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
        walletPaymentRecord.setOutTradeNo(outTradeNo);
        walletPaymentRecord.setTotalFee(String.valueOf(totalFee));
        walletPaymentRecord.setResult(WalletResultEnum.SUCCESS.toString());
        walletPaymentRecord.setCreateTime(new Date());
        walletPaymentRecordDao.insertSelective(walletPaymentRecord);
        System.out.println("钱包支付操作失败");
    }

}
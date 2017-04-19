package com.f2b2c.eco.service.platform.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.f2b2c.eco.dao.market.BUserBalanceDao;
import com.f2b2c.eco.dao.market.BUserBalanceRecordDao;
import com.f2b2c.eco.model.market.BUserBalanceModel;
import com.f2b2c.eco.model.market.BUserBalanceRecordModel;
import com.f2b2c.eco.service.platform.BAccountService;
import com.f2b2c.eco.status.MoneyOperateEnum;
import com.f2b2c.eco.util.BalanceOperateUtil;

/**
 *  账户余额管理
 * @author jane.hui
 *
 */
@Transactional
@Service("bAccountService")
public class BAccountServiceImpl implements BAccountService{
    
    @Autowired
    private BUserBalanceDao bUserBalanceDao;
    
    @Autowired
    private BUserBalanceRecordDao bUserBalanceRecordDao;

    /**
     * 返回操作余额结果
     */
    @Override
    public int saveBalance(Integer userId, Integer totalFee, String outTradeNo,String operateType,String operateContent,String tag) {
        BUserBalanceModel balance = bUserBalanceDao.selectByBUserId(userId);
        // 如果BUserBalance=0则代表插入更新用户余额表失败，如果不为空则返回该对象id
        Integer bUserId = 0;
        if(balance!=null&&balance.getId()!=null){
            if(MoneyOperateEnum.RECHARD.toString().equals(operateType)||MoneyOperateEnum.BROKERAGE.toString().equals(operateType)){
                bUserId = saveUserBalance(balance.getVersion(),BalanceOperateUtil.add(balance.getAccountBalance(), totalFee) , userId, balance.getId());
                if(bUserId==0){
                    return 400;
                }
            } else if(MoneyOperateEnum.PURCHASE.toString().equals(operateType)){
                if(balance.getAccountBalance()<totalFee){
                    return 401;
                } else {
                    bUserId = saveUserBalance(balance.getVersion(), BalanceOperateUtil.minus(balance.getAccountBalance(), totalFee), userId, balance.getId());
                    if(bUserId==0){
                        return 400;
                    }
                }
            }
        } else {
            // 插入用户帐户余额表字段
            bUserId = saveUserBalance(0, totalFee, userId, null);
            if(bUserId==0){
                return 400;
            }
        }
        
        // 用户帐户余额支付记录表
        BUserBalanceRecordModel bUserBalanceRecord = new BUserBalanceRecordModel();
        bUserBalanceRecord.setbUserBalanceId(bUserId);
        bUserBalanceRecord.setMoney(totalFee);
        bUserBalanceRecord.setOperateContent(operateContent);
        bUserBalanceRecord.setOperateType(operateType);
        bUserBalanceRecord.setTag(tag);
        bUserBalanceRecord.setCreateTime(new Date());
        bUserBalanceRecordDao.insertSelective(bUserBalanceRecord);
        return 200;
    }
    
    /**
     * 保存用户余额
     * @param version:版本号
     * @param totalFee:金额
     * @param userId:用户外键
     * @param id:用户主键
     * @return 返回操作结果
     */
    @Override
    public int saveUserBalance(Integer version,Integer totalFee,Integer userId,Integer id){
        BUserBalanceModel userBalance = new BUserBalanceModel();
        userBalance.setVersion(version);
        userBalance.setAccountBalance(totalFee);
        userBalance.setbUserId(userId);
        int size = 0;
        if(id!=null){
            userBalance.setId(id);
            size = bUserBalanceDao.updateByPrimaryKeyAndVersion(userBalance);
        } else {
            // 插入账户余额表字段
            size = bUserBalanceDao.insertSelective(userBalance);
        }
        // 如果插入或者更新操作不为0 则返回userBalance的id回去
        if(size!=0){
            return userBalance.getId();
        }
        return size;
    }
}
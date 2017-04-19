package com.f2b2c.eco.service.market.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.f2b2c.eco.dao.market.BSalesOrderDao;
import com.f2b2c.eco.dao.market.BUserBalanceDao;
import com.f2b2c.eco.dao.market.BUserBalanceRecordDao;
import com.f2b2c.eco.dao.market.CUserBalanceDao;
import com.f2b2c.eco.dao.market.CUserBalanceRecordDao;
import com.f2b2c.eco.dao.market.CUserDao;
import com.f2b2c.eco.dao.market.CWalletPaymentRecordDao;
import com.f2b2c.eco.dao.market.ReturnPayDao;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.BUserBalanceModel;
import com.f2b2c.eco.model.market.BUserBalanceRecordModel;
import com.f2b2c.eco.model.market.CUserBalanceModel;
import com.f2b2c.eco.model.market.CUserBalanceRecordModel;
import com.f2b2c.eco.model.market.CUserModel;
import com.f2b2c.eco.model.market.CWalletPaymentRecordModel;
import com.f2b2c.eco.service.market.AccountService;
import com.f2b2c.eco.share.pay.alipay.util.MoneyUtil;
import com.f2b2c.eco.status.CommissionEnum;
import com.f2b2c.eco.status.MoneyOperateEnum;
import com.f2b2c.eco.status.MoneyTagEnum;
import com.f2b2c.eco.status.RecommendTypeEnum;
import com.f2b2c.eco.status.WalletResultEnum;
import com.f2b2c.eco.util.BalanceOperateUtil;
import jodd.util.StringUtil;

/**
 *  账户余额管理
 * @author jane.hui
 *
 */
@Transactional
@Service("accountService")
public class AccountServiceImpl implements AccountService{

    /**
     * 日志记录器
     */
    private Logger logger=LoggerFactory.getLogger(AccountServiceImpl.class);
    
    @Autowired
    private CUserBalanceDao cUserBalanceDao;
    
    @Autowired
    private CUserBalanceRecordDao cUserBalanceRecordDao;
    
    @Autowired
    private CWalletPaymentRecordDao walletPaymentRecordDao;
    
    @Autowired
    private BSalesOrderDao bSalesOrderDao;
    
    @Autowired
    private CUserDao cUserDao;
    
    @Autowired
    private BUserBalanceDao bUserBalanceDao;
    
    @Autowired
    private BUserBalanceRecordDao bUserBalanceRecordDao;
    
    @Autowired
    private ReturnPayDao returnPayDao;
    
    
    
    /**
     * 支付业务
     */
    @Override
    public ApiResultModel<String> walletPay(Integer userId,Integer totalFee,String outTradeNo) {
        ApiResultModel<String> result = new ApiResultModel<String>();
        int size = 0;
        // 获取余额操作结果
        int state = saveBalance(userId, totalFee, outTradeNo,MoneyTagEnum.SUBTRACTION.toString(),"用户购买商品消费了"+MoneyUtil.fromFenToYuan(totalFee.toString()) +"元",MoneyOperateEnum.PURCHASE.toString());
        if(state==200){
            /**
             * 更新订单状态
             */
            size = bSalesOrderDao.updateOrderStatusByMergeNo(outTradeNo);
            if(size != 0){
                // 钱包支付操作记录表
                // 插入钱包支付操作记录表
                CWalletPaymentRecordModel walletPaymentRecord = new CWalletPaymentRecordModel();
                walletPaymentRecord.setOutTradeNo(outTradeNo);
                walletPaymentRecord.setTotalFee(String.valueOf(totalFee));
                walletPaymentRecord.setResult(WalletResultEnum.SUCCESS.toString());
                walletPaymentRecord.setCreateTime(new Date());
                walletPaymentRecordDao.insertSelective(walletPaymentRecord);
            } else {
                result.setMsg("订单号"+outTradeNo+":钱包支付失败");
                result.setStatus(400);
                return result;
            }
        } else if(state==400){
            result.setMsg("订单号"+outTradeNo+":钱包支付失败");
            result.setStatus(400);
            return result;
        } else {
            result.setMsg("订单号"+outTradeNo+":钱包余额不足");
            result.setStatus(401);
            return result;
        }
        result.setMsg("订单号"+outTradeNo+":钱包支付成功");
        result.setStatus(200);
        return result;
    }
    
    
    
    /**
     * 根据用户id获取账户余额
     * @param userId:用户外键
     * @return 返回账户余额
     */
    @Override
    public Integer getAccountBalance(Integer userId) {
        CUserBalanceModel cUserBalance = cUserBalanceDao.selectByCUserId(userId);
        if(cUserBalance!=null){
            return cUserBalance.getAccountBalance();
        }
        return 0;
    }
    
    public int walletPayTo(Integer userId,Integer totalFee,String outTradeNo) {
        CUserBalanceModel balance = cUserBalanceDao.selectByCUserId(userId);
        CUserBalanceModel userBalance = new CUserBalanceModel();
        Integer status = null;
        if(balance!=null&&balance.getId()!=null){
            userBalance.setVersion(balance.getVersion());
            if(balance.getAccountBalance()<totalFee){
                status = 0;
            } else {
                userBalance.setcUserId(userId);
                userBalance.setAccountBalance(BalanceOperateUtil.minus(balance.getAccountBalance(), totalFee));
                userBalance.setId(balance.getId());
                int size = cUserBalanceDao.updateByPrimaryKeyAndVersion(userBalance);
                if(size == 0){
                    status = 1;
                }else{
                    status = 2;
                }
            }
        } else {
            userBalance.setVersion(balance.getVersion()+1);
            userBalance.setAccountBalance(totalFee);
            userBalance.setcUserId(userId);
            int size = cUserBalanceDao.insertSelective(userBalance);
            if(size == 0){
                status = 1;
            }else{
                status = 2;
            }
        }
        if(status == 2){
            /**
             * 更新订单状态
             */
            bSalesOrderDao.updateOrderStatusByOrderNo(outTradeNo); //修改订单状态
            
            // 用户帐户余额支付记录表
            CUserBalanceRecordModel cUserBalanceRecord = new CUserBalanceRecordModel();
            cUserBalanceRecord.setcUserBalanceId(userBalance.getId());
            cUserBalanceRecord.setMoney(totalFee);
            cUserBalanceRecord.setOperateContent("用户购买商品消费了"+MoneyUtil.fromFenToYuan(totalFee.toString()) +"元");
            cUserBalanceRecord.setOperateType(MoneyOperateEnum.PURCHASE.toString());
            cUserBalanceRecord.setTag(MoneyTagEnum.SUBTRACTION.toString());
            cUserBalanceRecord.setCreateTime(new Date());
            cUserBalanceRecordDao.insertSelective(cUserBalanceRecord);
            
            // 钱包支付操作记录表
            // 插入钱包支付操作记录表
            CWalletPaymentRecordModel walletPaymentRecord = new CWalletPaymentRecordModel();
            walletPaymentRecord.setOutTradeNo(outTradeNo);
            walletPaymentRecord.setTotalFee(String.valueOf(totalFee));
            walletPaymentRecord.setResult(WalletResultEnum.SUCCESS.toString());
            walletPaymentRecord.setCreateTime(new Date());
            walletPaymentRecordDao.insertSelective(walletPaymentRecord);
        }
        return status;
    }

    /**
     * 计算佣金
     */
    @Override
    public int settleCommission(List<BSalesOrderDetailsModel> list,Integer userId,String orderNo) {
        BigDecimal totalBigDecimal = new BigDecimal(0);
        BigDecimal oneBigDecimal = new BigDecimal(100);
        BigDecimal totalPrice = null;
        BigDecimal sharePercent = null;
        if(userId!=null&&StringUtil.isNotEmpty(orderNo)){
            int size = 1;
            Integer returnPay = 0;
            Integer totalPay = 0;
            for (BSalesOrderDetailsModel detail : list) {
                if(CommissionEnum.YES.toString().equals(detail.getIsCommission())){
                    returnPay = returnPayDao.selectByDetailId(detail.getId());
                    if(returnPay==null){
                        returnPay = 0;
                    }
                    if(detail.getTotalPrice()>returnPay){
                        totalPay = detail.getTotalPrice() - returnPay;
                        totalPrice = new BigDecimal(totalPay);
                        if(StringUtil.isNotEmpty(detail.getSharePercent())){
                        sharePercent = new BigDecimal(detail.getSharePercent());
                            if(sharePercent!=null){
                                // 将总价格
                                totalBigDecimal = totalBigDecimal.add((totalPrice.multiply(sharePercent).divide(oneBigDecimal)));
                            }
                        }
                    }
                }
            }
            logger.error("订单号："+orderNo+"佣金金额为:"+totalBigDecimal);
            Integer total = totalBigDecimal.intValue();
            CUserModel user = cUserDao.select(userId);
            // 如果卖家算给推荐和平台的佣金小于1分钱则不计算佣金以及如果推荐人为空则佣金比例给平台
            if(total>=1&&user.getRecommendUser()!=null){
                // 此处0.6为推荐人获取的佣金钱
                BigDecimal commission = new BigDecimal(0.6);
                totalBigDecimal = totalBigDecimal.multiply(commission).setScale(0, RoundingMode.HALF_EVEN);
                if(RecommendTypeEnum.user.toString().equals(user.getRecommendType().toString())){
                    size = saveUserCommission(totalBigDecimal.intValue(), user);
                } else {
                    size = saveMerchantCommission(totalBigDecimal.intValue(), user);
                }
            }
            return size;
        } 
        return 1;
    }

    
    
    /**
     * 返回操作余额结果
     */
    @Override
    public int saveBalance(Integer userId, Integer totalFee, String outTradeNo,String operateType,String operateContent,String tag) {
        CUserBalanceModel balance = cUserBalanceDao.selectByCUserId(userId);
        // 如果cUserBalance=0则代表插入更新用户余额表失败，如果不为空则返回该对象id
        Integer cUserId = 0;
        if(balance!=null&&balance.getId()!=null){
            if(MoneyOperateEnum.RECHARD.toString().equals(operateType)||MoneyOperateEnum.BROKERAGE.toString().equals(operateType)){
                 cUserId = saveUserBalance(balance.getVersion(),BalanceOperateUtil.add(balance.getAccountBalance(), totalFee) , userId, balance.getId());
                if(cUserId==0){
                    return 400;
                }
            } else if(MoneyOperateEnum.PURCHASE.toString().equals(operateType)){
                if(balance.getAccountBalance()<totalFee){
                    return 401;
                } else {
                    cUserId = saveUserBalance(balance.getVersion(), BalanceOperateUtil.minus(balance.getAccountBalance(), totalFee), userId, balance.getId());
                    if(cUserId==0){
                        return 400;
                    }
                }
            }
        } else {
            // 插入用户帐户余额表字段
            cUserId = saveUserBalance(0, totalFee, userId, null);
            if(cUserId==0){
                return 400;
            }
        }
        
        // 用户帐户余额支付记录表
        CUserBalanceRecordModel cUserBalanceRecord = new CUserBalanceRecordModel();
        cUserBalanceRecord.setcUserBalanceId(cUserId);
        cUserBalanceRecord.setMoney(totalFee);
        cUserBalanceRecord.setOperateContent(operateContent);
        cUserBalanceRecord.setOperateType(operateType);
        cUserBalanceRecord.setTag(tag);
        cUserBalanceRecord.setCreateTime(new Date());
        cUserBalanceRecordDao.insertSelective(cUserBalanceRecord);
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
        CUserBalanceModel userBalance = new CUserBalanceModel();
        userBalance.setVersion(version);
        userBalance.setAccountBalance(totalFee);
        userBalance.setcUserId(userId);
        int size = 0;
        if(id!=null){
            userBalance.setId(id);
            size = cUserBalanceDao.updateByPrimaryKeyAndVersion(userBalance);
        } else {
            // 插入账户余额表字段
            size = cUserBalanceDao.insertSelective(userBalance);
        }
        // 如果插入或者更新操作不为0 则返回userBalance的id回去
        if(size!=0){
            return userBalance.getId();
        }
        return size;
    }

    /**
     * 保存用户分佣相关记录
     * @param total:佣金
     * @param userId:用户id
     * @return 返回分佣操作结果
     */
    @Override
    public int saveUserCommission(Integer total,CUserModel user) {
        int size = 0;
        CUserBalanceModel balance = cUserBalanceDao.selectByCUserId(user.getRecommendUser());
        CUserBalanceModel newBalance = null;
        if(balance==null){
            newBalance = new CUserBalanceModel();
            newBalance.setcUserId(user.getRecommendUser());
//          newBalance.setAccountBalance(total);
            newBalance.setTotalCommission(total);
            newBalance.setVersion(0);
            size = cUserBalanceDao.insertSelective(newBalance);
            if(size ==0){
                return size;
            }
        } else {
            newBalance = new CUserBalanceModel();
            newBalance.setId(balance.getId());
            newBalance.setcUserId(user.getRecommendUser());
//          newBalance.setAccountBalance(balance.getAccountBalance()+total);
            newBalance.setTotalCommission(balance.getTotalCommission()+total);
            newBalance.setVersion(balance.getVersion());
            size = cUserBalanceDao.updateByPrimaryKeyAndVersion(newBalance);
            if(size==0){
                return 0;
            }
        }
        //插入用户操作余额记录表
        CUserBalanceRecordModel cUserBalanceRecord = new CUserBalanceRecordModel();
        cUserBalanceRecord.setcUserBalanceId(newBalance.getId());
        cUserBalanceRecord.setCreateTime(new Date());
        cUserBalanceRecord.setOperateContent("用户购买商品获得"+MoneyUtil.fromFenToYuan(total.toString())+"元佣金");
        cUserBalanceRecord.setOperateType(MoneyOperateEnum.BROKERAGE.toString());
        cUserBalanceRecord.setMoney(total);
        cUserBalanceRecord.setTag(MoneyTagEnum.ADD.toString());
        size = cUserBalanceRecordDao.insertSelective(cUserBalanceRecord);
        return size;
    }
    
    /**
     * 保存用户分佣相关记录
     * @param total:佣金
     * @param userId:用户id
     * @return 返回分佣操作结果
     */
    @Override
    public int saveMerchantCommission(Integer total, CUserModel user) {
        int size = 0;
        BUserBalanceModel balance = bUserBalanceDao.selectByBUserId(user.getRecommendUser());
        BUserBalanceModel newBalance = null;
        if(balance==null){
            newBalance = new BUserBalanceModel();
            newBalance.setbUserId(user.getRecommendUser());
            /*newBalance.setAccountBalance(total);*/
            newBalance.setTotalCommission(total);
            newBalance.setVersion(0);
            size = bUserBalanceDao.insertSelective(newBalance);
            if(size ==0){
                return size;
            }
        } else {
            newBalance = new BUserBalanceModel();
            newBalance.setId(balance.getId());
            newBalance.setbUserId(balance.getbUserId());
            /*newBalance.setAccountBalance(balance.getAccountBalance()+total);*/
            newBalance.setTotalCommission(balance.getTotalCommission()+total);
            newBalance.setVersion(balance.getVersion());
            size = bUserBalanceDao.updateByPrimaryKeyAndVersion(newBalance);
            if(size==0){
                return 0;
            }
        }
        //插入用户操作余额记录表
        BUserBalanceRecordModel bUserBalanceRecord = new BUserBalanceRecordModel();
        bUserBalanceRecord.setbUserBalanceId(newBalance.getId());
        bUserBalanceRecord.setCreateTime(new Date());
        bUserBalanceRecord.setOperateContent("用户购买商品获得"+MoneyUtil.fromFenToYuan(total.toString())+"元佣金");
        bUserBalanceRecord.setOperateType(MoneyOperateEnum.BROKERAGE.toString());
        bUserBalanceRecord.setMoney(total);
        bUserBalanceRecord.setTag(MoneyTagEnum.ADD.toString());
        size = bUserBalanceRecordDao.insertSelective(bUserBalanceRecord);
        return size;
    }

    /**
     * 根据用户id判断是否设置支付密码
     * @param userId:用户id
     * @return 返回支付密码是否存在(1.存在  0.不存在)
     */
    @Override
    public Integer isPayPasswordExists(Integer userId) {
        CUserModel user = cUserDao.select(userId);
        if(user==null||StringUtil.isEmpty(user.getPayPassword())){
            return 0;
        }
        return 1;
    }
}
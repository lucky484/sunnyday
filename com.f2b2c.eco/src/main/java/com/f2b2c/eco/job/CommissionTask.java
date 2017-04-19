package com.f2b2c.eco.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.f2b2c.eco.dao.market.BUserBalanceDao;
import com.f2b2c.eco.dao.market.BUserBalanceRecordDao;
import com.f2b2c.eco.dao.market.CUserBalanceDao;
import com.f2b2c.eco.dao.market.CUserBalanceRecordDao;
import com.f2b2c.eco.model.bean.BUserCommissionBean;
import com.f2b2c.eco.model.bean.CUserCommissionBean;
import com.f2b2c.eco.model.market.BUserBalanceModel;
import com.f2b2c.eco.model.market.BUserBalanceRecordModel;
import com.f2b2c.eco.model.market.CUserBalanceModel;
import com.f2b2c.eco.model.market.CUserBalanceRecordModel;
import com.f2b2c.eco.status.CalculateEnum;
import com.f2b2c.eco.status.MoneyOperateEnum;
import com.f2b2c.eco.status.MoneyTagEnum;
import com.f2b2c.eco.util.DateUtil;
import com.f2b2c.eco.util.TaxRateUtil;
import jodd.util.StringUtil;

/**
 * 计算佣金的定时任务
 * @author jane.hui
 *
 */
@Service("commissionTask")
public class CommissionTask {

    @Autowired
    private CUserBalanceDao cUserBalanceDao;
    
    @Autowired
    private CUserBalanceRecordDao cUserBalanceRecordDao;
    
    @Autowired
    private BUserBalanceRecordDao bUserBalanceRecordDao;
    
    @Autowired
    private BUserBalanceDao bUserBalanceDao;
    
    /**
     * 计算佣金
     */
    public void computeCommission(){
        // 计算C端用户佣金
        List<CUserCommissionBean> cList = cUserBalanceRecordDao.computeCommissionByUserId();
        List<CUserBalanceModel> newCList = new ArrayList<CUserBalanceModel>();
        List<CUserBalanceRecordModel> newRecordList = new ArrayList<CUserBalanceRecordModel>();
        CUserBalanceModel cUserBalance = null;
        CUserBalanceRecordModel cUserBalanceRecord = null;
        Integer money = 0;
        BigDecimal taxRate = new BigDecimal(0);
        String startDate = DateUtil.getLastMonthStartDay() + " 00:00:00";
        String endDate = DateUtil.getLastMonthEndDay() + " 23:59:59";
        for (CUserCommissionBean commission : cList) {
            if(StringUtil.isBlank((commission.getIsCalculated()))||CalculateEnum.NO.toString().equals(commission.getIsCalculated())){
                cUserBalance = new CUserBalanceModel();
                cUserBalance.setcUserId(commission.getUserId());
                money = TaxRateUtil.getCommissionTax(commission.getMoney());
                taxRate = TaxRateUtil.getTaxRate(commission.getMoney());
                cUserBalance.setCommission(commission.getCommission() + money);
                cUserBalance.setVersion(commission.getVersion());
                newCList.add(cUserBalance);
                
                cUserBalanceRecord = new CUserBalanceRecordModel();
                cUserBalanceRecord.setCreateTime(new Date());
                cUserBalanceRecord.setcUserBalanceId(commission.getId());
                cUserBalanceRecord.setMoney(money);
                cUserBalanceRecord.setTag(MoneyTagEnum.ADD.toString());
                cUserBalanceRecord.setOperateType(MoneyOperateEnum.MONTHLY_COMMISSION_CALCULATION.toString());
                cUserBalanceRecord.setOperateContent("每个月1号用户收到平台的佣金，税率为:"+taxRate+"%,扣除税之后的佣金为:"+money+"元");
                newRecordList.add(cUserBalanceRecord);
            }
        }
        // 批量插入C端用户佣金
        if(newCList.size() > 0){
            cUserBalanceDao.updateCUserCommission(newCList);
            cUserBalanceRecordDao.updateIsCalculatedByLastMonth(startDate,endDate);
        }
        
        // 批量插入操作记录
        if(newRecordList.size() > 0){
            cUserBalanceRecordDao.insertBatchCUserCommissionRecord(newRecordList);
        }
        
        // 计算B端用户佣金
        money = 0;
        taxRate = new BigDecimal(0);
        List<BUserCommissionBean> bList = bUserBalanceRecordDao.computeCommissionByUserId();
        List<BUserBalanceModel> newBList = new ArrayList<BUserBalanceModel>();
        List<BUserBalanceRecordModel> newBUserRecordList = new ArrayList<BUserBalanceRecordModel>();
        BUserBalanceModel bUserBalance = null;
        BUserBalanceRecordModel bUserBalanceRecord = null;
        for (BUserCommissionBean commission : bList) {
            if(StringUtil.isBlank(commission.getIsCalculated())||CalculateEnum.YES.toString().equals(commission.getIsCalculated())){
                bUserBalance = new BUserBalanceModel();
                bUserBalance.setbUserId(commission.getUserId());
                money = TaxRateUtil.getCommissionTax(commission.getMoney());
                taxRate = TaxRateUtil.getTaxRate(commission.getMoney());
                bUserBalance.setCommission(commission.getCommission() + money);
                bUserBalance.setVersion(commission.getVersion());
                newBList.add(bUserBalance);
                
                bUserBalanceRecord = new BUserBalanceRecordModel();
                bUserBalanceRecord.setCreateTime(new Date());
                bUserBalanceRecord.setbUserBalanceId(commission.getId());
                bUserBalanceRecord.setMoney(money);
                bUserBalanceRecord.setTag(MoneyTagEnum.ADD.toString());
                bUserBalanceRecord.setOperateType(MoneyOperateEnum.MONTHLY_COMMISSION_CALCULATION.toString());
                bUserBalanceRecord.setOperateContent("每个月1号商户收到平台的佣金，税率为:"+taxRate+"%,扣除税之后的佣金为:"+money+"元");
                newBUserRecordList.add(bUserBalanceRecord);
            }
        }
        // 批量插入B端用户佣金
        if(newBList.size()>0){
            bUserBalanceDao.updateBUserCommission(newBList);
            bUserBalanceRecordDao.updateIsCalculatedByLastMonth();
        }
        // 批量插入B端用户操作记录表
        if(newBUserRecordList.size() > 0){
            bUserBalanceRecordDao.insertBatchBUserCommissionRecord(newBUserRecordList);
        }
    }
}
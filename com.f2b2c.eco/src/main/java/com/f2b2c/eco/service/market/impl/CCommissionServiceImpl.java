package com.f2b2c.eco.service.market.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.f2b2c.eco.dao.market.CUserBalanceDao;
import com.f2b2c.eco.dao.market.CUserBalanceRecordDao;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.CUserBalanceModel;
import com.f2b2c.eco.model.market.CUserBalanceRecordModel;
import com.f2b2c.eco.service.market.CCommissionService;
import com.f2b2c.eco.status.MoneyOperateEnum;
import com.f2b2c.eco.status.MoneyTagEnum;

/**
 * 佣金Service实现类
 * @author jane.hui
 *
 */
@Service("cCommissionService")
@Transactional
public class CCommissionServiceImpl implements CCommissionService {

    @Autowired
    private CUserBalanceDao cUserBalanceDao;
    
    @Autowired
    private CUserBalanceRecordDao cUserBalanceRecordDao;
    
    /**
     * 转出功能
     * @param userId:用户外键
     * @param money:金额
     * @return 返回转账操作结果
     */
    @Override
    public ApiResultModel<String> transfer(Integer userId, Integer money) {
        ApiResultModel<String> result = new ApiResultModel<String>();
        CUserBalanceModel cUserBalance = cUserBalanceDao.selectByCUserId(userId);
        if(cUserBalance.getCommission() < money){
            result.setMsg("超出转出金额");
            result.setStatus(200);
            return result;
        }
        // 更新转出金额功能
        CUserBalanceModel newBalance = new CUserBalanceModel();
        newBalance.setId(cUserBalance.getId());
        newBalance.setAccountBalance(cUserBalance.getAccountBalance() + money);
        newBalance.setCommission(cUserBalance.getCommission() - money);
        newBalance.setVersion(cUserBalance.getVersion());
        int size = cUserBalanceDao.updateByPrimaryKeyAndVersion(newBalance);
        if(size == 0){
            result.setMsg("转出功能操作失败");
            result.setStatus(400);
            return result;
        }
        
        // 插入C端用户操作余额记录
        CUserBalanceRecordModel cUserBalanceRecord = new CUserBalanceRecordModel();
        cUserBalanceRecord.setCreateTime(new Date());
        cUserBalanceRecord.setMoney(money);
        cUserBalanceRecord.setTag(MoneyTagEnum.ADD.toString());
        cUserBalanceRecord.setOperateType(MoneyOperateEnum.TRANSFERTOBALANCE.toString());
        cUserBalanceRecord.setcUserBalanceId(cUserBalance.getId());
        cUserBalanceRecord.setOperateContent("用户从佣金转出到余额"+ money +"元");
        size = cUserBalanceRecordDao.insertSelective(cUserBalanceRecord);
        if(size == 0){
            result.setMsg("转出功能操作失败");
            result.setStatus(400);
            return result;
        }
        result.setMsg("转出功能操作成功");
        result.setStatus(200);
        return result;
    }
}
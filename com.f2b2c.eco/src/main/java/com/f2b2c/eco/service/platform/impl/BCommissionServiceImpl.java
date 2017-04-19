package com.f2b2c.eco.service.platform.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.f2b2c.eco.dao.market.BUserBalanceDao;
import com.f2b2c.eco.dao.market.BUserBalanceRecordDao;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BUserBalanceModel;
import com.f2b2c.eco.model.market.BUserBalanceRecordModel;
import com.f2b2c.eco.service.platform.BCommissionService;
import com.f2b2c.eco.status.MoneyOperateEnum;
import com.f2b2c.eco.status.MoneyTagEnum;

/**
 * 佣金Service实现类
 * @author jane.hui
 *
 */
@Service("bCommissionService")
@Transactional
public class BCommissionServiceImpl implements BCommissionService {

	@Autowired
	private BUserBalanceDao bUserBalanceDao;
	
	@Autowired
	private BUserBalanceRecordDao bUserBalanceRecordDao;
    
    /**
     * 转出功能
     * @param userId:用户外键
     * @param money:金额
     * @return 返回转账操作结果
     */
    @Override
    public ApiResultModel<String> transfer(Integer userId, Integer money) {
        ApiResultModel<String> result = new ApiResultModel<String>();
        BUserBalanceModel bUserBalance = bUserBalanceDao.selectByBUserId(userId);
        if(bUserBalance.getCommission() < money){
            result.setMsg("超出转出金额");
            result.setStatus(200);
            return result;
        }
        // 更新转出金额功能
        BUserBalanceModel newBalance = new BUserBalanceModel();
        newBalance.setId(bUserBalance.getId());
        newBalance.setAccountBalance(bUserBalance.getAccountBalance() + money);
        newBalance.setCommission(bUserBalance.getCommission() - money);
        newBalance.setVersion(bUserBalance.getVersion());
        int size = bUserBalanceDao.updateByPrimaryKeyAndVersion(newBalance);
        if(size == 0){
            result.setMsg("转出功能操作失败");
            result.setStatus(400);
            return result;
        }
        
        // 插入C端用户操作余额记录
        BUserBalanceRecordModel bUserBalanceRecord = new BUserBalanceRecordModel();
        bUserBalanceRecord.setCreateTime(new Date());
        bUserBalanceRecord.setMoney(money);
        bUserBalanceRecord.setTag(MoneyTagEnum.ADD.toString());
        bUserBalanceRecord.setOperateType(MoneyOperateEnum.TRANSFERTOBALANCE.toString());
        bUserBalanceRecord.setbUserBalanceId(bUserBalance.getId());
        bUserBalanceRecord.setOperateContent("用户从佣金转出到余额"+ money +"元");
        size = bUserBalanceRecordDao.insertSelective(bUserBalanceRecord);
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
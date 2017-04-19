package com.f2b2c.eco.dao.market;

import java.util.List;
import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.bean.CUserCommissionBean;
import com.f2b2c.eco.model.market.CUserBalanceRecordModel;

/**
 * C端账户余额操作记录表
 * @author jane.hui
 *
 */
public interface CUserBalanceRecordDao {
	
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(CUserBalanceRecordModel record);

    /**
     * 插入可选的字段
     * @mbggenerated
     */
    int insertSelective(CUserBalanceRecordModel record);

    /**
     * 根据主键查询表
     * @mbggenerated
     */
    CUserBalanceRecordModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CUserBalanceRecordModel record);

    /**
     * 更新表字段
     * @mbggenerated
     */
    int updateByPrimaryKey(CUserBalanceRecordModel record);
    
    /**
     * 根据用户id获取用户余额变动ibao
     * @param userId:用户id
     * @return 返回用户余额变动信息
     */
    List<CUserBalanceRecordModel> selectBalanceRecordByUserId(Page page);
    
    /**
     * 计算C端每个用户的佣金
     * @return
     */
    List<CUserCommissionBean> computeCommissionByUserId();
    
    /**
     * 批量插入C端用户操作记录表
     * @param list:C端用户操作记录列表
     * @return 返回操作结果
     */
    int insertBatchCUserCommissionRecord(List<CUserBalanceRecordModel> list);
    
    /**
     * 将上个月的所有操作记录更新为已结算过
     * @return 返回操作结果
     */
    int updateIsCalculatedByLastMonth(String startDate,String endDate);
}
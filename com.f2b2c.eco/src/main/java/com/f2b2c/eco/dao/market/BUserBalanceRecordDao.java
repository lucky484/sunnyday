package com.f2b2c.eco.dao.market;

import java.util.List;
import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.bean.BUserCommissionBean;
import com.f2b2c.eco.model.market.BUserBalanceRecordModel;

/**
 * B端用户帐户记录操作表
 * @author jane.hui
 *
 */
public interface BUserBalanceRecordDao {
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(BUserBalanceRecordModel record);

    /**
     * 插入可选的字段
     * @mbggenerated
     */
    int insertSelective(BUserBalanceRecordModel record);

    /**
     * 根据主键查询表
     * @mbggenerated
     */
    BUserBalanceRecordModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的 表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BUserBalanceRecordModel record);

    /**
     * 更新表字段
     * @mbggenerated
     */
    int updateByPrimaryKey(BUserBalanceRecordModel record);

    /**
     * 根据用户id获取用户余额变动
     * @param userId:用户id
     * @return 返回用户余额变动信息
     */
    List<BUserBalanceRecordModel> selectBalanceRecordByUserId(Page page);
    
    /**
     * 计算B端每个用户的佣金
     * @return
     */
    List<BUserCommissionBean> computeCommissionByUserId();
    
    /**
     * 批量插入用户佣金
     * @param list:B端用户记录操作列表
     * @return 返回操作结果
     */
    int insertBatchBUserCommissionRecord(List<BUserBalanceRecordModel> list);
    
    /**
     * 将上个月的所有操作记录更新为已结算过
     * @return 返回操作结果
     */
    int updateIsCalculatedByLastMonth();
}
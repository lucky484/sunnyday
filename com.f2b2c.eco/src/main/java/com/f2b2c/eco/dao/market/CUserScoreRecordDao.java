package com.f2b2c.eco.dao.market;

import java.util.List;
import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.market.CUserScoreRecordModel;

public interface CUserScoreRecordDao {
    /**
     * 根据主键删除用户帐户操作记录表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入用户帐户操作记录表
     * @mbggenerated
     */
    int insert(CUserScoreRecordModel record);

    /**
     * 插入可选的用户帐户操作记录表字段
     * @mbggenerated
     */
    int insertSelective(CUserScoreRecordModel record);

    /**
     * 根据主键查询用户账户表字段
     * @mbggenerated
     */
    CUserScoreRecordModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的用户帐户表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CUserScoreRecordModel record);

    /**
     * 更新用户帐户操作记录表
     * @mbggenerated
     */
    int updateByPrimaryKey(CUserScoreRecordModel record);
    
    /**
     * 根据用户id获取用户积分变动ibao
     * @param userId:用户id
     * @return 返回用户积分变动信息
     */
    List<CUserScoreRecordModel> selectScoreRecordByUserId(Page page);
}
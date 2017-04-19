package com.f2b2c.eco.dao.market;

import com.f2b2c.eco.model.market.CUserScoreModel;

public interface CUserScoreDao {
    /**
     * 根据主键删除表字段
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入用户积分表字段
     * @mbggenerated
     */
    int insert(CUserScoreModel record);

    /**
     * 插入可选的用户积分字段
     * @mbggenerated
     */
    int insertSelective(CUserScoreModel record);

    /**
     * 根据主键id获取用户帐户积分表字段
     * @mbggenerated
     */
    CUserScoreModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的用户积分表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CUserScoreModel record);

    /**
     * 更新用户积分表
     * @mbggenerated
     */
    int updateByPrimaryKey(CUserScoreModel record);
    
    /**
     * 根据用户Id获取用户积分表
     * @param userId:用户id
     * @return 返回用户积分表
     */
    CUserScoreModel selectScoreByUserId(Integer userId);
    
    /**
     * 根據主鍵更新積分
     * @param record
     * @return 返回操作狀態
     */
    int updateScoreByPrimaryKey(CUserScoreModel record);
}
package com.f2b2c.eco.service.market;

import java.util.List;
import com.f2b2c.eco.model.market.CUserScoreRecordModel;

/**
 * 积分接口
 * @author jane.hui
 *
 */
public interface BScoreService {

	/**
	 * 获取我的积分
	 * @param userId:用户外键
	 * @return 返回我的积分
	 */
    Integer getMyScore(Integer userId);
    
    /**
     * 根据用户id获取积分变动记录信息
     * @param userId:用户外键
     * @return 返回积分变动记录
     */
    List<CUserScoreRecordModel> scoreRecord(String userId,Integer start,Integer length);
    
    /**
     * 换算积分
     * @param userId:用户id
     * @param totalFee:费用
     * @return 返回用户积分
     */
    int settleScore(Integer userId,Integer totalFee);
}
package com.f2b2c.eco.service.market.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.f2b2c.eco.dao.market.CUserScoreDao;
import com.f2b2c.eco.dao.market.CUserScoreRecordDao;
import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.market.CUserScoreModel;
import com.f2b2c.eco.model.market.CUserScoreRecordModel;
import com.f2b2c.eco.service.market.BScoreService;
import com.f2b2c.eco.share.pay.alipay.util.MoneyUtil;
import com.f2b2c.eco.status.MoneyTagEnum;

/**
 * 积分Service实现类
 * @author jane.hui
 *
 */
@Service("bScoreService")
public class BScoreServiceImpl implements BScoreService{

    @Autowired
    private CUserScoreDao cUserScoreDao;
    
    @Autowired
    private CUserScoreRecordDao cUserScoreRecordDao;
    
    /**
     * 获取我的积分
     */
    @Override
    public Integer getMyScore(Integer userId) {
        CUserScoreModel cUserScore = cUserScoreDao.selectScoreByUserId(userId);
        if(cUserScore==null){
            return 0;
        }
        return cUserScore.getScore();
    }

    /**
     * 获取我的积分变动记录
     * @param userId:用户外键
     * @param start:起始页
     * @param length:分页大小
     * @return 返回我的积分变动记录
     */
    @Override
    public List<CUserScoreRecordModel> scoreRecord(String userId, Integer start, Integer length) {
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        page.getParams().put("userId", userId);
        return cUserScoreRecordDao.selectScoreRecordByUserId(page);
    }
    
    /**
     * 结算积分
     * @param userId:用户外键
     * @param totalFee:金额
     */
    @Override
    public int settleScore(Integer userId,Integer totalFee) {
        CUserScoreModel cUserScore = cUserScoreDao.selectScoreByUserId(userId);
        CUserScoreModel insertUserScore = null;
        Integer score = MoneyUtil.fromFenToIntYuan(String.valueOf(totalFee));
        int size = 0;
        if(cUserScore==null){
            insertUserScore = new CUserScoreModel();
            insertUserScore.setcUserId(userId);
            insertUserScore.setScore(score);
            insertUserScore.setVersion(0);
            size = cUserScoreDao.insertSelective(insertUserScore);
        } else {
            insertUserScore = new CUserScoreModel();
            insertUserScore.setId(cUserScore.getId());
            insertUserScore.setcUserId(userId);
            insertUserScore.setScore(cUserScore.getScore()+score);
            insertUserScore.setVersion(cUserScore.getVersion());
            size = cUserScoreDao.updateScoreByPrimaryKey(insertUserScore);
        }
        if(size>0){
	        // 插入用户操作积分记录表
	        CUserScoreRecordModel cUserScoreRecord = new CUserScoreRecordModel();
	        cUserScoreRecord.setcUserScoreId(insertUserScore.getId());
	        cUserScoreRecord.setCreateTime(new Date());
	        cUserScoreRecord.setOperateType("3");
	        cUserScoreRecord.setOperateContent("用户购买商品获得"+score+"个积分");
	        cUserScoreRecord.setScore(score);
	        cUserScoreRecord.setTag(MoneyTagEnum.ADD.toString());
	        size = cUserScoreRecordDao.insertSelective(cUserScoreRecord);
        }
        return size;
    }
}
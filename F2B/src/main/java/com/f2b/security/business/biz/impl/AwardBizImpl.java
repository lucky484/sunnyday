package com.f2b.security.business.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.biz.AwardBiz;
import com.f2b.security.business.biz.RecordBiz;
import com.f2b.security.business.dao.AwardDao;
import com.f2b.security.domain.Award;
import com.f2b.security.domain.Record;

/**
 * Created by Administrator on 2016/3/24.
 */
@Service("AwardBiz")
public class AwardBizImpl implements AwardBiz
{


	private static final Logger logger = LoggerFactory.getLogger(AwardBizImpl.class);

	@Autowired
	private AwardDao awardDao;

	@Autowired
	private RecordBiz recordBiz;

	@Override
	@Transactional
	public String lottery(String openid, String nickname,String reward,String orderNo, String ip)
	{
		//新增记录
		Record record = new Record();
		record.setOpenId(openid);
		record.setNickname(nickname);
		record.setAward(reward);
		record.setLotteryDate(new Date());
		record.setDrawStatus(0);
		record.setOrderNo(orderNo);
		record.setIp(ip);
		recordBiz.addOrUpdate(record);
		logger.debug("新增{}人的记录成功，获奖{}", openid, reward);
		// 取消立即发奖，改为定期审核发放奖品
//		if (!reward.equals("0")) {
//			recordBiz.payMoney(openid,Integer.valueOf(reward));
//		}
		return reward;
	}

	@Override
	@Transactional
	public String lottery1(String idFlag, String openid)
	{
		return null;
	}

	@Override
	public Long totalRecord()
	{
		return this.totalRecord(null);
	}

	@Override
	public Long totalRecord(Map<String, String> queryHash)
	{
		return awardDao.totalRecord(queryHash);
	}

	@Override
	public List<Award> findList()
	{
		return this.findList(0, 0, null);
	}

	@Override
	public List<Award> findList(Map<String, String> queryHash)
	{
		return this.findList(0, 0, queryHash);
	}

	@Override
	public List<Award> findList(Integer pageNow, Integer pageSize)
	{
		return this.findList(pageNow, pageSize, null);
	}

	@Override
	public List<Award> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash)
	{
		return this.findList(pageNow, pageSize, "", queryHash);
	}

	@Override
	public List<Award> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash)
	{
		return awardDao.findList(pageNow, pageSize, sqlOrder, queryHash);
	}

	@Override
	public Award findModel(Long awardId)
	{
		return awardDao.findModel(awardId);
	}

	@Override
	@Transactional
	public void addOrUpdate(Award model)
	{
		if (model.getAwardId() != null && model.getAwardId() > 0)
		{
			awardDao.update(model);
		}
		else
		{
			awardDao.add(model);
		}

	}

	@Override
	@Transactional
	public void delete(Long awardId)
	{
		awardDao.delete(awardId);
	}

}

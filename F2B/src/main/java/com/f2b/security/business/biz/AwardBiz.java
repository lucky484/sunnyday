package com.f2b.security.business.biz;

import java.util.List;
import java.util.Map;

import com.f2b.security.domain.Award;
import com.f2b.security.domain.FarmOrder;

/**
 * Created by Administrator on 2016/3/24.
 */
@SuppressWarnings("unused")
public interface AwardBiz
{

    public String lottery(String openid, String nickname,String reward,String orderNo, String ip);

	public String lottery1(String idFlag, String openid);

	// ******************************************************************************
	// ********************************* CRUD START *********************************
	// ******************************************************************************

	/**
	 * 获取总记录数
	 */
	public Long totalRecord();

	/**
	 * 获取总记录数
	 */
	public Long totalRecord(Map<String, String> queryHash);

	/**
	 * 列表不分页
	 */
	public List<Award> findList();

	/**
	 * 列表不分页
	 */
	public List<Award> findList(Map<String, String> queryHash);

	/**
	 * 分页列表
	 */
	public List<Award> findList(Integer pageNow, Integer pageSize);

	/**
	 * 分页列表
	 */
	public List<Award> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash);

	/**
	 * 分页列表
	 */
	public List<Award> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash);

	/**
	 * id获取记录
	 */
	public Award findModel(Long awardId);

	/**
	 * 增加或修改记录
	 */
	public void addOrUpdate(Award model);

	/**
	 * 删除记录
	 */
	public void delete(Long awardId);


}

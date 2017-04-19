package com.f2b.security.business.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.f2b.security.domain.ShareRecord;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface ShareRecordDao {

	// ******************************************************************************
	// ********************************* CRUD START
	// *********************************
	// ******************************************************************************

	/**
	 * 获取总记录数
	 */
	public Long totalRecord(Map<String, String> queryHash);

	/**
	 * 分页列表
	 */
	public List<ShareRecord> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash);

	/**
	 * id获取记录
	 */
	public ShareRecord findModel(Long shareRecordId);

	/**
	 * 增加记录
	 */
	public Integer add(ShareRecord model);

	/**
	 * 修改记录
	 */
	public Integer update(ShareRecord model);

	/**
	 * 删除记录
	 */
	public Integer delete(Long shareRecordId);

	// ******************************************************************************
	// ********************************** CRUD END
	// **********************************
	// ******************************************************************************

	public List<ShareRecord> get(String openId, String shareOpenId);
	public List<ShareRecord> get(String openId);
	/**
	 * 获取当前分享人分享出去后的所有订单
	 * @param shareOpenId
	 * @return
	 */
	public List<ShareRecord> getMyShare(String shareOpenId);
	
	/**
	 * 导出线下支付
	 */
	public List<ShareRecord> getShareRecordExcl1();

	/**
	 * 导出全部正常分享的
	 */
	public List<ShareRecord> getShareRecordExcl();
}

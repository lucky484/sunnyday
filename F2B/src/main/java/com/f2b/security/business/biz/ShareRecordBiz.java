package com.f2b.security.business.biz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.f2b.security.domain.ShareRecord;

/**
 * Created by Administrator on 2016/3/24.
 */
@SuppressWarnings("unused")
public interface ShareRecordBiz {
	// ******************************************************************************
	// ********************************* CRUD START
	// *********************************
	// ******************************************************************************

	/**
	 * id获取记录
	 */
	public ShareRecord findModel(Long shareRecordId);

	/**
	 * 增加或修改记录
	 */
	public void addOrUpdate(ShareRecord model);

	/**
	 * 删除记录
	 */
	public void delete(Long shareRecordId);
	
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
    public List<ShareRecord> findList();

    /**
     * 列表不分页
     */
    public List<ShareRecord> findList(Map<String, String> queryHash);

    /**
     * 分页列表
     */
    public List<ShareRecord> findList(Integer pageNow, Integer pageSize);

    /**
     * 分页列表
     */
    public List<ShareRecord> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash);

    /**
     * 分页列表
     */
    public List<ShareRecord> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash);

	// ******************************************************************************
	// ********************************** CRUD END
	// **********************************
	// ******************************************************************************
	public List<ShareRecord> get(String openId, String shareOpenId);

	public List<ShareRecord> get(String openId);

	public List<ShareRecord> getMyShare(String shareOpenId);
	
	/**
	 * 导出线下支付
	 */
	public void getShareRecordExcl1(HttpServletResponse response);

	/**
	 * 导出全部正常分享的
	 */
	public void getShareRecordExcl(HttpServletResponse response);
}

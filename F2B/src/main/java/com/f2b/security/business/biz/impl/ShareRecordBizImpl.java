package com.f2b.security.business.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.biz.ShareRecordBiz;
import com.f2b.security.business.dao.ShareRecordDao;
import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.ExcelUtil;

/**
 * Created by Administrator on 2016/3/24.
 */
@Service("shareRecordBiz")
public class ShareRecordBizImpl implements ShareRecordBiz {

	private Logger logger = LoggerFactory.getLogger(ShareRecordBizImpl.class);

	@Autowired
	private ShareRecordDao shareRecordDao;

	@Override
	public ShareRecord findModel(Long recordId) {
		return shareRecordDao.findModel(recordId);
	}

	@Override
	@Transactional
	public void addOrUpdate(ShareRecord model) {
		if (model.getShareRecordId() != null && model.getShareRecordId() > 0) {
			shareRecordDao.update(model);
		} else {
			shareRecordDao.add(model);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		shareRecordDao.delete(id);
	}

	public List<ShareRecord> get(String openId, String shareOpenId) {
		return shareRecordDao.get(openId, shareOpenId);
	}

	public List<ShareRecord> get(String openId) {
		return shareRecordDao.get(openId);
	}

	public List<ShareRecord> getMyShare(String shareOpenId) {
		return shareRecordDao.getMyShare(shareOpenId);
	}

	@Override
	public Long totalRecord() {
		return this.totalRecord(null);
	}

	@Override
	public Long totalRecord(Map<String, String> queryHash) {
		return shareRecordDao.totalRecord(queryHash);
	}

	@Override
	public List<ShareRecord> findList() {
		return this.findList(0, 0, null);
	}

	@Override
	public List<ShareRecord> findList(Map<String, String> queryHash) {
		return this.findList(0, 0, queryHash);
	}

	@Override
	public List<ShareRecord> findList(Integer pageNow, Integer pageSize) {
		return this.findList(pageNow, pageSize, null);
	}

	@Override
	public List<ShareRecord> findList(Integer pageNow, Integer pageSize, Map<String, String> queryHash) {
		return this.findList(pageNow, pageSize, "", queryHash);
	}

	@Override
	public List<ShareRecord> findList(Integer pageNow, Integer pageSize, String sqlOrder, Map<String, String> queryHash) {
		return shareRecordDao.findList(pageNow, pageSize, sqlOrder, queryHash);
	}

	/**
	 * 导出线下支付
	 */
	@Override
	public void getShareRecordExcl1(HttpServletResponse response) {
		List<ShareRecord> excl = shareRecordDao.getShareRecordExcl1();
		List dataset = new ArrayList();
		for (ShareRecord r : excl) {
			r.setNickname(Base64.getFromBase64(r.getNickname()));
			dataset.add(r);
		}
		String[] header = new String[] {"编号","分享人ID","分享人昵称","购买者ID","购买者昵称","购买数量",
				"下单时间","修改时间","订单号","备注","是否发送红包(1:是；0：否)"};
		ExcelUtil.exportExcel2("导出线下支付记录", header, dataset, "yyyy-MM-dd", response);
		
	}

	/**
	 * 导出全部正常分享的
	 */
	@Override
	public void getShareRecordExcl(HttpServletResponse response) {
		List<ShareRecord> excl = shareRecordDao.getShareRecordExcl();
		List dataset = new ArrayList();
		for (ShareRecord r : excl) {
			r.setNickname(Base64.getFromBase64(r.getNickname()));
			r.setShareNickName(Base64.getFromBase64(r.getShareNickName()));
			dataset.add(r);
		}
		String[] header = new String[] {"编号","分享人ID","分享人昵称","购买者ID","购买者昵称","购买数量",
				"下单时间","修改时间","订单号","备注","是否发送红包(1:是；0：否)"};
		ExcelUtil.exportExcel2("导出线上支付记录", header, dataset, "yyyy-MM-dd", response);
		
	}

}

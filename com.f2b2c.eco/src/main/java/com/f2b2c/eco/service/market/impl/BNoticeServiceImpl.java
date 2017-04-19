package com.f2b2c.eco.service.market.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.BNoticeDao;
import com.f2b2c.eco.model.market.BNoticeModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.market.BNoticeService;

@Service
public class BNoticeServiceImpl implements BNoticeService {
	@Autowired
	private BNoticeDao bNoticeDao;

	@Override
	public List<BNoticeModel> findAllNotice() {
		return bNoticeDao.findAllNotice();
	}

	@Override
	public Page<BNoticeModel> findBNoticePage(Pageable pageable) {
		Integer start = pageable.getPageNumber();
		Integer length = pageable.getPageSize();
		int total = bNoticeDao.findAllNoticePageCount();
		List<BNoticeModel> noticeList = bNoticeDao.findAllNoticePage(start, length);
		Page<BNoticeModel> pages = new Pagination<>(noticeList, pageable, total);
		return pages;
	}

	@Override
	public int insertBnotice(Map<String, Object> map) {
		return bNoticeDao.insertBnotice(map);
	}

	@Override
	public int delBnotice(Map<String, Object> map) {
		return bNoticeDao.delBnotice(map);
	}

	@Override
	public BNoticeModel bnoticeDetail(Integer id) {
		// TODO Auto-generated method stub
		return bNoticeDao.bnoticeDetail(id);
	}


	@Override
	public int findAllNoticeCount() {
		return bNoticeDao.findAllNoticePageCount();
	}

	@Override
	public List<BNoticeModel> findAllNoticePage(int start, Integer pageSize) {
		return bNoticeDao.findAllNoticePage(start, pageSize);
	}

}
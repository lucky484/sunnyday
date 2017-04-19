package com.f2b2c.eco.service.market;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.market.BNoticeModel;

public interface BNoticeService {


	List<BNoticeModel> findAllNotice();

	Page<BNoticeModel> findBNoticePage(Pageable pageable);

	int insertBnotice(Map<String, Object> map);

	int delBnotice(Map<String, Object> map);

	BNoticeModel bnoticeDetail(Integer id);

	int findAllNoticeCount();

	List<BNoticeModel> findAllNoticePage(int start, Integer pageSize);

}

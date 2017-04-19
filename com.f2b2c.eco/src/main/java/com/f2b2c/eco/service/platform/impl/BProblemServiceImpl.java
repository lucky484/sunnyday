package com.f2b2c.eco.service.platform.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.BProblemDao;
import com.f2b2c.eco.model.platform.BProblemModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.BProblemService;

@Service
public class BProblemServiceImpl implements BProblemService {

	@Autowired
	private BProblemDao bProblemDao;

	@Override
	public Page<BProblemModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap) {
		
		int total = bProblemDao.countWithMap(paramMap);
		paramMap.put("pageable", pageable);
		List<BProblemModel> lists = bProblemDao.findWithPagination(paramMap);
		Page<BProblemModel> pages = new Pagination<>(lists,pageable,total);
		return pages;
	}

	@Override
	public void deleteProblemById(Integer id) {
		
		bProblemDao.deleteProblemById(id);
	}

	@Override
	public int addProblem(BProblemModel problemModel) {
		
		return bProblemDao.saveProblem(problemModel);
	}

	@Override
	public BProblemModel getProblemById(Integer id) {
		
		return bProblemDao.getProblemById(id);
	}

	@Override
	public int updateProblem(BProblemModel problemModel) {
		
		return bProblemDao.updateProblem(problemModel);
	}

	@Override
	public List<Problem> getAllProblemList() {
		
		return bProblemDao.getAllProblemList();
	}
	
	
	
	
}

package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.platform.BProblemModel;

public interface BProblemService {

	Page<BProblemModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap);

	void deleteProblemById(Integer id);

	int addProblem(BProblemModel problemModel);

	BProblemModel getProblemById(Integer id);

	int updateProblem(BProblemModel problemModel);

	List<Problem> getAllProblemList();

}

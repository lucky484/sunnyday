package com.f2b2c.eco.dao.platform;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.parsing.Problem;

import com.f2b2c.eco.model.platform.BProblemModel;

public interface BProblemDao {

	int countWithMap(Map<String, Object> paramMap);

	List<BProblemModel> findWithPagination(Map<String, Object> paramMap);

	void deleteProblemById(Integer id);

	int saveProblem(BProblemModel problemModel);

	BProblemModel getProblemById(Integer id);

	int updateProblem(BProblemModel problemModel);

	List<Problem> getAllProblemList();

	
}

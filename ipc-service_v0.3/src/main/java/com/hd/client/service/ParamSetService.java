package com.hd.client.service;

import java.util.Map;

import com.hd.client.model.ParamSet;

public interface ParamSetService {

	public ParamSet getParamSet();

	Map<String, Object> countDBSize();
}

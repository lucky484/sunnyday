package com.softtek.mdm.service;

import java.util.Map;

public interface TerminalService {

	int updateInfo(Map<String, Object> map);
	
	int report(Map<String, Object> map);
	
	int baseInfo(Map<String, Object> map);
	
	public int update(Map<String, Object> map);
}

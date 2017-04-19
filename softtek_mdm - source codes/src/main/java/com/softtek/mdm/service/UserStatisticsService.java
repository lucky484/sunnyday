package com.softtek.mdm.service;

import java.util.Map;

import com.softtek.mdm.model.Page;
/**
 * 
 * @author josen.yang
 *
 */
public interface UserStatisticsService {

    Page getAllUsersListsByMap(Map<String, Object> paramMap, Integer start, Integer length);

	Page getAllTotalUsersListsByMap(Map<String, Object> paramMap);

	Page getAllActiveUsersListsByMap(Map<String, Object> paramMap);

	Page getAllInActiveUsersListsByMap(Map<String, Object> paramMap);

	Page getAllDeleteUsersListsByMap(Map<String, Object> paramMap);

}

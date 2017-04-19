package com.softtek.mdm.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.softtek.mdm.model.NameList;
import com.softtek.mdm.model.Page;

public interface NameListService {

	 /**
     * 根据类型1 黑名单  0 是白名单查询黑白名单信息
     * @param map
     * @return
     */
    List<NameList> findListByType(Integer listType,Integer orgId);
    
    String selectByName(Integer listType,Integer orgId,String listName);
    
	/**
	 * 选择应用
	 * @return
	 */
	Page loadAppList(Integer start, Integer length,HttpSession session,HttpServletRequest request);
}

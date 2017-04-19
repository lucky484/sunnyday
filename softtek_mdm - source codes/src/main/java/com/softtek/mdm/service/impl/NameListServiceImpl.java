package com.softtek.mdm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softtek.mdm.dao.AppDao;
import com.softtek.mdm.dao.AppListDao;
import com.softtek.mdm.dao.ApplicationNameListDao;
import com.softtek.mdm.dao.NameListDao;
import com.softtek.mdm.model.AppList;
import com.softtek.mdm.model.NameList;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.NameListService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.DataGridModel;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;

@Service("nameListService")
public class NameListServiceImpl implements NameListService  {
	
	@Autowired
	private NameListDao nameListDao;
	
	@Autowired
	private ApplicationNameListDao applicationNameListDao;
	
	@Autowired
	private AppListDao appListDao;
	
	@Autowired
	private AppDao appDao;
	
	@Override
	public List<NameList> findListByType(Integer listType,Integer orgId) {
		Map<String, Integer> map=new HashMap<String,Integer>();
		map.put("listType", listType==null?-1:listType);
		map.put("orgId", orgId==null?0:orgId);
		return (List<NameList>) nameListDao.findListByType(map);
		 
	}

	@Override
	public String selectByName(Integer listType, Integer orgId, String listName) {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("listType", listType);
		map.put("orgId", orgId);
		map.put("listName", listName);
		Integer nameListId=nameListDao.selectByName(map);
		if(nameListId!=null){
			List<Integer> ids=applicationNameListDao.findAppIdsByNameListId(nameListId);
			if(ids!=null&&ids.size()>0){
				List<AppList> appLists=appListDao.selectByIds(ids);
				if(appLists!=null&&appLists.size()>0){
					return JSONArray.fromObject(appLists).toString();
				}
			}
		}
		return "";
		
	}

	/**
	 * 加载应用列表
	 */
	@Override
	public Page loadAppList(Integer start, Integer length,HttpSession session,HttpServletRequest request) {
		String type = request.getParameter("type");
		if(StringUtil.isNotBlank(type)&&Constant.YES.equals(type)){
			type = Constant.NO;
		} else if(StringUtil.isNotEmpty(type)&&Constant.NO.equals(type)){
			type = Constant.YES;
		}
		Page page = new Page();
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		DataGridModel params = new DataGridModel();
		params.getParams().put("orgId", organization.getId());
		params.setBegin(start);
		params.setNum(length);
		params.getParams().put("releaseType", type);
		int total = appDao.getApplicationCountByParams(params);
		page.setData(appDao.getApplicationListByParams(params));
		page.setRecordsFiltered(total);
		page.setRecordsTotal(total);
		return page;
	}
}

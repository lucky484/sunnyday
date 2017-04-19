package com.softtek.mdm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.StructureDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.UserStatisticsService;
import com.softtek.mdm.util.Constant;

@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {
	
	private static Logger logger=LoggerFactory.getLogger(UserStatisticsServiceImpl.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private StructureDao structureDao;
	
	
	
	@Override
    public Page getAllUsersListsByMap(Map<String, Object> paramMap, Integer start, Integer length) {
        Integer count = userDao.getUsersCountByMap(paramMap);
        String lastDay;
        List<UserModel> lists;
        Date date;
        Date nowDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            lastDay = (String) paramMap.get("lastDay");
            lists = new ArrayList<UserModel>();
            date = sdf.parse(lastDay);
            Date now = new Date();
            String nowStr = sdf.format(now);
            nowDate = sdf.parse(nowStr);

        if (start == 0) {
            UserModel todayUserModel = userDao.getTodayUsersListsByMap(paramMap);
                    if (date.getTime() >= nowDate.getTime()) {
                        lists.add(0, todayUserModel);
                        count += 1;
                    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length - 1);
                    paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
                } else {
                    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
                    paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
                    }
        } else {
                if (date.getTime() >= nowDate.getTime()) {
                    count += 1;
                    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
                    paramMap.put(Constant.PageRelatedConstant.START_PAGE, start - 1);
                } else {
                    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
                    paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
                }
        }
        Page page = new Page();
        lists.addAll(userDao.getUsersListsByMap(paramMap));
        page.setRecordsTotal(count);
		page.setData(lists);
        page.setRecordsFiltered(count);
		return page;
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
	}

	@Override
	public Page getAllTotalUsersListsByMap(Map<String, Object> paramMap) {
		Page page = new Page();
		Integer count = userDao.getTotalUsersCountByMap(paramMap);
		List<UserModel> lists = userDao.getTotalUsersListsByMap(paramMap);
		List<UserModel> userLists = new ArrayList<UserModel>();
        // 拼部门把所有的父部门显示出来
		if(CollectionUtils.isNotEmpty(lists)){
			List<StructureModel> structureLists = null;
			StringBuffer buffer = null;
			for(UserModel user:lists){
				structureLists = structureDao.getStructureListById(user.getStructure().getId());
				buffer = new StringBuffer();
				buffer.append("\\");
				for(StructureModel structure:structureLists){
					buffer.append(structure.getName()).append("\\");
				}
				user.setDepartName(buffer.toString().substring(0, buffer.toString().length()-1));
				userLists.add(user);
			}
		}	
		page.setRecordsTotal(count);
		page.setData(userLists);
		page.setRecordsFiltered(count);
		return page;
	}

	@Override
	public Page getAllActiveUsersListsByMap(Map<String, Object> paramMap) {
		
		Page page = new Page();
		Integer count = userDao.getActiveUsersCountByMap(paramMap);
		List<UserModel> lists = userDao.getActiveUsersListsByMap(paramMap);
		List<UserModel> userLists = new ArrayList<UserModel>();
        // 拼部门把所有的父部门显示出来
		if(CollectionUtils.isNotEmpty(lists)){
			List<StructureModel> structureLists = null;
			StringBuffer buffer = null;
			for(UserModel user:lists){
				structureLists = structureDao.getStructureListById(user.getStructure().getId());
				buffer = new StringBuffer();
				buffer.append("\\");
				for(StructureModel structure:structureLists){
					buffer.append(structure.getName()).append("\\");
				}
				user.setDepartName(buffer.toString().substring(0, buffer.toString().length()-1));
				userLists.add(user);
			}
		}
		page.setRecordsTotal(count);
		page.setData(lists);
		page.setRecordsFiltered(count);
		return page;
	}

	@Override
	public Page getAllInActiveUsersListsByMap(Map<String, Object> paramMap) {
		
		Page page = new Page();
		Integer count = userDao.getInActiveUsersCountByMap(paramMap);
		List<UserModel> lists = userDao.getInActiveUsersListsByMap(paramMap);
		List<UserModel> userLists = new ArrayList<UserModel>();
        // 拼部门把所有的父部门显示出来
		if(CollectionUtils.isNotEmpty(lists)){
			List<StructureModel> structureLists = null;
			StringBuffer buffer = null;
			for(UserModel user:lists){
				structureLists = structureDao.getStructureListById(user.getStructure().getId());
				buffer = new StringBuffer();
				buffer.append("\\");
				for(StructureModel structure:structureLists){
					buffer.append(structure.getName()).append("\\");
				}
				user.setDepartName(buffer.toString().substring(0, buffer.toString().length()-1));
				userLists.add(user);
			}
		}
		page.setRecordsTotal(count);
		page.setData(lists);
		page.setRecordsFiltered(count);
		return page;
	}

	@Override
	public Page getAllDeleteUsersListsByMap(Map<String, Object> paramMap) {
		
		Page page = new Page();
		Integer count = userDao.getDeleteUsersCountByMap(paramMap);
		List<UserModel> lists = userDao.getDeleteUsersListsByMap(paramMap);
		List<UserModel> userLists = new ArrayList<UserModel>();
        // 拼部门把所有的父部门显示出来
		if(CollectionUtils.isNotEmpty(lists)){
			List<StructureModel> structureLists = null;
			StringBuffer buffer = null;
			for(UserModel user:lists){
				structureLists = structureDao.getStructureListById(user.getStructure().getId());
				buffer = new StringBuffer();
				buffer.append("\\");
				for(StructureModel structure:structureLists){
					buffer.append(structure.getName()).append("\\");
				}
				user.setDepartName(buffer.toString().substring(0, buffer.toString().length()-1));
				userLists.add(user);
			}
		}
		page.setRecordsTotal(count);
		page.setData(lists);
		page.setRecordsFiltered(count);
		return page;
	}

}

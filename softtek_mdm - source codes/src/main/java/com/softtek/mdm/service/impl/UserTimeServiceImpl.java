package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.UserTimeDao;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.UserTimeModel;
import com.softtek.mdm.service.UserTimeService;

@Service
public class UserTimeServiceImpl implements UserTimeService{

	@Autowired
    private UserTimeDao userTimeDao;
	
	@Autowired
	private MessageSource messageSourceService;
	
	@Override
	public int updateUserTime(UserTimeModel userTimeModel) {
		
		return userTimeDao.updateUserTime(userTimeModel);
	}

	@Override
	public int queryUserTimeByIdAndDate(Integer deviceId, String date) {
		
		return userTimeDao.queryUserTimeByIdAndDate(deviceId, date);
	}

	@Override
	public int insertUserTime(UserTimeModel userTimeModel) {
		
		return userTimeDao.insertUserTime(userTimeModel);
	}

	@Override
    public UserTimeModel queryUserTime(Integer orgId, String startTime, String endTime, List<Integer> idList) {
		
        return userTimeDao.queryUserTime(orgId, startTime, endTime, idList);
	}

	@Override
	public Page queryAllUserTime(Map<String,Object> map) {
	    Page page = new Page();
	    List<UserTimeModel> list = userTimeDao.queryAllUserTime(map);
	    for(UserTimeModel userTime : list){
	    	String remark = "";
	    	int value = maxValue(userTime);
	    	userTime.setUserTimeMax(value);
	    	if((userTime.getZero() == null ? 0:userTime.getZero()) == value){
	    		 remark = messageSourceService.getMessage("tiles.views.institution.user.time.from.zero.to.one", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getOne() == null ? 0:userTime.getZero()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.one.to.two", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getTwo() == null ? 0:userTime.getTwo()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.two.to.three", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getThree() == null ? 0:userTime.getThree()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.three.to.four", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getFour() == null ? 0:userTime.getFour()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.four.to.five", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getFive() == null ? 0:userTime.getFive()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.five.to.six", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getSix() == null ? 0:userTime.getSix()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.six.to.seven", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getSeven() == null ? 0:userTime.getSeven()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.seven.to.eight", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getEight() == null ? 0:userTime.getEight()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.eight.to.nine", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getNine() == null ? 0:userTime.getNine()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.nine.to.ten", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getTen() == null ? 0:userTime.getTen()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.ten.to.eleven", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getEleven() == null ? 0:userTime.getEleven()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.eleven.to.twelve", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getTwelve() == null ? 0:userTime.getTwelve()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.twelve.to.thirteen", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getThirteen() == null ? 0:userTime.getThirteen()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.thirteen.to.fourteen", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getFourteen() == null ? 0:userTime.getFourteen()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.fourteen.to.fifteen", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getFifteen() == null ? 0:userTime.getFifteen()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.fifteen.to.sixteen", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getSixteen() == null ? 0:userTime.getSixteen()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.sixteen.to.seventeen", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getSeventeen() == null ? 0:userTime.getSeventeen()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.seventeen.to.eighteen", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getEighteen() == null ? 0:userTime.getEighteen()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.eighteen.to.nineteen", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getNineteen() == null ? 0:userTime.getNineteen()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.nineteen.to.twenty", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getTwenty() == null ? 0:userTime.getTwenty()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.twenty.to.twentyOne", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getTwentyOne() == null ? 0:userTime.getTwentyOne()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.twentyOne.to.twentyTwo", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getTwentyTwo() == null ? 0:userTime.getTwentyTwo()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.twentyTwo.to.twentyThree", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	if((userTime.getTwentyThree() == null ? 0:userTime.getTwentyThree()) == value){
	    		 remark += messageSourceService.getMessage("tiles.views.institution.user.time.from.twentyThree.to.zero", null, LocaleContextHolder.getLocale()) + ",";
	    	}
	    	userTime.setRemark(remark.substring(0, remark.length()-1));
	    }
	    int count = userTimeDao.queryAllUserTimeCount(map);
	    page.setData(list);
	    page.setRecordsTotal(count);
	    page.setRecordsFiltered(count);
		return page;
	}
	
	private int maxValue(UserTimeModel userTime){
		 List<Integer> list = new ArrayList<Integer>();
		 list.add(userTime.getZero() == null ? 0 : userTime.getZero());
		 list.add(userTime.getOne() == null ? 0 : userTime.getOne());
		 list.add(userTime.getTwo() == null ? 0 : userTime.getTwo());
		 list.add(userTime.getThree() == null ? 0 : userTime.getThree());
		 list.add(userTime.getFour() == null ? 0 : userTime.getFour());
		 list.add(userTime.getFive() == null ? 0 : userTime.getFive());
		 list.add(userTime.getSix() == null ? 0 : userTime.getSix());
		 list.add(userTime.getSeven() == null ? 0 : userTime.getSeven());
		 list.add(userTime.getEight() == null ? 0 : userTime.getEight());
		 list.add(userTime.getNine() == null ? 0 : userTime.getNine());
		 list.add(userTime.getTen() == null ? 0 : userTime.getTen());
		 list.add(userTime.getEleven() == null ? 0 : userTime.getEleven());
		 list.add(userTime.getTwelve() == null ? 0 : userTime.getTwelve());
		 list.add(userTime.getThirteen() == null ? 0 : userTime.getThirteen());
		 list.add(userTime.getFourteen() == null ? 0 : userTime.getFourteen());
		 list.add(userTime.getFifteen() == null ? 0 : userTime.getFifteen());
		 list.add(userTime.getSixteen() == null ? 0 : userTime.getSixteen());
		 list.add(userTime.getSeventeen() == null ? 0 : userTime.getSeventeen());
		 list.add(userTime.getEighteen() == null ? 0 : userTime.getEighteen());
		 list.add(userTime.getNineteen() == null ? 0 : userTime.getNineteen());
		 list.add(userTime.getTwenty() == null ? 0 : userTime.getTwenty());
		 list.add(userTime.getTwentyOne() == null ? 0 : userTime.getTwentyOne());
		 list.add(userTime.getTwentyTwo() == null ? 0 : userTime.getTwentyTwo());
		 list.add(userTime.getTwentyThree() == null ? 0 : userTime.getTwentyThree());
		 int value = Collections.max(list);
		 return value;
	} 
}

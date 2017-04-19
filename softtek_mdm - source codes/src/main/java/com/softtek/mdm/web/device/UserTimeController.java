package com.softtek.mdm.web.device;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.UserTimeModel;
import com.softtek.mdm.service.UserTimeService;

@Controller
@RequestMapping(value = "/terminal/usertime")
public class UserTimeController {
    
	@Autowired
	private UserTimeService userTimeService;
	
	@RequestMapping(value="/updateUserTime",method=RequestMethod.POST)
	@ResponseBody
	public DeviceResultModel<String> updateUserTime(HttpServletRequest request){
		
		String orgId = request.getParameter("orgId");
		String userId = request.getParameter("userId");
		String deviceId = request.getParameter("deviceId");
		String flag = request.getParameter("flag");
		DeviceResultModel<String> deviceResult = new DeviceResultModel<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(flag.equals("1")){
			Calendar currentTime = Calendar.getInstance();
			int hour = currentTime.get(Calendar.HOUR_OF_DAY);
			UserTimeModel userTime = new UserTimeModel();
			if(hour == 0){
				userTime.setZero(1);
			}else if(hour == 1){
				userTime.setOne(1);
			}else if(hour == 2){
				userTime.setTwo(1);
			}else if(hour == 3){
				userTime.setThree(1);
			}else if(hour == 4){
				userTime.setFour(1);
			}else if(hour == 5){
				userTime.setFive(1);
			}else if(hour == 6){
				userTime.setSix(1);
			}else if(hour == 7){
				userTime.setSeven(1);
			}else if(hour == 8){
				userTime.setEight(1);
			}else if(hour == 9){
				userTime.setNine(1);
			}else if(hour == 10){
				userTime.setTen(1);
			}else if(hour == 11){
				userTime.setEleven(1);
			}else if(hour == 12){
				userTime.setTwelve(1);
			}else if(hour == 13){
				userTime.setThirteen(1);
			}else if(hour == 14){
				userTime.setFourteen(1);
			}else if(hour == 15){
				userTime.setFifteen(1);
			}else if(hour == 16){
				userTime.setSixteen(1);
			}else if(hour == 17){
				userTime.setSeventeen(1);
			}else if(hour == 18){
				userTime.setEighteen(1);
			}else if(hour == 19){
				userTime.setNineteen(1);
			}else if(hour == 20){
				userTime.setTwenty(1);
			}else if(hour == 21){
				userTime.setTwentyOne(1);
			}else if(hour == 22){
				userTime.setTwentyTwo(1);
			}else if(hour == 23){
				userTime.setTwentyThree(1);
			}
			userTime.setOrgId(Integer.valueOf(orgId));
			userTime.setDeviceId(Integer.valueOf(deviceId));
			int result = 0;
			int count = userTimeService.queryUserTimeByIdAndDate(Integer.valueOf(deviceId), sdf.format(new Date()));
			if(count > 0){
				userTime.setUpdateTime(new Date());
				result = userTimeService.updateUserTime(userTime);
			}else{
				//如果当天没用用户使用的时间段，则新增，如果有则修改
	            userTime.setUserId(Integer.valueOf(userId));
	            userTime.setCreateTime(new Date());
	            userTime.setCreateBy(Integer.valueOf(userId));
	            userTime.setUpdateTime(new Date());
	            userTime.setUpdateBy(Integer.valueOf(userId));
	            result = userTimeService.insertUserTime(userTime);
			}
			if(result == 1){
				deviceResult.setMsg("success");
				deviceResult.setStatus(200);
			}else{
				deviceResult.setMsg("fail");
				deviceResult.setStatus(400);
			}
		}
		return deviceResult;
	}
	
}

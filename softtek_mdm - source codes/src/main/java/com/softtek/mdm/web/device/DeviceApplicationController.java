package com.softtek.mdm.web.device;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.softtek.mdm.bean.AppBean;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.service.ApplicationService;
import com.softtek.mdm.util.Constant.ReleaseType;
import com.softtek.mdm.web.http.BaseDTO;

/**
 * 应用管理
 * @author jane.hui
 *
 */
@Controller
@RequestMapping(value="/terminal/application")
public class DeviceApplicationController {
	
	@Autowired
	private ApplicationService applicationService;
	
	/**
	 * 应用管理
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public @ResponseBody DeviceResultModel<List<AppBean>> index(HttpServletRequest request,
			HttpServletResponse response,HttpSession session){
		DeviceResultModel<List<AppBean>> deviceResult=new DeviceResultModel<List<AppBean>>();
		List<AppBean> list = applicationService.getAppList(request, session,ReleaseType.ANDROID);
		deviceResult.setStatus(200);
		deviceResult.setMsg("success");
		deviceResult.setData(list);
		return deviceResult;
	}
	
	/**
	 * 应用管理(ios)
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getIosAppList",method=RequestMethod.GET)
	public @ResponseBody DeviceResultModel<List<AppBean>> getIosAppList(HttpServletRequest request,
			HttpServletResponse response,HttpSession session){
		DeviceResultModel<List<AppBean>> deviceResult=new DeviceResultModel<List<AppBean>>();
		List<AppBean> list = applicationService.getAppList(request, session,ReleaseType.IOS);
		deviceResult.setStatus(200);
		deviceResult.setMsg("success");
		deviceResult.setData(list);
		return deviceResult;
	}
	
	/**
	 * 获取装机数
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getInstalledNumber",method=RequestMethod.POST)
	public @ResponseBody DeviceResultModel<String> getInstalledNumber(HttpServletRequest request,HttpSession session){
		DeviceResultModel<String> deviceResult=new DeviceResultModel<String>();
		String result = applicationService.getInstalledNumber(request,session);
		if(BaseDTO.SUCCESS.equals(result)){
			deviceResult.setStatus(200);
			deviceResult.setMsg("success");
		} else {
			deviceResult.setStatus(400);
			deviceResult.setMsg("success");
		}
		return deviceResult;
	}
	
	/**
	 * 安装应用(iOS)
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/installApplication",method=RequestMethod.POST)
	public @ResponseBody DeviceResultModel<String> installApplication(HttpServletRequest request){
		return applicationService.installApplication(request);
	}
	
	/**
	 * 卸载应用
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/uninstallApplication",method=RequestMethod.POST)
	public @ResponseBody DeviceResultModel<String> uninstallApplication(HttpServletRequest request,HttpSession session){
		return applicationService.uninstallApplication(request);
	}
}
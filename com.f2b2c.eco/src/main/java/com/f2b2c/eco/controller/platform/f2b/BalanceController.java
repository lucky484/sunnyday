package com.f2b2c.eco.controller.platform.f2b;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.platform.BalanceSettingModel;
import com.f2b2c.eco.service.platform.BalanceSettingService;
/**
 * 结算业务
 * @author color.wu
 *
 */
import com.f2b2c.eco.share.dto.DTOResult;
import com.f2b2c.eco.status.MessageType;
@Controller(value="fBalaceController")
@RequestMapping(value="/farm/balance")
public class BalanceController extends BaseController {

	@Autowired
	private BalanceSettingService balanceSettingService;
	/**
	 * 参数设置页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/param",method=RequestMethod.GET)
	public final String paramsSetting(HttpServletRequest request, HttpServletResponse response,Model model){
		BalanceSettingModel entity=balanceSettingService.findFirst();
		if(null!=entity){
			model.addAttribute("model",entity);
		}
		return "platform/balance/param";
	}
	
	/**
	 * 修改参数信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public final DTOResult update(HttpServletRequest request, HttpServletResponse response){
		boolean isExists=true;
		BalanceSettingModel balanceSetting=balanceSettingService.findFirst();
		if(null==balanceSetting){
			isExists=false;
			balanceSetting=new BalanceSettingModel();
		}
		String temp=StringUtils.trimToNull(request.getParameter("param[province]"));
		if(temp!=null){
			balanceSetting.setProvince((int)(Double.valueOf(temp)*100));
		}
		temp=StringUtils.trimToNull(request.getParameter("param[city]"));
		if(temp!=null){
			balanceSetting.setCity((int)(Double.valueOf(temp)*100));
		}
		temp=StringUtils.trimToNull(request.getParameter("param[area]"));
		if(temp!=null){
			balanceSetting.setArea((int)(Double.valueOf(temp)*100));
		}
		temp=StringUtils.trimToNull(request.getParameter("param[notFruit]"));
		if(temp!=null){
			balanceSetting.setNotFruit((int)(Double.valueOf(temp)*100));
		}
		temp=StringUtils.trimToNull(request.getParameter("param[platform]"));
		if(temp!=null){
			balanceSetting.setPlatform((int)(Double.valueOf(temp)*100));
		}
		temp=StringUtils.trimToNull(request.getParameter("param[other]"));
		if(temp!=null){
			balanceSetting.setOther((int)(Double.valueOf(temp)*100));
		}
		int flag=0;
		if(isExists){
			flag=balanceSettingService.update(balanceSetting);
		}else{
			flag=balanceSettingService.insert(balanceSetting);
		}
		
		return flag>0?createDTOResultWithouti18n(MessageType.success, "设置保存成功！", null, null):
			createDTOResultWithouti18n(MessageType.error, "设置保存失败！", null, null);
	}
}

package com.softtek.mdm.web.device;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.LocaleIn;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.service.ParamSettingService;

/**
 * 系统参数信息控制器
 * @author brave.chen
 */
@Controller
@RequestMapping("/terminal/system/param")
public class SystemParamInfoControl
{
	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(SystemParamInfoControl.class);
	
	/**
	 * 参数设置服务类
	 */
	@Autowired
	private ParamSettingService paramSettingService;
	
	/**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSource;
	
	/**
	 * 保存用户与设备有关的信息
	 * 
	 * @param basic
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/querySysParams", method = RequestMethod.GET)
	public @ResponseBody DeviceResultModel<SystemParamSetModel> querySysParams(HttpServletRequest request, HttpServletResponse response,@LocaleIn Locale localeLanguage)
			throws IOException
	{
		DeviceResultModel<SystemParamSetModel> resultModel = new DeviceResultModel<SystemParamSetModel>();
		SystemParamSetModel model = null;
		try
		{
			model = paramSettingService.querySysParamSetting();
			resultModel.setStatus(200);
			resultModel.setMsg("success");
		}
		catch(Exception e)
		{
			resultModel.setStatus(404);
			String msg = messageSource.getMessage("web.device.query.system.param.resultdto.message.failed", null,localeLanguage);
			
			resultModel.setMsg(msg);
			logger.error("Query system params failed! Exception msg is " + e.getMessage());
		}
		
		resultModel.setData(model);
		return resultModel;
	}
}


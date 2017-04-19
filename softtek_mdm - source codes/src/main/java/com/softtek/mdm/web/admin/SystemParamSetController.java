
package com.softtek.mdm.web.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.EmailModelContent;
import com.softtek.mdm.model.EmailParamModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.service.EmailSendService;
import com.softtek.mdm.service.ParamSettingService;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.util.FileUtils;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.ResultDTO;

import net.sf.json.JSONObject;

/**
 * 系统参数设置控制器
 *
 * @author brave.csince JDK 1.6【请根据程序依改】
 */
@Controller
@RequestMapping("/system/param/manager")
public class SystemParamSetController extends BaseController
{
	/**
	 * 默认的logo图片
	 */
	private static final String DEFAULT_LOGO="logo.png";
	/**
	 * 用户产生新的logo时候，默认的图标会重新变成这个名称，用户图片替换为原默认图片
	 */
	private static final String DEFAULT_LOGO_BAK="_logo.png";
	/**
	 * 日志记录器
	 */
	private Logger logger = Logger.getLogger(SystemParamSetController.class);
	
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
	 * 线程池对象
	 */
	@Autowired
	private TaskExecutor taskExecutor;
	
	/**
	 * 邮件服务类
	 */
	@Autowired
	private EmailSendService emailSendService;
    
	/**
	 * 许可证管理页
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
    @Link(family="admin",label="web.admin.sysparam.manager.label",belong="web.admin.backup.belong")
    @RequestMapping(method = RequestMethod.GET)
    public final String index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            Model model) throws IOException
    {
        return "system/param/manager/index";
    }
	
    /**
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/updateMdmParam", method = RequestMethod.GET)
	@ResponseBody
	public final Object updateMdmParam(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
	{
		String address = StringUtils.trimToNull(request.getParameter("address"));
		String outControlTimeLimit = StringUtils.trimToNull(request.getParameter("outControlTimeLimit"));
		String deviceInfoCollPeriod = StringUtils.trimToNull(request.getParameter("deviceInfoCollPeriod"));
		String deviceIllegalCollPeriod = StringUtils.trimToNull(request.getParameter("deviceIllegalCollPeriod"));
		
		SystemParamSetModel spsModel = new SystemParamSetModel();
		spsModel.setId(1);
		spsModel.setMdmAddress(address);
		spsModel.setOutManagerTime(Integer.valueOf(outControlTimeLimit));
		spsModel.setDeviceInfoCollectTime(Integer.valueOf(deviceInfoCollPeriod));
		spsModel.setIllegalInfoCollectTime(Integer.valueOf(deviceIllegalCollPeriod));
		
		ResultDTO dto = new ResultDTO();
		
		try
		{
			SystemParamSetModel model = paramSettingService.querySysParamSetting();
			
			if (null != model)
			{
				paramSettingService.updateSysParamSetting(spsModel);
			}
			else
			{
				paramSettingService.addSysParamSetting(spsModel);
			}
		}
		catch(Exception e)
		{
			dto.type= ResultDTO.ERROR;
			logger.error("some excepiton hanppend, exception msg is " + e.getMessage());
			dto.message = messageSource.getMessage("web.admin.system.param.manager.resultdto.message.update.failed", null,
					LocaleContextHolder.getLocale());
			return dto;
		}
		
		// 更新成功后需要将消息推送给客户端
		Map<String, String> map = new HashMap<String, String>();
		String sysParamSetting = JSONObject.fromObject(spsModel).toString();
		map.put("sysParamSetting", sysParamSetting);
		
		MqProducerThread thread = new MqProducerThread("androidAll", null, null, 2, map);
		taskExecutor.execute(thread);
		
		dto.message = messageSource.getMessage("web.admin.system.param.manager.resultdto.message.update.success", null,
				LocaleContextHolder.getLocale());
		return dto;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateMessageAdviceParam", method = RequestMethod.GET)
	@ResponseBody
	public final Object updateMessageAdviceParam(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
	{
		ResultDTO dto = new ResultDTO();
		
		String smtpAddress = StringUtils.trimToNull(request.getParameter("smtpAddress"));
		String smtpPort = StringUtils.trimToNull(request.getParameter("smtpPort"));
		String mailUsername = StringUtils.trimToNull(request.getParameter("mailUsername"));
		String mailPassword = StringUtils.trimToNull(request.getParameter("mailPassword"));
		String sendUserMailAddress = StringUtils.trimToNull(request.getParameter("sendUserMailAddress"));
		
		
		EmailParamModel model = new EmailParamModel();
		model.setHost(smtpAddress);
		model.setPort(smtpPort);
		model.setUsername(mailUsername);
		model.setPassword(mailPassword);
		model.setSender(sendUserMailAddress);
		model.setIsSSL(0);
		
		try
		{
			paramSettingService.updateMessageAdviceParam(model);
		}
		catch(Exception e)
		{
			dto.type= ResultDTO.ERROR;
			dto.message = messageSource.getMessage("web.admin.system.param.manager.resultdto.message.update.failed", null,
					LocaleContextHolder.getLocale());
			return dto;
		}
		
		dto.message = messageSource.getMessage("web.admin.system.param.manager.resultdto.message.update.success", null,
				LocaleContextHolder.getLocale());
		return dto;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryMessageAdvice", method = RequestMethod.GET)
	@ResponseBody
	public final Object queryMessageAdvice(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
	{
		SystemParamSetModel model = new SystemParamSetModel();
		try
		{
			model = paramSettingService.querySysParamSetting();
			
		}
		catch(Exception e)
		{
			logger.error("some excepiton hanppend, exception msg is " + e.getMessage());
		}
		
		return model;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryMdmParam", method = RequestMethod.GET)
	@ResponseBody
	public final Map<String, Object> queryMdmParam(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		SystemParamSetModel sysParamModel = new SystemParamSetModel();
		map.put("result", "error");
		try
		{
			sysParamModel = paramSettingService.querySysParamSetting();
		}
		catch(Exception e)
		{
			logger.error("some excepiton hanppend, exception msg is " + e.getMessage());
		}
		map.put("result", "success");
		map.put("sysParamModel", sysParamModel);
		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryEmailModels", method = RequestMethod.GET)
	@ResponseBody
	public final Map<String, Object> queryEmailModels(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		List<EmailModelContent> cotentList= new ArrayList<EmailModelContent>();
		try
		{
			cotentList = paramSettingService.queryEmailModels();
			
		}
		catch(Exception e)
		{
			logger.error("some excepiton hanppend, exception msg is " + e.getMessage());
		}
		map.put("cotentList", cotentList);
		return map;
	}
	
	/**
	 * 更换图标
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatelogo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatelogo(MultipartFile files, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 Map<String, Object> map=new HashMap<String, Object>();
		if(files!=null){
			String path=FileUtils.getResourcesPath()+"/image/";
			File file =new File(path+DEFAULT_LOGO_BAK);
			if(file!=null&&!file.exists()){
				//将原先的logo重名称
				File defaultLogo =new File(path+DEFAULT_LOGO);
				defaultLogo.renameTo(file);
			}
			//存储用户的图片
			FileUtils.saveUploadFile(files.getInputStream(),path+DEFAULT_LOGO);
			map.put("result", "success");
			return map;
		} 
		map.put("result", "failed");
		return map;
}
	
	/**
	 * 确认修改系统风格
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifystyle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifystyle(MultipartFile files,String ischangelogo,String logopath,String  copyright,HttpSession session,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServletContext s1=request.getServletContext();  
		Map<String, Object> map=new HashMap<String, Object>();
		String path= request.getSession().getServletContext().getRealPath(""); 
		paramSettingService.modifystyle(files,s1,ischangelogo,logopath,copyright,path);
		return map;
	}
	
	@RequestMapping(value = "/queryEmailParams", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryEmailParams(HttpServletRequest request,HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		EmailParamModel emailParamModel = new EmailParamModel();
		map.put("result", "failed");
		try
		{
			emailParamModel = paramSettingService.queryEmailParams();
		}
		catch(Exception e)
		{
			logger.error("query email params failed,error message is " + e.getMessage());
			
		}
		
		map.put("emailParamModel", emailParamModel);
		map.put("result", "success");
		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/updateEmailModels", method = RequestMethod.GET)
	@ResponseBody
	public Object updateEmailModels(HttpServletRequest request,HttpServletResponse response, DataGridModel params)
	{
		ResultDTO dto = new ResultDTO();
		Object emailModels = params.getParams().get("emailModels");
		try
		{
			// json字符串转换成javabean对象
			Gson gson = new Gson();
			List<EmailModelContent> emailModelContents = gson.fromJson((String) emailModels, new TypeToken<List<EmailModelContent>>()
			{
			}.getType());

			if (CollectionUtils.isNotEmpty(emailModelContents))
			{
				paramSettingService.updateEmailModelsContent(emailModelContents);
			}
		}
		catch(Exception e)
		{
			logger.error("update email params failed,error message is " + e.getMessage());
			dto.type = ResultDTO.ERROR;
			return dto;
		}
		
		return dto;
	}
	
	/**
	 * 重置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetstyle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetstyle(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		 Map<String, Object> map=new HashMap<String, Object>();
		String path=FileUtils.getResourcesPath()+"/images/";
		File file=new File(path+DEFAULT_LOGO_BAK);
		if(file!=null&&file.exists()){
			File logo=new File(path+DEFAULT_LOGO);
			if(logo!=null&&logo.exists()){
				logo.delete();
			}
			file.renameTo(logo);
		    map.put("success","success");
		}else{
			map.put("success","failed");
		}
		return map;
	}
	
	@RequestMapping(value = "/sendTestMailByEmailParam", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO sendTestMailByEmailParam(HttpServletRequest request, HttpServletResponse response)
	{
		String smtpAddress = StringUtils.trimToNull(request.getParameter("smtpAddress"));
		String smtpPort = StringUtils.trimToNull(request.getParameter("smtpPort"));
		String mailUsername = StringUtils.trimToNull(request.getParameter("mailUsername"));
		String mailPassword = StringUtils.trimToNull(request.getParameter("mailPassword"));
		String sendUserMailAddress = StringUtils.trimToNull(request.getParameter("sendUserMailAddress"));
		// 2表示测试邮件类型
		EmailModelContent mailModelContent  = paramSettingService.queryEmailModelContentByType(3);
		
		ResultDTO dto = new ResultDTO();
		try
		{
			EmailParamModel email = new EmailParamModel();
			email.setHost(smtpAddress);
			email.setPort(smtpPort);
			email.setUsername(mailUsername);
			email.setPassword(mailPassword);
			List<String> toList = new ArrayList<String>();
			toList.add(sendUserMailAddress);
			email.setTo(toList);
			email.setIsSSL(0);
			email.setSubject(mailModelContent.getTheme());
			email.setText(mailModelContent.getContent());
			emailSendService.sendMail(email);
		}
		catch (AddressException e)
		{
			dto.type = ResultDTO.ERROR;
			logger.error("address is not available or correct, exception message is " + e.getMessage());
		}
		catch (MessagingException e)
		{
			dto.type = ResultDTO.ERROR;
			logger.error("send mail failed, the exception message is " + e.getMessage());
		}
		return dto;
	}
}
	



package com.softtek.mdm.web.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.LicenseInfoModel;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.util.LicenseUtil;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;

/**
 * Description: 许可证管理类
 * date: May 20, 2016 10:24:38 AM
 *
 * @author brave.chen
 */
@Controller
@RequestMapping("/system/license/manager")
public class LicenseManagerController extends BaseController
{
	/**
	 * 日志记录器
	 */
	private Logger logger = Logger.getLogger(LicenseManagerController.class);
	
	/**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSource;
	
	/**
	 * 许可证服务类
	 */
	@Autowired
	private LicenseService licenseService;
	
	/**
	 * 许可证管理页
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@Link(family="admin",label="web.admin.license.manager.label",belong="web.admin.backup.belong")
    @RequestMapping(method = RequestMethod.GET)
    public final String index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            Model model) throws IOException
    {
        return "system/lisence/manager/index";
    }
	
	/**
	 * 获取license信息
	 * @return
	 */
	@RequestMapping(value = "/getLicenseInfo", method = RequestMethod.GET)
    @ResponseBody
	public Map<String, Object> getLicenseInfo()
	{
		return generateLicenseInfo();
	}
	
	/**
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadLicenseFile", method = RequestMethod.POST)
    @ResponseBody
	public final Object uploadLicenseFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
    {
		return licenseService.uploadLicenseFile(file);
    }
	
	/**
	 * 
	 * 生成机器码返回给页面
	 *
	 * @author brave.chen
	 * @param activeCode 激活码
	 * @param request 请求
	 * @param response 响应
	 * @param session session对象
	 * @return 机器码
	 * @throws IOException
	 */
	@RequestMapping(value = "/generateMachineCode", method = RequestMethod.POST)
    @ResponseBody
	public final Map<String, Object> generateMachineCode(String activeCode, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
    {
		return licenseService.generateMachineCode(activeCode);
    }
	
	/**
	 * 生成license对象
	 *
	 * @author brave.chen
	 * @param licenseInfo license字符串
	 * @param request 请求
	 * @param response 响应
	 * @param session session对象
	 * @return license对象
	 * @throws IOException
	 */
	@RequestMapping(value = "/generateLicenseInfo", method = RequestMethod.POST)
    @ResponseBody
	public final Map<String,Object> generateLicenseInfo()
    {
		return licenseService.generateLicenseInfo();
    }
	
	/**
	 * 生成license对象
	 *
	 * @author brave.chen
	 * @param request 请求
	 * @param response 响应
	 * @param session session对象
	 */
	@RequestMapping(value = "/updateLicenseInfo", method = RequestMethod.POST)
    @ResponseBody
	public final Object updateLicenseInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
		Map<String, Object> map = new HashMap<String, Object>();
		ResultDTO dto = new ResultDTO();
		
		String licenseAdviceDay = StringUtils.trimToNull(request.getParameter("licenseAdviceDay"));
		String activeCode = StringUtils.trimToNull(request.getParameter("activeCode"));
		String machineFilePath = StringUtils.trimToNull(request.getParameter("machineCode"));
		
		LicenseInfoModel model = new LicenseInfoModel();
		model.setId(1);
		model.setActiveCode(activeCode);
		model.setAdviceDays(Integer.valueOf(licenseAdviceDay));
		model.setAdviceDays(Integer.valueOf(licenseAdviceDay == null ? "0" : licenseAdviceDay));
		model.setMachineFilePath(machineFilePath);
		try
		{
			LicenseInfoModel licenseInfoModel = licenseService.queryLicenseInfo();
			if (null == licenseInfoModel || null == licenseInfoModel.getLicenceFileBytes() || !activeCode.equals(licenseInfoModel.getActiveCode()))
			{
				byte[] bt = licenseService.getLicenseFileBytes();
				model.setLicenceFileBytes(bt);
			}
			
			if (null == licenseInfoModel)
			{
				licenseService.addLicenseInfoModel(model);
			}
			else
			{
				licenseService.updateLicenseInfo(model);
			}
			
			map = licenseService.checkLicense(activeCode, LicenseUtil.getCPUSerial(), LicenseUtil.getLocalMac());
			
			// 只有零的时候表示激活码，cpu和mac是匹配的
			if (MapUtils.isEmpty(map))
			{
				dto.type = BaseDTO.ERROR;
				dto.message = messageSource.getMessage("web.admin.system.license.manager.updatelicenseinfo.resultdto.message.license.failed", null,
						LocaleContextHolder.getLocale());
				return dto;
			}
		}
		catch(Exception e)
		{
			dto.type = BaseDTO.ERROR;
			dto.message = messageSource.getMessage("web.admin..system.license.manager.updatelicenseinfo.resultdto.message.failed", null,
					LocaleContextHolder.getLocale());
			logger.error("update licenseinfo failed, exception is " + e.getMessage());
			return dto;
		}
		
		request.getSession().setAttribute("islicense", true);
		dto.result = map;
		return dto;
    }
	
	
	/**
	 * 生成license对象
	 *
	 * @author brave.chen
	 * @param request 请求
	 * @param response 响应
	 * @param session session对象
	 * @throws IOException 
	 */
	@RequestMapping(value = "/downLoadMachineCodeFile", method = RequestMethod.GET)
	public final void downLoadMachineCodeFile(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
    {
		String mcFilePath = licenseService.getMachineCodeFilePath();
		InputStream is = null;
		FileInputStream fis = null;
		OutputStream os = null;
		
		try
		{
			String fileName = mcFilePath.substring(mcFilePath.lastIndexOf(File.separator) + 1,mcFilePath.length());
			response.setContentType("application/x-download");
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			File file  = new File(mcFilePath);
			int length = (int)file.length();
			is = new FileInputStream(file);
			byte[] buffer = new byte[length];
			is.read(buffer, 0, length);
			os = response.getOutputStream();
			os.write(buffer);
			os.flush();
		}
		catch (UnsupportedEncodingException e)
		{
			logger.error("the response do not support uft-8 encoding, exception message is " + e.getMessage());
		}
		catch (FileNotFoundException e)
		{
			
			String message = messageSource.getMessage("web.admin.system.license.manager.downloadmachinecodefile.licensefile.not.exist", null,
					LocaleContextHolder.getLocale());
			request.setAttribute("message", message);
			logger.error("file not find excepiton, exception message is " + e.getMessage());
		}
		catch (IOException e)
		{
			String message = messageSource.getMessage("web.admin.system.license.manager.downloadmachinecodefile.licensefile.failed", null,
					LocaleContextHolder.getLocale());
			request.setAttribute("message", message);
			logger.error("io exception hannpend, exception message is " + e.getMessage());
		}
		finally
		{
			try
			{
				if (null != fis)
				{
					fis.close();
				}
				if (os != null)
				{
					os.close();
				}
				if ( is != null)
				{
					is.close();
				}
			}
			catch (IOException e)
			{
				logger.error("io close failed, exception message is " + e.getMessage());
			}
		}
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/isActiveCodeSame", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> isActiveCodeSame(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		// map中的result默认为false表示激活码不相同
		map.put("isSame", false);
		String activeCode = StringUtils.trimToNull(request.getParameter("activeCode"));
		
		if (StringUtils.isNotEmpty(activeCode))
		{
			LicenseInfoModel licenseInfoModel = licenseService.queryLicenseInfo();
			if (null != licenseInfoModel && activeCode.equals(licenseInfoModel.getActiveCode()))
			{
				map.put("isSame", true);
			}
		}
		return map;
	}
}


package com.softtek.mdm.web.device;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.LocaleIn;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.service.OrganizationService;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.TerminalService;
import com.softtek.mdm.service.UserService;


/**
 * 处理设备登录和基本连接配置
 * 
 * @author color.wu
 */
@Controller()
@RequestMapping(value = "/terminal")
public class TerminalController {
	@Autowired
	private UserService userService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private TerminalService deviceLoginService;
	@Autowired
	private LicenseService licenseService;
	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * 验证设备配置信息
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public @ResponseBody DeviceResultModel<String> checkConfiguration(HttpServletRequest request,
			HttpServletResponse response, 
			HttpSession session,@LocaleIn Locale localeLanguage) throws IOException {
		String hostStr = StringUtils.trimToEmpty(request.getParameter("host"));
		String portStr = StringUtils.trimToEmpty(request.getParameter("port"));
		String orgName = StringUtils.trimToEmpty(request.getParameter("orgname"));
		String mString="config error";
		
		if (StringUtils.isNotBlank(hostStr) && StringUtils.isNotBlank(portStr) && StringUtils.isNotBlank(orgName)) {
			List<OrganizationModel> orgs = organizationService.findAll();
			
			boolean flag = false;
			Integer orgId = 0;
			
			if(CollectionUtils.isNotEmpty(orgs)){
				for (int i=0;i<orgs.size();i++) {
					OrganizationModel org=orgs.get(i);
					if (orgName.equals(org.getName())) {
						flag = true;
						orgId = org.getId();
						break;
					}
				}
			}
			
			if ((hostStr.equals(request.getServerName()) 
				|| hostStr.equals(request.getLocalAddr()))
				&& portStr.equals(String.valueOf(request.getServerPort())) 
				&& flag) {
				mString=messageSource.getMessage("web.device.devicelogincontroller.checkconfiguration.success", null, localeLanguage);
				return new DeviceResultModel<String>(200, mString, orgId.toString());
			}
		}else{
			mString=messageSource.getMessage("web.device.devicelogincontroller.checkconfiguration.failed", null, localeLanguage);
			return new DeviceResultModel<String>(401, mString, null);
		}
		return new DeviceResultModel<String>(404, mString, null);
	}
}

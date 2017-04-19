package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceManagerService;
import com.softtek.mdm.service.DeviceRuleService;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.LicenseService;
import com.softtek.mdm.service.OrganizationService;
import com.softtek.mdm.service.ParamSettingService;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.TreeManager;

/**
 * 机构管理员首页操作
 * 
 * @author color.wu
 *
 */
@Controller
@RequestMapping(value = "/institution")
public class HomeController {
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private DeviceManagerService deviceManagerService;
	@Autowired
	private DeviceRuleService deviceRuleService;
	@Autowired
	private TreeManager treeManager;
	@Autowired
	private ParamSettingService paramSettingService;
	@Autowired
	private LicenseService licenseService;
	
    /**
     * 进入首页
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@SuppressWarnings("unchecked")
	@Link(family = "institution", label = "web.institution.homecontroller.index.link.label")
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)
			throws IOException {
		
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<OrganizationModel> orgList = organizationService.findEnableOrganizationRecordsByManagerId(manager.getId());
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		int orgid = organization.getId();
		List<Integer> idlist = new ArrayList<Integer>();
        // 判断是否是管理员
		if (manager.getUser() != null) {
			Object object=session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			if(object instanceof List){
				List<StructureModel> lt = (List<StructureModel>) object;
				if (CollectionUtils.isNotEmpty(lt)) {
					idlist=(List<Integer>) CollectionUtils.collect(lt, new Transformer() {
						@Override
						public Object transform(Object input) {
							StructureModel s = (StructureModel) input;
							return s.getId();
						}
					});
				}
			}
		} else {
			idlist = null;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
        // 用户总数
		int userCount = userService.findCountByOrgId(orgid, idlist);
        // 设备策略总数
		int devicePolicyCount = deviceService.getDevicePolicySize(orgid);
        // 设备规则总数
		int deviceRoleCount = deviceRuleService.findDeviceRoleCount(orgid);
        // 用户策略总数
		int policyCount = policyService.queryPolicyCount(orgid);
        // 设备总数
        int deviceCount = deviceManagerService.findAllDeviceCount(orgid, idlist);
        // 破解总数
		int breakStatus = deviceManagerService.findBreakStatusCount(orgid, idlist);
        // 违反规则总数
		int irrCount = deviceManagerService.findIrrCount(orgid, idlist);
        // 待监控总数
		int notMonitoredCount = 0;
        // 托管中总数
		int trusteeshipCount = 0;
        // 注销中
		int cancellationCount = 0;
        // 监控中
		int monitoredCount = 0;
        SystemParamSetModel param=paramSettingService.querySysParamSetting();
        // 分钟*60=时间戳
        Integer outtime=Integer.valueOf(null==StringUtils.trimToNull(param.getOutManagerTime().toString())?"30":param.getOutManagerTime().toString().trim())*60;
		List<DeviceManagerModel> devicebasic = (List<DeviceManagerModel>) deviceManagerService.findAllByOrgID(orgid,
				idlist);
		if (CollectionUtils.isNotEmpty(devicebasic)) {
			for (DeviceManagerModel db : devicebasic) {
				Date a = db.getLast_collection_time();
				Date newdate = new Date();
				Integer isactive = db.getIsActive();
				Integer device_status = db.getDevice_status();
				long s = (newdate.getTime() - a.getTime()) / 1000;
				
				if (device_status ==(Integer)1) {
					cancellationCount++;
				} else {
					if (s >= outtime) {
						trusteeshipCount++;
					} else {
						if (isactive == (Integer)0) {
							notMonitoredCount++;
						} else {
							monitoredCount++;
						}
					}
				}
			}
		}

        // 设备丢失总数
		int deviceLostCount = deviceManagerService.findDeviceLostCount(orgid, idlist);
        // 安卓设备数量
		int androidCount = deviceBasicInfoService.findCountByOrgId(orgid, 1, idlist);
		int iosCount = deviceBasicInfoService.findCountByOrgId(orgid, 0, idlist);
		map.put("userCount", userCount);
		map.put("androidCount", androidCount);
		map.put("iosCount", iosCount);
		map.put("devicePolicyCount", devicePolicyCount);
		map.put("policyCount", policyCount);
        map.put("deviceCount", deviceCount);
		map.put("deviceRoleCount", deviceRoleCount);
		map.put("breakStatusCount", breakStatus);
		map.put("irrCount", irrCount);
        // 待监控数
		map.put("notMonitoredCount", notMonitoredCount);
        // 托管中数
		map.put("trusteeshipCount", trusteeshipCount);
		/* map.put("noPasswordCount",noPasswordCount); */
        // 丢失数
		map.put("deviceLostCount", deviceLostCount);
        // 注销中数
		map.put("cancellationCount", cancellationCount);
        // 监控中数
		map.put("monitoredCount", monitoredCount);
		model.addAttribute("orgs", orgList);
		model.addAttribute("idlist", idlist);
		model.addAttribute("map", map);
		
		int days=licenseService.diffEndTimeAndInformDays();
		if(days>=0){
			model.addAttribute("deadtime",days);
		}
		
		return "institution/home/index";
	}

	/**
     * 调整layout布局
     * 
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/aside", method = RequestMethod.POST)
	public void aside(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String aside = (String)session.getAttribute("aside");
		session.setAttribute("aside", "0".equals(aside)?"1":"0");
	}

	/**
     * 更改组织机构
     * 
     * @param iid
     * @param session
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/exchange", method = RequestMethod.POST)
	public @ResponseBody String exchange(Integer iid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OrganizationModel organization = organizationService.findOne(iid);
		session.setAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString(), organization);
		treeManager.InitTreeSession(session);
		return "success";
	}

	/**
     * 管理员转普通用户
     * 
     * @param request
     * @param response
     * @param session
     * @param attrs
     * @param model
     * @return
     */
	@RequestMapping(value = "/tocustomer", method = RequestMethod.GET)
	public final String toCustomer(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			RedirectAttributes attrs, Model model) {
		ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		UserModel user = userService.findOne(manager.getUser().getId());
		session.setAttribute(SessionStatus.SOFTTEK_CUSTOMER.toString(), user);
		return "redirect:/customer";
	}

}

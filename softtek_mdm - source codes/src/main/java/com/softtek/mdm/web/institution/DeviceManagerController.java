package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceLocationModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.DeviceNetworkStatusModel;
import com.softtek.mdm.model.DeviceSaftyModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.MessageSendModel;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PushMobileMsgModel;
import com.softtek.mdm.model.PushMsgHistoryModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.VirtualGroupModel;
import com.softtek.mdm.service.DeviceAppInfoService;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceLegalListService;
import com.softtek.mdm.service.DeviceLegalRecordService;
import com.softtek.mdm.service.DeviceManagerService;
import com.softtek.mdm.service.DeviceNetworkStatusService;
import com.softtek.mdm.service.DeviceSaftyService;
import com.softtek.mdm.service.ParamSettingService;
import com.softtek.mdm.service.PushMobileMsgService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.service.SysmanageDeviceLogService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.service.UserVirtualGroupService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.IosPushUtil;
import com.softtek.mdm.util.TreeManager;
import com.softtek.mdm.web.http.BaseDTO;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("institution/deviceManager")
public class DeviceManagerController {
     
	@Autowired
	private StructureService structureService;
	
	@Autowired
	private TreeManager treeManager;
	
	@Autowired
	private DeviceManagerService deviceManagerService;
	
	@Autowired
	private PushMobileMsgService pushMobileMsgService;
	
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	
	@Autowired
	private DeviceSaftyService deviceSaftyService;
	
	@Autowired
	private DeviceAppInfoService deviceAppInfoService;
	
	@Autowired
	private DeviceLegalListService deviceLegalListService;
	
	@Autowired
	private DeviceNetworkStatusService deviceNetworkStatusService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserVirtualGroupService userVirtualGroupService;
	
	@Autowired
	private DeviceLegalRecordService deviceLegalRecordService;
	
	@Autowired
	private SysmanageDeviceLogService sysmanageDeviceLogService;
	
	@Autowired
	private ParamSettingService paramSettingService;
	
	@Autowired
	private MessageSource messageSource;
	
	public static List<DeviceManagerModel> searchList;
	
    /**
     * 线程池对象
     */
	@Autowired
	private TaskExecutor taskExecutor;
	
	private Date locationRequestDate;

	@Link(family = "institution", label = "institution.policy.index.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.device.index.link.belong")
	@RequestMapping(method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpSession session,Model model){
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		@SuppressWarnings("unchecked")
		List<StructureModel> list= (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		NodeModel nodes=treeManager.bulidTreeIncludeDevice(list,organization.getId());
		String jsonStr="["+JSONObject.fromObject(nodes).toString()+"]";
		jsonStr=StringUtil.replace(jsonStr, "\"nodes\":[],", "");
		model.addAttribute("tree", jsonStr);
		return "institution/deviceManager/index";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="queryDeviceInfo",method=RequestMethod.GET)
	@ResponseBody
	public Page queryDeviceInfo(HttpServletRequest request,HttpSession session,Integer start,Integer length){
		Page page = new Page();
		searchList = null;
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer id;
		if("null".equals(request.getParameter("id"))){
			id = null;
		}else{
			id = Integer.valueOf(request.getParameter("id"));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		String seletedStatus = StringUtils.trimToEmpty(request.getParameter("seletedStatus"));
		Integer status  = StringUtils.isEmpty(seletedStatus) ? null : Integer.valueOf(seletedStatus);
        String iosstatus = StringUtils.trimToEmpty(request.getParameter("iosStatus"));
        Integer iosStatus = StringUtils.isEmpty(iosstatus) ? null : Integer.valueOf(iosstatus);
		String searchUserName = request.getParameter("searchUserName");
		String deviceType = request.getParameter("deviceType");
		String sequenceNumber = request.getParameter("sequenceNumber");
		String esnorimei = request.getParameter("esnorimei");
		List<StructureModel> list= (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		map.put("seletedStatus", status);
        map.put("iosStatus", iosStatus);
		map.put("searchUserName", searchUserName);
		map.put("deviceType", deviceType);
		map.put("sequenceNumber",sequenceNumber);
		map.put("esnorimei",esnorimei);
		map.put("begin",start);
		map.put("num",length);
        // 将查询条件放到session中，方便导出
		session.setAttribute("id", id);
		session.setAttribute("seletedStatus", status);
		session.setAttribute("searchUserName", searchUserName);
		session.setAttribute("deviceType", deviceType);
		session.setAttribute("sequenceNumber", sequenceNumber);
		session.setAttribute("esnorimei", esnorimei);
		page =  deviceManagerService.queryDeviceInfo(id, organization.getId(), map,list);
		if(status != null 
		   || StringUtils.isNotEmpty(searchUserName) 
		   || StringUtils.isNotEmpty(deviceType)
		   || StringUtils.isNotEmpty(sequenceNumber)
		   || StringUtils.isNotEmpty(esnorimei)){
			searchList = page.getData();
		}
		return page;
	}
	
	/**
     * 查询图文消息列表
     * 
     * @param params
     * @param request
     * @param model
     * @return
     */
	@RequestMapping(value="/queryPicInfoList")
	@ResponseBody
	public Page queryNetNameList(Integer start, Integer length,HttpServletRequest request,HttpSession session)
	{
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("pageNum", start == null ? 0 : start);
		map.put("pageSize", length == null ? 0 : length);
		map.put("orgId", organization.getId());
        return pushMobileMsgService.queryPushMsgListByParams(map);
	}
	
	@Log(operateType="logs.picandinfo.type.report",operateContent="logs.picandinfo.content.report",args={"createName","pushInfoTheme","userIds","udids"})
	@RequestMapping(value = "/pushMsgToMobile",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> reportPicAndInfoToMobile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	{
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
        Object obj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
        if (obj == null) {
        	returnMap.put("type",BaseDTO.ERROR);
            returnMap.put("message", "Session失效,请重新刷新");
            return returnMap;
        }
        
        Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
        if (orgObj == null) {
        	returnMap.put("type",BaseDTO.ERROR);
            returnMap.put("message", "Session失效,请重新刷新");
            return returnMap;
        }
        
        ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
        String createName = managerModel.getName();
		request.setAttribute("createName", createName);
        
		String picInfoId = StringUtils.trimToEmpty(request.getParameter("picInfoId"));
		
		String sns = StringUtils.trimToEmpty(request.getParameter("sns"));
		String uerIds = StringUtils.trimToEmpty(request.getParameter("userIds"));
		
		String[] snArr = sns.split(Constant.SplitSymbol.COMMA_SYMBOL);
		String[] userIdArr = uerIds.split(Constant.SplitSymbol.COMMA_SYMBOL);
		List<String> snList = Arrays.asList(snArr);
		List<String> userIdList = Arrays.asList(userIdArr);
		
		PushMobileMsgModel model = null;
		Long msgId = StringUtils.isEmpty(picInfoId) ? null : Long.valueOf(picInfoId);
		try
		{
			if (StringUtils.isNotEmpty(picInfoId))
			{
				model = pushMobileMsgService.queryPushMsgById(msgId);
                request.setAttribute("pushInfoTheme", null == model ? "未知" : model.getMsgTheme());
			}
			
			List<PushMsgHistoryModel> pushMsgHistoryModelList = new ArrayList<PushMsgHistoryModel>();
			if (null != model && CollectionUtils.isNotEmpty(snList))
			{
				OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
				
				MessageSendModel pMsgModel = getMessageSendModel(model, managerModel);
				
				
				Integer orgId = organization.getId();
				String pushMsgSend = JSONObject.fromObject(pMsgModel).toString();
				
				for (int i = 0; i < snList.size(); i++)
				{   
					PushMsgHistoryModel pushMsgHisModel = getPushMsgHisModel(snList.get(i), userIdList.get(i), model, orgId,
							createName);
					
					pushMsgHistoryModelList.add(pushMsgHisModel);
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("pushMsgSend", pushMsgSend);
				}
				
				pushMobileMsgService.savePushHistory(pushMsgHistoryModelList);
				
				returnMap.put("type",BaseDTO.SUCCESS);
                returnMap.put("message", "成功");

			}
		}
		catch(Exception e)
		{
			returnMap.put("type",BaseDTO.ERROR);
        	returnMap.put("message", e.getMessage());
		}
        return returnMap;
	}

	private PushMsgHistoryModel getPushMsgHisModel(String udid, String userId, PushMobileMsgModel model,
			Integer orgId, String createName)
	{
		PushMsgHistoryModel pushMsgHisModel = new PushMsgHistoryModel();
		pushMsgHisModel.setCreateUser(createName);
		pushMsgHisModel.setCreateTime(new Date());
		pushMsgHisModel.setPushMobileMsgModel(model);
		pushMsgHisModel.setUdId(udid);
		pushMsgHisModel.setUserId(Integer.valueOf(userId));
		pushMsgHisModel.setOrgId(orgId);
		return pushMsgHisModel;
	}

	private MessageSendModel getMessageSendModel(PushMobileMsgModel model, ManagerModel managerModel)
	{
		MessageSendModel pMsgModel = new MessageSendModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		pMsgModel.setMessage_title(model.getMsgTheme());
		pMsgModel.setMessage(model.getMsgTheme());
		pMsgModel.setCreateBy(managerModel.getId());
		pMsgModel.setCreateTime(new Date());
		pMsgModel.setUpdateTime(new Date());
		pMsgModel.setUpdateBy(managerModel.getId());
		pMsgModel.setName(managerModel.getName());
		pMsgModel.setCreateDateStr(sdf.format(new Date()));
		pMsgModel.setMsgId(String.valueOf(model.getId()));
		
		String imgUrls = StringUtils.trimToEmpty(model.getPicUrl());
		String[] imgUrlArr = imgUrls.split(Constant.SplitSymbol.COMMA_SYMBOL);
		pMsgModel.setImgUrl(imgUrlArr[0]);
		return pMsgModel;
	}
	
	/**
     * 设备详细信息
     */
	@RequestMapping(value="/deviceBasicInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deviceBasicInfo(HttpServletRequest request,Integer userId){
		Map<String,Object> map = new HashMap<String, Object>();
		List<DeviceBasicInfoModel> list = deviceBasicInfoService.findByUserId(userId);
		if(list != null && list.size() > 0){
			DeviceSaftyModel deviceSafty = deviceSaftyService.findOneWithDeviceId(list.get(0).getId());
			list.get(0).setIsActive(deviceSafty == null ? 0 : deviceSafty.getViolate());
		}
		map.put("list", list);
		return map;
	}
	
	@RequestMapping(value="/selectDevice",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> selectDevice(Integer userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		DeviceBasicInfoModel basicInfo = deviceBasicInfoService.findOne(userId);
		SystemParamSetModel systemParamSet = paramSettingService.querySysParamSetting();
		if( "android".equalsIgnoreCase(basicInfo.getDevice_type())){
            if (!"1".equals(basicInfo.getDevice_status())) { // 1-注销中
				if(((float)(new Date().getTime() -  basicInfo.getLast_collection_time().getTime())/60000) > (float)systemParamSet.getOutManagerTime()){
                    basicInfo.setDevice_status("2"); // 托管中
				}else{
					if( "0".equals(basicInfo.getIsActive())){
                        basicInfo.setDevice_status("3"); // 待监控
					}else{
                        basicInfo.setDevice_status("4"); // 监控中
					}
				}
			}
		}
		if (basicInfo != null) {
			DeviceSaftyModel deviceSafty = deviceSaftyService.findOneWithDeviceId(basicInfo.getId());
			basicInfo.getUser().setWeight(deviceSafty == null ? 0 : deviceSafty.getViolate());
		}
		map.put("basicInfo",basicInfo);
		return map;
	}
	
	@RequestMapping(value="/appInfo")
	@ResponseBody
	public Page appInfo(Integer userId,Integer start, Integer length,HttpServletRequest request){
		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		Page page = deviceAppInfoService.findByPage(userId, start, length);
		return page;
	}
	
	@RequestMapping(value="/illegalInfo")
	@ResponseBody
	public Page illegalInfo(Integer userId, Integer start, Integer length,HttpServletRequest request){
		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		Page page = deviceLegalListService.findIllegalListWithDeviceId(userId, start, length);
		return page;
	}
	
	@RequestMapping(value = "/netInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> netInfo(Integer userId,  HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
			DeviceNetworkStatusModel deviceNetworkStatus = deviceNetworkStatusService.findOne(userId);
			if (deviceNetworkStatus == null) {
				deviceNetworkStatus = new DeviceNetworkStatusModel();
			}
			map.put("deviceNetworkStatus", deviceNetworkStatus);
			return map;
	}
	
	@RequestMapping(value="/deviceSaftyInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deviceSaftyInfo(Integer userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		DeviceSaftyModel deviceSafty = deviceSaftyService.findOneWithDeviceId(userId);
		if (deviceSafty != null) {
			map.put("deviceSafty", deviceSafty);
		} else {
			map.put("deviceSafty", new DeviceSaftyModel());
		}
		return map;
	}
	
	@RequestMapping(value="/userInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getUserInfoMap(Integer userId, HttpSession session) throws IOException {
		DeviceManagerModel deviceManager = deviceManagerService.queryUserId(userId);
		UserModel user = null;
		user = userService.findOne(deviceManager.getUserId());
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("username", user.getUsername());
		maps.put("realname", user.getRealname());
		StructureModel temp = null;
		temp = structureService.getParents(user.getStructure().getId());
		if (temp != null) {
			String belongStr = temp.getName();
			while (temp.getParent() != null) {
				belongStr = StringUtil.insert(belongStr, temp.getParent().getName() + "/");
				temp = temp.getParent();
			}
			maps.put("department", belongStr);
		}
		maps.put("phone", user.getPhone());
		maps.put("email", user.getEmail());
		DateTime dt = new DateTime(user.getCreateTime().getTime());
		maps.put("createtime", dt.toString("yyyy/MM/dd HH:mm:ss"));
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		List<VirtualGroupModel> vtllist = (List<VirtualGroupModel>) userVirtualGroupService.findVtlGroupsByUser(organization.getId(), user.getId());
		String belongVtls = "";
		if (CollectionUtils.isNotEmpty(vtllist)) {
			for (VirtualGroupModel vtl : vtllist) {
				belongVtls = StringUtil.insert(belongVtls, vtl.getName() + "/");
			}
			maps.put("vtl", StringUtil.truncate(belongVtls, belongVtls.length() - 1));
		} else {
			maps.put("vtl", "");
		}
		maps.put("mark", user.getMark());
		maps.put("weight", user.getWeight() == null ? "0" : user.getWeight().toString());
		maps.put("gender", user.getGender() == null ? "0" : user.getGender().toString());
		maps.put("sign", user.getSign());
		maps.put("address", user.getAddress());
		maps.put("office", user.getOffice_phone());
		maps.put("position", user.getPosition());
		maps.put("orgName",organization.getName());
		return maps;
	}
	
	@RequestMapping(value="/illegalRecordInfo")
	@ResponseBody
    public Page illegalRecordInfo(Integer did, Integer start, Integer length){
    	start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		Page page = deviceLegalRecordService.findLegalRecordByDeviceId(did, start, length);
		return page;
    }
    
	@RequestMapping(value="deviceLogInfo")
	@ResponseBody
	public Page deviceLogInfo(Integer did, Integer start, Integer length,HttpServletRequest request){
		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		Page page = sysmanageDeviceLogService.findRecordWithDeviceId(did, start, length);
		return page;
	}
    @RequestMapping(value="/deviceLocation")
    @ResponseBody
	public Map<String,Object> deviceLocation(Integer did,Integer userId,HttpServletRequest request){
    	Date date = new Date();
    	locationRequestDate = date;
    	Map<String,Object> dateMap = new HashMap<String,Object>();
    	DeviceManagerModel deviceManager = deviceManagerService.queryDeviceInfoById(did);
    	Map<String, String> map = new HashMap<String, String>();
    	List<String> deviceToken = new ArrayList<String>();
		if (userId != null) {
			map.put("uploadxy", "1");
			map.put("sn", deviceManager.getSn());
			if("android".equalsIgnoreCase(deviceManager.getDevice_type())){
				MqProducerThread mqProducerThread=new MqProducerThread(userId.toString(), null, null, 2, map);
				taskExecutor.execute(mqProducerThread);
			}else if(deviceManager.getDevice_type().equals("ios")){
				deviceToken.add(deviceManager.getDeviceToken().replaceAll(" ", ""));
			}
			if(deviceToken.size() > 0){
				IosPushUtil.pushDataToIosTr(deviceToken, null, 1, "", map);
			}
	   }
		dateMap.put("locationTime", CommUtil.Date2String(date));
		return dateMap;
	}
    
    @RequestMapping(value="/getLocation")
    @ResponseBody
	public Map<String, Object> getLocation(String did,Integer times, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(times > 0){
		   DeviceLocationModel deviceLocation = deviceManagerService.queryDeviceLocation(Integer.valueOf(did));
		   if(deviceLocation != null){
			   if(deviceLocation.getCreateTime().getTime() > locationRequestDate.getTime()){
				   map.put("locationStatus","success");
                    map.put("deviceLocation", deviceLocation); // 定位成功
				   map.put("locationRequestDate", CommUtil.Date2String(locationRequestDate));
			   }else{
                    map.put("locationStatus", "locationing"); // 定位中
				   map.put("locationRequestDate", CommUtil.Date2String(locationRequestDate));
			   }
		   }
		}else{
            map.put("locationStatus", "fail");// 倒计时已经到了，定位失败
			map.put("locationRequestDate", CommUtil.Date2String(locationRequestDate));
		}
		return map;
	} 
    @RequestMapping(value="/queryAllDevice")
    @ResponseBody
    public Map<String,Object> queryAllDevice(Integer userId,HttpServletRequest request){
    	Map<String,Object> map = new HashMap<String, Object>();
    	List<DeviceManagerModel> list = deviceManagerService.queryAllDeviceById(userId);
    	map.put("list", list);
    	return map;
    }
    
    @RequestMapping(value="/exportExcle",method=RequestMethod.GET)
    @ResponseBody
    public void exportExcle(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
    	OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer id;
        if(session.getAttribute("id") == null){
			id = null;
		}else{
			id = (Integer)session.getAttribute("id");
		}
		String str = "#800000";
        int[] color = color(str); // 将16进制颜色转换为数组
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		List<StructureModel> strList= (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
        // 从session取出查询条件
	 	String seletedStatus =(String) session.getAttribute("seletedStatus");
		String searchUserName = (String) session.getAttribute("searchUserName");
		String deviceType = (String) session.getAttribute("deviceType");
		String sequenceNumber = (String) session.getAttribute("sequenceNumber");
		String esnorimei = (String) session.getAttribute("esnorimei");
		map.put("seletedStatus", seletedStatus);
		map.put("searchUserName", searchUserName);
		map.put("deviceType", deviceType);
		map.put("sequenceNumber",sequenceNumber);
		map.put("esnorimei",esnorimei);
        // 创建webbook，对应一个
    	HSSFWorkbook wb = new HSSFWorkbook();
        // 在创建号的webbokk中创建一个sheet
    	String sheetName = messageSource.getMessage("tiles.institution.device.device.info.export", null,LocaleContextHolder.getLocale());
    	String realname = messageSource.getMessage("tiles.views.netbehaviormanager.logmanager.username", null,LocaleContextHolder.getLocale());
    	String username = messageSource.getMessage("tiles.views.customer.personal.index.username", null,LocaleContextHolder.getLocale());
    	String devicename = messageSource.getMessage("tiles.views.institution.device.rule.history.table.deviceno", null,LocaleContextHolder.getLocale());
    	String sn = messageSource.getMessage("tiles.institution.device.manager.sequence.number", null,LocaleContextHolder.getLocale());
    	String deviceStatus = messageSource.getMessage("tiles.views.customer.index.index.basicinfo.devicestatus", null,LocaleContextHolder.getLocale());
    	HSSFSheet sheet = wb.createSheet(sheetName);
    	//7680=256*30
    	sheet.setColumnWidth(0, 7680);
    	sheet.setColumnWidth(1, 7680);
    	sheet.setColumnWidth(2, 7680);
    	sheet.setColumnWidth(3, 7680);
    	sheet.setColumnWidth(4, 7680);
        // 在sheet中添加表头行
    	HSSFRow row = sheet.createRow((int)0);
    	row.setHeight((short) 255);
        // 创建单元格，设置表头样式
    	HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
    	HSSFPalette palette = wb.getCustomPalette(); 
        palette.setColorAtIndex(HSSFColor.RED.index, (byte) color[0], (byte) color[1], (byte) color[2]);// 自定义颜色
    	HSSFFont font=wb.createFont();
    	font.setColor(HSSFColor.RED.index);
    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	font.setFontName("Times New Roman");
    	style.setFont(font);
    	HSSFCell cell = row.createCell((int) 0);
    	cell.setCellValue(realname);
    	cell.setCellStyle(style);
        cell = row.createCell((int) 1);
    	cell.setCellValue(username);
    	cell.setCellStyle(style);
    	cell = row.createCell((int) 2);
     	cell.setCellValue(devicename);
     	cell.setCellStyle(style);
     	cell = row.createCell((int) 3);
     	cell.setCellValue(sn);
     	cell.setCellStyle(style);
     	cell = row.createCell((int) 4);
     	cell.setCellValue(deviceStatus);
     	cell.setCellStyle(style);
     	String logOff = messageSource.getMessage("tiles.views.customer.index.index.devicestatus.logoff", null,LocaleContextHolder.getLocale());
     	String falloff = messageSource.getMessage("tiles.views.customer.index.index.devicestatus.falloff", null,LocaleContextHolder.getLocale());
     	String monitoring = messageSource.getMessage("tiles.views.customer.index.index.devicestatus.monitoring", null,LocaleContextHolder.getLocale());
     	String waitmonitor = messageSource.getMessage("tiles.views.customer.index.index.devicestatus.waitmonitor", null,LocaleContextHolder.getLocale());
     	List<DeviceManagerModel> list = deviceManagerService.queryDeviceExport(id, organization.getId(), map, strList);
     	int i = 0;
     	for(DeviceManagerModel deviceManager : list){
     		row = sheet.createRow((int) i + 1);
     	    row.createCell((int) 0).setCellValue(deviceManager.getRealName());
     	    row.createCell((int) 1).setCellValue(deviceManager.getUserName());
     	    row.createCell((int) 2).setCellValue(deviceManager.getDevice_name());
     	    row.createCell((int) 3).setCellValue(deviceManager.getSn());
     	    if("1".equals(deviceManager.getDeviceStatus())){
     	    	row.createCell((int) 4).setCellValue(logOff);
     	    }else if("2".equals(deviceManager.getDeviceStatus())){
     	    	row.createCell((int) 4).setCellValue(falloff);
     	    }else if("3".equals(deviceManager.getDeviceStatus())){
     	    	row.createCell((int) 4).setCellValue(waitmonitor);
     	    }else if("4".equals(deviceManager.getDeviceStatus())){
     	    	row.createCell((int) 4).setCellValue(monitoring);
     	    }
     		i++ ;
     	}
     	response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition", "attachment;filename=deviceInfo.xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close();
    }
    
     public int[] color(String str){
    	 int[] color=new int[3];
    	 color[0]=Integer.parseInt(str.substring(1, 3), 16); 
    	 color[1]=Integer.parseInt(str.substring(3, 5), 16); 
    	 color[2]=Integer.parseInt(str.substring(5, 7), 16); 
    	 return color;
     }
}

package com.softtek.mdm.web.institution;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.CollectVirtualModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.MessageSendModel;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PushMobileMsgModel;
import com.softtek.mdm.model.PushMsgHistoryModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.MessageSendService;
import com.softtek.mdm.service.PushMobileMsgService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.IosPushUtil;
import com.softtek.mdm.util.PropertyUtil;
import com.softtek.mdm.util.TreeManager;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.JsonResult;
import com.softtek.mdm.web.http.MessageType;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/institution/picAndTxtMeg")
public class MessageController extends BaseController
{

	private static final Logger logger = Logger.getLogger(MessageController.class);

	@Autowired
	private PushMobileMsgService pushMobileMsgService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private TreeManager treeManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSendService messageSendService;

	/**
     * 线程池对象
     */
	@Autowired
	private TaskExecutor taskExecutor;

    @Link(family = "institution", label = "tiles.aside.menu.picAndTxtMeg", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.messages.index.link.belong")
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model)
	{

		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
        // 组织架构部门功能列表获取
		@SuppressWarnings("unchecked")
		List<StructureModel> list = (List<StructureModel>) session
				.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		List<NodeModel> nodes = treeManager.buildMoreTree(list);
		String jsonStr = "[";
		int i = 1;
		for (NodeModel node : nodes)
		{
			jsonStr += JSONObject.fromObject(node).toString();
			if (i != nodes.size())
			{
				jsonStr += ",";
			}
			i++;
		}
		jsonStr += "]";
		jsonStr = StringUtil.replace(jsonStr, "\"nodes\":[],", "");
		model.addAttribute("tree", jsonStr);
		// List<UserModel> userList =
		// deviceService.getUserList(organization.getId());
		// model.addAttribute("userList", userList);
		List<CollectVirtualModel> virtualList = deviceService.selectVirtualList(organization.getId());
		model.addAttribute("virtualList", virtualList);
		return "institution/message/index";
	}

	/**
     * 上传图片的后台接收代码
     * 
     * @param request
     * @param response
     * @param files
     * @return
     */
	@Log(operateType = "logs.institution.message.uploadFile.type", operateContent = "logs.institution.message.uploadFile.content", args =
	{ "" })
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult uploadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) MultipartFile[] files)
	{

		JsonResult jsonResult = null;
		String path = CommUtil.APP_NEW_PATH;
		File newFile = null;
		boolean flag = false;
		List<String> pathList = new ArrayList<String>();
		if (files != null && files.length > 0)
		{
			for (MultipartFile file : files)
			{
				if (!file.isEmpty())
				{
					File dir = new File(path);
					if (!dir.exists())
					{
						dir.mkdirs();
					}
					String originName = file.getOriginalFilename();
					String extName = FilenameUtils.getExtension(originName);
					long time = (long) (new Date().getTime() + Math.random() * 6);
					newFile = new File(path + File.separator + String.valueOf(time) + "." + extName);
					try
					{
						file.transferTo(newFile);
						pathList.add(String.format("%s%s", CommUtil.IMAGE_URL,newFile.getName()));
					}
					catch (Exception e)
					{
                        // 需要做异常的处理
						flag = true;
						logger.error(e.getMessage());
					}
				}
			}
		}
		if (!flag)
		{
			jsonResult = createJsonResult(MessageType.success, "web.institution.message.uploadFile.success.label", null,
					pathList);
		}
		else
		{
			jsonResult = createJsonResult(MessageType.danger, "web.institution.message.uploadFile.fail.label", null,
					null);
		}
		return jsonResult;
	}

	@Log(operateType = "logs.institution.message.saveMessage.type", operateContent = "logs.institution.message.saveMessage.content", args =
	{ "" })
	@RequestMapping(value = "/saveMessage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult saveMessage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	{

		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager = (ManagerModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		String msgTitle = request.getParameter("msgTitle");
		String msgContent = request.getParameter("msgContent");

		String departIds = request.getParameter("departIds");
		String virtualIds = request.getParameter("virtualIds");
		String userIds = request.getParameter("userIds");

		
        // 获取用户管理的部门id列表
    	List<Integer> deptIdList = getManagerDeptIds(session);
    	
    	if (CollectionUtils.isEmpty(deptIdList))
    	{
    		deptIdList = new ArrayList<Integer>();
    	}
    	deptIdList.add(organization.getId());
    	
		PushMobileMsgModel pushMobileMsgModel = new PushMobileMsgModel();
		try
		{
			StringBuffer addBuf = null;
            // 解析msgContent中的图片的地址通过 jsoup技术来实现
			Document doc = Jsoup.parse(msgContent);
			Elements imgs = doc.getElementsByTag("img");
			if (imgs != null)
			{
				addBuf = new StringBuffer();
				addBuf = processElements(imgs, addBuf);
			}
			// 2016-05-09 jackson.zhu add comment[update primary key generated
			// strategy auto increment]
			// pushMobileMsgModel.setId(CommUtil.getPrimaryKey());
			pushMobileMsgModel
					.setPicUrl(StringUtils.isBlank(addBuf) ? "" : addBuf.substring(0, addBuf.lastIndexOf(",")));
			pushMobileMsgModel.setMsgTheme(msgTitle);
			pushMobileMsgModel.setCreateUserId(manager.getId());
			pushMobileMsgModel.setCreateUserName(manager.getName());
			pushMobileMsgModel.setCreateTime(new Date());
			pushMobileMsgModel.setContent(msgContent);
			pushMobileMsgModel.setOrgId(organization.getId());
			pushMobileMsgModel.setDepartIds(departIds);
			pushMobileMsgModel.setUserIds(userIds);
			pushMobileMsgModel.setVirtualIds(virtualIds);
			pushMobileMsgModel.setUpdateUserId(manager.getId());
			pushMobileMsgModel.setUpdateUserName(manager.getName());
			pushMobileMsgService.savePushMobileMsg(pushMobileMsgModel, deptIdList);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}

		return createJsonResult(MessageType.success, "web.institution.message.success.label", null, null);

	}

	private StringBuffer processElements(Elements imgs, StringBuffer addBuf)
	{
		for (Element img : imgs)
		{
			String imgSrc = img.attr("src");
			if (imgSrc != null && imgSrc.trim().length() > 0)
			{
				// addBuf = new StringBuffer();
				addBuf.append(imgSrc).append(",");
			}
		}
		return addBuf;

	}

	@RequestMapping(value = "/getAllPicAndTxts", method = RequestMethod.POST)
	@ResponseBody
	public Page getAllPicAndTxts(HttpServletRequest request, HttpServletResponse response, Integer start,
			Integer length, HttpSession session)
	{

		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		
        // 获取用户管理的部门id列表
    	List<Integer> deptIdList = getManagerDeptIds(session);
    	
    	if (CollectionUtils.isEmpty(deptIdList))
    	{
    		deptIdList = new ArrayList<Integer>();
    	}
    	deptIdList.add(organization.getId());
    	
		Page page = null;
		try
		{
			start = start == null ? 0 : start;
			length = length == null ? 10 : length;
			String name = request.getParameter("name");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageNum", start);
			map.put("pageSize", length);
			map.put("orgId", organization.getId());
			map.put("name", StringUtils.trimToNull(name));
			map.put("deptIdList", deptIdList);
			page = pushMobileMsgService.queryPushMsgListByParams(map);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		return page;
	}

	@RequestMapping(value = "/getPicAndTxtById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPicAndTxtById(HttpServletRequest request, HttpServletResponse response)
	{

		Map<String, Object> pageMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		PushMobileMsgModel pushMobileMsgModel = null;
		try
		{
			map.put("orgId", organization.getId());
			map.put("id", id);
			pushMobileMsgModel = pushMobileMsgService.queryPushMsgByMap(map);
			List<UserModel> userList = userService.getUserModels(pushMobileMsgModel.getUserIds());
			pageMap.put("pushMobileMsgModel", pushMobileMsgModel);
			pageMap.put("userList", userList);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		return pageMap;
	}

	@Log(operateType = "logs.institution.message.updateMessage.type", operateContent = "logs.institution.message.updateMessage.content", args =
	{ "" })
	@RequestMapping(value = "/updateMessage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult updateMessage(HttpServletRequest request, HttpServletResponse response)
	{

		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager = (ManagerModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", organization.getId());
		map.put("id", request.getParameter("id"));
		String msgTitle = request.getParameter("msgTitle");
		String msgContent = request.getParameter("msgContent");
		String departIds = request.getParameter("departIds");
		String virtualIds = request.getParameter("virtualIds");
		String userIds = request.getParameter("userIds");

		PushMobileMsgModel pushMobile = pushMobileMsgService.queryPushMsgByMap(map);
		PushMobileMsgModel pushMobileMsgModel = new PushMobileMsgModel();
		try
		{
			StringBuffer addBuf = null;
            // 解析msgContent中的图片的地址通过 jsoup技术来实现
			Document doc = Jsoup.parse(msgContent);
			Elements imgs = doc.getElementsByTag("img");
			if (imgs != null)
			{
				addBuf = new StringBuffer();
				addBuf = processElements(imgs, addBuf);
			}

			pushMobileMsgModel.setId(pushMobile.getId());
			pushMobileMsgModel.setMsgTheme(msgTitle);
			pushMobileMsgModel
					.setPicUrl(StringUtils.isBlank(addBuf) ? "" : addBuf.substring(0, addBuf.lastIndexOf(",")));
			pushMobileMsgModel.setContent(msgContent);
			pushMobileMsgModel.setUpdateTime(new Date());
			pushMobileMsgModel.setUpdateUserId(manager.getId());
			pushMobileMsgModel.setUserIds(userIds);
			pushMobileMsgModel.setDepartIds(departIds);
			pushMobileMsgModel.setVirtualIds(virtualIds);
			pushMobileMsgService.updatePushMobileMsg(pushMobileMsgModel);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}

		return createJsonResult(MessageType.success, "web.institution.message.success.label", null, null);

	}
	
	@RequestMapping(value = "/isMessageCreateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isMessageCreateUser(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		
		if(isOrganizaton(request.getSession()))
		{
			resultMap.put("isCreateUser", true);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", organization.getId());
		map.put("id", request.getParameter("id"));
		
		PushMobileMsgModel pushMobile = pushMobileMsgService.queryPushMsgByMap(map);
		
		ManagerModel manager = (ManagerModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		
		Integer currentUserId = manager.getId();
		
		if (currentUserId.equals(pushMobile == null ? null : pushMobile.getCreateUserId()))
		{
			resultMap.put("isCreateUser", true);
		}
		else
		{
			resultMap.put("isCreateUser", false);
		}
		
		return resultMap;
	}

	@Log(operateType = "logs.institution.message.deleteMessage.type", operateContent = "logs.institution.message.deleteMessage.content", args =
	{ "" })
	@RequestMapping(value = "/deleteMessage", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteMessage(HttpServletRequest request, HttpServletResponse response)
	{

		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", Integer.valueOf(request.getParameter("id")));
		map.put("orgId", organization.getId());
		PushMobileMsgModel pushMobileMsgModel = pushMobileMsgService.queryPushMsgByMap(map);
		String picUrl = pushMobileMsgModel.getPicUrl();
		Properties prop = PropertyUtil.getInstance().load("file");

        // 以前是1.0是上传到项目中的resources/upload目录下
		// String path =
		// request.getSession().getServletContext().getRealPath("resources" +
		// File.separator + prop.getProperty("uploadPath") + File.separator);
		String path = "";
		if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1)
		{
			path = prop.getProperty("fileServer");
		}
		else
		{
			path = prop.getProperty("fileServerLinux");
		}
		// path = path.replace("mdm","mdm.file");
		try
		{
			if (StringUtils.isNotBlank(picUrl))
			{
				if (picUrl.contains(","))
				{
					String[] urls = picUrl.split(",");
					for (int i = 0; i < urls.length; i++)
					{
						deletePath(path, urls[i]);
					}
				}
				else
				{
					deletePath(path, picUrl);
				}
			}
			pushMobileMsgService.deletePushMsgByMap(map);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		return createJsonResult(MessageType.success, "web.institution.message.success.label", null, null);

	}

	private void deletePath(String path, String url)
	{
		int index = url.lastIndexOf("/");
		String relativePath = url.substring(index + 1, url.length());
		String filePath = path + File.separator + relativePath;
		File file = new File(filePath);
		if (file.exists())
		{
			file.delete();
		}

	}

	@RequestMapping(value = "/validMsTitle", method = RequestMethod.GET)
	@ResponseBody
	public String validMsTitle(HttpServletRequest request, HttpServletResponse response)
	{

		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msgTitle", request.getParameter("msgTitle"));
		map.put("orgId", organization.getId());
		String id = request.getParameter("titleId");
		map.put("id", StringUtils.trimToNull(id));
		PushMobileMsgModel pushMobileMsgModel = pushMobileMsgService.queryPushMsgByTitle(map);
		if (pushMobileMsgModel == null)
		{
			return "false";
		}
		else
		{
			return "true";
		}
	}

	@RequestMapping(value = "/sendPushMsg", method = RequestMethod.GET)
	@ResponseBody
	public void sendPushMsg(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");
		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());

		ManagerModel managerModel = (ManagerModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
        String createName = managerModel.getName();
		

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgId", organization.getId());
		paramMap.put("id", id);
		PushMobileMsgModel model = pushMobileMsgService.queryPushMsgByMap(paramMap);
        model.setId(Integer.valueOf(id));
		MessageSendModel pMsgModel = new MessageSendModel();
		if (null != model)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			pMsgModel.setMessage_title(model.getMsgTheme());
			pMsgModel.setMessage(model.getMsgTheme());
			pMsgModel.setCreateBy(managerModel.getId());
			pMsgModel.setCreateTime(new Date());
			pMsgModel.setUpdateTime(new Date());
			pMsgModel.setUpdateBy(managerModel.getId());
			pMsgModel.setName(managerModel.getName());
			pMsgModel.setCreateDateStr(sdf.format(new Date()));
            pMsgModel.setMsgId(id);

			String imgUrls = StringUtils.trimToEmpty(model.getPicUrl());
			String[] imgUrlArr = imgUrls.split(Constant.SplitSymbol.COMMA_SYMBOL);
			pMsgModel.setImgUrl(imgUrlArr[0]);
		}

		List<Integer> userIdList = messageSendService.getUserIdListById(id, organization.getId());

		String pushMsgSend = JSONObject.fromObject(pMsgModel).toString();

		Map<String, String> map = new HashMap<String, String>();
		map.put("pushMsgSend", pushMsgSend);
		
		if (CollectionUtils.isNotEmpty(userIdList))
		{
			List<PushMsgHistoryModel> pushMsgHistoryModelList = new ArrayList<PushMsgHistoryModel>();
			/**
             * 1、首先根据用户查找出所有的设备，包括android和ios设备
             * 2、如果用户的所有设备中只包含ios设备，那么只走ios的普通推送
             * 3、如果用户的所有设备中只包含andorid设备，那么只走andorid的普通推送
             * 4、如果用户的所有设备中既包含ios设备又包含android设备，那么又必须走andorid设备推送又走ios推送
             */
			List<String> deviceToken = new ArrayList<String>();
			for (Integer userId : userIdList){
				PushMsgHistoryModel pushMsgHisModel = getPushMsgHisModel(String.valueOf(userId), model, organization.getId(),createName);
				pushMsgHistoryModelList.add(pushMsgHisModel);				
                // 2016-08-29 jackson添加ios图文消息推送，需要根据用户中的设备id去推送
				List<DeviceBasicInfoModel> devices = deviceService.getDevicesByUserId(userId);
				if(CollectionUtils.isNotEmpty(devices)){
                    boolean flag = false;
					for(DeviceBasicInfoModel deviceModel :devices){
						if(deviceModel.getDevice_type().equals("ios")){
                            if (StringUtils.isNotBlank(deviceModel.getDeviceToken())) {
                                deviceToken.add(deviceModel.getDeviceToken().replaceAll(" ", ""));
                            }
						}else{
                            if (flag == false) {
                                String topic = String.valueOf(userId);
                                MqProducerThread thread = new MqProducerThread(topic, null, null, 2, map);
                                taskExecutor.execute(thread);
                                flag = true;
                            }

						}
					}
				}
			}
			if(deviceToken.size() > 0){
                // 2016-09-01 jackson 修改代码
                // 由于ios推送数据量过多的情况下，不能推送。所以改成推送图文消息的id给ios客户端
				Map<String,String> iosMap = new HashMap<String,String>();
                iosMap.put("messageId", id);
                IosPushUtil.pushDataToIosTo(deviceToken, model.getMsgTheme(), 1, "", iosMap);
			}
			pushMobileMsgService.savePushHistory(pushMsgHistoryModelList);
		}
	}

	private PushMsgHistoryModel getPushMsgHisModel(String userId, PushMobileMsgModel model,
			Integer orgId, String createName)
	{
		PushMsgHistoryModel pushMsgHisModel = new PushMsgHistoryModel();
		pushMsgHisModel.setCreateUser(createName);
		pushMsgHisModel.setCreateTime(new Date());
		pushMsgHisModel.setPushMobileMsgModel(model);
		pushMsgHisModel.setUserId(Integer.valueOf(userId));
		pushMsgHisModel.setOrgId(orgId);
		return pushMsgHisModel;
	}
	
	@SuppressWarnings("unchecked")
	private List<Integer> getManagerDeptIds(HttpSession session)
	{
    	List<Integer> deptIdList = new ArrayList<Integer>();
    	
		ManagerModel managerModel = getManagerModelBySession(session);
		if (null != managerModel && managerModel.getUser() != null)
		{
			List<StructureModel> list1 = (List<StructureModel>) session
					.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1)
			{
				deptIdList.add(s.getId());
			}
		}
		else
		{
			deptIdList = null;
		}
		
		return deptIdList;
	}
	
	private boolean isOrganizaton(HttpSession session)
    {
    	List<Integer> deptIdList = getManagerDeptIds(session);
    	if (CollectionUtils.isEmpty(deptIdList))
    	{
    		return true;
    	}
    	
    	return false;
    }

    @RequestMapping(value = "/viewMember", method = RequestMethod.POST)
    @ResponseBody
    public Page viewMember(HttpServletRequest request, HttpServletResponse response, Integer start,
            Integer length, HttpSession session) {
        OrganizationModel organization = (OrganizationModel) request.getSession()
                .getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
        // 获取用户管理的部门id列表
        List<Integer> deptIdList = getManagerDeptIds(session);
        if (CollectionUtils.isEmpty(deptIdList)) {
            deptIdList = new ArrayList<Integer>();
        }
        deptIdList.add(organization.getId());

        Page page = null;
        try {
            start = start == null ? 0 : start;
            length = length == null ? 10 : length;
            String id = request.getParameter("id");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageNum", start);
            map.put("pageSize", length);
            map.put("orgId", organization.getId());
            map.put("id", Integer.valueOf(id));
            map.put("deptIdList", deptIdList);
            // page = pushMobileMsgService.queryPushMsgListByParams(map);
            page = pushMobileMsgService.queryViewMemberByParams(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return page;
    }
}

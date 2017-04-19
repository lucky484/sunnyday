package com.softtek.mdm.web.institution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jodd.util.StringUtil;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.annotation.Token;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.CollectVirtualModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.util.ImageUtil;
import com.softtek.mdm.util.TreeManager;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;

/**
 * 设备管理->设备策略功能
 * @author jane.hui
 *
 */
@Controller
@RequestMapping(value="/institution/device/policy")
public class DevicePolicyController extends BaseController{
    
	private static Logger logger = Logger.getLogger(DevicePolicyController.class);
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private TreeManager treeManager;
	
	@Autowired
	private MessageSource messageSourceService;

	/**
	 * 返回策略设备列表页面
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@Link(family="institution", label = "web.institution.device.policy.index.label",parent="web.institution.homecontroller.index.link.label",belong="web.institution.device.index.link.belong")
	@RequestMapping(method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		// 组织架构部门功能列表获取
		@SuppressWarnings("unchecked")
		List<StructureModel> list=(List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		List<NodeModel> nodes=treeManager.buildMoreTree(list);
		String jsonStr = "[";
		int i = 1;
		for (NodeModel node : nodes) {
			jsonStr+=JSONObject.fromObject(node).toString();
			if(i!=nodes.size()){
				jsonStr += ",";
			}
			i++;
		}
		jsonStr += "]";
		jsonStr=StringUtil.replace(jsonStr, "\"nodes\":[],", "");
		model.addAttribute("tree", jsonStr);
        List<CollectVirtualModel> virtualList = deviceService.selectVirtualList(organization.getId());
		model.addAttribute("virtualList",virtualList);
		return "institution/device/policy/index";
	}
	
	/**
	 * 获取设备策略List数据
	 * @param params
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/queryAll")
	@ResponseBody
	public Page queryAll(Integer start, Integer length,HttpSession session,HttpServletRequest request){
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		String objName = request.getParameter("name");
		String policytype = request.getParameter("policytype");
        return deviceService.getDevicePolicyList(manager,organization.getId(),policytype,objName,start, length);
	}
	
	/**
	 * 获取设备策略列表数据
	 * @param start
	 * @param length
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryDeviceAll")
	@ResponseBody
	public Page queryDeviceAll(Integer start, Integer length,HttpSession session,HttpServletRequest request){
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String policyId = request.getParameter("policyId");
		String type = request.getParameter("type");
		return deviceService.getDeviceListByPolicyId(organization.getId(), policyId, type, start, length);
	}
	
	/**
	 * 查询策略名称是否存在
	 * @param virtualGroup
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/iosExists", method = RequestMethod.GET)
	public @ResponseBody String iosExists(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException {
		OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String name= request.getParameter("iosName");
		String id = request.getParameter("iosId");
		boolean isExists = false;
		if(StringUtil.isNotBlank(name)) {
			if(StringUtil.isNotEmpty(id)){
				Integer intId = Integer.valueOf(id.toString());
				isExists = deviceService.iosExists(name, intId, organization.getId());
			} else {
				isExists = deviceService.iosExists(name, null, organization.getId());
			}
		} 
		return isExists?"failed":"success";
	}
	
	/**
	 * 查询策略名称是否存在
	 * @param virtualGroup
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exists", method = RequestMethod.GET)
	public @ResponseBody String isExists(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException {
		OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String name= request.getParameter("name");
		String id = request.getParameter("id");
		boolean isExists = false;
		if(StringUtil.isNotEmpty(id)){
			Integer intId = Integer.valueOf(id.toString());
			isExists = deviceService.exists(name, intId, organization.getId());
		} else {
			isExists = deviceService.exists(name, null, organization.getId());
		}
		
		return isExists?"failed":"success";
	}
	
	/**
	 * 设备策略删除功能
	 * @param request
	 * @return
	 */
	@Log(operateType="web.institution.device.policy.operatetype.remove",operateContent="web.institution.device.policy.operatecontent.remove",args={"name"})
	@RequestMapping(value="/delPolicy")
	@ResponseBody
	public Object delPolicy(HttpServletRequest request){
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		try {
		    resultDto = deviceService.deletePolicy(request);
		} catch(Exception e){
			logger.error("something wrong when delete device policy by id, id is " + id);
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.delpolicy.failed.lable",null, LocaleContextHolder.getLocale());
		}
		return resultDto;
	}
	/**
	 * 删除IOS设备策略
	 * @return
	 */
	@Log(operateType="web.institution.device.policy.operatetype.remove",operateContent="web.institution.device.policy.operatecontent.remove",args={"name"})
	@RequestMapping(value="/delIosPolicy")
	@ResponseBody
	public Object delIosPolicy(HttpServletRequest request){
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		try {
		    resultDto = deviceService.deleteIosPolicy(request);
		} catch(Exception e){
			logger.error("something wrong when delete device policy by id, id is " + id);
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.deliospolicy.failed.lable",null, LocaleContextHolder.getLocale());
		}
		return resultDto;
	}
	
	/**
	 * 根据名称查询用户
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/findUserByName")
	@ResponseBody
	public Object findUserByName(HttpServletRequest request,HttpSession session){
	    OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String name = request.getParameter("name");
		String userids = request.getParameter("userids");
		List<StructureModel> structureList = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
		List<Integer> ids = new ArrayList<Integer>();
		if (CollectionUtils.isNotEmpty(structureList)) {
			ids=(List<Integer>) CollectionUtils.collect(structureList, new Transformer() {
				@Override
				public Object transform(Object input) {
					if(input instanceof StructureModel){
						StructureModel ste=(StructureModel) input;
						return ste==null?null:ste.getId();
					}
					return null;
				}
			});
		}
	    return deviceService.findUserByName(organization.getId(), name,userids,ids);
	}
	
	/**
	 * 设备策略启用/禁用功能
	 * @param request
	 * @return
	 */
	@Log(operateType="web.institution.device.policy.operatetype.enable",operateContent="web.institution.device.policy.operatecontent.enable",args={"name"})
	@RequestMapping(value="/enablePolicy")
	@ResponseBody
	public Object enablePolicy(HttpSession session, HttpServletRequest request){
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		try {
		    resultDto = deviceService.enablePolicy(request);
		} catch(Exception e){
			logger.error("something wrong when enable device policy by id, id is " + id);
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
		}
		return resultDto;
	}
	
	/**
	 * 设备策略启用/禁用功能
	 * @param request
	 * @return
	 */
	@Log(operateType="web.institution.device.policy.operatetype.enable",operateContent="web.institution.device.policy.operatecontent.enable",args={"name"})
	@RequestMapping(value="/enableIosPolicy")
	@ResponseBody
	public Object enableIosPolicy(HttpSession session, HttpServletRequest request){
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		try {
		    resultDto = deviceService.enableIosPolicy(request);
		} catch(Exception e){
			logger.error("something wrong when enable device policy by id, id is " + id);
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.enablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
		}
		return resultDto;
	}
	
	/**
	 * 设备策略启用/禁用功能
	 * @param request
	 * @return
	 */
	@Log(operateType="web.institution.device.policy.operatetype.disable",operateContent="web.institution.device.policy.operatecontent.disable",args={"name"})
	@RequestMapping(value="/disableIosPolicy")
	@ResponseBody
	public Object disableIosPolicy(HttpSession session,HttpServletRequest request){
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		try {
		    resultDto = deviceService.enableIosPolicy(request);
		} catch(Exception e){
			logger.error("something wrong when d device policy by id, id is " + id + " error message :" +e.getMessage());
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.disablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
		}
		return resultDto;
	}
	
	/**
	 * 设备策略启用/禁用功能
	 * @param request
	 * @return
	 */
	@Log(operateType="web.institution.device.policy.operatetype.disable",operateContent="web.institution.device.policy.operatecontent.disable",args={"name"})
	@RequestMapping(value="/disablePolicy")
	@ResponseBody
	public Object disablePolicy(HttpSession session,HttpServletRequest request){
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		try {
		    resultDto = deviceService.enablePolicy(request);
		} catch(Exception e){
			logger.error("something wrong when d device policy by id, id is " + id);
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.disablepolicy.failed.lable",null, LocaleContextHolder.getLocale());
		}
		return resultDto;
	}
	
	/**
	 * 新增保存Android策略配置
	 * @return
	 */
	@Token(needRemoveToken=true)
	@Log(operateType="web.institution.device.policy.operatetype.add",operateContent="web.institution.device.policy.operatecontent.add",args={"params[name]"})
	@RequestMapping(value="/saveAndroidPolicy")
	@ResponseBody
	public Object saveAndroidPolicy(HttpServletRequest request,DataGridModel params){
		String tokenStr = CommUtil.generate32UUID();
		request.getSession().setAttribute("token",tokenStr);
		// 保存设备策略
		ResultDTO resultDto = new ResultDTO();
		try {
		    resultDto = deviceService.saveAndroidPolicy(request, params);
		} catch(Exception e){
			logger.error("something wrong when add device policy,exception message is " + e.getMessage());
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.failed.lable",null, LocaleContextHolder.getLocale());
			resultDto.type = BaseDTO.DANGER;
		}
		return resultDto;
	}
	
	/**
	 * 保存Ios策略
	 * @param request
	 * @param params
	 * @return
	 */
	@Token(needRemoveToken=true)
	@Log(operateType="web.institution.ios.device.policy.operatetype.add",operateContent="web.institution.ios.device.policy.operatecontent.add",args={"params[name]"})
	@RequestMapping(value="/saveIosPolicy")
	@ResponseBody
	public Object saveIosPolicy(HttpServletRequest request,DataGridModel params){
		// 保存设备策略
		ResultDTO resultDto = new ResultDTO();
		try {
		    resultDto = deviceService.saveIosPolicy(request, params);
		} catch(Exception e){
			logger.error("something wrong when add device policy,exception message is " + e.getMessage());
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.failed.lable",null, LocaleContextHolder.getLocale());
			resultDto.type = BaseDTO.DANGER;
		}
		return resultDto;
	}
	
	/**
	 * 修改保存Android策略配置
	 * @return
	 */
	@Token(needRemoveToken=true)
	@Log(operateType="web.institution.device.policy.operatetype.modify",operateContent="web.institution.device.policy.operatecontent.modify",args={"params[name]"})
	@RequestMapping(value="/updateAndroidPolicy")
	@ResponseBody
	public Object updateAndroidPolicy(HttpServletRequest request,DataGridModel params){
		String tokenStr = CommUtil.generate32UUID();
		request.getSession().setAttribute("token",tokenStr);
		// 保存设备策略
		ResultDTO resultDto = new ResultDTO();
		try {
		    resultDto = deviceService.saveAndroidPolicy(request, params);
		} catch(Exception e){
			logger.error("something wrong when modify device policy,exception message is " + e.getMessage());
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.saveandroidpolicy.failed.lable",null, LocaleContextHolder.getLocale());
			resultDto.type = BaseDTO.DANGER;
			return resultDto;
		}
		return resultDto;
	}
	
	/**
	 * 修改保存Android策略配置
	 * @return
	 */
	@Log(operateType="web.institution.ios.device.policy.operatetype.modify",operateContent="web.institution.ios.device.policy.operatecontent.modify",args={"params[name]"})
	@RequestMapping(value="/updateIosPolicy")
	@ResponseBody
	public Object updateIosPolicy(HttpServletRequest request,DataGridModel params){
		String tokenStr = CommUtil.generate32UUID();
		request.getSession().setAttribute("token",tokenStr);
		// 保存设备策略
		ResultDTO resultDto = new ResultDTO();
		try {
		    resultDto = deviceService.saveIosPolicy(request, params);
		} catch(Exception e){
			logger.error("something wrong when modify device policy,exception message is " + e.getMessage());
			resultDto.message = messageSourceService.getMessage("web.institution.device.policy.saveiospolicy.success.lable",null, LocaleContextHolder.getLocale());
			resultDto.type = BaseDTO.DANGER;
			return resultDto;
		}
		return resultDto;
	}
	
	/**
	 * 添加token
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addToken")
	@ResponseBody
	public Object addToken(HttpServletRequest request){	
		String tokenStr = CommUtil.generate32UUID();
		request.getSession().setAttribute("token",tokenStr);
		return tokenStr;
	}
	
	/**
	 * 查看Androlid策略配置
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findPolicy")
	@ResponseBody
	public Object findPolicy(HttpServletRequest request){
		return deviceService.findPolicy(request);
	}
	
	/**
	 * 查看IOS策略配置
	 * @return
	 */
	@RequestMapping(value="/findIosPolicy")
	@ResponseBody
	public Object findIosPolicy(HttpServletRequest request){
		return deviceService.findIosPolicy(request);
	}
	
	/**
	 * 编辑Android策略配置
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/editPolicy")
	@ResponseBody
	public Object editPolicy(HttpServletRequest request){
		String tokenStr = CommUtil.generate32UUID();
		request.getSession().setAttribute("token",tokenStr);
		
		ResultDTO resultDTO  = new ResultDTO();
		resultDTO.token = tokenStr;
		resultDTO.result = deviceService.editPolicy(request);
		return resultDTO;
	}
	
	/**
	 * 查询黑白名单
	 * @param params
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/queryNameList")
	@ResponseBody
	public Page queryNameList(Integer start, Integer length,HttpServletRequest request,HttpSession session){
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		String type = request.getParameter("type");
        return deviceService.selectNameList(manager,organization.getId(),null,type,start, length);
	}
	
	/**
	 * 查询网页黑白名单
	 * @param params
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/queryNetNameList")
	@ResponseBody
	public Page queryNetNameList(Integer start, Integer length,HttpServletRequest request,HttpSession session){
		OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		String type = request.getParameter("type");
        return deviceService.selectNetNameList(manager,organization.getId(),null,type,start, length);
	}
	
	/**
	 * 编辑Ios策略配置
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/editIosPolicy")
	@ResponseBody
	public Object editIosPolicy(HttpServletRequest request){
		ResultDTO resultDTO  = new ResultDTO();
		String tokenStr = CommUtil.generate32UUID();
		request.getSession().setAttribute("token",tokenStr);
		resultDTO.token = tokenStr;
		resultDTO.result = deviceService.editPolicy(request);
		return resultDTO;
	}

	/**
	 * 上传图片
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadImg")
	@ResponseBody
	public Object uploadImg(HttpServletRequest request){
		ResultDTO resultDTO  = new ResultDTO();
		String savePath = CommUtil.APP_ICON_PATH + Constant.getModule.DEVICE_POLICY + Constant.SplitSymbol.SLASH;
		File file = new File(savePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			// 如果不存在，创建目录
			file.mkdir();
		}
		// 上传文件
		String fileName;
		try {
			fileName = ImageUtil.getUploadFilePath(request, savePath);
		} catch (IllegalStateException | IOException e) {
			resultDTO.type = BaseDTO.WARNING;
			resultDTO.message = messageSourceService.getMessage("web.institution.device.policy.uploadimg.message.failed",null, LocaleContextHolder.getLocale());
			return resultDTO;
		}
		resultDTO.result = fileName;
		resultDTO.type = BaseDTO.SUCCESS;
		return resultDTO;
	}
}
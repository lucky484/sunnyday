/**
 * Project Name:com.softtek.mdm File Name:NetBehaviorController.java Package
 * Name:com.softtek.mdm.web.netbahavior Date:Apr 13, 20168:57:29 AM Copyright
 * (c) 2016, brave.chen@softtek.com All Rights Reserved.【请根据具体情况修改模板】
 *
 */
package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NetBehaviorBlackWhiteList;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.NetBehaviorService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.Constant.NetBehaviorConstant;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.util.ExportData;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;

/**
 * 上网行为控制层 date: Apr 13, 2016 8:57:29 AM
 *
 * @author brave.chen
 */
@Controller
@RequestMapping(value = "/institution/netbehavior/blackwitelist")
public class NetBehaviorController extends BaseController
{
    
	/**
     * 日志记录器
     */
	private Logger logger = Logger.getLogger(NetBehaviorController.class);
	
    /**
     * 黑白名单名称
     */
    public static final String BLACK_WHITE_LIST_NAME = "blackWhiteListName";
    
    /**
     * 上网行为服务类
     */
    @Autowired
    private NetBehaviorService netBehaviorService;
    
    /**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSource;

    /**
     * 用户角色主页
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    
    @Link(family = "institution", label = "institution.netbehavior.bwlist.index.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.netBehavior.index.link.belong")
    @RequestMapping(method = RequestMethod.GET)
    public final String index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            Model model) throws IOException
    {
        return "/institution/netbehavior/bwlist";
    }

    /**
     * ajax获取角色的分页
     * 
     * @param request
     *            请求对象
     * @param response
     *            响应对象
     * @param start
     *            起始页
     * @param length
     *            每页显示的记录数
     * @return 页面对象
     * @throws IOException
     *             异常
     */
    @RequestMapping(value = "/pages", method = RequestMethod.POST)
    @ResponseBody
    public Page queryByPage(HttpSession session, HttpServletRequest request, HttpServletResponse response,
            Integer start, Integer pageLength) throws Exception
	{
        // 获取用户管理的部门id列表
    	
    	List<Integer> deptIdList = getManagerDeptIds(session);
    	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String objName = StringUtils.trimToEmpty(request.getParameter(BLACK_WHITE_LIST_NAME));
		String type = StringUtils.trimToEmpty(request.getParameter(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_TYPE));

		Integer organizationId = getOrgIdBySession(session);

		paramMap.put(BLACK_WHITE_LIST_NAME, objName);
		paramMap.put("type", type);
		paramMap.put("start", start);
		paramMap.put("pageLength", pageLength);
		paramMap.put(Constant.FullRegionUseConstant.ORGANIZATION_ID, organizationId);
		if (CollectionUtils.isEmpty(deptIdList))
		{
			deptIdList = new ArrayList<Integer>();
		}
		deptIdList.add(organizationId);
		paramMap.put("deptIdList", deptIdList);
		
		Page page = netBehaviorService.queryBlackWhiteListByParams(paramMap);
		return page;
	}
    
    /**
     * 名单删除功能
     * 
     * @param request
     *            请求对象
     * @return 结果对象
     * @throws IOException
     *             异常
     */
    @Log(operateType="logs.netbehavior.type.delete",operateContent="logs.netbehavior.content.delete",args={"params[blackWhiteListId]"})
    @RequestMapping(value="/delNameList")
    @ResponseBody
    public Object delNameList(HttpServletRequest request, HttpSession session) throws Exception
    {
        NetBehaviorBlackWhiteList bwList = new NetBehaviorBlackWhiteList();
        String id = StringUtils.trimToEmpty(request.getParameter(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_ID));
        bwList.setId(Integer.valueOf(id));
        ResultDTO dto = null;
        
        try
        {
        	dto = netBehaviorService.delNetBehaviorBlackWhiteList(id);
        }
        catch(Exception e)
        {
        	logger.error("something wrong when delete net behavior black white list by id, id is " + id);
        	dto.type = BaseDTO.ERROR;
			dto.message = messageSource.getMessage("web.institution.delnamelist.resultdto.message.failed", null,
					LocaleContextHolder.getLocale());
        }
        
        return dto;
    }
    
	@Log(operateType="logs.netbehavior.type.add",operateContent="logs.netbehavior.content.add",args={"params[blackWhiteListName]"})
    @RequestMapping(value = "/saveBwList", method = RequestMethod.POST)
    @ResponseBody
    public Object saveBwList(HttpServletRequest request,HttpSession session, DataGridModel params)
    {
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
    	
    	ManagerModel managerModel = getManagerModelBySession(session);
    	if (null != managerModel)
    	{
    		paramsMap.put("createUserId", managerModel.getId());
    		paramsMap.put("createUserName", managerModel.getName());
    	}
    	
        // 获取用户管理的部门id列表
    	List<Integer> deptIdList = getManagerDeptIds(session);
    	if (CollectionUtils.isEmpty(deptIdList))
		{
			deptIdList = new ArrayList<Integer>();
		}
    	
    	Integer organizationId = getOrgIdBySession(session);
    	deptIdList.add(organizationId);
    	paramsMap.put("organizationId", organizationId);
    	
    	Object  netUrlStr = params.getParams().get("netNameWithUrlArr");
    	paramsMap.put("netUrlStr", netUrlStr);
    	
    	String name = StringUtils.trimToNull((String) params.getParams().get(NetBehaviorConstant.BLACK_WHITE_LIST_NAME));
    	paramsMap.put(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_NAME, name);
        
        String remark = StringUtils.trimToNull((String) params.getParams().get(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_REMARK));
        paramsMap.put(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_REMARK, remark);
        
        String type = StringUtils.trimToNull((String) params.getParams().get(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_TYPE));
        paramsMap.put(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_TYPE, Integer.valueOf(type));
        
        paramsMap.put("deptIdList", deptIdList);
        ResultDTO dto = new ResultDTO();
        try
        {
        	dto = netBehaviorService.addNetBehaviorBlackWhiteList(paramsMap);
        }
        catch(Exception e)
        {
        	logger.error("something wrong when add black white list,exception message is " + e.getMessage());
        	dto.type = BaseDTO.ERROR;
			dto.message = messageSource.getMessage("web.institution.savebwlist.resultdto.message.failed", null,
					LocaleContextHolder.getLocale());
        }
    	return dto;
    }

    @Log(operateType="logs.netbehavior.type.modfiy",operateContent="logs.netbehavior.content.modfiy",args={"params[blackWhiteListName]"})
    @RequestMapping(value = "/updateBwList", method = RequestMethod.POST)
    @ResponseBody
    public Object updateBwList(HttpServletRequest request,HttpSession session, DataGridModel params)
    {
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
    	ResultDTO resultDto = new ResultDTO();
    	
    	ManagerModel managerModel = getManagerModelBySession(session);
    	if (null != managerModel)
    	{
    		paramsMap.put("createUserId", managerModel.getId());
    		paramsMap.put("createUserName", managerModel.getName());
    	}
    	
        Object  netUrlStr = params.getParams().get("netNameWithUrlArr");
        paramsMap.put("netUrlStr", netUrlStr);
        
        String blackWhiteListId = (String) params.getParams().get("blackWhiteListId");
        paramsMap.put("blackWhiteListId", blackWhiteListId);

        paramsMap.put(NetBehaviorConstant.BLACK_WHITE_LIST_NAME, (String) params.getParams().get(NetBehaviorConstant.BLACK_WHITE_LIST_NAME));
        paramsMap.put(NetBehaviorConstant.BLACK_WHITE_LIST_REMARK, (String) params.getParams().get(NetBehaviorConstant.BLACK_WHITE_LIST_REMARK));
        
        String type = (String) params.getParams().get(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_TYPE);
        paramsMap.put(NetBehaviorConstant.BLACK_WHITE_LIST_TYPE, Integer.valueOf(type));
        
        try
        {
        	resultDto = netBehaviorService.updateBwList(paramsMap);
        }
        catch(Exception e)
        {
        	logger.error("something wrong when modify white black list!exception message is " + e.getMessage());
            resultDto.type = BaseDTO.ERROR;
            resultDto.message = messageSource.getMessage("web.institution.updatebwlist.resultdto.message.failed", null, 
					LocaleContextHolder.getLocale());
            return resultDto;
        }

        return resultDto;
    }
    
    @Log(operateType="logs.netbehavior.type.query",operateContent="logs.netbehavior.content.query",args={"params[blackWhiteListName]"})
    @RequestMapping(value = "/queryBwList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> queryBwList(HttpServletRequest request,HttpSession session, DataGridModel params)
    {
        Map<String,Object> map = new HashMap<String, Object>();
        
        String id = StringUtils.trimToEmpty(request.getParameter(Constant.NetBehaviorConstant.BLACK_WHITE_LIST_ID));
        NetBehaviorBlackWhiteList bwList = new NetBehaviorBlackWhiteList();
        try
        {
            bwList = netBehaviorService.queryNetBehaviorBlackWhiteList(id);
        }
        catch(Exception e)
        {
        	logger.error("something wrong when get white black list , excepiton message is " + e.getMessage());
            map.put("type",BaseDTO.ERROR);
            map.put("message",messageSource.getMessage("web.institution.querybwlist.resultdto.message.failed", null, 
					LocaleContextHolder.getLocale()));
            return map;
        }

        map.put("bwList", bwList);
        map.put("type",BaseDTO.SUCCESS);
        map.put("message",messageSource.getMessage("web.institution.querybwlist.resultdto.message.success", null, 
				LocaleContextHolder.getLocale()));
        
        return map;
    }
    
    /**
     * 校验黑白名单名称
     * 
     * @param request
     *            请求对象
     * @param session
     *            session对象
     * @return 验证字符串
     */
    @RequestMapping(value = "/checkBwListName", method = RequestMethod.GET)
    @ResponseBody
    public String checkBwListName(String blackWhiteListName,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException
    {
    	blackWhiteListName = StringUtils.trimToEmpty(blackWhiteListName);
    	String bwListId = StringUtils.trimToEmpty(request.getParameter("bwListId"));
    	OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
    	NetBehaviorBlackWhiteList bwListByName = netBehaviorService.queryBwListByName(blackWhiteListName,organization.getId());
    	if (StringUtils.isEmpty(bwListId))
    	{
    		if (null != bwListByName)
    		{
    			return "true";
    		}
    	}
    	else
    	{
    		NetBehaviorBlackWhiteList bwListById = netBehaviorService.queryNetBehaviorBlackWhiteList(bwListId);
    		String nameBwListById = bwListById == null ? null : bwListById.getBlackWhiteName();
    		
    		if(null != bwListByName && !blackWhiteListName.equals(nameBwListById))
    		{
    			return "true";
    		}
    	}
    	
    	return "false";
    }
    
    @RequestMapping(value = "/isOrginalCreateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isOrginalCreateUser(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		NetBehaviorBlackWhiteList netBwList = netBehaviorService.queryNetBehaviorBlackWhiteList(request.getParameter("id"));
		
		ManagerModel manager = (ManagerModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		
		
		Integer currentUserId = manager.getId();
		
		if (currentUserId.equals(netBwList == null ? null : netBwList.getCreateUserId()))
		{
			resultMap.put("isCreateUser", true);
		}
		else
		{
			resultMap.put("isCreateUser", false);
		}
		
        // 如果是机构管理员则认为也是其创建的不需要提示
		if(isOrganizaton(request.getSession()))
		{
			resultMap.put("isCreateUser", true);
		}
		return resultMap;
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

    /**
     * 获取下载模板
     */
    @RequestMapping(value = "/getexportmodel", method = RequestMethod.GET)
    @ResponseBody
    public void getvirmodel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExportData exportData = new ExportData();

        String headers[][] = {
                { messageSource.getMessage("tiles.views.netbehaviormanager.blackwhitelist.url.name.need", null,
                        LocaleContextHolder.getLocale()), "String" },
                { messageSource.getMessage("tiles.views.netbehaviormanager.blackwhitelist.url.address.need", null,
                        LocaleContextHolder.getLocale()), "String" } };
        SXSSFWorkbook workbook = exportData.getwb(headers, "sheet1");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-msdownload");
        OutputStream os = null;
        String fileName = messageSource.getMessage("tiles.views.blacklist.export.model", null,
                LocaleContextHolder.getLocale()) + ".xlsx";
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * 导入用户
     */

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> importvirmember(MultipartFile files, HttpServletRequest request,
            HttpServletResponse response, HttpSession session)
            throws Exception {
        // 判断是xls还是xlsx
        Integer filetype = 0;
        if (files.getOriginalFilename().endsWith("xls")) {
            filetype = 03;
        }
        if (files.getOriginalFilename().endsWith("xlsx")) {
            filetype = 07;
        }
        // 判断是否是管理员
        InputStream ins = files.getInputStream();
        Map<String, Object> resultMap = netBehaviorService.importExcel(ins, filetype);
        return resultMap;

    }
}

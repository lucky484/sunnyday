package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.NetBehaviorLogService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.web.BaseController;

/**
 * 上网行为日志控制类
 * date: Apr 15, 2016 12:31:05 AM
 *
 * @author brave.chen
 */
@Controller
@RequestMapping(value = "/institution/netbehavior/log")
public class NetBehaviorLogController extends BaseController
{
    /**
     * 上网行为日志服务类
     */
    @Autowired
    private NetBehaviorLogService netBehaviorLogService;
    
    /**
     * 用户角色主页
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Link(family = "institution", label = "institution.netbehavior.log.index.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.netBehavior.index.link.belong")
    @RequestMapping(method = RequestMethod.GET)
    public final String index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            Model model) throws IOException
    {
        return "/institution/netbehavior/bwlog";
    }
    
    /**
     * ajax获取角色的分页
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @param start 起始页
     * @param length 每页显示的记录数
     * @return 页面对象
     * @throws IOException 异常
     */
    @RequestMapping(value = "/pages", method = RequestMethod.POST)
    @ResponseBody
    public Page dptmanager(HttpSession session, HttpServletRequest request, HttpServletResponse response,
            Integer start, Integer pageLength) throws IOException
    {
        String userName = request.getParameter("name");
        String content = request.getParameter("content");
        
        start = start == null ? 0 : start;
        pageLength = pageLength == null ? 10 : pageLength;
        
        List<Integer> deptIdList = getManagerDeptNames(session);
        
        Integer organizationId = -1;
        Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
        if (orgObj != null) {
        	OrganizationModel organization = (OrganizationModel)orgObj;
        	organizationId = organization.getId();
        }
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
        paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, pageLength);
        paramMap.put("name", StringUtils.trimToNull(userName));
        paramMap.put("content", StringUtils.trimToNull(content));
        paramMap.put("organizationId", organizationId);
        paramMap.put("deptNameList", deptIdList);
        
        
        Page page = netBehaviorLogService.queryLogsByParams(paramMap);
        return page;
    }
    
    @SuppressWarnings("unchecked")
	private List<Integer> getManagerDeptNames(HttpSession session)
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
}


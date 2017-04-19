package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.DeviceFluxModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.DeviceFluxService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.web.BaseController;

@Controller
@RequestMapping("/institution/systemStatistics/flux/abnormal")
public class SystemFluxAbnormalController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(SystemUserStatisticsController.class);
	
	@Autowired
    private DeviceFluxService deviceFluxService;

	
    /**
     * 设备统计列表首页
     * 
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @Link(family = "institution", label = "tiles.aside.menu.device.flux.abnormal", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.statistics.index.link.belong")
	@RequestMapping(method = RequestMethod.GET)
	public String deviceIndex(HttpServletRequest request, HttpServletResponse response, Model model,
			HttpSession session) throws IOException {
        return "institution/systemStatistics/flux/abnormal";
	}

	/**
     * 根据条件查询所有设备的统计信息
     * 
     * @param request
     * @param response
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/getallfluxabonormalstatistics", method = RequestMethod.POST)
    @ResponseBody
    public Page getAllUserStatistics(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            Integer start,
			Integer length) {
		OrganizationModel organization = (OrganizationModel) request.getSession()
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		start = start == null ? 0 : start;
		length = length == null ? 10 : length;
		paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
		paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
		paramMap.put("org_id", organization.getId());
        // 获取部门管理员管理部门list
        List<Integer> depardIds = getManagerIds(session);
        paramMap.put("groupId", depardIds);
        Page page = deviceFluxService.getAllFluxAbListsByMap(paramMap);
		return page;
	}

    /**
     * 查询异常用户的对应列表
     * 
     * @param request
     * @param response
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/appfluxlsit", method = RequestMethod.POST)
    @ResponseBody
    public Page appFluxLsit(HttpServletRequest request, HttpServletResponse response, Integer start,
            Integer length) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String userId = request.getParameter("userId");
        String sn = request.getParameter("sn");
        String id = request.getParameter("id");
        start = start == null ? 0 : start;
        length = length == null ? 10 : length;
        paramMap.put("userId", StringUtils.trimToNull(userId));
        paramMap.put("sn", StringUtils.trimToNull(sn));
        paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
        paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
        paramMap.put("id", Integer.valueOf(id));
        Page page = deviceFluxService.getAppFluxLsit(paramMap);
        return page;
    }


	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	@ResponseBody
    public List<DeviceFluxModel> getUserStatisChart(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
        String userId = request.getParameter("userId");
        String sn = request.getParameter("sn");
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", StringUtils.trimToNull(userId));
        paramMap.put("sn", StringUtils.trimToNull(sn));
        List<DeviceFluxModel> lists = deviceFluxService.getFluxABListsByMap(paramMap);
		return lists;

	}

    /**
     * 如果不为null则是所管理部门的id，如果是null，那么表示是机构管理员
     * 
     * @param session
     * @return
     */
    private List<Integer> getManagerIds(HttpSession session) {
        ManagerModel manager = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
        if (manager.getUser() != null) {
            // 部门管理员
            @SuppressWarnings("unchecked")
            List<StructureModel> list = (List<StructureModel>) session
                    .getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
            @SuppressWarnings("unchecked")
            List<Integer> depardIds = (List<Integer>) CollectionUtils.collect(list, new Transformer() {
                @Override
                public Object transform(Object input) {
                    StructureModel ste = (StructureModel) input;
                    return ste.getId();
                }
            });
            return depardIds;
        }
        return null;
    }

}

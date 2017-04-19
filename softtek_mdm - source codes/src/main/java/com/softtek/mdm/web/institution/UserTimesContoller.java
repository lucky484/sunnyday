package com.softtek.mdm.web.institution;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserTimeModel;
import com.softtek.mdm.service.UserTimeService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.CommUtil;

@Controller
@RequestMapping(value = "institution/userTimes")
public class UserTimesContoller {

	@Autowired
	private UserTimeService userTimeService;

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Link(family = "institution", label = "web.institution.user.time.label", parent = "web.institution.homecontroller.index.link.label", belong = "web.institution.statistics.index.link.belong")
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		return "institution/userTime/index";
	}

	/**
     * 绘制图表的24小时时间段的查询方法
     * 
     * @param request
     * @return
     * @throws ParseException
     */
	@RequestMapping(value = "/queryUserTime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUserTime(HttpServletRequest request, HttpSession session) throws ParseException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String searchType = request.getParameter("searchType");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<String> searchDates = getSearchType(searchType);
        if (CollectionUtils.isNotEmpty(searchDates)) {
            if (searchDates.size() == 1) {
                paramMap.put("firstDay", searchDates.get(0));
                paramMap.put("lastDay", searchDates.get(0));
            } else {
                paramMap.put("firstDay", searchDates.get(0));
                paramMap.put("lastDay", searchDates.get(1));
            }
        } else {
            paramMap.put("firstDay", StringUtils.trimToNull(startTime));
            paramMap.put("lastDay", StringUtils.trimToNull(endTime));
        }
        String startTimeStr = (String) paramMap.get("firstDay");
        String endTimeStr = (String) paramMap.get("lastDay");
        List<Integer> depardIds = getManagerIds(session);
        UserTimeModel usertime = userTimeService.queryUserTime(organization.getId(), startTimeStr, endTimeStr,
                depardIds);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("usertime", usertime);
		return map;
	}

	@RequestMapping(value = "/queryAllUserTime", method = RequestMethod.POST)
	@ResponseBody
	public Page queryAllUserTime(Integer start, Integer length, HttpServletRequest request, HttpSession session)
			throws ParseException {
		OrganizationModel organization = (OrganizationModel) session
				.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Map<String, Object> map = new HashMap<String, Object>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String searchType = request.getParameter("searchType");
        List<String> searchDates = getSearchType(searchType);
        if (CollectionUtils.isNotEmpty(searchDates)) {
            if (searchDates.size() == 1) {
                map.put("firstDay", searchDates.get(0));
                map.put("lastDay", searchDates.get(0));
            } else {
                map.put("firstDay", searchDates.get(0));
                map.put("lastDay", searchDates.get(1));
            }
        } else {
            map.put("firstDay", StringUtils.trimToNull(startTime));
            map.put("lastDay", StringUtils.trimToNull(endTime));
        }

		map.put("orgId", organization.getId());
		map.put("start", start);
		map.put("length", length);
        List<Integer> depardIds = getManagerIds(session);
        map.put("groupId", depardIds);
		Page page = userTimeService.queryAllUserTime(map);
		return page;
	}

	/**
     * 计算本周时间范围 如果当天是周二：周一~周二 如果当天是周四：周一~周四 如果当天是周六：周一~周六 如果当天是周日：上周一~周日
     * 
     * @return map |-start yyyy-mm-dd |-end yyyy-mm-dd
     */
	private Map<String, String> computeTime() {
		Calendar c = Calendar.getInstance();
		Map<String, String> dateMap = new HashMap<String, String>();
		dateMap.put("end", sdf.format(c.getTime()));

		int dayWeek = c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DATE, 1 == dayWeek ? -6 : 2 - dayWeek);
		dateMap.put("start", sdf.format(c.getTime()));
		
		return dateMap;
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

    private List<String> getSearchType(String searchType) {
        List<String> lists = new ArrayList<String>();
        if (StringUtils.isNotBlank(searchType)) {
            // 按天进行统计
            if (StringUtils.equals(searchType, "1")) {
                // 获取当天的时间
                String today = CommUtil.getCurrentDay();
                lists.add(today);
            } else if (StringUtils.equals(searchType, "2")) {// 按周进行统计
                String firstDay = CommUtil.getFirstOfWeek();
                String lastDay = CommUtil.getLastOfWeek();
                lists.add(firstDay);
                lists.add(lastDay);
            } else {// 按月进行统计
                String firstDay = CommUtil.getFirstOfMonth();
                String lastDay = CommUtil.getLastOfMonth();
                lists.add(firstDay);
                lists.add(lastDay);
            }
        }
        return lists;
    }
}

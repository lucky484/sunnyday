package com.softtek.mdm.web.institution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.bean.NetBean;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.NetStatisticsService;
import com.softtek.mdm.service.SystemWordsService;
import com.softtek.mdm.status.SessionStatus;

import jodd.util.StringUtil;

/**
 * 上网统计管理
 * 
 * @author jane.hui
 *
 */
@Controller
@RequestMapping(value="/institution/net")
public class NetStatisticsController {

    @Autowired
    private NetStatisticsService netStatisticsService;
    
    @Autowired
    private SystemWordsService systemWordsService;
    
    /**
     * 返回上传统计管理
     * 
     * @param request
     * @return
     */
     @Link(family="institution",label="web.institution.net.statistics.index.label",parent = "web.institution.homecontroller.index.link.label", belong="web.institution.systemStatistics.user.belong")
     @RequestMapping(method=RequestMethod.GET)
     public String index(HttpServletRequest request,Model model){
         model.addAttribute("websiteList", systemWordsService.selectClassifyList());
         return "institution/net/index";
     }
     
     /**
     * 获取设备策略List数据
     * 
     * @param params
     * @param request
     * @param model
     * @return
     */
     @RequestMapping(value="/queryAll")
     @ResponseBody
     public Page queryAll(Integer start, Integer length,HttpSession session,HttpServletRequest request){
         OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
         String type = request.getParameter("type");
        List<Integer> depardIds = getManagerIds(session);
        return netStatisticsService.getNetList(organization.getId(), type, start, length, depardIds);
     }
     
     /**
     * 获取设备策略List数据
     * 
     * @param params
     * @param request
     * @param model
     * @return
     */
     @RequestMapping(value="/getCountByWebsite") 
     @ResponseBody
     public Object getCountByWebsite(Integer start, Integer length,HttpSession session,HttpServletRequest request){
         Map<String, Object> map = new HashMap<String, Object>();
         OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
        List<Integer> depardIds = getManagerIds(session);
        List<NetBean> list = netStatisticsService.getCountByWebsite(organization.getId(), depardIds);
         String[] s1 = new String[list.size()];
         Integer[] s2 = new Integer[list.size()];
         int i = 0;
         for (NetBean netBean : list) {
            s1[i] = netBean.getWebsiteName();
            s2[i] = netBean.getCount();
            i++;
        }
        if(list.size()>0){
            map.put("accessAmount", s2);
            map.put("website", s1);
            return map;
        }
        return null;
     }
     
     /**
     * 获取设备策略List数据
     * 
     * @param params
     * @param request
     * @param model
     * @return
     */
     @RequestMapping(value="/find") 
     @ResponseBody
     public Page find(Integer start, Integer length,HttpSession session,HttpServletRequest request){
         OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
         String type = request.getParameter("type");
         String name = request.getParameter("name");
        // 其它
         if(StringUtil.isEmpty(type)){
             type = "0";
         }
        List<Integer> depardIds = getManagerIds(session);
        return netStatisticsService.find(organization.getId(), name, Integer.valueOf(type), start, length, depardIds);
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
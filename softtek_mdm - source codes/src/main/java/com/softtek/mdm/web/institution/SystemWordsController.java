package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.softtek.mdm.dao.SystemWordsDao;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.SystemWordsModel;
import com.softtek.mdm.service.SystemWordsService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;
import jodd.util.StringUtil;

/**
 * 统计管理-> 系统词库管理
 * @author jane.hui
 *
 */
@Controller
@RequestMapping(value="/institution/words")
public class SystemWordsController {

    @Autowired
    private SystemWordsService systemWordsService;
    
    private static Logger logger = Logger.getLogger(SystemWordsController.class);
    
    @Autowired
    private MessageSource messageSourceService;
    
    @Autowired
    private SystemWordsDao systemWordsDao;
    
    /**
     * 返回策略设备列表页面
     * @param request
     * @param response
     * @param session
     * @param model
     * @return
     */
    @Link(family="institution", label = "web.institution.system.words.index.label",parent="web.institution.homecontroller.index.link.label",belong="web.institution.system.words.index.belong")
    @RequestMapping(method=RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){
        model.addAttribute("websiteList", systemWordsService.selectClassifyList());
        return "institution/words/index";
    }
    
    /**
     * 获取词库管理的数据
     * @param params
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/queryAll")
    @ResponseBody
    public Page queryAll(Integer start, Integer length,HttpSession session,HttpServletRequest request){
        OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
        String name = request.getParameter("name");
        return systemWordsService.getSystemWordsList(organization.getId(), name, start, length);
    }
    
    
    /**
     * 系统词库删除功能
     * @param request
     * @return
     */
    @Log(operateType="web.institution.system.words.operatetype.remove",operateContent="web.institution.system.words.operatecontent.remove",args={"name"})
    @RequestMapping(value="/delWords")
    @ResponseBody
    public Object delWords(HttpServletRequest request){
        ResultDTO resultDto = new ResultDTO();
        String id = request.getParameter("id");
        try {
            if(StringUtil.isNotEmpty(id)){
                resultDto = systemWordsService.deleteWords(Integer.valueOf(id));
            }
        } catch(Exception e){
            logger.error("something wrong when delete system words by id, id is " + id);
            resultDto.type = BaseDTO.DANGER;
            resultDto.message = messageSourceService.getMessage("web.institution.system.words.delwords.success.lable",null, LocaleContextHolder.getLocale());
        }
        return resultDto;
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
        if(StringUtil.isNotBlank(id)){
            Integer intId = Integer.valueOf(id.toString());
            isExists = systemWordsService.exists(name, intId, organization.getId());
        } else {
            isExists = systemWordsService.exists(name, null, organization.getId());
        }
        
        if (isExists == true) {
            return "failed";
        } else {
            return "success";
        }
    }
    
    /**
     * 系统词库删除功能
     * @param request
     * @return
     */
    @Log(operateType="web.institution.system.words.operatetype.save",operateContent="web.institution.system.words.operatecontent.save",args={"name"})
    @RequestMapping(value="/saveWords",method=RequestMethod.POST)
    @ResponseBody
    public Object saveWords(HttpServletRequest request,HttpSession session){
        OrganizationModel organization=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
        ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
        ResultDTO resultDto = new ResultDTO();
        String name = request.getParameter("name");
        String website_type = request.getParameter("website_type");
        String id = request.getParameter("id");
        try {
            if(StringUtil.isNotEmpty(name)&&StringUtil.isNotEmpty(website_type)){
                SystemWordsModel systemWordsModel = new SystemWordsModel();
                systemWordsModel.setName(name);
                systemWordsModel.setWebsiteClassifyId(Integer.valueOf(website_type));
                systemWordsModel.setCreateDate(new Date());
                systemWordsModel.setCreateUser(manager.getId());
                systemWordsModel.setUpdateDate(new Date());
                systemWordsModel.setUpdateUser(manager.getId());
                systemWordsModel.setOrgId(organization.getId());
                int size = 0;
                if(StringUtil.isNotEmpty(id)){
                    systemWordsModel.setId(Integer.valueOf(id));
                    size = systemWordsDao.updateByPrimaryKeySelective(systemWordsModel);
                } else {
                    size = systemWordsDao.insertSelective(systemWordsModel);
                } 
                if(size == 0){
                    resultDto.type = BaseDTO.DANGER;
                    resultDto.message = messageSourceService.getMessage("web.institution.system.words.save.failed.lable", null,LocaleContextHolder.getLocale());
                    return resultDto;
                }
            }
        } catch(Exception e){
            if(StringUtil.isNotEmpty(id)){
                logger.error("something wrong when save system words by id, id is " + id+",exception message:"+e.toString());
            } else {
                logger.error("something wrong when save system words,exception message:"+e.toString());
            }
            resultDto.type = BaseDTO.DANGER;
            resultDto.message = messageSourceService.getMessage("web.institution.system.words.save.failed.lable",null, LocaleContextHolder.getLocale());
        }
        resultDto.type = BaseDTO.SUCCESS;
        resultDto.message = messageSourceService.getMessage("web.institution.system.words.save.success.lable",null, LocaleContextHolder.getLocale());
        return resultDto;
    }
    
    /**
     * 系统词库删除功能
     * @param request
     * @return
     */
    @RequestMapping(value="/getWords")
    @ResponseBody
    public Map<String,Object> getWords(HttpServletRequest request){
        String id = request.getParameter("id");
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtil.isNotEmpty(id)){
            SystemWordsModel systemWords = systemWordsDao.selectByPrimaryKey(Integer.valueOf(id));
            map.put("systemWords", systemWords);
            map.put("classifyList", systemWordsService.selectClassifyList());
            return map;
        }
        return map;
    }
}
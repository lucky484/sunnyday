package com.softtek.mdm.web.institution;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.softtek.mdm.abs.AbstractImportExcel;
import com.softtek.mdm.annotation.Log;
import com.softtek.mdm.annotation.Token;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.DeviceService;
import com.softtek.mdm.service.NameListService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.CommUtil;
import com.softtek.mdm.util.DataGridModel;
import com.softtek.mdm.util.ExcelUtils;
import com.softtek.mdm.util.ExportData;
import com.softtek.mdm.web.BaseController;
import com.softtek.mdm.web.http.BaseDTO;
import com.softtek.mdm.web.http.ResultDTO;
import jodd.util.StringUtil;

/**
 * 黑名单列表管理
 * @author jane.hui
 *
 */
@Controller
@RequestMapping(value="/institution/nameList")
public class NameListController extends BaseController {
	
	private static Logger logger = Logger.getLogger(NameListController.class);
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private MessageSource messageSource;
	
    /**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSourceService;
    
    @Autowired
    private NameListService nameListService;
    
    @Autowired
    @Qualifier("importNameListService")
    private AbstractImportExcel abstractImportExcel;
	
	/**
	 * 返回应用管理-黑白名单页面
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@Link(family="institution", label = "web.institution.name.list.index.label",parent="web.institution.homecontroller.index.link.label",belong="web.institution.application.index.link.belong")
	@RequestMapping(method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model){  
		return "institution/nameList/index";
	}
	
	/**
	 * 查询黑白名单列表数据
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
		String type = request.getParameter("type");
        return deviceService.selectNameList(manager,organization.getId(),objName, type, start, length);
	}
	
	/**
	 * 加载应用列表
	 * @param start
	 * @param length
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loadAppList")
	@ResponseBody
	public Page loadAppList(Integer start, Integer length,HttpSession session,HttpServletRequest request){
		return nameListService.loadAppList(start, length, session, request);
	}
	
	/**
	 * 名单删除功能
	 * @param request
	 * @return
	 */
	@Log(operateType="web.institution.name.list.operatetype.remove",operateContent="web.institution.name.list.operatecontent.remove",args={"name"})
	@RequestMapping(value="/delNameList")
	@ResponseBody
	public Object delNameList(HttpServletRequest request){
		ResultDTO resultDto = new ResultDTO();
		String id = request.getParameter("id");
		try {
		resultDto = deviceService.delNameList(request);
		} catch(Exception e){
			logger.error("something wrong when delete application black white list by id, id is " + id);
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.name.list.delnamelist.failed.lable",null, LocaleContextHolder.getLocale());
			return resultDto;
		}
		return resultDto;
	}
	
	/**
	 * 更新黑白名单列表
	 * @return
	 */
	@Token(needRemoveToken=true)
	@Log(operateType="web.institution.name.list.operatetype.modify",operateContent="web.institution.name.list.operatecontent.modify",args={"params[listName]"})
	@RequestMapping(value="/updateNameList",method = RequestMethod.POST)
	@ResponseBody
	public Object updateNameList(HttpServletRequest request,DataGridModel params,HttpSession session){
		ResultDTO resultDto = new ResultDTO();
		try {
		resultDto = deviceService.saveNameList(request, params);
		} catch(Exception e){
			logger.error("something wrong when add black white list,exception message is " + e.getMessage());
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.name.list.saveNameList.failed.lable",null, LocaleContextHolder.getLocale());
			return resultDto;
		}
		return resultDto;
	}
	
	/**
	 * 保存黑白名单列表
	 * @return
	 */
	@Token(needRemoveToken=true)
	@Log(operateType="web.institution.name.list.operatetype.add",operateContent="web.institution.name.list.operatecontent.add",args={"params[listName]"})
	@RequestMapping(value="/saveNameList",method = RequestMethod.POST)
	@ResponseBody
	public Object saveNameList(HttpServletRequest request,DataGridModel params,HttpSession session){
		ResultDTO resultDto = new ResultDTO();
		try {
		    resultDto = deviceService.saveNameList(request, params);
		} catch(Exception e){
			logger.error("something wrong when modify black white list,exception message is " + e.getMessage());
			resultDto.type = BaseDTO.DANGER;
			resultDto.message = messageSourceService.getMessage("web.institution.name.list.saveNameList.failed.lable",null, LocaleContextHolder.getLocale());
			return resultDto;
		}
		return resultDto;
	}
	
	/**
	 * 编辑应用
	 * @return
	 */
	@RequestMapping(value="/editApplication")
	@ResponseBody
	public Object editApplication(HttpServletRequest request){
		ResultDTO resultDTO = new ResultDTO();
		String id = request.getParameter("id");
		if(StringUtil.isBlank(id)) {
			return null;
		}
		String tokenStr = CommUtil.generate32UUID();
		request.getSession().setAttribute("token",tokenStr);
		resultDTO.token = tokenStr;
		resultDTO.result = deviceService.editApplication(Integer.valueOf(id));
		return resultDTO;
	}
	
	/**
	 * 查询名单名称是否存在
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exists", method = RequestMethod.GET)
	public @ResponseBody String isExists(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException {
		OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		String name= request.getParameter("listName");
		String id = request.getParameter("id");
		boolean isExists = false;
		if(StringUtil.isNotBlank(id)) {
			Integer intId = Integer.valueOf(id.toString());
			 isExists = deviceService.isNameListExists(name, intId, organization.getId());
		} else {
			isExists = deviceService.isNameListExists(name, null, organization.getId());
		}
		if (isExists == true) {
			return "failed";
		} else {
			return "success";
		}
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
     * 下载应用导入模板
     * 
     * @param request
     * @param response
     * @throws Exception
     */
	@RequestMapping(value = "/getAppExcelTemplate", method = RequestMethod.GET)
	@ResponseBody
	public void getuserexcelmodel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExportData exportData = new ExportData();
		String headers[][] = {
				{ messageSource.getMessage("web.institution.nameListcontroller.export.excel.label1", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.nameListcontroller.export.excel.label2", null,
						LocaleContextHolder.getLocale()), "String" },
				{ messageSource.getMessage("web.institution.nameListcontroller.export.excel.label3", null,
						LocaleContextHolder.getLocale()), "String" }};
		SXSSFWorkbook workbook = exportData.getwb(headers, "sheet1");
		// XSSFWorkbook workbook = new XSSFWorkbook();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload");
		OutputStream os = null;
		String fileName = messageSource.getMessage("web.institution.nameListcontroller.export.excel.model", null,
				LocaleContextHolder.getLocale());
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		os = response.getOutputStream();
		workbook.write(os);
		os.flush();
		os.close();
	}
	
	/**
     * 导入用户
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@Log(operateType = "logs.namelistcontroller.member.type.import", operateContent = "logs.namelistcontroller.content.member.import")
	@RequestMapping(value = "/importApps", method = RequestMethod.POST)
	@ResponseBody
	public Object importApps(MultipartFile files, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		ResultDTO resultDTO = new ResultDTO();
        // 判断是xls还是xlsx
		Integer fileType = ExcelUtils.getExcelVersion(files.getOriginalFilename());
		if(fileType==null) {
			String messages = messageSource.getMessage("web.institution.namelistcontroller.excel.erromodel.erromessages", null,LocaleContextHolder.getLocale());
			resultDTO.type = BaseDTO.ERROR;
			resultDTO.message = messages;
			return resultDTO;
		}
		Integer rowCount = 3;
		String[] titles = new String[3];
		titles[0] = messageSource.getMessage("web.institution.nameListcontroller.export.excel.label1", null, LocaleContextHolder.getLocale());
		titles[1] = messageSource.getMessage("web.institution.nameListcontroller.export.excel.label2", null, LocaleContextHolder.getLocale());
		titles[2] = messageSource.getMessage("web.institution.nameListcontroller.export.excel.label3", null, LocaleContextHolder.getLocale());
		return abstractImportExcel.createWorksbook(fileType, files, rowCount, titles);
	}
}

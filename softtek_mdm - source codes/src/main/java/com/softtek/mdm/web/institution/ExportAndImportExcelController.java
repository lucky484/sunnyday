package com.softtek.mdm.web.institution;

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

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.ExportAndImportExcelService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.ExportData;
import com.softtek.mdm.web.BaseController;


/**
 * @author josen.yang
 *
 */
@Controller
@RequestMapping(value = "/institution/excel")
public class ExportAndImportExcelController extends BaseController {
	
	@Autowired
	private ExportAndImportExcelService exportAndImportExcelService;
	@Autowired
	MessageSource messageSource;


	
	/**
	 * 下载虚拟组模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getvirmodel", method = RequestMethod.GET)
	@ResponseBody
	public void getvirmodel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExportData exportData = new ExportData();
		 //messageSource.getMessage("web.institution.exportandimportexcelcontroller.exceltip1", null, LocaleContextHolder.getLocale()), "String"
		String headers[][] = { { }, 
				{ messageSource.getMessage("web.institution.exportandimportexcelcontroller.exceltip2", null, LocaleContextHolder.getLocale()), "String" } };
		SXSSFWorkbook workbook = exportData.getwb(headers, "sheet1");
		// XSSFWorkbook workbook = new XSSFWorkbook();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload");
		OutputStream os = null;
		String fileName = messageSource.getMessage("web.institution.exportandimportexcelcontroller.exceltip3", null, LocaleContextHolder.getLocale())+".xlsx";
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		os = response.getOutputStream();
		workbook.write(os);
		os.flush();
		os.close();
	}
	
	/**
	 * 导入虚拟组成员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importvirmember", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> importvirmember(MultipartFile files, String id,String colid,String isradio,HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		//判断是xls还是xlsx
		Integer filetype=0;
		if(files.getOriginalFilename().toLowerCase().endsWith("xls")){
			 filetype=03; 
		}
		if(files.getOriginalFilename().toLowerCase().endsWith("xlsx")){
			 filetype=07; 
		}
		//获取机构id
		OrganizationModel organization = (OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		Integer orgid = organization.getId();
		//获取操作人信息
		ManagerModel managerModel = (ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		List<Integer> idlist = new ArrayList<Integer>();
		// 判断是否是管理员
		if (managerModel.getUser() != null) {
			@SuppressWarnings("unchecked")
			List<StructureModel> list1 = (List<StructureModel>) session.getAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString());
			for (StructureModel s : list1) {
				idlist.add(s.getId());
			}
		} else {
			idlist = null;
		}
		InputStream ins = files.getInputStream();
		Map<String, Integer> map=new HashMap<>();
		map.put("groupid",Integer.parseInt(id));
		map.put("orgid",orgid);
		map.put("colId",Integer.parseInt(colid));
		map.put("isradio",Integer.parseInt(isradio));
		map.put("filetype",filetype);
		Map<String, Object> resultMap= exportAndImportExcelService.importvirmember(ins,idlist,map);
		return resultMap;
	}
}

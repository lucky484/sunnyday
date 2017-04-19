package com.softtek.mdm.web.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.SecurityEventLogModel;
import com.softtek.mdm.service.SecurityEventLogService;

@Controller
@RequestMapping(value="/admin/securityEventLog")
public class SecurityEventLogController {
    
	@Autowired
	private SecurityEventLogService securityEventLogService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	 @Link(family="admin",label="web.admin.securityeventlog.manager.label")
	 @RequestMapping(method=RequestMethod.GET)
	 public String index(HttpServletRequest request){
		 return "admin/securityLog/index";
	 }
	 
	 /**
	  * 
	  * @param request
	  * @param session
	  * @param start
	  * @param length
	  * @return
	  */
	@RequestMapping(value="/queryAllSecurityLog")
	@ResponseBody
	public Page queryAllSecurityLog(HttpServletRequest request,HttpSession session,Integer start,Integer length) {
		Page page = new Page();
		String eventType = request.getParameter("eventType");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("eventType", eventType);
		map.put("start", start==null?0:start);
		map.put("pageSize", length==null?10:length);
		session.setAttribute("eventType", eventType);
		page = securityEventLogService.queryAllSecurityLog(map);
		return page;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value="/export", method = RequestMethod.GET)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{
		String eventType = (String) session.getAttribute("eventType") == "" ? "" : (String) session.getAttribute("eventType");
		HSSFWorkbook wb = exportExcle(eventType);
		response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition", "attachment;filename=securityAudit.xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close();
	}
	
	/**
	 * 
	 * @param in
	 * @param eventType
	 * @return
	 * @throws IOException
	 */
	public HSSFWorkbook exportExcle(String eventType) throws IOException{
		Map<String,Object> map = new HashMap<String, Object>();
		String str = "#800000";
		int[] color = color(str); //将16进制颜色转换为数组
		HSSFWorkbook wb = new HSSFWorkbook();
		String sheetName = messageSource.getMessage("tiles.institution.log.security.info.export", null,LocaleContextHolder.getLocale());
		String event_type = messageSource.getMessage("tiles.views.customer.device.index.table.eventtype", null,LocaleContextHolder.getLocale());
		String level = messageSource.getMessage("tiles.institution.log.security.level", null,LocaleContextHolder.getLocale());
		String ceateDate = messageSource.getMessage("tiles.institution.log.security.create.date", null,LocaleContextHolder.getLocale());
		String eventContent = messageSource.getMessage("tiles.institution.log.security.event.content", null,LocaleContextHolder.getLocale());
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setColumnWidth(0, 30*256);
    	sheet.setColumnWidth(1, 30*256);
    	sheet.setColumnWidth(2, 30*256);
    	sheet.setColumnWidth(3, 30*256);
    	//在sheet中添加表头行
    	HSSFRow row = sheet.createRow((int)0);
    	row.setHeight((short) (12.75*20));
    	//创建单元格，设置表头样式
    	HSSFCellStyle style = wb.createCellStyle();
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
    	HSSFPalette palette = wb.getCustomPalette(); 
	    palette.setColorAtIndex(HSSFColor.RED.index,(byte)color[0], (byte)color[1], (byte)color[2]);//自定义颜色
    	HSSFFont font=wb.createFont();
    	font.setColor(HSSFColor.RED.index);
    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	font.setFontName("Times New Roman");
    	style.setFont(font);
    	HSSFCell cell = row.createCell((int) 0);
    	cell.setCellValue(event_type);
    	cell.setCellStyle(style);
        cell = row.createCell((int) 1);
    	cell.setCellValue(level);
    	cell.setCellStyle(style);
    	cell = row.createCell((int) 2);
     	cell.setCellValue(ceateDate);
     	cell.setCellStyle(style);
     	cell = row.createCell((int) 3);
     	cell.setCellValue(eventContent);
     	cell.setCellStyle(style);
		int i = 0;
		map.put("eventType",eventType);
		//查询要导出的数据
		List<SecurityEventLogModel> list = securityEventLogService.querySecurityLogExport(map);
		String passwordError = messageSource.getMessage("tiles.institution.log.security.password.error", null,LocaleContextHolder.getLocale());
		String urlError = messageSource.getMessage("tiles.institution.log.security.url.error", null,LocaleContextHolder.getLocale());
		String nomal = messageSource.getMessage("tiles.institution.log.security.level.nomal", null,LocaleContextHolder.getLocale());
		String important = messageSource.getMessage("tiles.institution.log.security.level。important", null,LocaleContextHolder.getLocale());
		//向单元格中填充数据
		for(SecurityEventLogModel security : list){
			i++;
		    if(security.getEventType().equals("2")){
				security.setEventTypeStr(passwordError);
			}else{
				security.setEventTypeStr(urlError);
			}
			if(security.getLevel().equals("1")){
				security.setLevelStr(nomal);
			}else{
				security.setLevelStr(important);
			}
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(security.getEventTypeStr());
			row.createCell(1).setCellValue(security.getLevelStr());
			row.createCell(2).setCellValue(security.getCreateDateStr());
			row.createCell(3).setCellValue(security.getOperateContent());
		}
		return wb;
	}
	 public int[] color(String str){
    	 int[] color=new int[3];
    	 color[0]=Integer.parseInt(str.substring(1, 3), 16); 
    	 color[1]=Integer.parseInt(str.substring(3, 5), 16); 
    	 color[2]=Integer.parseInt(str.substring(5, 7), 16); 
    	 return color;
     }
}

package com.softtek.pst.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.softtek.pst.model.ChanceManageModel;
import com.softtek.pst.model.LoggingModel;
import com.softtek.pst.model.ProjectManagerModel;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.ChanceManageService;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.service.ProjectManagerService;
import com.softtek.pst.util.Page;


@Controller
@RequestMapping(value="/projectsManagement/chanceManage")
public class ChanceManageController {
          
	
	
		 private Logger logger = Logger.getLogger(ChanceManageController.class);
		 private final int ORIGIN_X = 0; // 报表中写入内容的起始横坐标，即excel的行
		 private final int ORIGIN_Y = 0; // 报表中写入内容的起始纵坐标，即excel的列
		
	     @Autowired
	     private ChanceManageService chanceManageService;
	     
	     @Autowired
		 private LoggingService loggingService;
	     
	     @Autowired
	     private ProjectManagerService projectManagerService;
	     
	     @RequestMapping(value="/chanceManage")
	     public String chanceManage(HttpServletRequest request){
	    	 request.setAttribute("url", request.getServletPath());
	    	 return "chanceManage/chanceManageList";
	     }
	     
	     @RequestMapping(value="/addChanceManage")
	     public String addChanceManage(HttpServletRequest request){
	    	 request.setAttribute("url", request.getServletPath());
	    	 List<ProjectManagerModel> list = projectManagerService.queryAllProjectManager();
	    	 request.setAttribute("list", list);
	    	 return "chanceManage/addChanceManage";
	     }
	     
	     @RequestMapping(value="/edit")
	     public ModelAndView editChanceManage(long chanceManageId,HttpServletRequest request){
	    	 request.setAttribute("url", request.getServletPath());
	    	 ChanceManageModel chanceManage = chanceManageService.queryDetailById(chanceManageId);
	    	 List<ProjectManagerModel> list = projectManagerService.queryAllProjectManager();
	    	 ModelAndView mav = new ModelAndView("chanceManage/editChanceManage");
	    	 mav.addObject("cm", chanceManage);
	    	 mav.addObject("list", list);
	    	 return mav;
	     }
	     
	     @RequestMapping(value="/view")
	     public ModelAndView viewChanceManage(long chanceManageId,HttpServletRequest request){
	    	 request.setAttribute("url", request.getServletPath());
	    	 ChanceManageModel chanceManage = chanceManageService.queryDetailById(chanceManageId);
	    	 ModelAndView mav = new ModelAndView("chanceManage/viewChanceManage");
	    	 mav.addObject("cm", chanceManage);
	    	 return mav;
	     }
	     
	     @RequestMapping(value="/getChanceManageLists")
	     @ResponseBody
	     public Page<ChanceManageModel> getChanceManageLists(Integer start, Integer length, String column, String dir, String sch,
					HttpServletRequest request,HttpSession session){
	    	 if (sch != null && !sch.equals("")) {
					sch = "%" + sch + "%";
				} else {
					sch = "%%";
				}
			   Page<ChanceManageModel> page = new Page<ChanceManageModel>();
			   page = chanceManageService.getChanceManage(start, length, column, dir, sch);
			   UserModel user = (UserModel) request.getSession().getAttribute("user");
			   String str = (String) request.getSession().getAttribute("authorities");
			   List<ChanceManageModel> list = page.getData();
			   if(!str.contains("cr_price_read")){
				   list.stream().forEach(chanceManage ->{
					   if(user.getUserId() != chanceManage.getCreatorId()){
						   chanceManage.setForecastQuotation(0); 
					   }
				   });
			   }
			   session.setAttribute("search", sch);
			   return page;
	     }
	     
	     @RequestMapping("/insertChanceManage")
	     @ResponseBody
	     public Map<String,Object> insertChanceManage(HttpServletRequest request,ChanceManageModel chanceManage) throws ParseException{
	    	 UserModel user = (UserModel) request.getSession().getAttribute("user");
	    	 Map<String,Object> map = new HashMap<String, Object>();
	    	 String leadTime = request.getParameter("lead_time");
	    	 String startTime = request.getParameter("start_time");
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	 chanceManage.setLeadTime(sdf.parse(leadTime));
	    	 chanceManage.setStartTime(sdf.parse(startTime));
	    	 chanceManage.setCreatorId(user.getUserId());
	    	 chanceManage.setUpdateBy(user.getUserId());
	    	 int result = chanceManageService.addChanceManage(chanceManage);
	    	 if(result == 1){
	    		    // 记录日志
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "新增机会管理:【" + chanceManage.getProjectName() + "】");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(1);
					loggingModel.setEventName("增加");
					loggingModel.setTables("chance_manage");
					loggingService.addLogging(loggingModel);
					map.put("success", true);
	    	 }else{
	    		    map.put("fail", false);
	    		    map.put("errmsg", "添加失败，请稍候再试或联系管理员");
	    	 }
	    	 return map;
	     }
	     
	 	@RequestMapping("/updateChanceManage")
		@ResponseBody
		public Map<String,Object> updateChanceManage(HttpServletRequest request,ChanceManageModel chanceManage) throws ParseException{
			Map<String,Object> map = new HashMap<String, Object>();
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			String leadTime = request.getParameter("lead_time");
	    	String startTime = request.getParameter("start_time");
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	chanceManage.setLeadTime(sdf.parse(leadTime));
	    	chanceManage.setStartTime(sdf.parse(startTime));
	    	chanceManage.setUpdateBy(user.getUserId());
			int result = chanceManageService.updateChanceManage(chanceManage);
			if(result == 1){
				// 记录日志
				LoggingModel loggingModel = new LoggingModel();
				loggingModel.setOperator(user.getChineseName());
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = (date.format(new Date())).toString();
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "修改机会管理:【" + chanceManage.getProjectName() + "】");
				loggingModel.setUserId(user.getUserId());
				loggingModel.setEventType(1);
				loggingModel.setEventName("修改");
				loggingModel.setTables("chance_manage");
				loggingService.addLogging(loggingModel);
				map.put("success", true);
			}else{
				map.put("fail", false);
				map.put("errmsg", "添加失败，请稍候再试或联系管理员");
			}
			return map;
		}
	 	
	 	@RequestMapping("/deleteChanceManage")
		@ResponseBody
		public Map<String,Object> deleteChanceManage(long id,HttpServletRequest request){
			Map<String,Object> map = new HashMap<String, Object>();
			int result = chanceManageService.deleteChanceManage(id);
			if(result == 1){
				UserModel user = (UserModel) request.getSession().getAttribute("user");
				LoggingModel loggingModel = new LoggingModel();
				loggingModel.setOperator(user.getChineseName());
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = (date.format(new Date())).toString();
				ChanceManageModel chanceManage = chanceManageService.queryDetailById(id);
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "删除机会管理【" + chanceManage.getProjectName() + "】");
				loggingModel.setUserId(user.getUserId());
				loggingModel.setEventType(2);
				loggingModel.setEventName("删除");
				loggingModel.setTables("chance_manage");
				loggingService.addLogging(loggingModel);
				map.put("result", result);
			}
			return map;
		}
	 	
		@RequestMapping("/export")
		public void export(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws WriteException{
			String sch = (String) session.getAttribute("search");
			List<ChanceManageModel> list = chanceManageService.getChanceManage(null, null, "create_time", "desc", sch).getData();
			WritableWorkbook wb = null;
			OutputStream stream = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
			String fileName = "机会管理列表" + format.format(new Date()) + ".xls";
			try {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				response.setContentType("application/vnd.ms-excel");
				stream = response.getOutputStream();
			} catch (IOException e1) {
				logger.info(e1.getMessage());
			}
			int i = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableFont wfont1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 标题单元格定义
			try {
				wcf_title.setAlignment(jxl.format.Alignment.CENTRE);
				WritableCellFormat wcf = new WritableCellFormat(wfont);
				WritableCellFormat wcf1 = new WritableCellFormat(wfont1);
				wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置标题居中对齐
				wcf1.setAlignment(jxl.format.Alignment.CENTRE); // 设置内容居中对齐
				wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);// 设置边框
				wcf1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);// 设置边框
				// 创建workbook
				wb = Workbook.createWorkbook(stream);
				WritableSheet sheet = wb.createSheet("机会管理列表", i++);
				Label label;
				label = new Label(ORIGIN_Y, ORIGIN_X, "机会管理列表", wcf_title);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y, 21);
				label = new Label(ORIGIN_Y, ORIGIN_X + 1, "项目名称", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 1, ORIGIN_X + 1, "项目经理", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 2, ORIGIN_X + 1, "开始日期", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 3, ORIGIN_X + 1, "交付日期", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 4, ORIGIN_X + 1, "预估报价", wcf);
				sheet.addCell(label);
				for(ChanceManageModel chanceManage : list){
					i++;
					label = new Label(ORIGIN_Y, ORIGIN_X + i, chanceManage.getProjectName(), wcf1);
					sheet.addCell(label);
					
					label = new Label(ORIGIN_Y + 1, ORIGIN_X + i, chanceManage.getProjectManagerName(), wcf1);
					sheet.addCell(label);
					sheet.setColumnView(1, 18);
					
					label = new Label(ORIGIN_Y + 2, ORIGIN_X + i, sdf.format(chanceManage.getStartTime()), wcf1);
					sheet.addCell(label);
					sheet.setColumnView(ORIGIN_Y + 2, 18);
					
					label = new Label(ORIGIN_Y + 3, ORIGIN_X + i, sdf.format(chanceManage.getLeadTime()), wcf1);
					sheet.addCell(label);
					sheet.setColumnView(ORIGIN_Y + 3, 18);
					
					label = new Label(ORIGIN_Y + 4, ORIGIN_X + i, String.valueOf(chanceManage.getForecastQuotation()), wcf1);
					sheet.addCell(label);
					sheet.setColumnView(ORIGIN_Y + 4, 18);
				}
				// 记录日志
				UserModel user = (UserModel) request.getSession().getAttribute("user");
				LoggingModel loggingModel = new LoggingModel();
				loggingModel.setOperator(user.getChineseName());
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = (date.format(new Date())).toString();
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "导出了机会管理列表");
				loggingModel.setUserId(user.getUserId());
				loggingModel.setEventType(4);
				loggingModel.setEventName("导出");
				loggingModel.setTables("chance_manage");
				loggingService.addLogging(loggingModel);
				wb.write();
				wb.close();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}

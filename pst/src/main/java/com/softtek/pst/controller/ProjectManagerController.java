package com.softtek.pst.controller;

import java.io.IOException;
import java.io.OutputStream;
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

import com.softtek.pst.model.LoggingModel;
import com.softtek.pst.model.ProjectManagerCommentModel;
import com.softtek.pst.model.ProjectManagerModel;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.service.ProjectManagerCommentService;
import com.softtek.pst.service.ProjectManagerService;
import com.softtek.pst.util.Page;

@Controller
@RequestMapping(value="/projectsManagement/projectManager")
public class ProjectManagerController {
       
	
	   private Logger logger = Logger.getLogger(ProjectManagerController.class);
	   private final int ORIGIN_X = 0; // 报表中写入内容的起始横坐标，即excel的行
	   private final int ORIGIN_Y = 0; // 报表中写入内容的起始纵坐标，即excel的列
		
	   @Autowired
	   private ProjectManagerService projectManagerService;
	   
	   @Autowired
	   private LoggingService loggingService;
	   
	   @Autowired
	   private ProjectManagerCommentService projectManagerCommentService;
	   
		@RequestMapping(value = "projectManagerList")
		public String getprojectManager(HttpServletRequest request) {
			request.setAttribute("url", request.getServletPath());
			return "projectManager/projectManagerList";
		}
		
		@RequestMapping(value = "addProjectManager")
		public String addProjectManager(HttpServletRequest request){
			request.setAttribute("url", request.getServletPath());
			return "projectManager/addProjectManager";
		}
	    
		@RequestMapping(value = "edit")
		public ModelAndView edit(long projectManagerId,HttpServletRequest request){
			request.setAttribute("url", request.getServletPath());
			ProjectManagerModel pm = projectManagerService.queryDetailById(projectManagerId);
			ModelAndView mav = new ModelAndView("projectManager/editProjectManager");
			mav.addObject("pm", pm);
			return mav;
		} 
		
		@RequestMapping(value = "view")
		public ModelAndView view(long projectManagerId,HttpServletRequest request){
			request.setAttribute("url", request.getServletPath());
			ProjectManagerModel pm = projectManagerService.queryDetailById(projectManagerId);
			List<ProjectManagerCommentModel> list = projectManagerCommentService.queryCommentByProjectManagerId(projectManagerId);
			ModelAndView mav = new ModelAndView("projectManager/viewProjectManager");
			mav.addObject("pm", pm);
			mav.addObject("list", list);
			return mav;
		}
		
	   @RequestMapping(value="/getProjectManagerLists")
	   @ResponseBody
	   public Page<ProjectManagerModel> getProjectManagerLists(Integer start, Integer length, String column, String dir, String sch,
				HttpServletRequest request,HttpSession session){
		   if (sch != null && !sch.equals("")) {
				sch = "%" + sch + "%";
			} else {
				sch = "%%";
			}
		   Page<ProjectManagerModel> page = new Page<ProjectManagerModel>();
		   page = projectManagerService.getProjectManager(start, length, column, dir,sch);
		   session.setAttribute("search", sch);
		   return page;
	   }
	   
		@RequestMapping("/insertProjectManager")
		@ResponseBody
	   public Map<String,Object> addProjectManager(HttpServletRequest request,ProjectManagerModel projectManager){
		   Map<String,Object> result = new HashMap<String, Object>();
		   UserModel user = (UserModel) request.getSession().getAttribute("user");
		   projectManager.setCreatorId(user.getUserId());
		   projectManager.setUpdateBy(user.getUserId());
		   int count = projectManagerService.addProjectManager(projectManager);
		   if(count == 1){
				// 记录日志
				LoggingModel loggingModel = new LoggingModel();
				loggingModel.setOperator(user.getChineseName());
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = (date.format(new Date())).toString();
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "新增项目经理:【" + projectManager.getName() + "】");
				loggingModel.setUserId(user.getUserId());
				loggingModel.setEventType(1);
				loggingModel.setEventName("增加");
				loggingModel.setTables("project_manager");
				loggingService.addLogging(loggingModel);
				result.put("success", true);
		   }else{
			   result.put("fail", false);
			   result.put("errmsg", "添加失败，请稍候再试或联系管理员");
		   }
		   return result;
	   }
	   
		@RequestMapping("/updateProjectManager")
		@ResponseBody
		public Map<String,Object> updateProjectManager(HttpServletRequest request,ProjectManagerModel projectManager){
			Map<String,Object> map = new HashMap<String, Object>();
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			projectManager.setUpdateBy(user.getUserId());
			int result = projectManagerService.updateProjectManager(projectManager);
			if(result == 1){
				// 记录日志
				LoggingModel loggingModel = new LoggingModel();
				loggingModel.setOperator(user.getChineseName());
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = (date.format(new Date())).toString();
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "修改项目经理:【" + projectManager.getName() + "】");
				loggingModel.setUserId(user.getUserId());
				loggingModel.setEventType(1);
				loggingModel.setEventName("修改");
				loggingModel.setTables("project_manager");
				loggingService.addLogging(loggingModel);
				map.put("success", true);
			}else{
				map.put("fail", false);
				map.put("errmsg", "添加失败，请稍候再试或联系管理员");
			}
			return map;
		}
		
		@RequestMapping("/addComment")
		@ResponseBody
		public Map<String,Object> addComment(HttpServletRequest request,String projectManagerId,String comment){
			Map<String,Object> map = new HashMap<String, Object>();
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			ProjectManagerCommentModel pmc = new ProjectManagerCommentModel();
			pmc.setComment(comment);
			pmc.setCritic(user.getChineseName());
			pmc.setProjectManagerId(Long.parseLong(projectManagerId));
			int result = projectManagerCommentService.addProjectManagerComment(pmc);
			if(result == 1){
				// 记录日志
				LoggingModel loggingModel = new LoggingModel();
				loggingModel.setOperator(user.getChineseName());
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = (date.format(new Date())).toString();
				ProjectManagerModel pm = new ProjectManagerModel();
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "对【" + pm.getName() + "】" + "进行了评论");
				loggingModel.setUserId(user.getUserId());
				loggingModel.setEventType(5);
				loggingModel.setEventName("评论");
				loggingModel.setTables("project_manager_comment");
				loggingService.addLogging(loggingModel);
				map.put("success", true);
				pmc.setCreateTime(new Date());
				map.put("comment", pmc);
			}else {
				map.put("success", false);
			}
			return map;
		}
		
		@RequestMapping("/deleteProjectManager")
		@ResponseBody
		public Map<String,Object> deleteProjectManager(long id,HttpServletRequest request){
			Map<String,Object> map = new HashMap<String, Object>();
			int result = projectManagerService.deleteProjectManager(id);
			if(result == 1){
				UserModel user = (UserModel) request.getSession().getAttribute("user");
				LoggingModel loggingModel = new LoggingModel();
				loggingModel.setOperator(user.getChineseName());
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = (date.format(new Date())).toString();
				ProjectManagerModel projectManagerModel = projectManagerService.queryDetailById(id);
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "删除项目经理【" + projectManagerModel.getName() + "】");
				loggingModel.setUserId(user.getUserId());
				loggingModel.setEventType(2);
				loggingModel.setEventName("删除");
				loggingModel.setTables("project_manager");
				loggingService.addLogging(loggingModel);
				map.put("result", result);
			}
			return map;
		}
		@RequestMapping("/export")
		public void export(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws WriteException{
			String search = (String)session.getAttribute("search");
			List<ProjectManagerModel> list = projectManagerService.getProjectManagerListsExport(search);
			WritableWorkbook wb = null;
			OutputStream stream = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
			String fileName = "项目经理列表" + format.format(new Date()) + ".xls";
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
				WritableSheet sheet = wb.createSheet("项目经理列表", i++);
				Label label;
				label = new Label(ORIGIN_Y, ORIGIN_X, "项目经理列表", wcf_title);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y, 21);
				label = new Label(ORIGIN_Y, ORIGIN_X + 1, "姓名", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 1, ORIGIN_X + 1, "邮箱", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 2, ORIGIN_X + 1, "电话", wcf);
				sheet.addCell(label);
				label = new Label(ORIGIN_Y + 3, ORIGIN_X + 1, "添加时间", wcf);
				sheet.addCell(label);
				for(ProjectManagerModel projectManager : list){
					i++;
					label = new Label(ORIGIN_Y, ORIGIN_X + i, projectManager.getName(), wcf1);
					sheet.addCell(label);
					
					label = new Label(ORIGIN_Y + 1, ORIGIN_X + i, projectManager.getEmail(), wcf1);
					sheet.addCell(label);
					sheet.setColumnView(1, 18);
					
					label = new Label(ORIGIN_Y + 2, ORIGIN_X + i, projectManager.getPhone(), wcf1);
					sheet.addCell(label);
					sheet.setColumnView(ORIGIN_Y + 2, 18);
					
					label = new Label(ORIGIN_Y + 3, ORIGIN_X + i, sdf.format(projectManager.getCreateTime()), wcf1);
					sheet.addCell(label);
					sheet.setColumnView(ORIGIN_Y + 3, 18);
				}
				// 记录日志
				UserModel user = (UserModel) request.getSession().getAttribute("user");
				LoggingModel loggingModel = new LoggingModel();
				loggingModel.setOperator(user.getChineseName());
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = (date.format(new Date())).toString();
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "导出了项目经理列表");
				loggingModel.setUserId(user.getUserId());
				loggingModel.setEventType(4);
				loggingModel.setEventName("导出");
				loggingModel.setTables("project_manager");
				loggingService.addLogging(loggingModel);
				wb.write();
				wb.close();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}

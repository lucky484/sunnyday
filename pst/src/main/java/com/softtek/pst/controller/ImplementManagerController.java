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

import com.softtek.pst.model.ImplementManagerCommentModel;
import com.softtek.pst.model.ImplementManagerModel;
import com.softtek.pst.model.LoggingModel;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.ImplementManagerCommentService;
import com.softtek.pst.service.ImplementManagerService;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.util.Page;

@Controller
@RequestMapping(value="/projectsManagement/implementManager")
public class ImplementManagerController {
         
	     private Logger logger = Logger.getLogger(ImplementManagerController.class);
		 private final int ORIGIN_X = 0; // 报表中写入内容的起始横坐标，即excel的行
		 private final int ORIGIN_Y = 0; // 报表中写入内容的起始纵坐标，即excel的列
			     
	     @Autowired
	     private ImplementManagerService implementManagerService;
	     
	     @Autowired
		 private LoggingService loggingService;
	     
	     @Autowired
	     private ImplementManagerCommentService implementManagerCommentService;
	
	     @RequestMapping(value="/implementManagerList")
	     public String getImplementManager(HttpServletRequest request){
	    		request.setAttribute("url", request.getServletPath());
	    		return "implementManager/implementManagerList";
	     }
	     
	     @RequestMapping(value="/addImplementManager")
	     public String addImplementManager(HttpServletRequest request){
	    	    request.setAttribute("url", request.getServletPath());
	    		return "implementManager/addImplementManager";
	     }
	     
	     @RequestMapping(value="/edit")
	     public ModelAndView editImplementManager(long implementManagerId, HttpServletRequest request){
	    	 request.setAttribute("url", request.getServletPath());
	    	 ImplementManagerModel implementManager = implementManagerService.queryDetailById(implementManagerId);
	    	 ModelAndView mav = new ModelAndView("implementManager/editImplementManager");
	    	 mav.addObject("im", implementManager);
	    	 return mav;
	     }
	     
	 	@RequestMapping(value = "view")
		public ModelAndView view(long implementManagerId,HttpServletRequest request){
			request.setAttribute("url", request.getServletPath());
			ImplementManagerModel implementManager = implementManagerService.queryDetailById(implementManagerId);
			List<ImplementManagerCommentModel> list = implementManagerCommentService.queryCommentByImplementManagerId(implementManagerId);
			ModelAndView mav = new ModelAndView("implementManager/viewImplementManager");
			mav.addObject("im", implementManager);
			mav.addObject("list", list);
			return mav;
		}
	     
	     @RequestMapping(value="/getImplementManagerLists")
	     @ResponseBody
	     public Page<ImplementManagerModel> getImplementManagerLists(Integer start, Integer length, String column, String dir, String sch,
					HttpServletRequest request,HttpSession session){
	    	 if (sch != null && !sch.equals("")) {
					sch = "%" + sch + "%";
				} else {
					sch = "%%";
				}
			   Page<ImplementManagerModel> page = new Page<ImplementManagerModel>();
			   page = implementManagerService.getImplementManager(start, length, column, dir, sch);
			   session.setAttribute("search", sch);
			   return page;
	     }
	     
	     @RequestMapping("/insertImplementManager")
	     @ResponseBody
	     public Map<String,Object> addImplementManager(HttpServletRequest request,ImplementManagerModel implementManager){
	    	 Map<String,Object> map = new HashMap<String, Object>();
	    	 UserModel user = (UserModel) request.getSession().getAttribute("user");
	    	 implementManager.setCreatorId(user.getUserId());
	    	 implementManager.setUpdateBy(user.getUserId());
	    	 int result = implementManagerService.addImplementManager(implementManager);
	    	 if(result == 1){
	    		    // 记录日志
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "新增客户经理:【" + implementManager.getName() + "】");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(1);
					loggingModel.setEventName("增加");
					loggingModel.setTables("implement_manager");
					loggingService.addLogging(loggingModel);
					map.put("success", true);
	    	 }else{
	    		    map.put("fail", false);
	    		    map.put("errmsg", "添加失败，请稍候再试或联系管理员");
	    	 }
	    	 return map;
	     }
	     
			@RequestMapping("/updateImplementManager")
			@ResponseBody
			public Map<String,Object> updateImplementManager(HttpServletRequest request,ImplementManagerModel implementManager){
				Map<String,Object> map = new HashMap<String, Object>();
				UserModel user = (UserModel) request.getSession().getAttribute("user");
				implementManager.setUpdateBy(user.getUserId());
				int result = implementManagerService.updateImplementManager(implementManager);
				if(result == 1){
					// 记录日志
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "修改客户经理:【" + implementManager.getName() + "】");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(1);
					loggingModel.setEventName("修改");
					loggingModel.setTables("implement_manager");
					loggingService.addLogging(loggingModel);
					map.put("success", true);
				}else{
					map.put("fail", false);
					map.put("errmsg", "添加失败，请稍候再试或联系管理员");
				}
				return map;
			}
			
			@RequestMapping("/deleteImplementManager")
			@ResponseBody
			public Map<String,Object> deleteImplementManager(long id,HttpServletRequest request){
				Map<String,Object> map = new HashMap<String, Object>();
				int result = implementManagerService.deleteImplementManager(id);
				if(result == 1){
					UserModel user = (UserModel) request.getSession().getAttribute("user");
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					ImplementManagerModel implementManager = implementManagerService.queryDetailById(id);
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "删除客户经理【" + implementManager.getName() + "】");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(2);
					loggingModel.setEventName("删除");
					loggingModel.setTables("implement_manager");
					loggingService.addLogging(loggingModel);
					map.put("result", result);
				}
				return map;
			}
			
			@RequestMapping("/addComment")
			@ResponseBody
			public Map<String,Object> addComment(HttpServletRequest request,String implementManagerId,String comment){
				Map<String,Object> map = new HashMap<String, Object>();
				UserModel user = (UserModel) request.getSession().getAttribute("user");
				ImplementManagerCommentModel implementManagerComment = new ImplementManagerCommentModel();
				implementManagerComment.setComment(comment);
				implementManagerComment.setCritic(user.getChineseName());
				implementManagerComment.setImplementManagerId(Long.parseLong(implementManagerId));
				int result = implementManagerCommentService.addImplementManagerComment(implementManagerComment);
				if(result == 1){
					// 记录日志
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					ImplementManagerModel im = new ImplementManagerModel();
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "对【" + im.getName() + "】" + "进行了评论");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(5);
					loggingModel.setEventName("评论");
					loggingModel.setTables("implement_manager_comment");
					loggingService.addLogging(loggingModel);
					map.put("success", true);
					implementManagerComment.setCreateTime(new Date());
					map.put("comment", implementManagerComment);
				}else {
					map.put("success", false);
				}
				return map;
			}
			@RequestMapping("/export")
			public void export(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws WriteException{
				String sch = (String) session.getAttribute("search");
				List<ImplementManagerModel> list = implementManagerService.getImplementManager(null, null, "create_time", "desc", sch).getData();
				WritableWorkbook wb = null;
				OutputStream stream = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
				String fileName = "客户经理列表" + format.format(new Date()) + ".xls";
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
					WritableSheet sheet = wb.createSheet("客户经理列表", i++);
					Label label;
					label = new Label(ORIGIN_Y, ORIGIN_X, "客户经理列表", wcf_title);
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
					for(ImplementManagerModel implementManager : list){
						i++;
						label = new Label(ORIGIN_Y, ORIGIN_X + i, implementManager.getName(), wcf1);
						sheet.addCell(label);
						
						label = new Label(ORIGIN_Y + 1, ORIGIN_X + i, implementManager.getEmail(), wcf1);
						sheet.addCell(label);
						sheet.setColumnView(1, 18);
						
						label = new Label(ORIGIN_Y + 2, ORIGIN_X + i, implementManager.getPhone(), wcf1);
						sheet.addCell(label);
						sheet.setColumnView(ORIGIN_Y + 2, 18);
						
						label = new Label(ORIGIN_Y + 3, ORIGIN_X + i, sdf.format(implementManager.getCreateTime()), wcf1);
						sheet.addCell(label);
						sheet.setColumnView(ORIGIN_Y + 3, 18);
					}
					// 记录日志
					UserModel user = (UserModel) request.getSession().getAttribute("user");
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "导出了客户经理列表");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(4);
					loggingModel.setEventName("导出");
					loggingModel.setTables("implement_manager");
					loggingService.addLogging(loggingModel);
					wb.write();
					wb.close();
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
}

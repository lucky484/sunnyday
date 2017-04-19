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
import com.softtek.pst.model.OutsourcingManageCommentModel;
import com.softtek.pst.model.OutsourcingManageModel;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.service.OutsourcingManageCommentService;
import com.softtek.pst.service.OutsourcingManageService;
import com.softtek.pst.util.Page;

@Controller
@RequestMapping(value="/projectsManagement/outsourcingManage")
public class OutsourcingManageController {
       
	   private Logger logger = Logger.getLogger(OutsourcingManageController.class);
	   private final int ORIGIN_X = 0; // 报表中写入内容的起始横坐标，即excel的行
	   private final int ORIGIN_Y = 0; // 报表中写入内容的起始纵坐标，即excel的列
	   
	   
	   @Autowired
	   private OutsourcingManageService outsourcingManageService;
	   
	   @Autowired
	   private OutsourcingManageCommentService outsourcingManageCommentService;
	   
	   @Autowired
	   private LoggingService loggingService;
	
	    @RequestMapping(value="/outsourcingManage")
	    public String outsourcingManage(HttpServletRequest request){
	    	request.setAttribute("url", request.getServletPath());
	    	return "outsourcingManage/outsourcingManageList";
	    }
	    
	    @RequestMapping(value="/addOutsourcingManage")
	    public String addOutsourcingManage(HttpServletRequest request){
	    	request.setAttribute("url", request.getServletPath());
	    	return "outsourcingManage/addOutsourcingManage";
	    }
	    
	    @RequestMapping(value="/edit")
	    public ModelAndView editOutsourcing(long outsourcingManageId ,HttpServletRequest request){
	    	request.setAttribute("url", request.getServletPath());
	    	OutsourcingManageModel outsourcingMange = outsourcingManageService.queryDetailById(outsourcingManageId);
	        ModelAndView mav = new ModelAndView("outsourcingManage/editOutsourcingManage");
	        mav.addObject("om", outsourcingMange);
	        return mav;
	    }
	    
	    @RequestMapping(value="/view")
	    public ModelAndView viewOutsourcing(long outsourcingManageId ,HttpServletRequest request){
	    	request.setAttribute("url", request.getServletPath());
	    	OutsourcingManageModel outsourcingMange = outsourcingManageService.queryDetailById(outsourcingManageId);
	    	List<OutsourcingManageCommentModel> list = outsourcingManageCommentService.queryCommentByOutSourcingManageId(outsourcingManageId);
	        ModelAndView mav = new ModelAndView("outsourcingManage/viewOutsourcingManage");
	        mav.addObject("om", outsourcingMange);
	        mav.addObject("list", list);
	        return mav;
	    }
	    
	     @RequestMapping(value="/getOutsourcingManageLists")
	     @ResponseBody
	     public Page<OutsourcingManageModel> getOutsourcingManageLists(Integer start, Integer length, String column, String dir, String sch,
					HttpServletRequest request,HttpSession session){
	    	 if (sch != null && !sch.equals("")) {
					sch = "%" + sch + "%";
				} else {
					sch = "%%";
				}
			   Page<OutsourcingManageModel> page = new Page<OutsourcingManageModel>();
			   page = outsourcingManageService.getOutsourcingManage(start, length, column, dir, sch);
			   session.setAttribute("search", sch);
			   return page;
	     }
	     
	     @RequestMapping("/insertOutsourcingManage")
	     @ResponseBody
	     public Map<String,Object> addOutsourcingManage(HttpServletRequest request,OutsourcingManageModel outsourcingMange){
	    	 Map<String,Object> map = new HashMap<String, Object>();
	    	 UserModel user = (UserModel) request.getSession().getAttribute("user");
	    	 outsourcingMange.setCreatorId(user.getUserId());
	    	 outsourcingMange.setUpdateBy(user.getUserId());
	    	 int result = outsourcingManageService.addOutsourcingManage(outsourcingMange);
	    	 if(result == 1){
	    		    // 记录日志
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "新增外包管理:【" + outsourcingMange.getName() + "】");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(1);
					loggingModel.setEventName("增加");
					loggingModel.setTables("outsourcing_manage");
					loggingService.addLogging(loggingModel);
					map.put("success", true);
	    	 }else{
	    		    map.put("fail", false);
	    		    map.put("errmsg", "添加失败，请稍候再试或联系管理员");
	    	 }
	    	 return map;
	     }
	     
	        @RequestMapping("/updateOutsourcingManage")
			@ResponseBody
			public Map<String,Object> updateOutsourcingManage(HttpServletRequest request,OutsourcingManageModel outsourcingMange){
				Map<String,Object> map = new HashMap<String, Object>();
				UserModel user = (UserModel) request.getSession().getAttribute("user");
				outsourcingMange.setUpdateBy(user.getUserId());
				int result = outsourcingManageService.updateOutsourcingManage(outsourcingMange);
				if(result == 1){
					// 记录日志
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "修改外包管理:【" + outsourcingMange.getName() + "】");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(1);
					loggingModel.setEventName("修改");
					loggingModel.setTables("outsourcing_manage");
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
			public Map<String,Object> addComment(HttpServletRequest request,String outsourcingManageId,String comment){
				Map<String,Object> map = new HashMap<String, Object>();
				UserModel user = (UserModel) request.getSession().getAttribute("user");
				OutsourcingManageCommentModel outsourcingManageComment = new OutsourcingManageCommentModel();
				outsourcingManageComment.setComment(comment);
				outsourcingManageComment.setCritic(user.getChineseName());
				outsourcingManageComment.setOutsourcingManageId(Long.parseLong(outsourcingManageId));
				int result = outsourcingManageCommentService.addOutsourcingManageComment(outsourcingManageComment);
				if(result == 1){
					// 记录日志
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					OutsourcingManageModel om = new OutsourcingManageModel();
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "对【" + om.getName() + "】" + "进行了评论");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(5);
					loggingModel.setEventName("评论");
					loggingModel.setTables("outsourcing_manage_comment");
					loggingService.addLogging(loggingModel);
					map.put("success", true);
					outsourcingManageComment.setCreateTime(new Date());
					map.put("comment", outsourcingManageComment);
				}else {
					map.put("success", false);
				}
				return map;
			}
			@RequestMapping("/deleteOutsourcingManage")
			@ResponseBody
			public Map<String,Object> deleteOutsourcingManage(long id,HttpServletRequest request){
				Map<String,Object> map = new HashMap<String, Object>();
				int result = outsourcingManageService.deleteOutsourcingManage(id);
				if(result == 1){
					UserModel user = (UserModel) request.getSession().getAttribute("user");
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					OutsourcingManageModel outsourcingManage = outsourcingManageService.queryDetailById(id);
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "删除外包管理【" + outsourcingManage.getName() + "】");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(2);
					loggingModel.setEventName("删除");
					loggingModel.setTables("outsourcing_manage");
					loggingService.addLogging(loggingModel);
					map.put("result", result);
				}
				return map;
			}
			@RequestMapping("/export")
			public void export(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws WriteException{
				String sch = (String) session.getAttribute("search");
				List<OutsourcingManageModel> list = outsourcingManageService.getOutsourcingManage(null, null, "create_time", "desc", sch).getData();
				WritableWorkbook wb = null;
				OutputStream stream = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
				String fileName = "外包管理列表" + format.format(new Date()) + ".xls";
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
					WritableSheet sheet = wb.createSheet("外包管理列表", i++);
					Label label;
					label = new Label(ORIGIN_Y, ORIGIN_X, "外包管理列表", wcf_title);
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
					for(OutsourcingManageModel outsourcingManage : list){
						i++;
						label = new Label(ORIGIN_Y, ORIGIN_X + i, outsourcingManage.getName(), wcf1);
						sheet.addCell(label);
						
						label = new Label(ORIGIN_Y + 1, ORIGIN_X + i, outsourcingManage.getEmail(), wcf1);
						sheet.addCell(label);
						sheet.setColumnView(1, 18);
						
						label = new Label(ORIGIN_Y + 2, ORIGIN_X + i, outsourcingManage.getPhone(), wcf1);
						sheet.addCell(label);
						sheet.setColumnView(ORIGIN_Y + 2, 18);
						
						label = new Label(ORIGIN_Y + 3, ORIGIN_X + i, sdf.format(outsourcingManage.getCreateTime()), wcf1);
						sheet.addCell(label);
						sheet.setColumnView(ORIGIN_Y + 3, 18);
					}
					// 记录日志
					UserModel user = (UserModel) request.getSession().getAttribute("user");
					LoggingModel loggingModel = new LoggingModel();
					loggingModel.setOperator(user.getChineseName());
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String datetime = (date.format(new Date())).toString();
					loggingModel.setAction(user.getChineseName() + "于" + datetime + "导出了外包管理列表");
					loggingModel.setUserId(user.getUserId());
					loggingModel.setEventType(4);
					loggingModel.setEventName("导出");
					loggingModel.setTables("outsourcing_manage");
					loggingService.addLogging(loggingModel);
					wb.write();
					wb.close();
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
}

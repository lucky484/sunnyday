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
import com.softtek.pst.model.ProjectFinancialModel;
import com.softtek.pst.model.ProjectModel;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.service.ProjectFinancialService;
import com.softtek.pst.service.ProjectService;
import com.softtek.pst.util.Page;

@Controller
@RequestMapping("/projectsManagement/finance")
public class FinanceController {
	
	 private Logger logger = Logger.getLogger(FinanceController.class);
	 private final int ORIGIN_X = 0; // 报表中写入内容的起始横坐标，即excel的行
	 private final int ORIGIN_Y = 0; // 报表中写入内容的起始纵坐标，即excel的列
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private LoggingService loggingService;
	
	@Autowired
	private ProjectFinancialService projectFinancialService;
	
	@RequestMapping(value = "financeList")
	public String financeList(HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		return "finance/financeList";
	}

	@RequestMapping(value = "getFinances")
	@ResponseBody
	public Page<ProjectModel> getFinances(Integer start, Integer length, String column, String dir, String sch,
			HttpServletRequest request,HttpSession session) {
		if (sch != null && !sch.equals("")) {
			sch = "%" + sch + "%";
		} else {
			sch = "%%";
		}
		Page<ProjectModel> page = projectService.getProjectsForFinance(start, length, column, dir, sch);
		session.setAttribute("search", sch);
		return page;
	}

	@RequestMapping(value = "updateFinance")
	public ModelAndView updateFinance(HttpServletRequest request, Integer id) {
		request.setAttribute("url", request.getServletPath());
		ProjectModel project = projectService.getProjectById(id);
		return new ModelAndView("finance/updateFinance", "project", project);
	}
    
	@RequestMapping(value = "addFinanceReceive")
	public ModelAndView addFinanceReceive(HttpServletRequest request,Integer projectId) {
		request.setAttribute("url", request.getServletPath());
	//	request.setAttribute("projectId",projectId);
		return new ModelAndView("finance/addFinanceReceive","projectId",projectId);
	}
	
	@RequestMapping(value="addFinancePayment")
	public ModelAndView addFinancePayment(HttpServletRequest request,Integer projectId){
		request.setAttribute("url", request.getServletPath());
		return new ModelAndView("finance/addFinancePayment","projectId",projectId);
	}
	
	@RequestMapping(value="viewFinanceReceive")
	public ModelAndView viewFinanceReceive(HttpServletRequest request,Integer projectId){
		request.setAttribute("url", request.getServletPath());
		List<ProjectFinancialModel> list = projectFinancialService.queryFinancialReceive(projectId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		list.stream().forEach(a -> {
			a.setCreateTimeStr(sdf.format(a.getCreateTime()));
		});
		return new ModelAndView("finance/viewFinanceReceive","list",list);
	}
	
	@RequestMapping(value="viewFinancePayment")
	public ModelAndView viewFinancePayment(HttpServletRequest request,Integer projectId){
		request.setAttribute("url", request.getServletPath());
		List<ProjectFinancialModel> list = projectFinancialService.queryFinancialPayment(projectId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		list.stream().forEach(a -> {
			a.setCreateTimeStr(sdf.format(a.getCreateTime()));
		});
		return new ModelAndView("finance/viewFinancePayment","list",list);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, ProjectModel project) {
		Map<String, Object> result = new HashMap<>();
		int res = projectService.updateFinance(project);
		if (res == 1) {
			result.put("success", true);
			// 记录日志
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "更新项目【" + project.getProjectName() + "】财务信息");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(1);
			loggingModel.setEventName("增加");
			loggingModel.setTables("project");
			loggingService.addLogging(loggingModel);
		} else {
			result.put("success", false);
			result.put("errmsg", "更新项目财务信息失败，请稍候再试或联系管理员");
		}
		return result;
	}
	
	@RequestMapping("/addProjectFinancial")
	@ResponseBody
	public Map<String,Object> addProjectFinancial(HttpServletRequest request,ProjectFinancialModel projectFinancial){
		Map<String,Object> map = new HashMap<String, Object>();
		int result = projectFinancialService.addProjectFinancial(projectFinancial);
		if(result == 1){
			// 记录日志
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			ProjectModel project = projectService.getProjectById(projectFinancial.getProjectId());
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			if(projectFinancial.getReceive() > 0){
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "对项目:【" + project.getProjectName() + "】添加收款");
			}else{
				loggingModel.setAction(user.getChineseName() + "于" + datetime + "对项目:【" + project.getProjectName() + "】添加付款");
			}
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(1);
			loggingModel.setEventName("增加");
			loggingModel.setTables("project_financial");
			loggingService.addLogging(loggingModel);
			map.put("success", true);
		}else{
			map.put("success", false);
			map.put("errmsg", "添加失败，请稍候再试或联系管理员");
		}
		return map;
	}
	
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws WriteException{
		String sch = (String) session.getAttribute("search");
		List<ProjectModel> list = projectService.getProjectsForFinance(null, null, "create_time", "desc", sch).getData();
		WritableWorkbook wb = null;
		OutputStream stream = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
		String fileName = "财务管理列表" + format.format(new Date()) + ".xls";
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
			WritableSheet sheet = wb.createSheet("财务管理列表", i++);
			Label label;
			label = new Label(ORIGIN_Y, ORIGIN_X, "财务管理列表", wcf_title);
			sheet.addCell(label);
			sheet.setColumnView(ORIGIN_Y, 21);
			label = new Label(ORIGIN_Y, ORIGIN_X + 1, "项目编号", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 1, ORIGIN_X + 1, "项目名称", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 2, ORIGIN_X + 1, "项目经理", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 3, ORIGIN_X + 1, "客户经理", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 4, ORIGIN_X + 1, "开始时间", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 5, ORIGIN_X + 1, "结束时间", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 6, ORIGIN_X + 1, "总价", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 7, ORIGIN_X + 1, "已收款", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 8, ORIGIN_X + 1, "未收款", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 9, ORIGIN_X + 1, "外包款", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 10, ORIGIN_X + 1, "已付款", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 11, ORIGIN_X + 1, "未付款", wcf);
			sheet.addCell(label);
			for(ProjectModel project : list){
				i++;
				label = new Label(ORIGIN_Y, ORIGIN_X + i, project.getProjectCode(), wcf1);
				sheet.addCell(label);
				
				label = new Label(ORIGIN_Y + 1, ORIGIN_X + i, project.getProjectName(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(1, 18);
				
				label = new Label(ORIGIN_Y + 2, ORIGIN_X + i, project.getProjectManager(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 2, 18);
				
				label = new Label(ORIGIN_Y + 3, ORIGIN_X + i, project.getImplementManager(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 3, 18);
				
				label = new Label(ORIGIN_Y + 4, ORIGIN_X + i,sdf.format(project.getStartTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 4, 18);

				label = new Label(ORIGIN_Y + 5, ORIGIN_X + i, sdf.format(project.getLeadTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 5, 18);
				
				label = new Label(ORIGIN_Y + 6, ORIGIN_X + i,"￥" + String.valueOf(project.getCrTotal()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 6, 18);
				
				label = new Label(ORIGIN_Y + 7, ORIGIN_X + i, "￥" + String.valueOf(project.getReceive()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 7, 18);
				
				label = new Label(ORIGIN_Y + 8, ORIGIN_X + i, "￥" + String.valueOf(project.getUnreceive()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 8, 18);
				
				label = new Label(ORIGIN_Y + 9, ORIGIN_X + i, "￥" + String.valueOf(project.getOutsourcingQuotation()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 9, 18);
				
				label = new Label(ORIGIN_Y + 10, ORIGIN_X + i, "￥" + String.valueOf(project.getPayment()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 10, 18);
				
				label = new Label(ORIGIN_Y + 11, ORIGIN_X + i, "￥" + String.valueOf(project.getUnpayment()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 11, 18);
			}
			// 记录日志
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "导出了财务管理列表");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(4);
			loggingModel.setEventName("导出");
			loggingModel.setTables("project");
			loggingService.addLogging(loggingModel);
			wb.write();
			wb.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

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

import com.softtek.pst.model.CustomerModel;
import com.softtek.pst.model.LoggingModel;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.CustomerService;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.util.Page;

@Controller
@RequestMapping("/projectsManagement/customers")
public class CustomerController {

	private Logger logger = Logger.getLogger(CustomerController.class);
	private final int ORIGIN_X = 0; // 报表中写入内容的起始横坐标，即excel的行
	private final int ORIGIN_Y = 0; // 报表中写入内容的起始纵坐标，即excel的列

	@Autowired
	private CustomerService customerService;
	@Autowired
	private LoggingService loggingService;

	@RequestMapping(value = "customerList")
	public String getcustomerList(HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		return "customer/customerList";
	}

	@RequestMapping(value = "addCustomer")
	public String addCustomer(HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		return "customer/addCustomer";
	}

	@RequestMapping(value = "editCustomer")
	public ModelAndView editCustomer(long customerId, HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		CustomerModel cm = customerService.getCustomerById(customerId);
		return new ModelAndView("customer/editCustomer", "cm", cm);
	}

	@RequestMapping(value = "viewCustomer")
	public ModelAndView viewCustomer(long customerId, HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		CustomerModel cm = customerService.getCustomerById(customerId);
		return new ModelAndView("customer/viewCustomer", "cm", cm);
	}

	@RequestMapping("getCustomerInfo")
	@ResponseBody
	public CustomerModel getCustomerInfo(long customerId) {
		return customerService.getCustomerById(customerId);
	}

	@RequestMapping("deleteCustomer")
	@ResponseBody
	public int deleteCustomer(long customerId, HttpServletRequest request) {
		// 记录日志
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		LoggingModel loggingModel = new LoggingModel();
		loggingModel.setOperator(user.getChineseName());
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datetime = (date.format(new Date())).toString();
		CustomerModel cm = customerService.getCustomerById(customerId);
		loggingModel.setAction(user.getChineseName() + "于" + datetime + "删除了一条客户【" + cm.getCustomerName() + "】");
		loggingModel.setUserId(user.getUserId());
		loggingModel.setEventType(2);
		loggingModel.setEventName("删除");
		loggingModel.setTables("customer");
		int result = customerService.deleteCustomer(customerId);
		if (result == 1) {
			loggingService.addLogging(loggingModel);
			logger.info("客户删除成功,ID:" + customerId);
		} else {
			logger.info("客户删除失败,ID:" + customerId);
		}
		return result;
	}

	@RequestMapping("insertCustomer")
	@ResponseBody
	public Map<String,Object> insert(CustomerModel customerModel, HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		customerModel.setCreatorId(user.getUserId());
		customerModel.setUpdateBy(user.getUserId());
		if (customerService.addCustomer(customerModel) == 1) {
			// 记录日志
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "新增了名为【" + customerModel.getCustomerName() + "】的客户");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(1);
			loggingModel.setEventName("增加");
			loggingModel.setTables("customer");
			loggingService.addLogging(loggingModel);
			logger.info("添加客户成功，名称：" + customerModel.getCustomerName());
			map.put("result", 1);
		} else {
			logger.info("添加客户失败，名称：" + customerModel.getCustomerName());
			map.put("result", 0);
			map.put("errmsg", "新增客户失败");
		}
        return map;
	}

	@RequestMapping("updateCustomer")
	@ResponseBody
	public Map<String,Object> updateCustomer(CustomerModel customerModel, HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		CustomerModel existCustomer = customerService.getCustomerById(customerModel.getCustomerId());
		existCustomer.setCompanyName(customerModel.getCompanyName());
		existCustomer.setCustomerTitle(customerModel.getCustomerTitle());
		existCustomer.setCustomerName(customerModel.getCustomerName());
		existCustomer.setCustomerPhone(customerModel.getCustomerPhone());
		existCustomer.setCompanyPhone(customerModel.getCompanyPhone());
		existCustomer.setCompanyAddress(customerModel.getCompanyAddress());
		existCustomer.setBank(customerModel.getBank());
		existCustomer.setPaymentAccount(customerModel.getPaymentAccount());
		existCustomer.setCompanyIdNumber(customerModel.getCompanyIdNumber());
		existCustomer.setUpdateTime(customerModel.getUpdateTime());
		customerModel.setUpdateBy(user.getUserId());
		int result = customerService.editCustomer(customerModel);
		if (result == 1) {
			// 记录日志
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "修改了名为【" + customerModel.getCustomerName() + "】的客户");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(3);
			loggingModel.setEventName("修改");
			loggingModel.setTables("customer");
			loggingService.addLogging(loggingModel);
			logger.info("修改成功");
			map.put("result",result);
		} else {
			map.put("result",result);
			map.put("errmsg","修改客户失败");
			logger.info("修改失败");
		}
		return map;
	}

	@RequestMapping(value = "getCustomer")
	@ResponseBody
	public Page<CustomerModel> getCustomer(Integer start, Integer length, String column, String dir, String sch, HttpServletRequest request,HttpSession session) {
		if (sch != null && !sch.equals("")) {
			sch = "%" + sch + "%";
		}
		Page<CustomerModel> page = customerService.getCustomer(start, length, column, dir, sch);
		session.setAttribute("search", sch);
		return page;
	}
	
	@RequestMapping(value="export")
	public void export(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws WriteException{
		String sch = (String) session.getAttribute("search");
		List<CustomerModel> list = customerService.getCustomer(null, null, "add_time", "desc", sch).getData();
		WritableWorkbook wb = null;
		OutputStream stream = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
		String fileName = "客户列表" + format.format(new Date()) + ".xls";
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
			WritableSheet sheet = wb.createSheet("客户列表", i++);
			Label label;
			label = new Label(ORIGIN_Y, ORIGIN_X, "客户列表", wcf_title);
			sheet.addCell(label);
			sheet.setColumnView(ORIGIN_Y, 21);
			label = new Label(ORIGIN_Y, ORIGIN_X + 1, "公司名称", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 1, ORIGIN_X + 1, "客户名称", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 2, ORIGIN_X + 1, "客户头衔", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 3, ORIGIN_X + 1, "客户电话", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 4, ORIGIN_X + 1, "添加时间", wcf);
			sheet.addCell(label);
			for(CustomerModel customer : list){
				i++;
				label = new Label(ORIGIN_Y, ORIGIN_X + i, customer.getCompanyName(), wcf1);
				sheet.addCell(label);
				
				label = new Label(ORIGIN_Y + 1, ORIGIN_X + i, customer.getCustomerNameShort(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(1, 18);
				
				label = new Label(ORIGIN_Y + 2, ORIGIN_X + i, customer.getCustomerTitle(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 2, 18);
				
				label = new Label(ORIGIN_Y + 3, ORIGIN_X + i, customer.getCustomerPhone(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 3, 18);
				
				label = new Label(ORIGIN_Y + 4, ORIGIN_X + i, sdf.format(customer.getAddTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 4, 18);
			}
			// 记录日志
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "导出了客户列表");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(4);
			loggingModel.setEventName("导出");
			loggingModel.setTables("customer");
			loggingService.addLogging(loggingModel);
			wb.write();
			wb.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

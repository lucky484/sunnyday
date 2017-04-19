package com.softtek.pst.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.softtek.pst.model.Role;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.LoggingService;
import com.softtek.pst.service.RoleService;
import com.softtek.pst.service.UserService;
import com.softtek.pst.util.Page;

@Controller
@RequestMapping("/usersManagement/users")
public class UserController {
     
	 private Logger logger = Logger.getLogger(UserController.class);
	 private final int ORIGIN_X = 0; // 报表中写入内容的起始横坐标，即excel的行
	 private final int ORIGIN_Y = 0; // 报表中写入内容的起始纵坐标，即excel的列
	
	
	@RequestMapping("login")
	public String login() {
		return "user/login";
	}

	@RequestMapping("userList")
	public String getUserList(HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		return "user/userList";
	}

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private LoggingService loggingService;

	@RequestMapping("addUserList")
	public ModelAndView addUser(HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		List<Role> list = roleService.getRole();
		return new ModelAndView("user/addUser", "roles", list);
	}
	

	
	@RequestMapping(value = "editPsd")
	public ModelAndView editPsd(HttpServletRequest request){
		UserModel um = (UserModel)request.getSession().getAttribute("user");
		request.setAttribute("url", request.getServletPath());
		return new ModelAndView("user/editPsd", "um", um);
	}

	@RequestMapping("editUserList")
	public ModelAndView editUser(long userId, HttpServletRequest request) {
		request.setAttribute("url", request.getServletPath());
		List<Role> list = roleService.getRole();
		UserModel um = userService.getUserById(userId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("roles", list);
		mav.addObject("um", um);
		mav.setViewName("user/editUser");
		return mav;
	}

	@RequestMapping(value = "getUsers")
	@ResponseBody
	public Page<UserModel> getUsers(Integer start, Integer length, String column, String dir, String sch, HttpServletRequest request,HttpSession session) {
		String search = sch;
		if (search != null && !search.equals("")) {
			search = "%" + sch + "%";
		} else {
			search = "%%";
		}
		Page<UserModel> page = userService.getUsers(start, length, column, dir, search);
		session.setAttribute("search", search);
		return page;
	}
	
	//修改密码时判断输入的初始密码是否和数据库的初始密码相同
	@RequestMapping(value = "checkPsd")
	@ResponseBody
	public boolean checkPsd(HttpServletRequest request) {
		MessageDigest md5 = null;
		try {
			 md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		String password = request.getParameter("initPassword");
		password = byte2hexString(md5.digest(password.getBytes()));
		String useId = request.getParameter("userId");
		long userId = Long.valueOf(useId);
		UserModel um = userService.getUserById(userId);
		if (password.equals(um.getUserPassword())) {
			return true;
		} else {
			return false;
		}
	}

	//判断用户名是否与数据库已有相同
	@RequestMapping(value = "checkUserName")
	@ResponseBody
	public boolean checkUserName(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		List<UserModel> list = userService.getAllUser();
		int checkNum = 0;
		for (UserModel um : list) {
			checkNum = 1;
			if (userName.trim().equals(um.getUserName())) {
				checkNum = 0;
				break;
			}
		}
		if (checkNum == 0) {
			return false;
		} else {
			return true;
		}
	}

	//判断用户名是否与数据库已有相同，但允许不修改用户名
	@RequestMapping(value = "checkEditUserName")
	@ResponseBody
	public boolean checkEditUserName(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String useId = request.getParameter("userId");
		long userId = Long.valueOf(useId);
		String un = userService.getUserById(userId).getUserName();
		List<UserModel> list = userService.getAllUser();
		int checkNum = 0;
		if (userName.equals(un)) {
			checkNum = 1;
		} else {
			for (UserModel um : list) {
				checkNum = 1;
				if (userName.equals(um.getUserName())) {
					checkNum = 0;
					break;
				}
			}
		}
		if (checkNum == 0) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = "addUser")
	@ResponseBody
	public Map<String, Object> addUser(UserModel um, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		MessageDigest md5 = null;
		try {
			 md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
	    String password = byte2hexString(md5.digest(um.getUserPassword().getBytes()));
	    um.setUserPassword(password);
		if (userService.addUser(um) == 1) {
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "新增了用户【" + user.getUserName() + "】");
			loggingModel.setOperator(user.getChineseName());
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(1);
			loggingModel.setEventName("新增");
			loggingModel.setTables("user");
			loggingService.addLogging(loggingModel);
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result;
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public int deleteUser(long userId, HttpServletRequest request) {
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		int result = userService.deleteUser(userId);
		if (result == 1) {
			LoggingModel loggingModel = new LoggingModel();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "删除了用户【" + user.getUserName() + "】");
			loggingModel.setOperator(user.getChineseName());
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(2);
			loggingModel.setEventName("删除");
			loggingModel.setTables("user");
			loggingService.addLogging(loggingModel);
		} 
		return result;
	}

	@RequestMapping(value = "updateUser")
	@ResponseBody
	public Map<String, Object> updateUser(UserModel um, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		MessageDigest md5 = null;
		try {
			 md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
	    String password = byte2hexString(md5.digest(um.getUserPassword().getBytes()));
	    um.setUserPassword(password);
		if (userService.updateUser(um) == 1) {
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "修改了用户【" + user.getUserName() + "】");
			loggingModel.setOperator(user.getChineseName());
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(3);
			loggingModel.setEventName("修改");
			loggingModel.setTables("user");
			loggingService.addLogging(loggingModel);
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result;
	}
	
	@RequestMapping(value = "updatePsd")
	@ResponseBody
	public Map<String, Object> editPsd(UserModel um, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		MessageDigest md5 = null;
		try {
			 md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		String password = byte2hexString(md5.digest(um.getUserPassword().getBytes()));
		um.setUserPassword(password);
		if (userService.updateUser(um) == 1) {
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "修改了用户【" + user.getUserName() + "】的登录密码");
			loggingModel.setOperator(user.getChineseName());
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(7);
			loggingModel.setEventName("修改密码");
			loggingModel.setTables("user");
			loggingService.addLogging(loggingModel);
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result;
	}

	@RequestMapping("tologin")
	@ResponseBody
	public String login(String username, String password, HttpServletRequest request) {
		MessageDigest md5 = null;
		try {
			 md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		password = byte2hexString(md5.digest(password.getBytes()));
		UserModel user = userService.login(username, password);
		if (user == null) {
			return "false";
		} else {
			if(!user.getChineseName().equals("") || user.getChineseName() != null){
				user.setShowInfo(user.getChineseName());
			}else if(!user.getEnglishName().equals("") || user.getEnglishName() != null){
				user.setShowInfo(user.getEnglishName());
			}else{
				user.setShowInfo(user.getUserName());
			}
			request.getSession().setAttribute("user", user);
			List<String> authorities = new ArrayList<>();
			List<String> authorityUrls = new ArrayList<>();
//			List<String> authorities = user.getRole().getRoleAuthorities().stream().map(a -> a.getAuthority().getAuthorityName()).collect(Collectors.toList());
//			List<String> authorityUrls = user.getRole().getRoleAuthorities().stream().map(a -> a.getAuthority().getAuthorityUrl()).collect(Collectors.toList());
			List<UserModel> list = userService.getAuthorityByUserId(user.getUserId());
			list.stream().forEach(a -> {
				authorities.add(a.getAuthorityName());
				authorityUrls.add(a.getAuthorityUrl());
			});
//			user.getRole().getRoleAuthorities().stream().forEach(a -> {
//				authorities.add(a.getAuthority().getAuthorityName());
//				authorityUrls.add(a.getAuthority().getAuthorityUrl());
//			});
			// 将当前用户的所有权限名放入authorities集合中
			request.getSession().setAttribute("authorities", authorities.toString());
			// 将当前用户的所有权限URL放入authorityUrls集合中
			request.getSession().setAttribute("authorityUrls", authorityUrls);
			LoggingModel loggingModel = new LoggingModel();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			// 设置用户最后登录时间
			String userName = user.getUserName();
			Date lastLoginTime = new Date();
			userService.updateLastLoginTime(lastLoginTime, userName);
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "登录系统");
			loggingModel.setOperator(user.getChineseName());
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(6);
			loggingModel.setEventName("登录");
			loggingModel.setTables("user");
			loggingService.addLogging(loggingModel);
			return "true";
		}
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		request.getSession().setAttribute("authorities", null);
		return "redirect:login.do";
	}
	
	private static String byte2hexString(byte[] bytes) {
          StringBuffer bf = new StringBuffer(bytes.length * 2);
          for (int i = 0; i < bytes.length; i++) {
              if ((bytes[i] & 0xff) < 0x10) {
                  bf.append("T0");
              }
              bf.append(Long.toString(bytes[i] & 0xff, 16));
          }
          return bf.toString();
      }
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws WriteException{
		String sch = (String) session.getAttribute("search");
		List<UserModel> list = userService.getUsers(null, null, "create_time", "desc", sch).getData();
		WritableWorkbook wb = null;
		OutputStream stream = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
		String fileName = "用户列表" + format.format(new Date()) + ".xls";
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
			WritableSheet sheet = wb.createSheet("用户列表", i++);
			Label label;
			label = new Label(ORIGIN_Y, ORIGIN_X, "用户列表", wcf_title);
			sheet.addCell(label);
			sheet.setColumnView(ORIGIN_Y, 21);
			label = new Label(ORIGIN_Y, ORIGIN_X + 1, "用户名", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 1, ORIGIN_X + 1, "密码", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 2, ORIGIN_X + 1, "最后登录时间", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 3, ORIGIN_X + 1, "创建时间", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 4, ORIGIN_X + 1, "所属角色", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 5, ORIGIN_X + 1, "英文名", wcf);
			sheet.addCell(label);
			label = new Label(ORIGIN_Y + 6, ORIGIN_X + 1, "中文名", wcf);
			sheet.addCell(label);
			for(UserModel user : list){
				i++;
				label = new Label(ORIGIN_Y, ORIGIN_X + i, user.getUserName(), wcf1);
				sheet.addCell(label);
				
				label = new Label(ORIGIN_Y + 1, ORIGIN_X + i, user.getUserPassword(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(1, 18);
				
				label = new Label(ORIGIN_Y + 2, ORIGIN_X + i, sdf.format(user.getLastLoginTime()), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 2, 18);
				
				label = new Label(ORIGIN_Y + 3, ORIGIN_X + i, user.getCreateTime() != null ? sdf.format(user.getCreateTime()) : "", wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 3, 18);
				
				label = new Label(ORIGIN_Y + 4, ORIGIN_X + i,user.getRole().getRoleDescription(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 4, 18);

				label = new Label(ORIGIN_Y + 5, ORIGIN_X + i, user.getEnglishName(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 5, 18);
				
				label = new Label(ORIGIN_Y + 6, ORIGIN_X + i, user.getChineseName(), wcf1);
				sheet.addCell(label);
				sheet.setColumnView(ORIGIN_Y + 6, 18);
				
			}
			// 记录日志
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			LoggingModel loggingModel = new LoggingModel();
			loggingModel.setOperator(user.getChineseName());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String datetime = (date.format(new Date())).toString();
			loggingModel.setAction(user.getChineseName() + "于" + datetime + "导出了用户列表");
			loggingModel.setUserId(user.getUserId());
			loggingModel.setEventType(4);
			loggingModel.setEventName("导出");
			loggingModel.setTables("user");
			loggingService.addLogging(loggingModel);
			wb.write();
			wb.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

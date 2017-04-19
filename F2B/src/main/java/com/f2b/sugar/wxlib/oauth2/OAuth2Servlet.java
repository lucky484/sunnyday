package com.f2b.sugar.wxlib.oauth2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;
import com.f2b.sugar.wxlib.ParamesAPI.WeixinUtil;

import java.io.IOException;
import java.io.PrintWriter;

public class OAuth2Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String code = request.getParameter("code");
		if (!"authdeny".equals(code)) {
			String access_token = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret).getToken();
			// agentid 跳转链接时所在的企业应用ID
			// 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
			String UserID = OAuth2Core.getUserID(access_token, code, "您的agentid");
			request.setAttribute("UserID", UserID);
		} else {
			out.print("授权获取失败，至于为什么，自己找原因。。。");
		}
		// 跳转到index.jsp
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}

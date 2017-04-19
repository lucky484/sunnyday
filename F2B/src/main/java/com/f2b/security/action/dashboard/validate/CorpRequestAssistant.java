package com.f2b.security.action.dashboard.validate;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.f2b.security.action.dashboard.validate.service.CoreServiceAssistant;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;
import com.f2b.sugar.wxlib.encryption.AesException;
import com.f2b.sugar.wxlib.encryption.WXBizMsgCrypt;

/**
 * Author: 居泽平  Date: 14-11-2, 15:40
 */
@Controller
public class CorpRequestAssistant {

	private static final Logger logger = LoggerFactory.getLogger(CorpRequestAssistant.class);

	@Autowired
	private CoreServiceAssistant coreServiceAssistant;

    //	@RequestMapping(value = "/corp/assistant", method = RequestMethod.POST)
    //	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //
    //		//################ 将请求、响应的编码均设置为UTF-8（防止中文乱码） ################
    //		request.setCharacterEncoding("UTF-8");
    //		response.setCharacterEncoding("UTF-8");
    //
    //		//################ 微信加密签名 ################
    //		String msg_signature = request.getParameter("msg_signature");
    //		logger.info(String.format("msg_signature:[%s]", msg_signature));
    //
    //		//################ 时间戳 ################
    //		String timestamp = request.getParameter("timestamp");
    //		logger.info(String.format("timestamp:[%s]", timestamp));
    //
    //		//################ 随机数 ################
    //		String nonce = request.getParameter("nonce");
    //		logger.info(String.format("nonce:[%s]", nonce));
    //
    //		//################ 从请求中读取整个post数据 ################
    //		InputStream inputStream = request.getInputStream();
    //
    //		//################ commons.io.jar方法 ################
    //		String Post = IOUtils.toString(inputStream, "UTF-8");
    //		logger.debug(String.format("Post打印结果:[%s]", Post));
    //
    //		try {
    //            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(ParamesAPI.token, ParamesAPI.encodingAESKey, ParamesAPI.appId);
    //
    //			//################ 解密消息 ################
    //			String Msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, Post);
    //			logger.debug(String.format("Msg打印结果:[%s]", Msg));
    //
    //			//################ 调用核心业务类接收消息、处理消息 ################
    //			String respMessage = coreServiceAssistant.processRequest(request, Msg);
    //			logger.debug(String.format("respMessage打印结果:[%s]", respMessage));
    //
    //			//################ 加密回复消息 ################
    //			String encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
    //			logger.debug(String.format("encryptMsg打印结果:[%s]", encryptMsg));
    //			//################ 流返回输出 ################
    //			PrintWriter out = response.getWriter();
    //			out.print(encryptMsg);
    //			out.close();
    //		} catch (AesException ex) {
    //			logger.error("出错", ex);
    //		}
    //	}
	
	@RequestMapping(value = "/public/assistant", method = RequestMethod.POST)
	public void doPost2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//################ 将请求、响应的编码均设置为UTF-8（防止中文乱码） ################
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		//################ 微信加密签名 ################
		String msg_signature = request.getParameter("msg_signature");
		logger.info(String.format("msg_signature:[%s]", msg_signature));

		//################ 时间戳 ################
		String timestamp = request.getParameter("timestamp");
		logger.info(String.format("timestamp:[%s]", timestamp));

		//################ 随机数 ################
		String nonce = request.getParameter("nonce");
		logger.info(String.format("nonce:[%s]", nonce));

		//################ 从请求中读取整个post数据 ################
		InputStream inputStream = request.getInputStream();

		//################ commons.io.jar方法 ################
		String Post = IOUtils.toString(inputStream, "UTF-8");
		logger.debug(String.format("Post打印结果:[%s]", Post));
		
		// 是哪种加密模式就用哪种解决方案
		String encryptType = request.getParameter("encrypt_type");

		if("aec".equals(encryptType)){
    		try {
                WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(ParamesAPI.token, ParamesAPI.encodingAESKey, ParamesAPI.appId);
    
    			//################ 解密消息 ################
    			String Msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, Post);
    			logger.debug(String.format("Msg打印结果:[%s]", Msg));
    
    			//################ 调用核心业务类接收消息、处理消息 ################
    			String respMessage = coreServiceAssistant.processRequest2(request, Msg);
    			logger.debug(String.format("respMessage打印结果:[%s]", respMessage));
    
    			//################ 加密回复消息 ################
    			String encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
    			logger.debug(String.format("encryptMsg打印结果:[%s]", encryptMsg));
    			//################ 流返回输出 ################
    			PrintWriter out = response.getWriter();
    			out.print(encryptMsg);
    			out.close();
    		} catch (AesException ex) {
    			logger.error("出错", ex);
    		}
		}else{
		  //################ 调用核心业务类接收消息、处理消息 ################
            String respMessage = coreServiceAssistant.processRequest2(request, Post);
            logger.debug(String.format("respMessage打印结果:[%s]", respMessage));

            //################ 加密回复消息 ################
            logger.debug(String.format("encryptMsg打印结果:[%s]", respMessage));
            //################ 流返回输出 ################
            PrintWriter out = response.getWriter();
            out.print(respMessage);
            out.close();
		}
	}

    //	/**
    //	 * 企业号校验
    //	 * @param request
    //	 * @param response
    //	 * @throws ServletException
    //	 * @throws IOException
    //	 */
    //	@RequestMapping(value = "/corp/assistant", method = RequestMethod.GET)
    //	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //
    //		logger.info(String.format("RequestURL:[%s]", request.getRequestURL()));
    //
    //		//################ 微信加密签名 ################
    //		String msg_signature = request.getParameter("msg_signature");
    //		logger.info(String.format("msg_signature:[%s]", msg_signature));
    //
    //		//################ 时间戳 ################
    //		String timestamp = request.getParameter("timestamp");
    //		logger.info(String.format("timestamp:[%s]", timestamp));
    //
    //		//################ 随机数 ################
    //		String nonce = request.getParameter("nonce");
    //		logger.info(String.format("nonce:[%s]", nonce));
    //
    //		//################ 随机字符串 ################
    //		String echostr = request.getParameter("echostr");
    //		logger.info(String.format("echostr:[%s]", echostr));
    //
    //		try {
    //			//################ 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败 ################
    //            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(ParamesAPI.token, ParamesAPI.encodingAESKey, ParamesAPI.appId);
    //
    //			//################ 验证URL函数 ################
    //			String result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
    //			logger.info(String.format("Result:[%s]", result));
    //
    //			//################ 流返回输出 ################
    //			PrintWriter out = response.getWriter();
    //			out.print(result);
    //			out.close();
    //		} catch (AesException ex) {
    //			logger.error("解密出错", ex);
    //		}
    //	}
	/**
	 * 公众号校验，参数名和企业号不一样
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/public/assistant", method = RequestMethod.GET)
	protected void doGet2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info(String.format("RequestURL:[%s]", request.getRequestURL()));
		
		//################ 微信加密签名 ################
		String signature = request.getParameter("signature");
		logger.info(String.format("signature:[%s]", signature));
		
		//################ 时间戳 ################
		String timestamp = request.getParameter("timestamp");
		logger.info(String.format("timestamp:[%s]", timestamp));
		
		//################ 随机数 ################
		String nonce = request.getParameter("nonce");
		logger.info(String.format("nonce:[%s]", nonce));
		
		//################ 随机字符串 ################
		String echostr = request.getParameter("echostr");
		logger.info(String.format("echostr:[%s]", echostr));
		
		try {
			//################ 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败 ################
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(ParamesAPI.token, ParamesAPI.encodingAESKey, ParamesAPI.appId);
			
			//################ 验证URL函数 ################
			String result = wxcpt.VerifyURLForPublic(signature, timestamp, nonce, echostr);
			logger.info(String.format("Result:[%s]", result));
			
			//################ 流返回输出 ################
			PrintWriter out = response.getWriter();
			out.print(result);
			out.close();
		} catch (AesException ex) {
			logger.error("解密出错", ex);
		}
	}
}

package com.f2b2c.eco.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.f2b2c.eco.share.dto.DTOResult;
import com.f2b2c.eco.status.MessageType;

/**
 * 用于显示和传递消息
 * @author color.wu
 *
 */
public class BaseController {
	/** session message key. */
	public static final String MSG_KEY = "msg";

	/** session message type. */
	public static final String MSG_TYPE = "type";
	
	
	/**
	 * 不需要国际化的消息
	 * @param type MessageType
	 * @param message Message Key
	 * @param args Message arguments
	 * @param content JsonResult Content
	 * @return JsonResult Object
	 */
	protected DTOResult createDTOResultWithouti18n(MessageType type, String message, Object[] args, Object content) {
		return new DTOResult(type, String.format(message, args), content);
	}
	
	/**
	 * Try to resolve the message and add to the flash attributes. without i18n
	 * @param attrs
	 * @param msg
	 * @param type
	 */
	protected void addFlashMessageWithoutI18n(RedirectAttributes attrs, String msg,MessageType type){
		attrs.addFlashAttribute(MSG_KEY, msg);
		attrs.addFlashAttribute(MSG_TYPE, type.name());
	}
}

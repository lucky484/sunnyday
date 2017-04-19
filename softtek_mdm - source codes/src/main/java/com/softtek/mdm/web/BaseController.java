package com.softtek.mdm.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.web.http.JsonResult;
import com.softtek.mdm.web.http.MessageType;



public class BaseController {

	@Autowired
	private MessageSource messageSource;
	
	/** session message key. */
	public static final String MSG_KEY = "msg";

	/** session message type. */
	public static final String MSG_TYPE = "type";
	
	/**
	 * 
	 * @param type MessageType
	 * @param message Message Key
	 * @param args Message arguments
	 * @param content JsonResult Content
	 * @return JsonResult Object
	 */
	protected JsonResult createJsonResult(MessageType type, String message, Object[] args, Object content) {
		return new JsonResult(type, messageSource.getMessage(message, args, LocaleContextHolder.getLocale()), content);
	}
	
	/**
	 * Try to resolve the message and add to the flash attributes.
	 * 
	 * @param attrs RedirectAttributes
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 */
	protected void addFlashMeesage(RedirectAttributes attrs, String code) {
		addFlashMeesage(attrs, code, null, MessageType.info);
	}
	
	/**
	 * Try to resolve the message and add to the flash attributes.
	 * 
	 * @param attrs RedirectAttributes
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 * @param args Array of arguments that will be filled in for params within the message (params look like "{0}",
	 *            "{1,date}", "{2,time}" within a message),or {@code null} if none.
	 */
	protected void addFlashMeesage(RedirectAttributes attrs, String code, Object[] args) {
		addFlashMeesage(attrs, code, args, MessageType.info);
	}
	
	/**
	 * Try to resolve the message and add to the flash attributes.
	 * 
	 * @param attrs RedirectAttributes
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 * @param type message type {@link com.jumper.onlinerecruit.web.http.MessageType}
	 * 
	 */
	protected void addFlashMeesage(RedirectAttributes attrs, String code, MessageType type) {
		addFlashMeesage(attrs, code, null, type);
	}
	
	/**
	 * Try to resolve the message and add to the flash attributes.
	 * 
	 * @param attrs RedirectAttributes
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 * @param type message type {@link com.jumper.onlinerecruit.web.http.MessageType}
	 * @param args Array of arguments that will be filled in for params within the message (params look like "{0}",
	 *            "{1,date}", "{2,time}" within a message),or {@code null} if none.
	 */
	protected void addFlashMeesage(RedirectAttributes attrs, String code, Object[] args, MessageType type) {
		String msg = messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
		attrs.addFlashAttribute(MSG_KEY, msg);
		attrs.addFlashAttribute(MSG_TYPE, type.name());
	}
	
	/**
	 * Try to resolve the message and add to the modelMap.
	 * 
	 * @param modelMap Model
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 */
	protected void addMessage(Model modelMap, String code) {
		addMessage(modelMap, code, null, MessageType.info);
	}
	
	/**
	 * Try to resolve the message and add to the modelMap.
	 * 
	 * @param model Model
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 * @param args Array of arguments that will be filled in for params within the message (params look like "{0}",
	 *            "{1,date}", "{2,time}" within a message),or {@code null} if none.
	 */
	protected void addMessage(Model model, String code, Object[] args) {
		addMessage(model, code, args, MessageType.info);
	}
	
	/**
	 * Try to resolve the message and add to the modelMap.
	 * 
	 * @param model Model
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 * @param type message type {@link com.jumper.onlinerecruit.web.http.MessageType}
	 * 
	 */
	protected void addMessage(Model model, String code, MessageType type) {
		addMessage(model, code, null, type);
	}
	
	/**
	 * Try to resolve the message and add to the modelMap.
	 * 
	 * @param model Model
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 * @param type message type {@link com.jumper.onlinerecruit.web.http.MessageType}
	 * @param args Array of arguments that will be filled in for params within the message (params look like "{0}",
	 *            "{1,date}", "{2,time}" within a message),or {@code null} if none.
	 */
	protected void addMessage(Model model, String code, Object[] args, MessageType type) {
		String msg = messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
		model.addAttribute(MSG_KEY, msg);
		model.addAttribute(MSG_TYPE, type.name());
	}
	
	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}
	
	/**
	 * Description:super.从session对象中获取组织架构对象id
	 *
	 * @author brave.chen
	 * @param session session对象
	 * @return 组织机构id
	 */
	protected Integer getOrgIdBySession(HttpSession session)
	{
		Integer orgId = null;
		OrganizationModel organization = getOrgModelBySession(session);
		if (organization != null)
		{
			orgId = organization.getId();
		}
		
		return orgId;
	}
	
	/**
	 * Description:从session中获取组织机构对象
	 *
	 * @author brave.chen
	 * @param session session对象
	 * @return 组织机构对象
	 */
	protected OrganizationModel getOrgModelBySession(HttpSession session)
	{
		OrganizationModel organization = null;
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		
		if (orgObj != null)
		{
			organization = (OrganizationModel) orgObj;
		}
		
		return organization;
	}
	
	/**
	 * Description:从session中获取管理员对象
	 *
	 * @author brave.chen
	 * @param session session对象
	 * @return 管理员对象
	 */
	protected ManagerModel getManagerModelBySession(HttpSession session)
	{
		ManagerModel managerModel = null;
		Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		
		if (orgObj != null)
		{
			managerModel = (ManagerModel) orgObj;
		}
		
		return managerModel;
	}
}

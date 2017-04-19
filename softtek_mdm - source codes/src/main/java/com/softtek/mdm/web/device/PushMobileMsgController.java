
package com.softtek.mdm.web.device;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softtek.mdm.annotation.LocaleIn;
import com.softtek.mdm.helper.breadcrumb.Link;
import com.softtek.mdm.model.DeviceResultModel;
import com.softtek.mdm.model.MessageSendModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PushMobileMsgModel;
import com.softtek.mdm.model.PushMsgHistoryModel;
import com.softtek.mdm.service.PushMobileMsgService;
import com.softtek.mdm.status.SessionStatus;
import com.softtek.mdm.util.Constant;

/**
 * 推送消息控制层 date: Apr 13, 2016 8:57:29 AM
 *
 * @author brave.chen
 */
@Controller
@RequestMapping(value = "/terminal/picAndInfo")
public class PushMobileMsgController
{
	
	/**
     * 打出错误日志
     */
	private static Logger logger = LoggerFactory.getLogger(PushMobileMsgController.class);
	
	@Autowired
	private PushMobileMsgService pushMobileMsgService;
	
	/**
     * 国际化服务类
     */
    @Autowired
	private MessageSource messageSource;
	
//	/**
    // * 线程池对象
//	 */
//	@Autowired
//	private TaskExecutor taskExecutor;
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    public static final String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>

    public static final String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>

    public static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    public static Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
    public static Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
    public static Pattern p_html= Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
    
	/**
     * 用户角色主页
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Link(family = "institution", label = "institution.netbehavior.bwlist.index.label", parent = "institution.home.index.label", belong = "institution.netbehavior.index.label")
    @RequestMapping(method = RequestMethod.GET)
    public final String index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            Model model) throws IOException
    {
        return "/institution/push/pushmsg";
    }
    
    /**
     * ajax获取角色的分页
     * 
     * @param request
     *            请求对象
     * @param response
     *            响应对象
     * @param start
     *            起始页
     * @param length
     *            每页显示的记录数
     * @return 页面对象
     * @throws IOException
     *             异常
     */
    @RequestMapping(value = "/pages", method = RequestMethod.POST)
    @ResponseBody
    public Page queryByPage(HttpSession session, HttpServletRequest request, HttpServletResponse response,
            Integer start, Integer pageLength) throws IOException
    {
 
    	String msgTheme = StringUtils.trimToEmpty("msgTheme");
    	String content = StringUtils.trimToEmpty("content");
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pageNum", start == null ? 0 : start);
        paramMap.put("pageSize", pageLength == null ? 0 : pageLength);
        paramMap.put("msgTheme", msgTheme);
        paramMap.put("content", content);
        Integer organizationId = -1;
        Object orgObj = session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
        if (orgObj != null) {
        	OrganizationModel organization = (OrganizationModel)orgObj;
        	organizationId = organization.getId();
        }
        
        paramMap.put("orgId", organizationId);
        
        Page page = pushMobileMsgService.queryPushMsgListByParams(paramMap);
        
        return page;
    }
    
	@RequestMapping(value = "/viewMsgPage",method=RequestMethod.GET)
	public String viewMsgPage(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException
	{
		String msgId = StringUtils.trim(request.getParameter("msgId"));
		Long id = StringUtils.isEmpty(msgId) ? null : Long.valueOf(msgId);
		PushMobileMsgModel msgModel = pushMobileMsgService.queryPushMsgById(id);
		model.addAttribute("msgContent", msgModel == null ? "" : msgModel.getContent());
		return "viewpushmessage/pushmessage";
	}
	
	/**
     * 
     * @param userId
     * @param udId
     * @param offSet
     *            保存的是客户端目前存储的图文消息总数
     * @param pageSize
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/getMsgPageHistory",method=RequestMethod.GET)
	@ResponseBody
    public DeviceResultModel<Page> getMsgPageHistory(String userId, String udId, Integer offSet,
            Integer pageSize,
            HttpServletRequest request, HttpServletResponse response,@LocaleIn Locale localeLanguage) throws IOException
	{
        DeviceResultModel<Page> resultModel = new DeviceResultModel<Page>();
		
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("offSet", offSet==null?0:offSet);
        map.put("pageSize", Long.MAX_VALUE);
        map.put("userId", userId);
        Page messagesPages= pushMobileMsgService.find(map);
        int count=(int) messagesPages.getRecordsTotal();
        
        //pushMobileMsgService.selectPushHistory(userId, util.getStart(),pageSize);
        
		List<MessageSendModel> messageSendModels = generateToMsgSendModels((List<PushMsgHistoryModel>)messagesPages.getData());
        for (int i = 0; i < messageSendModels.size(); i++) {
            if (StringUtils.isNotEmpty(messageSendModels.get(i).getMessage())) {
                String contentStr = Html2Text(messageSendModels.get(i).getMessage());
                messageSendModels.get(i).setMessage(contentStr);
            }
        }
        Page p = new Page();
        p.setData(messageSendModels);
        p.setRecordsFiltered(count);
        p.setRecordsTotal(count);
		if (CollectionUtils.isEmpty(messageSendModels))
		{
            resultModel.setStatus(200);
            resultModel.setData(p);
            String msg = messageSource.getMessage("web.deivce.get.message.history.has.delete.null", null,
                    localeLanguage);
			resultModel.setMsg(msg);
		}
		else
		{
            resultModel.setData(p);
			resultModel.setStatus(200);
			String msg = messageSource.getMessage("web.deivce.get.message.history.get.success", null,localeLanguage);
			resultModel.setMsg(msg);
		}
		
		return resultModel;
	}

	private List<MessageSendModel> generateToMsgSendModels(List<PushMsgHistoryModel> pushMsgHisModels)
	{
		List<MessageSendModel> models = new ArrayList<MessageSendModel>();
		if (CollectionUtils.isNotEmpty(pushMsgHisModels))
		{
			for (PushMsgHistoryModel hisModel : pushMsgHisModels)
			{
				MessageSendModel msgSendModel = new MessageSendModel();
				PushMobileMsgModel pushMbMsgModel = hisModel.getPushMobileMsgModel();
				msgSendModel.setMessage_title(pushMbMsgModel.getMsgTheme());
                msgSendModel.setMessage(pushMbMsgModel.getContent());
				msgSendModel.setCreateTime(hisModel.getCreateTime());
				msgSendModel.setName(hisModel.getCreateUser());
				msgSendModel.setCreateDateStr(sdf.format(hisModel.getCreateTime()));
				msgSendModel.setMsgId(String.valueOf(pushMbMsgModel.getId()));
				String imgUrls = StringUtils.trimToEmpty(pushMbMsgModel.getPicUrl());
				String[] imgUrlArr = imgUrls.split(Constant.SplitSymbol.COMMA_SYMBOL);
				msgSendModel.setImgUrl(imgUrlArr[0]);
				models.add(msgSendModel);
			}
		}
		return models;
	}

    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Matcher m_script;
        java.util.regex.Matcher m_style;
        java.util.regex.Matcher m_html;
        try {
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        // 剔除空格行
        textStr = textStr.replaceAll("[ ]+", " ");
        textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        return textStr;// 返回文本字符串
    }

}


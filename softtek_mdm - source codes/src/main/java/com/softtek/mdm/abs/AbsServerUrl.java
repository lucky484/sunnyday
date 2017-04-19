package com.softtek.mdm.abs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.softtek.mdm.dao.CommandDao;
import com.softtek.mdm.model.Command;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.ios.plist.PlistUtil;
import jodd.util.StringUtil;

public abstract class AbsServerUrl
{
	/**
	 * 日志记录对象
	 */
	private static Logger logger = Logger.getLogger(AbsServerUrl.class);
	
	@Autowired
	private CommandDao commandDao;
	
	/**
	 * 处理空闲状态
	 * @param map 请求体中的参数map对象
	 * @return 处理结果
	 */
	/*@Deprecated
	public abstract String operateIdleStatus(Map<String, Object> map,HttpServletResponse response);*/
	
	
	/**
	 * 处理空闲状态
	 * @param map 请求体中的参数map对象
	 * @return 处理结果
	 */
	public abstract String operateIdleStatus(Map<String, Object> map);
	
	/**
	 * 处理消息处理成功的
	 * @param map 请求体中的参数map对象
	 * @return 处理结果
	 */
	public abstract String operateAcknowledgedStatus(Map<String, Object> map);
	
	/**
	 * 处理错误状态返回
	 * @param map 请求体中的参数map对象
	 */
	public void operateErrorStatus(Map<String, Object> map)
	{	
		String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		Command command = commandDao.selectByPrimaryKey(commandUUID);
		if(command!=null) {
			if(PlistUtil.RequestType.InstallApplication.equals(command.getCommand())) {
				String rejectionReason = (String) map.get(Constant.MobileConfig.REJECTION_REASON);
				updateCommand(commandUUID, Constant.Result.ERROR, rejectionReason);
			} else {
				updateCommand(commandUUID, Constant.Result.ERROR, Constant.getStatus.ERROR);
			}
		}
		logger.error("An error has occurred.");
	}

	/**
	 * 处理错误状态返回
	 * @param map 请求体中的参数map对象
	 */
	public void operateCommandFormatErrorStatus(Map<String, Object> map)
	{
		String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		Command command = commandDao.selectByPrimaryKey(commandUUID);
		if(command!=null) {
			updateCommand(commandUUID, Constant.Result.COMMAND_FORMAT_ERROR, Constant.getStatus.ERROR);
		}
		logger.error("A protocol error has occurred. The command may be malformed.");
	}
	
	/**
	 * 处理未即时生效的状态
	 * @param map 请求体中的参数map对象
	 */
	public void operateNotNowStatus(Map<String, Object> map)
	{
		String commandUUID = (String) map.get(Constant.MobileConfig.COMMAND_UUID);
		Command command = commandDao.selectByPrimaryKey(commandUUID);
		if(command!=null) {
			updateCommand(commandUUID, Constant.Result.NOTNOW, Constant.getStatus.NOTNOW);
		}
		logger.info("The device received the command, but cannot perform it at this time. It poll the server again in the future.");
	}
	
	
	
	/**
	 * 此方法 不允许再被使用
	 * 设置response内容，返回给MDM Server用于执行文件名称为filename的plist文件指令
	 * @param response
	 * @param filename 文件名
	 * @param plistStr plistXMLStr
	 */
	@Deprecated
	protected void setResponse(HttpServletResponse response,String filename,String plistStr){
		response.setHeader("content-type", "application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", String.format("attachment; filename=%s.plist", filename));
	    PrintWriter sos=null;
		try {
			sos = response.getWriter();
			sos.write(plistStr);
			sos.flush();
		} catch (IOException e) {
			logger.error("setResponse error cause:"+e.getMessage());
		} finally {
			sos.close();
		}
	}
	
	// 更新命令表
	private void updateCommand(String uuid, String doIt, String result) {
		Command command = commandDao.selectByPrimaryKey(uuid);
		if(StringUtil.isNotEmpty(uuid)){
			command.setDoit(doIt);
			command.setUpdateDate(new Date());
			command.setResult(result);
			commandDao.updateByPrimaryKeySelective(command);
		}
	}
}
